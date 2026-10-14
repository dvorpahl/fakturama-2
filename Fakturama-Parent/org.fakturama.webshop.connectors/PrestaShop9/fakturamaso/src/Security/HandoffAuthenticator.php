<?php
namespace FakturamaSso\Security;

use PrestaShopBundle\Security\Admin\UserTokenManager;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Core\Exception\AuthenticationException;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Security\Http\Authenticator\AbstractAuthenticator;
use Symfony\Component\Security\Http\Authenticator\Passport\Passport;

final class HandoffAuthenticator extends AbstractAuthenticator
{
    public function __construct(
        private readonly UrlGeneratorInterface $router,
        private readonly UserTokenManager $userTokenManager
    ) {
    }

    public function supports(Request $request): ?bool
    {
        return false;
    }

    public function authenticate(Request $request): Passport
    {
        throw new \LogicException('This authenticator is only used for a verified programmatic handoff.');
    }

    public function onAuthenticationSuccess(Request $request, TokenInterface $token, string $firewallName): ?Response
    {
        $productId = (int) $request->attributes->get('fakturama_sso_product_id');
        $url = $this->router->generate('admin_products_edit', [
            'productId' => $productId,
            '_token' => $this->userTokenManager->getSymfonyToken(),
        ]);
        $response = new RedirectResponse($url, Response::HTTP_FOUND);
        $response->headers->set('Cache-Control', 'no-store, private');
        $response->headers->set('Referrer-Policy', 'no-referrer');
        return $response;
    }

    public function onAuthenticationFailure(Request $request, AuthenticationException $exception): ?Response
    {
        return new Response('PrestaShop SSO authentication failed.', Response::HTTP_FORBIDDEN, [
            'Cache-Control' => 'no-store, private',
            'Referrer-Policy' => 'no-referrer',
        ]);
    }
}
