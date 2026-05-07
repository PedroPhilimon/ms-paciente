package com.pacientes.servicio_pacientes.dto;

import com.pacientes.servicio_pacientes.model.Paciente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacienteDTO {
    private Long id;
    private String run;
    private String nombre;
    private String apellido;
    private String prevision;

    /*public Producto toModel(){
        return new Producto(id, nombreProducto, precio, descripcion, categoria != null ? categoria.toModel() : null);
    }*/

    /*public static PacienteDTO fromModel(Paciente p) {
        if(p == null) return null;
        return new PacienteDTO(p.getId(), p.getNombre(), p.getApellido(), p.getPrevision(), CategoriaDTO.fromModel(p.getCategoria()));
    } */
}
