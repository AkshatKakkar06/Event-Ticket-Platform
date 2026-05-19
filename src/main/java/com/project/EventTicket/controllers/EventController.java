package com.project.EventTicket.controllers;

import com.project.EventTicket.domain.CreateEventRequest;
import com.project.EventTicket.domain.UpdateEventRequest;
import com.project.EventTicket.domain.dtos.*;
import com.project.EventTicket.domain.entities.Event;
import com.project.EventTicket.mappers.EventMapper;
import com.project.EventTicket.services.EventService;
import com.project.EventTicket.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto){

        UUID organizerId = JwtUtil.parseJwt(jwt);
        CreateEventRequest request = eventMapper.fromDto(createEventRequestDto);
        Event event = eventService.createEvent(organizerId, request);
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(event);
        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDto>> listEventsForOrganizer(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable){

        UUID organizerId = JwtUtil.parseJwt(jwt);
        Page<Event> pagedEvents = eventService.listEventsForOrganizer(organizerId, pageable);
        return ResponseEntity.ok(pagedEvents.map(eventMapper::toListEventResponseDto));
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEventDetailsForOrganizer(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId) {

        UUID organizerId = JwtUtil.parseJwt(jwt);
        return eventService.getEventDetailsForOrganizer(organizerId, eventId)
                .map(eventMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto){

        UUID organizerId = JwtUtil.parseJwt(jwt);
        UpdateEventRequest updateEventRequest = eventMapper.fromUpdateEventResponseDto(updateEventRequestDto);
        Event event = eventService.updateEventForOrganizer(organizerId, eventId, updateEventRequest);
        return ResponseEntity.ok(eventMapper.toUpdateEventResponseDto(event));
    }

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId){
        UUID organizerId = JwtUtil.parseJwt(jwt);
        eventService.deleteEventForOrganizer(organizerId, eventId);
        return ResponseEntity.noContent().build();
    }

}

