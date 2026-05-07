package com.pacientes.servicio_pacientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pacientes.servicio_pacientes.dto.PacienteDTO;
import com.pacientes.servicio_pacientes.model.Paciente;
import com.pacientes.servicio_pacientes.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        List<Paciente> pacientes = pacienteService.findAll();

        if(pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pacientes);
    }

    @PostMapping
public ResponseEntity<Paciente> crear(@Valid @RequestBody PacienteDTO pacienteDTO) {
    Paciente nuevoPaciente = new Paciente();
    nuevoPaciente.setRun(pacienteDTO.getRun());
    nuevoPaciente.setNombre(pacienteDTO.getNombre());
    nuevoPaciente.setApellido(pacienteDTO.getApellido());
    nuevoPaciente.setPrevision(pacienteDTO.getPrevision());
    
    return ResponseEntity.status(201).body(pacienteService.save(nuevoPaciente));
}

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Integer id) {
        try {
            Paciente paciente = pacienteService.findById(id);
            return ResponseEntity.ok(paciente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(@PathVariable Long id, @Valid @RequestBody PacienteDTO pacienteDTO) {
    try {
        Paciente pac = pacienteService.findById(id);
        
        if (pac == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualizamos los datos desde el DTO
        pac.setRun(pacienteDTO.getRun());
        pac.setNombre(pacienteDTO.getNombre());
        pac.setApellido(pacienteDTO.getApellido()); // En singular como tu modelo
        pac.setPrevision(pacienteDTO.getPrevision());

        pacienteService.save(pac);
        return ResponseEntity.ok(pac);
        
    } catch (Exception e) {
        return ResponseEntity.internalServerError().build();
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            pacienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}




