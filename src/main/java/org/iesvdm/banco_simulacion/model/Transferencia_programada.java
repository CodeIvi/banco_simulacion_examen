package org.iesvdm.banco_simulacion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Transferencia_programada {
    private long id;
    private long cuenta_origen_id;
    private String nombre_beneficiario;
    private String iban_destino;
    private BigDecimal importe;
    private String concepto;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fecha_programada;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fecha_creacion;
    private String estado;
}
