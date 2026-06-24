package com.project.EventTicket.repositories;

import com.project.EventTicket.domain.entities.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {
}
