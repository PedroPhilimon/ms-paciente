package com.pacientes.servicio_pacientes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pacientes.servicio_pacientes.dto.PacienteRequestDTO;
import com.pacientes.servicio_pacientes.model.Paciente;
import com.pacientes.servicio_pacientes.repository.PacienteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<PacienteRequestDTO> findAll() {
        return pacienteRepository.findAll() 
                .stream()
                .map(this::mapToResponseDTO) 
                .collect(Collectors.toList());
    }
    
    public Paciente findById(long id) {
        return pacienteRepository.findById(id).get();
    }

    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void delete(Long id) {
        pacienteRepository.deleteById(id);
    }


    private PacienteRequestDTO mapToResponseDTO(Paciente paciente) {
        return PacienteRequestDTO.builder()
                .id(paciente.getId())
                .run(paciente.getRun())
                .nombre(paciente.getNombre())
                .apellido(paciente.getApellido())
                .fechaNacimiento(paciente.getFechaNacimiento())
                .prevision(paciente.getPrevision())
                .build();
    }
}
