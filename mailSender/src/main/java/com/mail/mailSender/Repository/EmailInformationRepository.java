package com.mail.mailSender.Repository;

import com.mail.mailSender.model.SendEmailInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailInformationRepository extends JpaRepository<SendEmailInformation, Long> {
}
