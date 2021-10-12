package ca.smu.a00444569.iqviacodetest.controllers;

import ca.smu.a00444569.iqviacodetest.models.Message;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class Publisher {

    private final JmsTemplate jmsTemplate;
    private final ActiveMQQueue queue;

    @Autowired
    public Publisher(JmsTemplate jmsTemplate, ActiveMQQueue queue) {
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
    }

    @PostMapping("/publish")
    @ApiOperation(value = "Publishes the sent message to the queue.", code = 202)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Message published successfully."),
            @ApiResponse(code = 400, message = "Malformed input. Please try with valid data.")
    })
    public ResponseEntity<String> publish(@RequestBody Message message) {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");

        long delay = 0;
        try {
            delay = formatter.parse(message.getDateTime()).getTime() - now.getTime();
        } catch (ParseException e) {
            return new ResponseEntity<>("Not a valid date. Please try with valid data.", HttpStatus.BAD_REQUEST);
        }

//        If the given time lies in the past, we are returning an error.
        if (delay <= 0) {
            return new ResponseEntity<>("Not a future date. Please try with valid data.", HttpStatus.BAD_REQUEST);
        }

//        After passing the check, the message is published to the queue with the calculated delay in milliseconds.
        long finalDelay = delay;
        this.jmsTemplate.convertAndSend(this.queue, message.getMessage(), config -> {
            config.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, finalDelay);
            return config;
        });
        return new ResponseEntity<>("Message published successfully.", HttpStatus.ACCEPTED);
    }
}
