package com.accenture.accreditation_service.events.publishers;

import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccreditationEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${accreditation.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey.accreditation}")
    private String accreditationCreatedRoutingKey;

    public void sendAccreditationCreatedNotification(AccreditationDtoOutput accreditationDtoOutput) {
        rabbitTemplate.convertAndSend(exchange, accreditationCreatedRoutingKey, accreditationDtoOutput);
    }
}
