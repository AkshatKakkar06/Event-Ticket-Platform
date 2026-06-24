package com.project.EventTicket.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTicketTypeDetailsResponseDto {
    private UUID id;
    private String name;
    private Double price;
    private Integer availableCount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
