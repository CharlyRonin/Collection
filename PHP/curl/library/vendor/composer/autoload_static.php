<?php

// autoload_static.php @generated by Composer

namespace Composer\Autoload;

class ComposerStaticInitb67d9a000aaff853fee2cdc959f5b720
{
    public static $prefixLengthsPsr4 = array (
        'C' => 
        array (
            'Curl\\' => 5,
        ),
    );

    public static $prefixDirsPsr4 = array (
        'Curl\\' => 
        array (
            0 => __DIR__ . '/..' . '/php-curl-class/php-curl-class/src/Curl',
        ),
    );

    public static function getInitializer(ClassLoader $loader)
    {
        return \Closure::bind(function () use ($loader) {
            $loader->prefixLengthsPsr4 = ComposerStaticInitb67d9a000aaff853fee2cdc959f5b720::$prefixLengthsPsr4;
            $loader->prefixDirsPsr4 = ComposerStaticInitb67d9a000aaff853fee2cdc959f5b720::$prefixDirsPsr4;

        }, null, ClassLoader::class);
    }
}
