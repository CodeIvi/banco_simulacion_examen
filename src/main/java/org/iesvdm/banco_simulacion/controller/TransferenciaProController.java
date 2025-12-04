package org.iesvdm.banco_simulacion.controller;

import jakarta.servlet.http.HttpSession;
import org.iesvdm.banco_simulacion.dto.ConfirmDTO;
import org.iesvdm.banco_simulacion.dto.Paso1DTO;
import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.Transferencia_programada;
import org.iesvdm.banco_simulacion.service.CuentaOrigSevice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@SessionAttributes("transfPro")
@RequestMapping("/transferencias")
public class TransferenciaProController {
    private CuentaOrigSevice cuentaOrigSevice;

    public TransferenciaProController(CuentaOrigSevice cuentaOrigSevice) {
        this.cuentaOrigSevice = cuentaOrigSevice;
    }

    @ModelAttribute("transfPro")
    public Transferencia_programada transferenciaProgramada(){
        return new Transferencia_programada();
    }

    @GetMapping("/transferir/paso1")
    public String paso1Get(Model model, Paso1DTO paso1DTO, HttpSession httpSession){
        List<CuentaOrigen> listaCuentas = cuentaOrigSevice.getAll();
        paso1DTO = (Paso1DTO) httpSession.getAttribute("paso1DTO");
        if(paso1DTO == null){
            paso1DTO = new Paso1DTO();
        }

        model.addAttribute("paso1DTO",paso1DTO);
        model.addAttribute("listaCuentas",listaCuentas);

        return "paso1";
    }


    @PostMapping("/transferir/paso2")
    public String pas2Post(Model model, @ModelAttribute Paso1DTO paso1DTO, HttpSession httpSession,
                           @ModelAttribute("transfPro") Transferencia_programada transferenciaProgramada,
                           ConfirmDTO confirmDTO){
        httpSession.setAttribute("paso1DTO",paso1DTO);
        CuentaOrigen cuenta = cuentaOrigSevice.findById(paso1DTO.getId());
        httpSession.setAttribute("cuentaOrigen",cuenta);
        String respuesta = null;
        if(!cuentaOrigSevice.comprobarFecha(paso1DTO)){
            respuesta = "Fecha no válida";
            List<CuentaOrigen> listaCuentas = cuentaOrigSevice.getAll();
            model.addAttribute("respuesta",respuesta);
            model.addAttribute("listaCuentas",listaCuentas);
            return "paso1";
        }
        transferenciaProgramada.setCuenta_origen_id(cuenta.getId());
        transferenciaProgramada.setNombre_beneficiario(paso1DTO.getNombre_beneficiario());
        transferenciaProgramada.setImporte(paso1DTO.getImporte());
        transferenciaProgramada.setConcepto(paso1DTO.getConcepto());
        transferenciaProgramada.setFecha_programada(paso1DTO.getFecha_programada());
        transferenciaProgramada.setFecha_creacion(LocalDateTime.now());
        transferenciaProgramada.setIban_destino(paso1DTO.getIbanDestino());
        transferenciaProgramada.setEstado("En Proceso");

        model.addAttribute("confirmDTO",confirmDTO);
        model.addAttribute("cuentaOrigen",cuenta);
        model.addAttribute("transfPro",transferenciaProgramada);
        return  "paso2";
    }

    @PostMapping("/transferir/confimarcion")
    public String confirmPost(Model model, @ModelAttribute ConfirmDTO confirmDTO,@ModelAttribute("transfPro") Transferencia_programada transferenciaProgramada,
                              HttpSession httpSession){
        CuentaOrigen cuenta = (CuentaOrigen) httpSession.getAttribute("cuentaOrigen");
        String referencia = "TRF-2026-000789";
        transferenciaProgramada.setEstado("Finalizado");
        cuentaOrigSevice.create(transferenciaProgramada);

        model.addAttribute("referencia",referencia);
        model.addAttribute("cuenta",cuenta);
        model.addAttribute("confirmDTO",confirmDTO);
        model.addAttribute("transfPro",transferenciaProgramada);



        return "confirmacion";
    }
}
