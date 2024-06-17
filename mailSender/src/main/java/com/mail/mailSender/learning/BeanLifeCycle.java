package com.mail.mailSender.learning;

//@Component
public class BeanLifeCycle {
    public Calculator calculator;

//    public BeanLifeCycle(Calculator calculator){
//        this.calculator = calculator;
//    }

    public void setCalculator(Calculator calculator){
        this.calculator = calculator;
    }
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
