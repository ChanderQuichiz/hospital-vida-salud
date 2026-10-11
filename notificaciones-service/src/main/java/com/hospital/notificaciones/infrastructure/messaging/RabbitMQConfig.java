package com.hospital.notificaciones.infrastructure.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "hospital.eventos";
    public static final String QUEUE_CITAS = "notificaciones.citas";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue queueCitas() {
        return new Queue(QUEUE_CITAS, true);
    }

    @Bean
    public Binding bindingCitaConfirmada(Queue queueCitas, TopicExchange exchange) {
        return BindingBuilder.bind(queueCitas).to(exchange).with("cita.confirmada");
    }

    @Bean
    public Binding bindingCitaReprogramada(Queue queueCitas, TopicExchange exchange) {
        return BindingBuilder.bind(queueCitas).to(exchange).with("cita.reprogramada");
    }

    @Bean
    public Binding bindingCitaCancelada(Queue queueCitas, TopicExchange exchange) {
        return BindingBuilder.bind(queueCitas).to(exchange).with("cita.cancelada");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
