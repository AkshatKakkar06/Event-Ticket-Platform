package com.project.EventTicket.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPublishedEventDetailsResponseDto {
    private UUID id;
    private String name;
    private String venue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<GetPublishedTicketTypeDetailsResponseDto> ticketTypes = new ArrayList<>();

}
