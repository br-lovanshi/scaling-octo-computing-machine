package com.mail.mailSender.Repository;

import com.mail.mailSender.model.SMTPConfig;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMTPConfigRepository extends JpaRepository<SMTPConfig,Long> {
    public boolean existsByEmail(String email);
    public boolean existsByAppName(String AppName);
}
