package com.hospital.auditoria.infrastructure.messaging;

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
    public static final String QUEUE_AUDITORIA = "auditoria.eventos";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue queueAuditoria() {
        return new Queue(QUEUE_AUDITORIA, true);
    }

    @Bean
    public Binding bindingPaciente(Queue queueAuditoria, TopicExchange exchange) {
        return BindingBuilder.bind(queueAuditoria).to(exchange).with("paciente.registrado");
    }

    @Bean
    public Binding bindingCitasWildcard(Queue queueAuditoria, TopicExchange exchange) {
        return BindingBuilder.bind(queueAuditoria).to(exchange).with("cita.*");
    }

    @Bean
    public Binding bindingAtencion(Queue queueAuditoria, TopicExchange exchange) {
        return BindingBuilder.bind(queueAuditoria).to(exchange).with("atencion.registrada");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
