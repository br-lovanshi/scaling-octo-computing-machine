package com.mail.mailSender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailSenderApplication.class, args);
	}

}
