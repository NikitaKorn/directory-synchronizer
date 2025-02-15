package com.dirsynch.directorysynchronizer.di.config;

import java.util.Map;

public interface Configuration {
    String getPackageToScan();
    Map<Class, Class> getInterfaceToImplementations();
}
