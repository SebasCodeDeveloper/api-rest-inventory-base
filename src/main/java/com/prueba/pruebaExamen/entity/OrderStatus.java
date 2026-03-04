package com.prueba.pruebaExamen.entity;

/**
 * Definición de los estados permitidos dentro del ciclo de vida de una transacción.
 * Este enumerado garantiza la consistencia de los datos y restringe las transiciones
 * de la orden a valores predefinidos en el sistema.
 */
public enum OrderStatus {
    /**
     * Estado inicial asignado automáticamente al momento de la persistencia de la orden.
     */
    CREATED,

    /**
     * Indica que el proceso de pago ha sido completado satisfactoriamente por el usuario.
     */
    PAID,

    /**
     * Representa la anulación de la transacción y la reversión de los artículos al inventario.
     */
    CANCELLED,

    /**
     * Señala que la orden se encuentra actualmente en fase de procesamiento logístico u operativo.
     */
    IN_PROGRESS,
}