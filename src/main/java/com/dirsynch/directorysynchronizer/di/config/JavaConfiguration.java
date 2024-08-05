package com.dirsynch.directorysynchronizer.di.config;

import java.util.Map;

public class JavaConfiguration implements Configuration {
    @Override
    public String getPackageToScan() {
        return "com.dirsynch.directorysynchronizer";
    }

    @Override
    public Map<Class, Class> getInterfaceToImplementations() {
        return Map.of();
    }
}
