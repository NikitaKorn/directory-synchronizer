package com.dirsynch.directorysynchronizer.di.configurator;

public interface BeanConfigurator {
    <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass);
}
