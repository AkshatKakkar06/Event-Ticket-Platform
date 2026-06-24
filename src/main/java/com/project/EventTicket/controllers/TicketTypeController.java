package com.project.EventTicket.controllers;

import com.project.EventTicket.services.TicketTypeService;
import com.project.EventTicket.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events/{eventId}/ticket-types")
@RequiredArgsConstructor
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;

    @PostMapping("/{ticketTypeId}/tickets")
    public ResponseEntity<Void> purchaseTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @PathVariable UUID ticketTypeId){
        UUID userId = JwtUtil.parseJwt(jwt);
        ticketTypeService.purchaseTicket(userId, eventId, ticketTypeId);
        return ResponseEntity.noContent().build();
    }
}
