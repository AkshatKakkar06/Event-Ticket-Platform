package com.project.EventTicket.services;

import com.project.EventTicket.domain.entities.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {
    TicketValidation validateTicketByQrCodeForEvent(UUID qrCodeId, UUID eventId);
    TicketValidation validateTicketManuallyForEvent(UUID ticketId, UUID eventId);
}
