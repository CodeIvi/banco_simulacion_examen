package org.iesvdm.banco_simulacion.service;

import org.iesvdm.banco_simulacion.model.Transferencia_programada;
import org.iesvdm.banco_simulacion.repository.TransfProgRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TransfService {
    private TransfProgRepository transfProgRepository;

    public TransfService(TransfProgRepository transfProgRepository) {
        this.transfProgRepository = transfProgRepository;
    }


    public List<Transferencia_programada> getAll(){
       return transfProgRepository.getAll();
    }
}
