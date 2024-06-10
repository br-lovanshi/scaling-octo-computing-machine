package com.mail.mailSender.repository;

import com.mail.mailSender.dto.mailJob.GetAllJobDTO;
import com.mail.mailSender.model.MailJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MailJobRepository extends JpaRepository<MailJob, Long> {
    public boolean existsBySubject(String subject);

    @Query("SELECT m FROM MailJob m LEFT JOIN FETCH m.recipients WHERE m.id = :id")
    Optional<MailJob> findByIdWithRecipients(@Param("id") Long id);

    @Query("SELECT NEW com.mail.mailSender.dto.mailJob.GetAllJobDTO(m.id, m.subject) FROM MailJob m")
    List<GetAllJobDTO>  getAllJobDTOs();
}
