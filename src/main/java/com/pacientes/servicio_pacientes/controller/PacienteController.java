package com.pacientes.servicio_pacientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pacientes.servicio_pacientes.model.Paciente;
import com.pacientes.servicio_pacientes.service.PacienteService;

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

public ResponseEntity<Paciente> actualizar(@PathVariable Integer id, @RequestBody Paciente paciente) {

    try{

        Paciente pac = pacienteService.findById(id);

        pac.setId(id);

        pac.setRun(paciente.getRun());

        pac.setNombre(paciente.getNombre());

        pac.setApellidos(paciente.getApellidos());

        pac.setFechaNacimiento(paciente.getFechaNacimiento());

        pac.setCorreo(paciente.getCorreo());



        pacienteService.save(pac);

       

        return ResponseEntity.ok(paciente);

    } catch (Exception e) {

        return ResponseEntity.notFound().build();

    }

}



}
