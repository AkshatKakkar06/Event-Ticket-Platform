package com.project.EventTicket.controllers;

import com.project.EventTicket.domain.dtos.GetPublishedEventDetailsResponseDto;
import com.project.EventTicket.domain.dtos.ListPublishedEventResponseDto;
import com.project.EventTicket.mappers.EventMapper;
import com.project.EventTicket.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            @RequestParam(required = false) String q,
            Pageable pageable){
        if(null!=q && !q.trim().isEmpty()){
            return ResponseEntity.ok(eventService.searchPublishedEvents(q, pageable)
                    .map(eventMapper::toListPublishedEventResponseDto));
        }
        else{
            return ResponseEntity.ok(eventService.listPublishedEvents(pageable)
                    .map(eventMapper::toListPublishedEventResponseDto));
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEventDetails(
            @PathVariable UUID eventId){
        return eventService.getPublishedEventDetails(eventId)
                .map(eventMapper::toGetPublishedEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
