package com.dirsynch.directorysynchronizer.di.factory;


import com.dirsynch.directorysynchronizer.di.annotation.Inject;
import com.dirsynch.directorysynchronizer.di.config.Configuration;
import com.dirsynch.directorysynchronizer.di.config.JavaConfiguration;
import com.dirsynch.directorysynchronizer.di.configurator.BeanConfigurator;
import com.dirsynch.directorysynchronizer.di.configurator.JavaBeanConfigurator;
import com.dirsynch.directorysynchronizer.di.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class BeanFactory {
    private final BeanConfigurator beanConfigurator;
    private final Configuration configuration;
    private final ApplicationContext applicationContext;

    public BeanFactory(ApplicationContext applicationContext){
        this.configuration = new JavaConfiguration();
        this.beanConfigurator = new JavaBeanConfigurator(configuration.getPackageToScan(), configuration.getInterfaceToImplementations());
        this.applicationContext = applicationContext;
    };

    public <T> T getBean(Class<T> clazz) {
        Class<? extends T> implementationClass = clazz;
        if (implementationClass.isInterface()){
            implementationClass = beanConfigurator.getImplementationClass(implementationClass);
        }

        T bean = null;
        try {
            bean = implementationClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        for(Field field : Arrays.stream(implementationClass.getDeclaredFields()).filter(field -> field.isAnnotationPresent(Inject.class)).toList()){
            field.setAccessible(true);
            try {
                field.set(bean, applicationContext.getBean(field.getType()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return bean;
    }
}
