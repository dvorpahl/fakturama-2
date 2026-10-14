<?php

if (!defined('_PS_VERSION_')) {
    exit;
}

function upgrade_module_1_0_1($module)
{
    // The Symfony route path now includes the module name. No stored settings change.
    return true;
}
