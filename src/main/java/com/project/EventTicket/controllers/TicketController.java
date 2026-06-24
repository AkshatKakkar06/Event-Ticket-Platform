package com.project.EventTicket.controllers;

import com.project.EventTicket.domain.dtos.GetTicketDetailsResponseDto;
import com.project.EventTicket.domain.dtos.ListTicketResponseDto;
import com.project.EventTicket.domain.entities.Ticket;
import com.project.EventTicket.mappers.TicketMapper;
import com.project.EventTicket.services.QrCodeService;
import com.project.EventTicket.services.TicketService;
import com.project.EventTicket.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final QrCodeService qrCodeService;
    private final TicketMapper ticketMapper;

    @GetMapping
    public ResponseEntity<Page<ListTicketResponseDto>> listTickets(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable){
        UUID userId = JwtUtil.parseJwt(jwt);
        Page<Ticket> pagedTickets = ticketService.listTickets(userId, pageable);
        return ResponseEntity.ok(pagedTickets.map(ticketMapper::toListTicketResponseDto));
    }

    @GetMapping(path = "/{ticketId}")
    public ResponseEntity<GetTicketDetailsResponseDto> getTicketDetails(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId){
        UUID userId = JwtUtil.parseJwt(jwt);
        return ticketService.getTicketDetails(userId, ticketId)
                .map(ticketMapper::toGetTicketDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQRCode(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId){

        UUID userId = JwtUtil.parseJwt(jwt);
        byte[] image = qrCodeService.getQrCodeImageForUserAndTicket(userId, ticketId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(image);
    }

}
