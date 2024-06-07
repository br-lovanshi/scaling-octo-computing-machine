package com.mail.mailSender.Repository;

import com.mail.mailSender.model.MailJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailJobRepository extends JpaRepository<MailJob, Long> {
    public boolean existsBySubject(String subject);

    @Query("SELECT m FROM MailJob m LEFT JOIN FETCH m.recipients WHERE m.id = :id")
    Optional<MailJob> findByIdWithRecipients(@Param("id") Long id);
}
