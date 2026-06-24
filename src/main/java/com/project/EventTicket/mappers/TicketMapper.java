package com.project.EventTicket.mappers;

import com.project.EventTicket.domain.dtos.GetTicketDetailsResponseDto;
import com.project.EventTicket.domain.dtos.ListTicketResponseDto;
import com.project.EventTicket.domain.entities.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);

    @Mapping(target = "name",source = "ticket.ticketType.name")
    @Mapping(target = "price",source = "ticket.ticketType.price")
    @Mapping(target = "description",source = "ticket.ticketType.description")
    @Mapping(target = "eventName",source = "ticket.ticketType.event.name")
    @Mapping(target = "eventVenue",source = "ticket.ticketType.event.venue")
    @Mapping(target = "startDate",source = "ticket.ticketType.event.startDate")
    @Mapping(target = "endDate",source = "ticket.ticketType.event.endDate")
    GetTicketDetailsResponseDto toGetTicketDetailsResponseDto(Ticket ticket);

}
