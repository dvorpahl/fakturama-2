<?php

if (!defined('_PS_VERSION_')) {
    exit;
}

function upgrade_module_1_0_2($module)
{
    // Use the protected module configuration controller for form submission.
    // Existing employee and shared-key settings are preserved.
    return true;
}
