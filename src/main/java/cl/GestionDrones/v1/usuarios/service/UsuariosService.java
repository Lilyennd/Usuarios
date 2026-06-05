package cl.GestionDrones.v1.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.GestionDrones.v1.usuarios.client.PilotosWebClient;
import cl.GestionDrones.v1.usuarios.dto.CreateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.dto.PilotoResponse;
import cl.GestionDrones.v1.usuarios.dto.UpdateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.exception.ResourceNotFoundException;
import cl.GestionDrones.v1.usuarios.mapper.UsuariosMapper;
import cl.GestionDrones.v1.usuarios.model.Usuarios;
import cl.GestionDrones.v1.usuarios.repository.UsuariosRepository;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuarioRepository;

    @Autowired
    private PilotosWebClient pilotosWebClient;

    public List<Usuarios> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuarios saveUsuario(CreateUsuarioRequest request) {
        Usuarios nuevoUsuario = UsuariosMapper.toUsuario(request);
        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuarios getUsuarioId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "El usuario con ID " + id + " no existe."));
    }

    public Usuarios updateUsuario(Integer id, UpdateUsuarioRequest request) {
        Usuarios usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se puede actualizar: El usuario con ID " + id + " no existe."));

        UsuariosMapper.updateFromDto(request, usuarioExistente);
        return usuarioRepository.save(usuarioExistente);
    }

    public String deleteUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                "No se puede eliminar: El usuario con ID " + id + " no existe.");
        }
        usuarioRepository.deleteById(id);
        return "Usuario eliminado exitosamente.";
    }

    public int totalUsuarios() {
        return (int) usuarioRepository.count();
    }

    public Usuarios obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "El usuario con correo " + email + " no existe."));
    }


    public List<PilotoResponse> obtenerTodosPilotos() {
        return pilotosWebClient.obtenerTodosPilotos();
    }

    public PilotoResponse obtenerPilotoPorRun(String run) {
        PilotoResponse piloto = pilotosWebClient.obtenerPilotoPorRun(run);
        if (piloto == null) {
            throw new ResourceNotFoundException("No se encontró el piloto con RUN: " + run);
        }
        return piloto;
    }
}


