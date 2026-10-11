package com.hospital.citas.infrastructure.messaging;

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
    public static final String QUEUE_ATENCIONES = "citas.atenciones";
    public static final String ROUTING_KEY_ATENCION = "atencion.registrada";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue queueAtenciones() {
        return new Queue(QUEUE_ATENCIONES, true);
    }

    @Bean
    public Binding bindingAtenciones(Queue queueAtenciones, TopicExchange exchange) {
        return BindingBuilder.bind(queueAtenciones).to(exchange).with(ROUTING_KEY_ATENCION);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
