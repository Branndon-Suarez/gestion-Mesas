package com.gestion_mesas.demo.dto.mesa;

import com.gestion_mesas.demo.entity.Mesa;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MesaCreateDTO {
    @NotBlank(message = "El número de la mesa es obligatorio.")
    // [1-9] cifra inicial mayor o igual a 1
    // [0-9] segunda cifra en adelante acepta números de 0 a 9
    // Uso de '*' y no '+' al final
    // '+' Busca UNA o más repeticiones. Obliga que mínimo halla 2 dígitos
    // '*' Busca CERO o más repeticiones. Obliga que mínimo halla 1 digite
    // Repetición en regexp: N.º veces que puede repetirse un patrón(caracteres por ejemplo) luego de haberse definido.
    @Pattern(
        regexp = "^[1-9][0-9]*$",
        message = "El número NO puede contener letras y debe ser mayor o igual a 1."
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
