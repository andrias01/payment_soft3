package com.co.eatupapi.messaging.commercial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Publisher que envía mensajes de cierre de sesión de mesa
 * hacia el commercial-service a través de RabbitMQ.
 *
 * Publica en el exchange {@code table.session.close.request.exchange}
 * con routing key {@code table.session.close.request}, que es
 * consumido por el listener {@code consumeCloseSessionRequest}
 * del commercial-service.
 */
@Component
public class TableSessionClosePublisher {

    private static final Logger log = LoggerFactory.getLogger(TableSessionClosePublisher.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.table-session-close}")
    private String exchange;

    @Value("${rabbitmq.routing-key.table-session-close}")
    private String routingKey;

    public TableSessionClosePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publica una solicitud de cierre de sesión de mesa.
     *
     * @param message datos de la mesa cuya sesión debe cerrarse.
     */
    public void publish(TableSessionCloseMessage message) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.info("Solicitud de cierre de sesión publicada: tableId={}", message.getTableId());
        } catch (Exception ex) {
            log.error("Error al publicar solicitud de cierre de sesión para tableId={}: {}",
                    message.getTableId(), ex.getMessage(), ex);
        }
    }
}
