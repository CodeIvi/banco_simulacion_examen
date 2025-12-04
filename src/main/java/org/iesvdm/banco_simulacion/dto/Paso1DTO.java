package org.iesvdm.banco_simulacion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paso1DTO {
    private long id;
    @NotBlank(message = "Debes rellenar el campo Nombre del Beneficiario")
    private String nombre_beneficiario;
    @Size(min = 24,max=24, message = "Error en el IBAN")
    private String ibanDestino;
    @Min(value=1,message = "Debes introducir un valor en el importe")
    private BigDecimal importe;
    private String concepto;
    private LocalDateTime fecha_programada;

}
