package com.project.EventTicket.services.impls;

import com.project.EventTicket.domain.CreateEventRequest;
import com.project.EventTicket.domain.UpdateEventRequest;
import com.project.EventTicket.domain.UpdateTicketTypeRequest;
import com.project.EventTicket.domain.entities.Event;
import com.project.EventTicket.domain.enums.EventStatusEnum;
import com.project.EventTicket.domain.entities.TicketType;
import com.project.EventTicket.domain.entities.User;
import com.project.EventTicket.exceptions.EventNotFoundException;
import com.project.EventTicket.exceptions.EventTicketTypeNotFoundException;
import com.project.EventTicket.exceptions.EventUpdateException;
import com.project.EventTicket.exceptions.UserNotFoundException;
import com.project.EventTicket.repositories.EventRepository;
import com.project.EventTicket.repositories.UserRepository;
import com.project.EventTicket.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest createEventRequest){

        User organizer = userRepository.findById(organizerId).orElseThrow(() ->
                new UserNotFoundException(String.format("User with id '%s' not found", organizerId)));

        Event eventToBeCreated = new Event();

        List<TicketType> ticketTypesToBeCreated = createEventRequest.getTicketTypes()
                .stream()
                .map(ticketType -> {
                    TicketType ticketTypeToBeCreated = new TicketType();
                    ticketTypeToBeCreated.setName(ticketType.getName());
                    ticketTypeToBeCreated.setPrice(ticketType.getPrice());
                    ticketTypeToBeCreated.setAvailableCount(ticketType.getAvailableCount());
                    ticketTypeToBeCreated.setDescription(ticketType.getDescription());
                    ticketTypeToBeCreated.setEvent(eventToBeCreated);
                    return ticketTypeToBeCreated;})
                .toList();


        eventToBeCreated.setName(createEventRequest.getName());
        eventToBeCreated.setVenue(createEventRequest.getVenue());
        eventToBeCreated.setStartDate(createEventRequest.getStartDate());
        eventToBeCreated.setEndDate(createEventRequest.getEndDate());
        eventToBeCreated.setSalesStart(createEventRequest.getSalesStart());
        eventToBeCreated.setSalesEnd(createEventRequest.getSalesEnd());
        eventToBeCreated.setStatus(createEventRequest.getStatus());
        eventToBeCreated.setOrganizer(organizer);
        eventToBeCreated.setTicketTypes(ticketTypesToBeCreated);

        eventRepository.save(eventToBeCreated);
        return eventToBeCreated;
    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable){
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Optional<Event> getEventDetailsForOrganizer(UUID organizerId, UUID eventId){
        return eventRepository.findByIdAndOrganizerId(eventId, organizerId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest event){
        if(event.getId() == null){
            throw new EventUpdateException("EventId cannot be null");
        }
        if(!Objects.equals(eventId, event.getId())){
            throw new EventUpdateException("EventId cannot be updated");
        }

        Event existingEvent = eventRepository.findByIdAndOrganizerId(eventId, organizerId)
                .orElseThrow(()-> new EventNotFoundException(String.format("Event with id '%s' not found", eventId)));
        existingEvent.setName(event.getName());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setStartDate(event.getStartDate());
        existingEvent.setEndDate(event.getEndDate());
        existingEvent.setSalesStart(event.getSalesStart());
        existingEvent.setSalesEnd(event.getSalesEnd());
        existingEvent.setStatus(event.getStatus());
        List<TicketType> existingTicketTypes = existingEvent.getTicketTypes();
        List<UpdateTicketTypeRequest> ticketTypeRequests = event.getTicketTypes();
        Set<UUID> reqIds = ticketTypeRequests.stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        existingTicketTypes.removeIf(ticketType -> !reqIds.contains(ticketType.getId()));
        Map<UUID,TicketType> existingTicketTypeIndex = existingTicketTypes.stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));
        for(UpdateTicketTypeRequest updateTicketTypeRequest : ticketTypeRequests){
            if(null == updateTicketTypeRequest.getId()){
                //create new ticketType
                TicketType ticketType = new TicketType();
                ticketType.setName(updateTicketTypeRequest.getName());
                ticketType.setPrice(updateTicketTypeRequest.getPrice());
                ticketType.setAvailableCount(updateTicketTypeRequest.getAvailableCount());
                ticketType.setDescription(updateTicketTypeRequest.getDescription());
                ticketType.setEvent(existingEvent);
                existingTicketTypes.add(ticketType);
            }
            else if(existingTicketTypeIndex.containsKey(updateTicketTypeRequest.getId())){
                //update existing ticketType
                TicketType ticketType = existingTicketTypeIndex.get(updateTicketTypeRequest.getId());
                ticketType.setName(updateTicketTypeRequest.getName());
                ticketType.setPrice(updateTicketTypeRequest.getPrice());
                ticketType.setAvailableCount(updateTicketTypeRequest.getAvailableCount());
                ticketType.setDescription(updateTicketTypeRequest.getDescription());
            }
            else{
                //error
                throw new EventTicketTypeNotFoundException(String.format("TicketType with id '%s' not found", updateTicketTypeRequest.getId()));
            }
        }
        return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID eventId){
        getEventDetailsForOrganizer(organizerId, eventId).ifPresent(eventRepository::delete);

    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String q, Pageable pageable) {
        return eventRepository.searchEvents(q, pageable);
    }

    @Override
    public Optional<Event> getPublishedEventDetails(UUID eventId) {
        return eventRepository.findByIdAndStatus(eventId, EventStatusEnum.PUBLISHED);
    }
}
