package com.global.GobalRent.services;

import com.global.GobalRent.entity.ReservationEntity;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.resource.Emailv31;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Mailservice {

    private final MailjetClient mailjetClient;

    private final SpringTemplateEngine templateEngine;


    @Async
    @Retryable(
            value = { IOException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void sendEmail(ReservationEntity reserve) {

        String html = getReserveHtml(reserve);

        try {
            MailjetRequest request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", "globalrentarmenia@gmail.com")
                                            .put("Name", "Global Rent"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", reserve.getUser().getEmail())
                                                    .put("Name", reserve.getUser().getName())))
                                    .put(Emailv31.Message.SUBJECT, "Confirmación de reserva")
                                    .put(Emailv31.Message.HTMLPART, html)
                            )
                    );

            mailjetClient.post(request);

        } catch (Exception e) {
            throw new RuntimeException("Error enviando correo con Mailjet", e);
        }
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
