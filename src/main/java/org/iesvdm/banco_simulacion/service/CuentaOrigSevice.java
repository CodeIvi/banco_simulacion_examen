package org.iesvdm.banco_simulacion.service;

import org.iesvdm.banco_simulacion.dto.Paso1DTO;
import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.Transferencia_programada;
import org.iesvdm.banco_simulacion.repository.CuentaOrigenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CuentaOrigSevice {
    private CuentaOrigenRepository cuentaOrigenRepository;

    public CuentaOrigSevice(CuentaOrigenRepository cuentaOrigenRepository) {
        this.cuentaOrigenRepository = cuentaOrigenRepository;
    }

    public List<CuentaOrigen> getAll(){
        return cuentaOrigenRepository.getAll();
    }

    public CuentaOrigen findById(long id){
        return cuentaOrigenRepository.findById(id);
    }

    public Boolean comprobarFecha(Paso1DTO paso1DTO){
        boolean respuesta = false;
        int dia = paso1DTO.getFecha_programada().getDayOfMonth();
        int mes = paso1DTO.getFecha_programada().getMonthValue();
        int anio = paso1DTO.getFecha_programada().getYear();
        LocalDateTime fecha_actual = LocalDateTime.now();
        int dia1 = fecha_actual.getDayOfMonth();
        int mes1 = fecha_actual.getMonthValue();
        int anio1 = fecha_actual.getYear();

        if(dia>dia1 && mes >= mes1 && anio>=anio1){
            respuesta = true;
        }
        return respuesta;

    }

    public Transferencia_programada create(Transferencia_programada transferenciaProgramada){
        return  cuentaOrigenRepository.create(transferenciaProgramada);
    }
}
