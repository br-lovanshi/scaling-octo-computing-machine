package com.mail.mailSender.repository;

import com.mail.mailSender.dto.smtpConfig.GetAllSMTPConfigDTO;
import com.mail.mailSender.model.SMTPConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SMTPConfigRepository extends JpaRepository<SMTPConfig,Long> {
    public boolean existsByEmail(String email);
    public boolean existsByAppName(String AppName);
    @Query("SELECT NEW com.mail.mailSender.dto.smtpConfig.GetAllSMTPConfigDTO(c.id, c.appName, c.email) from SMTPConfig c")
    List<GetAllSMTPConfigDTO> getAllSMTPConfigDTOs();
}
