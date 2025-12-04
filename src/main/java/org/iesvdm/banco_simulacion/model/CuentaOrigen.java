package org.iesvdm.banco_simulacion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaOrigen {
    private long id;
    private String alias_cuenta;
    private String iban;
}
