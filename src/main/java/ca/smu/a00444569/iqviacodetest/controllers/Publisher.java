package ca.smu.a00444569.iqviacodetest.controllers;

import ca.smu.a00444569.iqviacodetest.models.Message;
import ca.smu.a00444569.iqviacodetest.services.MessageQueuePublisherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;

@RestController
public class Publisher {

    private final JmsTemplate jmsTemplate;
    private final Queue queue;
    private final MessageQueuePublisherService messageQueuePublisherService;

    @Autowired
    public Publisher(JmsTemplate jmsTemplate, Queue queue, MessageQueuePublisherService messageQueuePublisherService) {
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
        this.messageQueuePublisherService = messageQueuePublisherService;
    }

    @PostMapping("/publish")
    @ApiOperation(value = "Publishes the sent message to the queue.", code = 202)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Message published successfully."),
            @ApiResponse(code = 400, message = "Malformed input. Please try with valid data.")
    })
    public ResponseEntity<String> publish(@RequestBody Message message) {
        return this.messageQueuePublisherService.publishMessage(message, this.jmsTemplate, this.queue);
    }
}
