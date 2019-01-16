package com.baidu.disconf.client.support.registry;

import org.springframework.context.ApplicationContext;

import com.baidu.disconf.client.support.registry.impl.SpringRegistry;


public class RegistryFactory {

    public static Registry getSpringRegistry(ApplicationContext applicationContext) {

        SpringRegistry registry = new SpringRegistry();
        registry.setApplicationContext(applicationContext);

        return registry;
    }
}
