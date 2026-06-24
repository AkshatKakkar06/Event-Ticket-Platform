package com.project.EventTicket.mappers;

import com.project.EventTicket.domain.CreateEventRequest;
import com.project.EventTicket.domain.UpdateEventRequest;
import com.project.EventTicket.domain.dtos.*;
import com.project.EventTicket.domain.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateEventRequest fromDto(CreateEventRequestDto createEventRequestDto);
    CreateEventResponseDto toDto(Event event);
    ListEventResponseDto toListEventResponseDto(Event event);
    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);
    UpdateEventRequest fromUpdateEventResponseDto(UpdateEventRequestDto updateEventRequestDto);
    UpdateEventResponseDto toUpdateEventResponseDto(Event event);
    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);
    GetPublishedEventDetailsResponseDto toGetPublishedEventDetailsResponseDto(Event event);
}

