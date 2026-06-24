package com.project.EventTicket.services.impls;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.project.EventTicket.domain.entities.QrCode;
import com.project.EventTicket.domain.entities.Ticket;
import com.project.EventTicket.domain.enums.QrCodeStatusEnum;
import com.project.EventTicket.exceptions.QrCodeGenerationException;
import com.project.EventTicket.exceptions.QrCodeNotFoundException;
import com.project.EventTicket.exceptions.UserNotFoundException;
import com.project.EventTicket.repositories.QrCodeRepository;
import com.project.EventTicket.services.QrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrCodeServiceImpl implements QrCodeService {
    private static final int qrCodeHeight = 300;
    private static final int qrCodeWidth = 300;
    private final QRCodeWriter qrCodeWriter;
    private final QrCodeRepository qrCodeRepository;
    @Override
    public QrCode generateQrCode(Ticket ticket) {
        try {
            UUID qrCodeId = UUID.randomUUID();
            String image = generateQrCodeImage(qrCodeId);
            QrCode qrCode = new QrCode();
            qrCode.setId(qrCodeId);
            qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
            qrCode.setValue(image);
            qrCode.setTicket(ticket);
            return qrCodeRepository.saveAndFlush(qrCode);
        }
        catch(WriterException | IOException ex){
            throw new QrCodeGenerationException("Failed to generate Qr Code",ex);
        }
    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId, userId)
                .orElseThrow(QrCodeNotFoundException::new);
        try{
            String value = qrCode.getValue();
            return Base64.getDecoder().decode(value);
        }
        catch(IllegalArgumentException ex){
            log.error("Invalid base64 qr code for ticket ID: {}",ticketId, ex);
            throw new QrCodeNotFoundException();
        }
    }

    private String generateQrCodeImage(UUID qrCodeId) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeId.toString(),
                BarcodeFormat.QR_CODE,
                qrCodeWidth,
                qrCodeHeight);
        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(qrCodeImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        }

    }
}
