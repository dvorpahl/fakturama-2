<?php
namespace FakturamaSso\Controller\Admin;

use Db;
use Module;
use PrestaShopBundle\Controller\Admin\PrestaShopAdminController;
use PrestaShopBundle\Entity\Repository\EmployeeRepository;
use PrestaShopBundle\Security\Attribute\AdminSecurity;
use Symfony\Component\DependencyInjection\Attribute\Autowire;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Security\Http\Authentication\UserAuthenticatorInterface;
use FakturamaSso\Security\HandoffAuthenticator;

final class HandoffController extends PrestaShopAdminController
{
    private const MAX_CLOCK_SKEW_SECONDS = 90;

    public function __construct(
        private readonly EmployeeRepository $employees,
        private readonly HandoffAuthenticator $handoffAuthenticator,
        #[Autowire(service: 'security.authenticator.manager.main')]
        private readonly UserAuthenticatorInterface $authenticatorManager
    ) {
    }

    #[AdminSecurity('true')]
    public function handoffAction(Request $request): Response
    {
        if (!$request->isSecure()) {
            return $this->failure(Response::HTTP_FORBIDDEN, 'HTTPS is required.');
        }

        $productId = filter_var($request->query->get('productId'), FILTER_VALIDATE_INT, ['options' => ['min_range' => 1]]);
        $issuedAt = filter_var($request->query->get('iat'), FILTER_VALIDATE_INT);
        $nonce = (string) $request->query->get('nonce', '');
        $signature = (string) $request->query->get('signature', '');
        if (!$productId || !$issuedAt || !preg_match('/^[a-f0-9]{32}$/', $nonce) || !preg_match('/^[a-f0-9]{64}$/', $signature)) {
            return $this->failure(Response::HTTP_BAD_REQUEST, 'Invalid handoff request.');
        }
        if (abs(time() - $issuedAt) > self::MAX_CLOCK_SKEW_SECONDS) {
            return $this->failure(Response::HTTP_FORBIDDEN, 'Handoff request expired.');
        }

        /** @var \FakturamaSo|false $module */
        $module = Module::getInstanceByName('fakturamaso');
        $secret = $module ? $module->getSecret() : '';
        if (strlen($secret) !== 64 || !ctype_xdigit($secret)) {
            return $this->failure(Response::HTTP_SERVICE_UNAVAILABLE, 'Fakturama SSO is not configured.');
        }
        $canonical = "v1\n" . $productId . "\n" . $issuedAt . "\n" . $nonce;
        $expected = hash_hmac('sha256', $canonical, hex2bin($secret));
        if (!hash_equals($expected, $signature)) {
            return $this->failure(Response::HTTP_FORBIDDEN, 'Invalid handoff signature.');
        }
        if (!$this->consumeNonce($nonce, $issuedAt)) {
            return $this->failure(Response::HTTP_FORBIDDEN, 'Handoff request was already used.');
        }

        $employeeId = (int) \Configuration::get('FAKTURAMASO_EMPLOYEE_ID');
        $employee = $employeeId > 0 ? $this->employees->find($employeeId) : null;
        if (!$employee instanceof UserInterface || !$employee->isActive()) {
            return $this->failure(Response::HTTP_SERVICE_UNAVAILABLE, 'The configured service employee is unavailable.');
        }

        $request->attributes->set('fakturama_sso_product_id', (int) $productId);
        $response = $this->authenticatorManager->authenticateUser($employee, $this->handoffAuthenticator, $request);
        if (!$response instanceof Response) {
            return $this->failure(Response::HTTP_FORBIDDEN, 'PrestaShop did not establish the service employee session.');
        }
        $response->headers->set('Cache-Control', 'no-store, private');
        $response->headers->set('Referrer-Policy', 'no-referrer');
        return $response;
    }

    private function consumeNonce(string $nonce, int $issuedAt): bool
    {
        $database = Db::getInstance();
        $database->execute('DELETE FROM `' . _DB_PREFIX_ . 'fakturamaso_nonce` WHERE `issued_at` < ' . (int) (time() - 600));
        return $database->insert('fakturamaso_nonce', [
            'nonce_hash' => hash('sha256', $nonce),
            'issued_at' => $issuedAt,
        ]);
    }

    private function failure(int $status, string $message): Response
    {
        return new Response($message, $status, [
            'Cache-Control' => 'no-store, private',
            'Referrer-Policy' => 'no-referrer',
            'X-Content-Type-Options' => 'nosniff',
        ]);
    }
}
