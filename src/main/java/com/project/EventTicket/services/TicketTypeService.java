package com.project.EventTicket.services;

import com.project.EventTicket.domain.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID eventId, UUID ticketTypeId);
}
