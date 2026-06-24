package com.project.EventTicket.services;

import com.project.EventTicket.domain.entities.QrCode;
import com.project.EventTicket.domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket);
    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
