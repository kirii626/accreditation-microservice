package com.accenture.accreditation_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RabbitConfig {

    @Value("${accreditation.exchange}")
    private String accreditationExchange;

    @Value("${rabbitmq.routingkey.accreditation}")
    private String accreditationCreatedRoutingKey;

    @Value("${rabbitmq.queue.accreditation}")
    private String accreditationQueue;

    @Bean
    @Primary
    public TopicExchange accreditationTopicExchange() {
        return new TopicExchange(accreditationExchange);
    }

    @Bean
    public Queue accreditationQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "accreditation.dlx");
        args.put("x-dead-letter-routing-key", "accreditation.dlq");
        args.put("x-message-ttl", 20000);

        return new Queue(accreditationQueue, true, false, false, args);
    }

    @Bean
    public Binding bindingAccreditationCreated(Queue accreditationQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(accreditationQueue)
                .to(exchange)
                .with(accreditationCreatedRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue("accreditation.dlq");
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange("accreditation.dlx");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with("accreditation.dlq");
    }
}
