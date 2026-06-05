package com.co.eatupapi.messaging.commercial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Mensaje enviado hacia la cola table.session.close.request.queue
 * del commercial-service para cerrar la sesión de la mesa
 * cuando una factura queda completamente pagada.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableSessionCloseMessage {

    /** ID de la mesa cuya sesión activa debe cerrarse. */
    private String tableId;

    /** ID de la ubicación/sede (informativo). */
    private UUID locationId;

    /** Mensaje descriptivo para el log de auditoría. */
    private String message;
}
