//package com.nteport.admin.config;
//
//import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class NacosConfig implements ApplicationRunner {
//
//    @Autowired(required = false)
//    private NacosAutoServiceRegistration registration;
//
//    @Value("${server.port}")
//    Integer port;
//
//    @Override
//    public void run(ApplicationArguments args) {
//        if (registration != null && port != null) {
//            registration.setPort(port);
//            registration.start();
//        }
//    }
//
//}
