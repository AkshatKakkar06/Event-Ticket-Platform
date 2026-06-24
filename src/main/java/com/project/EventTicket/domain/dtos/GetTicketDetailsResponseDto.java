package com.project.EventTicket.domain.dtos;

import com.project.EventTicket.domain.enums.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTicketDetailsResponseDto {
    private UUID id;
    private TicketStatusEnum status;
    private String name;
    private Double price;
    private String description;
    private String eventName;
    private String eventVenue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
