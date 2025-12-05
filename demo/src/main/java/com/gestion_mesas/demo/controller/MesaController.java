package com.gestion_mesas.demo.controller;

import com.gestion_mesas.demo.dto.mesa.MesaCreateDTO;
import com.gestion_mesas.demo.dto.mesa.MesaUpdateDTO;
import com.gestion_mesas.demo.entity.Mesa;
import com.gestion_mesas.demo.entity.Pedido;
import com.gestion_mesas.demo.service.impl.MesaService;
import com.gestion_mesas.demo.service.impl.PedidoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mesas")
public class MesaController {
    private static final String REDIRECT_LISTAR = "redirect:/mesas/listar";
    public final MesaService mesaService;
    public final PedidoService pedidoService;
    public MesaController(MesaService mesaService, PedidoService pedidoService) {
        this.mesaService = mesaService;
        this.pedidoService= pedidoService;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Mesa> listaMesas = mesaService.listarMesas();
        Map<Long, Pedido> pedidosActivos = new HashMap<>();

        for(Mesa m : listaMesas) {
            Pedido activo = pedidoService.obtenerPedidoActivo(m.getIdMesa());
            pedidosActivos.put(m.getIdMesa(), activo);
        }

        model.addAttribute("listaMesas", listaMesas);
        model.addAttribute("pedidosActivos", pedidosActivos);
        return "mesa/listar";
    }

    @GetMapping("/formGuardar")
    public String mostrarFormGuardar(Model model) {
        model.addAttribute("mesaCreateDTO", new MesaCreateDTO());
        // Enviar el enum a la vista del formualrio de crear
        model.addAttribute("estadosMesa", Mesa.estadoMesa.values());
        return "mesa/formGuardar";
    }

    @PostMapping("/guardar")
    public String guardarMesa(
            @Valid @ModelAttribute("mesaCreateDTO") MesaCreateDTO dto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            // Volver a enviar el enum a la vista del formulario de crear con el fin de que vuelva a cargar los option dle formulario cuando se inserte un valor no válido.
            model.addAttribute("estadosMesa", Mesa.estadoMesa.values());
            return "mesa/formGuardar";
        }

        Mesa mesa = new Mesa();
        mesa.setNumero(Integer.parseInt(dto.getNumero()));
        mesa.setCapacidad(Integer.parseInt(dto.getCapacidad()));
        mesa.setEstado(dto.getEstadoMesa());

        mesaService.guardarMesa(mesa);

        return REDIRECT_LISTAR;
    }

    @GetMapping("/formAct/{id}")
    public String mostrarFormAct(@PathVariable Long id, Model model) {
        Mesa mesa = mesaService.buscarById(id);

        MesaUpdateDTO dto = new MesaUpdateDTO();
        dto.setIdMesa(mesa.getIdMesa());
        dto.setNumero(String.valueOf(mesa.getNumero()));
        dto.setCapacidad(String.valueOf(mesa.getCapacidad()));
        dto.setEstadoMesa(mesa.getEstado());

        model.addAttribute("mesaUpdateDTO", dto);
        model.addAttribute("estadoMesa", Mesa.estadoMesa.values());
        return "mesa/formAct";
    }

    @PostMapping("/actualizar")
    public String actualizarMesa(
            @Valid @ModelAttribute("mesaUpdateDTO") MesaUpdateDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "/mesa/formAct";
        }

        Mesa mesaActual = mesaService.buscarById(dto.getIdMesa());
        mesaActual.setNumero(Integer.parseInt(dto.getNumero()));
        mesaActual.setCapacidad(Integer.parseInt(dto.getCapacidad()));
        mesaActual.setEstado(dto.getEstadoMesa());

        mesaService.guardarMesa(mesaActual);

        return REDIRECT_LISTAR;
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarMesa(@PathVariable Long id) {
        mesaService.eliminarMesa(id);
        return REDIRECT_LISTAR;
    }
}
