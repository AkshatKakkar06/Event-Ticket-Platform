package com.project.EventTicket.repositories;

import com.project.EventTicket.domain.entities.TicketType;
import com.project.EventTicket.domain.enums.EventStatusEnum;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, UUID> {

    @Query("SELECT tt FROM TicketType tt WHERE tt.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TicketType> findByIdWithLock(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE TicketType tt SET tt.availableCount = tt.availableCount - 1 WHERE tt.id = :id AND tt.availableCount>0")
    int decrementCount(@Param("id") UUID id);

    Optional<TicketType> findByIdAndEventIdAndEventStatus(UUID id, UUID eventId, EventStatusEnum status);
}
