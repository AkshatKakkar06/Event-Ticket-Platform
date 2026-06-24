package com.project.EventTicket.services.impls;

import com.project.EventTicket.domain.entities.QrCode;
import com.project.EventTicket.domain.entities.Ticket;
import com.project.EventTicket.domain.entities.TicketValidation;
import com.project.EventTicket.domain.enums.EventStatusEnum;
import com.project.EventTicket.domain.enums.QrCodeStatusEnum;
import com.project.EventTicket.domain.enums.TicketValidationMethodEnum;
import com.project.EventTicket.domain.enums.TicketValidationStatusEnum;
import com.project.EventTicket.exceptions.EventNotFoundException;
import com.project.EventTicket.exceptions.QrCodeNotFoundException;
import com.project.EventTicket.exceptions.TicketNotFoundException;
import com.project.EventTicket.repositories.EventRepository;
import com.project.EventTicket.repositories.QrCodeRepository;
import com.project.EventTicket.repositories.TicketRepository;
import com.project.EventTicket.repositories.TicketValidationRepository;
import com.project.EventTicket.services.QrCodeService;
import com.project.EventTicket.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {
    private final EventRepository eventRepository;
    private final QrCodeRepository qrCodeRepository;
    private final TicketRepository ticketRepository;
    private final TicketValidationRepository ticketValidationRepository;

    @Override
    public TicketValidation validateTicketByQrCodeForEvent(UUID qrCodeId, UUID eventId) {
        if(!eventRepository.existsByIdAndStatus(eventId, EventStatusEnum.PUBLISHED)){
            throw new EventNotFoundException(String.format("No published event with id %s found", eventId));
        }
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() ->
                        new QrCodeNotFoundException(String.format("No active qr code found for id '%s'", qrCodeId)));
        Ticket ticket = qrCode.getTicket();
        return validateTicket(ticket, TicketValidationMethodEnum.QR_SCAN, eventId);
    }

    @Override
    public TicketValidation validateTicketManuallyForEvent(UUID ticketId, UUID eventId) {
        if(!eventRepository.existsByIdAndStatus(eventId, EventStatusEnum.PUBLISHED)){
            throw new EventNotFoundException(String.format("No published event with id %s found", eventId));
        }
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(()->
                        new TicketNotFoundException(String.format("Ticket with id '%s' not found",ticketId)));
        return validateTicket(ticket, TicketValidationMethodEnum.MANUAL, eventId);


    }

    private TicketValidation validateTicket(Ticket ticket, TicketValidationMethodEnum ticketValidationMethodEnum,UUID eventId) {
        TicketValidation ticketValidation = new TicketValidation();
        TicketValidationStatusEnum status;
        if(ticket.getTicketType().getEvent().getId().equals(eventId)){
            status = ticket.getTicketValidations().stream()
                    .filter(v -> v.getStatus().equals(TicketValidationStatusEnum.VALID))
                    .findFirst()
                    .map(v -> TicketValidationStatusEnum.INVALID)
                    .orElse(TicketValidationStatusEnum.VALID);
        }
        else{
            status = TicketValidationStatusEnum.INVALID;
        }
        ticketValidation.setStatus(status);
        ticketValidation.setValidationMethod(ticketValidationMethodEnum);
        ticketValidation.setTicket(ticket);
        return ticketValidationRepository.save(ticketValidation);
    }
}
