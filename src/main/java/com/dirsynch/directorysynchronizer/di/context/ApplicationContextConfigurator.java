package com.dirsynch.directorysynchronizer.di.context;


import com.dirsynch.directorysynchronizer.di.factory.BeanFactory;

public class ApplicationContextConfigurator {

    public static ApplicationContext run(){
        ApplicationContext applicationContext = new ApplicationContext();
        BeanFactory beanFactory = new BeanFactory(applicationContext);
        applicationContext.setBeanFactory(beanFactory);
        applicationContext.loadClasses("com.dirsynch.directorysynchronizer");

        return applicationContext;
    }
}
