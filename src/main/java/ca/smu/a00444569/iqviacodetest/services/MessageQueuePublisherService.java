package ca.smu.a00444569.iqviacodetest.services;

import ca.smu.a00444569.iqviacodetest.models.Message;
import org.apache.activemq.ScheduledMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MessageQueuePublisherService {

    public ResponseEntity<String> publishMessage(Message message, JmsTemplate jmsTemplate, Queue queue) {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");

//        Calculating delay by subtracting current time from future time in milliseconds.
        long delay;
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
        jmsTemplate.convertAndSend(queue, message.getMessage(), config -> {
            config.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, finalDelay);
            return config;
        });
        return new ResponseEntity<>("Message published successfully.", HttpStatus.ACCEPTED);
    }
}
