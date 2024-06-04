package com.mail.mailSender.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class SendEmailInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private List<String> recipients;
    private String smtpServer;
    private String smtpPort;
    private String password;
    private String subject;
    private String email;
    private String message;

    public SendEmailInformation() {
    }

    public SendEmailInformation(List<String> recipients, String smtpServer, String smtpPort, String password, String subject, String email, String message) {
        this.recipients = recipients;
        this.smtpServer = smtpServer;
        this.smtpPort = smtpPort;
        this.password = password;
        this.subject = subject;
        this.email = email;
        this.message = message;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void SetRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
