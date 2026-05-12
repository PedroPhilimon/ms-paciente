package com.pacientes.servicio_pacientes.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pacientes.servicio_pacientes.dto.PacienteRequestDTO;
import com.pacientes.servicio_pacientes.dto.PacienteResponseDTO;
import com.pacientes.servicio_pacientes.model.Paciente;
import com.pacientes.servicio_pacientes.repository.PacienteRepository;
import com.pacientes.servicio_pacientes.service.PacienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    @Override
    public List<PacienteResponseDTO> findAll() {
        return pacienteRepository.findAll()
                .stream()
                .map(PacienteResponseDTO::fromEntity) 
                .collect(Collectors.toList());
    }

    @Override
    public PacienteResponseDTO findByDto(Long id) {
        return pacienteRepository.findById(id)
                .map(PacienteResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("No se encontró el paciente con ID: " + id));
    }

    @Override
    public PacienteResponseDTO create(PacienteRequestDTO dto) {
        Paciente paciente = new Paciente();
        
        paciente.setRun(dto.getRun()); 
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setPrevision(dto.getPrevision());

        Paciente guardarPaciente = pacienteRepository.save(paciente);

        //Convertir la entidad guardada de vuelta a DTO para la respuesta
        return PacienteResponseDTO.fromEntity(guardarPaciente);
    }

   @Override
    public PacienteResponseDTO update(Long id, PacienteRequestDTO dto) {
        // 1. Buscar el paciente existente o lanzar error si no existe
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: Paciente no encontrado con ID: " + id));

        // 2. Actualizar los campos con la información del DTO
        // IMPORTANTE: Mantener o actualizar el RUN y el resto de los datos
        paciente.setRun(dto.getRun()); 
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setPrevision(dto.getPrevision());

        // Nota: No es necesario hacer paciente.setId(id) porque ya lo obtuvimos por su ID
        // y JPA reconoce que debe actualizar ese registro específico.

        // 3. Guardar los cambios
        Paciente actualizarPaciente = pacienteRepository.save(paciente);

        // 4. Retornar la respuesta convertida a DTO
        return PacienteResponseDTO.fromEntity(actualizarPaciente);
    }


    @Override
    public void delete(Long id) {
        // 1. Verificar si existe (reutilizamos la lógica de buscar)
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Paciente no encontrado con ID: " + id);
        }   
    
        // 2. Eliminar de la base de datos
        pacienteRepository.deleteById(id);
    }
    
}

    

