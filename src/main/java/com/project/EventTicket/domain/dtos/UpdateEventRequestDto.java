package com.project.EventTicket.domain.dtos;

import com.project.EventTicket.domain.enums.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class UpdateEventRequestDto {

    @NotNull(message = "Event id is required")
    private UUID id;

    @NotBlank(message = "Event name is required")
    private String name;

    @NotBlank(message = "Event venue is required")
    private String venue;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;

    @NotNull(message = "Event status must be provided")
    private EventStatusEnum status;

    @NotEmpty(message = "At least one ticket type is required")
    @Valid
    private List<UpdateTicketTypeRequestDto> ticketTypes = new ArrayList<>();
}

