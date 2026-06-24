package com.project.EventTicket.services.impls;

import com.project.EventTicket.domain.entities.QrCode;
import com.project.EventTicket.domain.entities.Ticket;
import com.project.EventTicket.domain.entities.TicketType;
import com.project.EventTicket.domain.entities.User;
import com.project.EventTicket.domain.enums.EventStatusEnum;
import com.project.EventTicket.domain.enums.TicketStatusEnum;
import com.project.EventTicket.exceptions.EventTicketTypeNotFoundException;
import com.project.EventTicket.exceptions.TicketsSoldOutException;
import com.project.EventTicket.exceptions.UserNotFoundException;
import com.project.EventTicket.repositories.TicketRepository;
import com.project.EventTicket.repositories.TicketTypeRepository;
import com.project.EventTicket.repositories.UserRepository;
import com.project.EventTicket.services.QrCodeService;
import com.project.EventTicket.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {
    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;
    private final TicketRepository ticketRepository;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID eventId, UUID ticketTypeId) {

        TicketType ticketType = ticketTypeRepository.findByIdAndEventIdAndEventStatus(ticketTypeId, eventId, EventStatusEnum.PUBLISHED).orElseThrow(() ->
                new EventTicketTypeNotFoundException(String.format("Ticket Type with id '%s' not found",ticketTypeId)));

        int rowsAffected = ticketTypeRepository.decrementCount(ticketTypeId);
        if(rowsAffected == 0) {
            throw new TicketsSoldOutException("Tickets Sold Out");
        }
        User purchaser = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String.format("User with id '%s' not found", userId)));

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setPurchaser(purchaser);
        ticket.setTicketType(ticketType);
        Ticket savedTicket = ticketRepository.save(ticket);
        QrCode qrCode = qrCodeService.generateQrCode(savedTicket);
        return ticketRepository.save(savedTicket);
    }
}
