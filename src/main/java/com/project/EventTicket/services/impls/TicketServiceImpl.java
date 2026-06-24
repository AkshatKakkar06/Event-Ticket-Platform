package com.project.EventTicket.services.impls;

import com.project.EventTicket.domain.entities.Ticket;
import com.project.EventTicket.repositories.TicketRepository;
import com.project.EventTicket.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Override
    public Page<Ticket> listTickets(UUID userId, Pageable pageable) {
         return ticketRepository.findByPurchaserId(userId, pageable);
    }

    @Override
    public Optional<Ticket> getTicketDetails(UUID userId, UUID ticketId) {
        return ticketRepository.findByIdAndPurchaserId(ticketId, userId);
    }
}
