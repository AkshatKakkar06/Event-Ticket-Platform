package com.project.EventTicket.controllers;

import com.project.EventTicket.domain.dtos.TicketValidationRequestDto;
import com.project.EventTicket.domain.dtos.TicketValidationResponseDto;
import com.project.EventTicket.domain.entities.TicketValidation;
import com.project.EventTicket.domain.enums.TicketValidationMethodEnum;
import com.project.EventTicket.mappers.TicketValidationMapper;
import com.project.EventTicket.services.TicketValidationService;
import com.project.EventTicket.services.impls.TicketValidationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events/{eventId}/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {
    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validatTicketForEvent(
            @PathVariable UUID eventId,
            @RequestBody TicketValidationRequestDto ticketValidationRequestDto){
        UUID id = ticketValidationRequestDto.getId();
        TicketValidationMethodEnum method = ticketValidationRequestDto.getTicketValidationMethod();
        TicketValidation ticketValidation;
        if(TicketValidationMethodEnum.QR_SCAN.equals(method)){
            ticketValidation = ticketValidationService.validateTicketByQrCodeForEvent(id, eventId);
        }
        else{
            ticketValidation = ticketValidationService.validateTicketManuallyForEvent(id, eventId);
        }

        return ResponseEntity.ok(ticketValidationMapper.toTicketValidationResponseDto(ticketValidation));
    }

}
