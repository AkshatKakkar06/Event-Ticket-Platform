package com.project.EventTicket.services;

import com.project.EventTicket.domain.CreateEventRequest;
import com.project.EventTicket.domain.UpdateEventRequest;
import com.project.EventTicket.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organiserId, CreateEventRequest createEventRequest);
    Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);
    Optional<Event> getEventDetailsForOrganizer(UUID organizerId, UUID eventId);
    Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest event);
    void deleteEventForOrganizer(UUID organizerId, UUID eventId);

    Page<Event> listPublishedEvents(Pageable pageable);
    Page<Event> searchPublishedEvents(String q, Pageable pageable);
    Optional<Event> getPublishedEventDetails(UUID eventId);
}
