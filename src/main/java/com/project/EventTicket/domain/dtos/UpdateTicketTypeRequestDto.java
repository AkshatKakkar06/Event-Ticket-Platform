package com.project.EventTicket.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketTypeRequestDto {

    private UUID id;

    @NotBlank(message = "Ticket type name is required")
    private String name;

    @NotNull(message = "price is required")
    @PositiveOrZero(message = "Price must be zero or greater")
    private Double price;

    private Integer availableCount;

    private String description;
}
