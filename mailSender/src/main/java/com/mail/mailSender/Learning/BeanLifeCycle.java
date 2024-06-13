package com.mail.mailSender.Learning;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

//@Component
public class BeanLifeCycle {


    public BeanLifeCycle(){
        System.out.println("Bean created");
    }
//@PostConstruct
public void initiation(){
        System.out.println("Bean initiaion");
    }

//    @PreDestroy
    public void destroy(){
        System.out.println("bean destroyed");
    }
}
