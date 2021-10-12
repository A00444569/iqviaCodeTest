package ca.smu.a00444569.iqviacodetest.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class Config {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    //    A bean to create a queue
    @Bean
    public ActiveMQQueue queue() {
        return new ActiveMQQueue("iqvia.test");
    }

    //    A bean to create ActiveMQ connection factory to connect with the broker at the given URL
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerUrl);
        return factory;
    }

    //    A bean to return a jmsTemplate created using the factory
    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }
}
