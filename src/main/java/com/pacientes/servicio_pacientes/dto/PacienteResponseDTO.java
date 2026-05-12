package com.pacientes.servicio_pacientes.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PacienteResponseDTO {
    private Long id;
    private String run;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String prevision;
}
