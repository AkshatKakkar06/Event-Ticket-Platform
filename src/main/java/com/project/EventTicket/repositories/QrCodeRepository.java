package com.project.EventTicket.repositories;

import com.project.EventTicket.domain.entities.QrCode;
import com.project.EventTicket.domain.enums.QrCodeStatusEnum;
import com.project.EventTicket.domain.enums.TicketStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {

    Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID userId);
    Optional<QrCode> findByIdAndStatus(UUID Id, QrCodeStatusEnum status);
}
