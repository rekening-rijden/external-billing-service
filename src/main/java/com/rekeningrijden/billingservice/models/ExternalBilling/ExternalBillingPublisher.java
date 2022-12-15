package com.rekeningrijden.billingservice.models.ExternalBilling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExternalBillingPublisher {

    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.externalbill.create.key}")
    private String externalBillingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalBillingPublisher.class);

    private RabbitTemplate rabbitTemplate;

    public ExternalBillingPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ExternalBillingResponseDTO externalBillingResponseDTO) {
        try{
            String message = mapper.writeValueAsString(externalBillingResponseDTO);
            rabbitTemplate.convertAndSend(exchange, externalBillingKey, message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}