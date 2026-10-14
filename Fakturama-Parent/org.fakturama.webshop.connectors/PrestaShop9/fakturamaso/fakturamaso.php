<?php
if (!defined('_PS_VERSION_')) {
    exit;
}

require_once __DIR__ . '/vendor/autoload.php';

class FakturamaSo extends Module
{
    const SECRET_KEY = 'FAKTURAMASO_SECRET_ENCRYPTED';
    const EMPLOYEE_KEY = 'FAKTURAMASO_EMPLOYEE_ID';

    public function __construct()
    {
        $this->name = 'fakturamaso';
        $this->tab = 'administration';
        $this->version = '1.0.2';
        $this->author = 'Fakturama';
        $this->need_instance = 0;
        $this->bootstrap = true;
        parent::__construct();
        $this->displayName = $this->trans('Fakturama SSO bridge', [], 'Modules.Fakturamaso.Admin');
        $this->description = $this->trans('Opens linked products from Fakturama through a short-lived signed back-office handoff.', [], 'Modules.Fakturamaso.Admin');
        $this->ps_versions_compliancy = ['min' => '9.2.0', 'max' => _PS_VERSION_];
    }

    public function install()
    {
        return parent::install()
            && $this->installNonceTable()
            && Configuration::updateValue(self::EMPLOYEE_KEY, 0)
            && $this->ensureSecret();
    }

    public function uninstall()
    {
        Db::getInstance()->execute('DROP TABLE IF EXISTS `' . _DB_PREFIX_ . 'fakturamaso_nonce`');
        Configuration::deleteByName(self::SECRET_KEY);
        Configuration::deleteByName(self::EMPLOYEE_KEY);
        return parent::uninstall();
    }

    private function installNonceTable()
    {
        return Db::getInstance()->execute('CREATE TABLE IF NOT EXISTS `' . _DB_PREFIX_ . 'fakturamaso_nonce` (
            `nonce_hash` CHAR(64) NOT NULL,
            `issued_at` INT UNSIGNED NOT NULL,
            PRIMARY KEY (`nonce_hash`),
            KEY `issued_at` (`issued_at`)
        ) ENGINE=' . _MYSQL_ENGINE_ . ' DEFAULT CHARSET=utf8mb4');
    }

    private function ensureSecret()
    {
        if (Configuration::get(self::SECRET_KEY)) {
            return true;
        }
        return $this->storeSecret(bin2hex(random_bytes(32)));
    }

    private function encryptionKey()
    {
        return hash('sha256', _COOKIE_KEY_, true);
    }

    private function storeSecret($secret)
    {
        $nonce = random_bytes(12);
        $tag = '';
        $ciphertext = openssl_encrypt($secret, 'aes-256-gcm', $this->encryptionKey(), OPENSSL_RAW_DATA, $nonce, $tag);
        if ($ciphertext === false) {
            return false;
        }
        return Configuration::updateValue(self::SECRET_KEY, base64_encode($nonce . $tag . $ciphertext));
    }

    public function getSecret()
    {
        $stored = Configuration::get(self::SECRET_KEY);
        $payload = $stored ? base64_decode($stored, true) : false;
        if ($payload === false || strlen($payload) < 29) {
            return '';
        }
        $secret = openssl_decrypt(substr($payload, 28), 'aes-256-gcm', $this->encryptionKey(), OPENSSL_RAW_DATA, substr($payload, 0, 12), substr($payload, 12, 16));
        return is_string($secret) ? $secret : '';
    }

    public function getContent()
    {
        $output = '';
        if (Tools::isSubmit('submitFakturamaSso') || Tools::isSubmit('rotateFakturamaSso')) {
            $employeeId = (int) Tools::getValue('employee_id');
            if ($employeeId <= 0) {
                $output .= $this->displayError($this->trans('Choose an active service employee account.', [], 'Modules.Fakturamaso.Admin'));
            } else {
                Configuration::updateValue(self::EMPLOYEE_KEY, $employeeId);
            }
            if ($employeeId > 0 && Tools::isSubmit('rotateFakturamaSso')) {
                if ($this->storeSecret(bin2hex(random_bytes(32)))) {
                    $output .= $this->displayConfirmation($this->trans('The shared key was rotated. Update Fakturama on every workstation.', [], 'Modules.Fakturamaso.Admin'));
                } else {
                    $output .= $this->displayError($this->trans('Could not rotate the shared key.', [], 'Modules.Fakturamaso.Admin'));
                }
            } elseif ($employeeId > 0) {
                $output .= $this->displayConfirmation($this->trans('Settings saved.', [], 'Modules.Fakturamaso.Admin'));
            }
        }

        $action = htmlspecialchars(AdminController::$currentIndex . '&configure=' . $this->name . '&token=' . Tools::getAdminTokenLite('AdminModules'), ENT_QUOTES, 'UTF-8');
        $employeeId = (int) Configuration::get(self::EMPLOYEE_KEY);
        $secret = htmlspecialchars($this->getSecret(), ENT_QUOTES, 'UTF-8');
        $output .= '<form method="post" action="' . $action . '" class="form-horizontal">
            <div class="panel"><h3>Fakturama PrestaShop SSO</h3>
            <p>Fakturama opens the product editor as the configured service employee. Create an employee with only the product permissions your staff need.</p>
            <div class="form-group"><label class="control-label col-lg-3">Service employee ID</label><div class="col-lg-9"><input class="form-control" type="number" min="1" name="employee_id" value="' . $employeeId . '" required></div></div>
            <div class="form-group"><label class="control-label col-lg-3">Shared key for Fakturama</label><div class="col-lg-9"><input class="form-control" type="text" readonly value="' . $secret . '"><p class="help-block">Copy this 64-character key into each Fakturama workstation. It is encrypted in the shop configuration.</p></div></div>
            <button class="btn btn-primary" type="submit" name="submitFakturamaSso" value="1">Save</button>
            <button class="btn btn-default" type="submit" name="rotateFakturamaSso" value="1" onclick="return confirm(\'Rotate the key? All Fakturama workstations must be updated.\');">Rotate shared key</button>
            </div></form>';
        return $output;
    }
}
