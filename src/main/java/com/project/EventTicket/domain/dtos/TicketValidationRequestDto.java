package com.project.EventTicket.domain.dtos;

import com.project.EventTicket.domain.enums.TicketValidationMethodEnum;
import com.project.EventTicket.domain.enums.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketValidationRequestDto {
    private UUID id;
    private TicketValidationMethodEnum ticketValidationMethod;

}
