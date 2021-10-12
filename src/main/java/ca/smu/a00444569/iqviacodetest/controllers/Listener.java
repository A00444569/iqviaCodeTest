package ca.smu.a00444569.iqviacodetest.controllers;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {
//    A subscriber to the message queue. It prints the received message on the console.
    @JmsListener(destination = "iqvia.test")
    public void listen(String message) {
        System.out.println(message);
    }
}
