package cl.GestionDrones.v1.usuarios.service;

import java.util.List;
import org.springframework.stereotype.Service;

import cl.GestionDrones.v1.usuarios.dto.CreateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.dto.UpdateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.exception.EmailException;
import cl.GestionDrones.v1.usuarios.mapper.UsuariosMapper;
import cl.GestionDrones.v1.usuarios.model.Usuarios;
import cl.GestionDrones.v1.usuarios.repository.UsuariosRepository;

@Service
public class UsuariosService {
    
    private final UsuariosRepository usuariosRepository;

    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public List<Usuarios> listarTodos() {
        return usuariosRepository.findAll();
    }

    public Usuarios obtenerPorId(Integer id) {
    return usuariosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el ID: " + id));
    }

    public Usuarios crear(CreateUsuarioRequest request) {
    if (usuariosRepository.existsByCorreo(request.correo())) {
        // Lanzamos tu nueva excepción específica
        throw new EmailException("El correo '" + request.correo() + "' ya se encuentra registrado");
    }
    
    Usuarios nuevoUsuario = UsuariosMapper.toEntity(request);
    return usuariosRepository.save(nuevoUsuario);
}

public Usuarios actualizar(Integer id, UpdateUsuarioRequest request) {
    Usuarios usuarioExistente = usuariosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el ID: " + id));
    if (request.password() != null && !request.password().isBlank()) {
        usuarioExistente.setPassword(request.password());
    }

    if (request.correo() != null && !request.correo().isBlank()) {
        if (!usuarioExistente.getCorreo().equals(request.correo()) && 
            usuariosRepository.existsByCorreo(request.correo())) {
            throw new EmailException("El correo '" + request.correo() + "' ya está en uso por otro usuario");
        }
        usuarioExistente.setCorreo(request.correo());
    }
    return usuariosRepository.save(usuarioExistente);
}

    public void eliminar(Integer id) {
        if (!usuariosRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, usuario no encontrado con el ID: " + id);
        }
        usuariosRepository.deleteById(id);
    }
}