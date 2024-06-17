package com.mail.mailSender.learning;


public class Calculator {
    public Calculator(){
        System.out.println("Calculator is created.");
    }
    public double percentageTomoney(int money, double percent){

        double decimle = percent/100;

        double v = decimle * money;
        System.out.println(v);
        return v;
    }

    public void getGSTPercentToMoney(int money, int gst){
        double charge = money/gst;
        System.out.println(charge);
    }
}
