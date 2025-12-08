package com.global.GobalRent.services;

import com.global.GobalRent.entity.ReservationEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class Mailservice {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;


    @Async
    @Retryable(
            value = { MessagingException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void sendEmail( ReservationEntity reserve) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        helper.setTo(reserve.getUser().getEmail());

        message.setSubject("confirmación de reserva");

        helper.setText(getReserveHtml(reserve),true);

        mailSender.send(message);
    }

    public String getReserveHtml(ReservationEntity reserve){

        Context context = new Context();

        context.setVariable("nombre",reserve.getUser().getName());
        context.setVariable("carModel",reserve.getCar().getModel());
        context.setVariable("startDate",reserve.getStartDate());
        context.setVariable("startTime",reserve.getStartTime());
        context.setVariable("endDate",reserve.getEndDate());
        context.setVariable("endTime",reserve.getEndTime());
        context.setVariable("startPlace",reserve.getStartPlace());
        context.setVariable("endPlace",reserve.getEndPlace());

        String startPlacelink = "https://www.google.com/maps/search/?api=1&query=Armeniaquindio" + reserve.getStartPlace();
        String endPlaceLink = "https://www.google.com/maps/search/?api=1&query=Armeniaquindio" + reserve.getEndPlace();

        context.setVariable("startPlaceLink",startPlacelink);
        context.setVariable("endPlaceLink",endPlaceLink);

        context.setVariable("total",reserve.getTotalPrice());

        return templateEngine.process("confirm-reserve",context);
    }
}
