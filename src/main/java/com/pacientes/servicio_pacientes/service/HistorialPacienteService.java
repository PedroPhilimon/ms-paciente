package com.pacientes.servicio_pacientes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pacientes.servicio_pacientes.model.HistorialPaciente;
import com.pacientes.servicio_pacientes.repository.HistorialPacienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistorialPacienteService {
    private final HistorialPacienteRepository historialRepository;

    public HistorialPaciente guardar(HistorialPaciente historial) {
        return historialRepository.save(historial);
    }

    public List<HistorialPaciente> obtenerPorPaciente(Long pacienteId) {
        return historialRepository.findAll().stream()
                .filter(h -> h.getPaciente().getId().equals(pacienteId))
                .toList();
    }
}
