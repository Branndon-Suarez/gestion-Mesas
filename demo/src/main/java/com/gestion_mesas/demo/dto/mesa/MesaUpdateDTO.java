package com.gestion_mesas.demo.dto.mesa;

import com.gestion_mesas.demo.entity.Mesa;
import jakarta.validation.constraints.*;

public class MesaUpdateDTO {
    @NotNull(message = "El ID es obligatorio.")
    private Long idMesa;

    @NotBlank(message = "El número de la mesa es obligatorio.")
    @Pattern(
            regexp = "^[1-9][0-9]*$",
            message = "El número debe ser mayor o igual a 1 y no puede contener letras."
    )
    private String numero;

    @NotBlank(message = "La cantidad es obligatoria.")
    @Pattern(
            regexp = "^[1-9][0-9]*$",
            message = "La cantidad NO puede contener letras y debe ser mayor o igual a 1."
    )
    private String capacidad;

    @NotNull(message = "Obligatorio seleccionar el estado de la mesa.")
    private Mesa.estadoMesa estadoMesa;

    //Getters y setters
    public Long getIdMesa() {
        return idMesa;
    }
    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public Mesa.estadoMesa getEstadoMesa() {
        return estadoMesa;
    }
    public void setEstadoMesa(Mesa.estadoMesa estado) {
        this.estadoMesa = estado;
    }
}
