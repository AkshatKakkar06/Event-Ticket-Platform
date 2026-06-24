package com.project.EventTicket.domain.dtos;

import com.project.EventTicket.domain.enums.EventStatusEnum;
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
public class ListEventResponseDto {
    private UUID id;
    private String name;
    private String venue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<ListTicketTypeResponseDto> ticketTypes = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
