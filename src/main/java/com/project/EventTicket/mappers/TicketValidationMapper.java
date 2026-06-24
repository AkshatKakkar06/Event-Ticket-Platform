package com.project.EventTicket.mappers;

import com.project.EventTicket.domain.dtos.TicketValidationRequestDto;
import com.project.EventTicket.domain.dtos.TicketValidationResponseDto;
import com.project.EventTicket.domain.entities.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {
    @Mapping(target = "ticketId", source = "ticketValidation.ticket.id")
    TicketValidationResponseDto toTicketValidationResponseDto(TicketValidation ticketValidation);
}
