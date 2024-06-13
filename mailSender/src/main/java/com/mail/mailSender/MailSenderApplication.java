package com.mail.mailSender;

import com.mail.mailSender.Learning.BeanLifeCycle;
import com.mail.mailSender.service.mailJob.MailJobService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@EnableAsync
public class MailSenderApplication {

	public static void main(String[] args) {
		ApplicationContext container =  SpringApplication.run(MailSenderApplication.class, args);

//		BeanLifeCycle beanLifeCycle = container.getBean(BeanLifeCycle.class);



	}

}
