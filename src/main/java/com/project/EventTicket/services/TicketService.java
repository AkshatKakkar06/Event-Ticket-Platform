package com.project.EventTicket.services;

import com.project.EventTicket.domain.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TicketService {
    Page<Ticket> listTickets(UUID userId, Pageable pageable);
    Optional<Ticket> getTicketDetails(UUID userId, UUID ticketId);
}
