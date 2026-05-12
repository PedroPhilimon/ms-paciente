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

import com.pacientes.servicio_pacientes.dto.PacienteRequestDTO;
import com.pacientes.servicio_pacientes.model.HistorialPaciente;
import com.pacientes.servicio_pacientes.model.Paciente;
import com.pacientes.servicio_pacientes.service.HistorialPacienteService;
import com.pacientes.servicio_pacientes.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<PacienteRequestDTO>> obtenerTodas() {
        List<PacienteRequestDTO> citas = pacienteService.findAll();
        return ResponseEntity.ok(citas);
    }

    
    @Autowired
    private HistorialPacienteService historialService;

    // Endpoint para ver el historial de un paciente específico
    @GetMapping("/{id}/historial")
    public ResponseEntity<List<HistorialPaciente>> getHistorial(@PathVariable Long id) {
        return ResponseEntity.ok(historialService.obtenerPorPaciente(id));
    }

    // Endpoint para agregar una nueva entrada al historial
    @PostMapping("/historial")
    public ResponseEntity<HistorialPaciente> crearHistorial(@RequestBody HistorialPaciente historial) {
        return ResponseEntity.ok(historialService.guardar(historial));
    }

        @PostMapping
        public ResponseEntity<Paciente> crear(@Valid @RequestBody PacienteRequestDTO pacienteDTO) {

        Paciente nuevoPaciente = new Paciente();
        
        nuevoPaciente.setRun(pacienteDTO.getRun());
        nuevoPaciente.setNombre(pacienteDTO.getNombre());
        nuevoPaciente.setApellido(pacienteDTO.getApellido());
        nuevoPaciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        nuevoPaciente.setPrevision(pacienteDTO.getPrevision());

        return ResponseEntity.status(201).body(pacienteService.save(nuevoPaciente));
    }



    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Long id) {
        try {
            Paciente paciente = pacienteService.findById(id);
            return ResponseEntity.ok(paciente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    

        @PutMapping("/{id}")
        public ResponseEntity<Paciente> actualizar(@PathVariable Long id, @Valid @RequestBody PacienteRequestDTO pacienteDTO) {
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
    public ResponseEntity<Paciente> eliminar(@PathVariable Long id) {
        try {
            pacienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}




