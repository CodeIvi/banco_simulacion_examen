package org.iesvdm.banco_simulacion.controller;

import org.iesvdm.banco_simulacion.model.Transferencia_programada;
import org.iesvdm.banco_simulacion.repository.TransfProgRepository;
import org.iesvdm.banco_simulacion.service.TransfService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/transferencias")
public class CuentasProgramadasController {
    private TransfService transfService;


    public CuentasProgramadasController(TransfService transfService) {
        this.transfService = transfService;
    }


    @GetMapping("/transferenciasProgramadas")
    public String cuentasGet(Model model) {
        List<Transferencia_programada> listaTrans = transfService.getAll();

        model.addAttribute("listaTrans",listaTrans);

        return "transferenciasProgramadas";


    }


}

