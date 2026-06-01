package cl.GestionDrones.v1.usuarios.mapper;

import cl.GestionDrones.v1.usuarios.dto.CreateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.dto.UpdateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.model.Usuarios;

public class UsuariosMapper {

    public static Usuarios toEntity(CreateUsuarioRequest request) {
        if (request == null) {
            return null;
        }

        Usuarios usuario = new Usuarios();
        usuario.setCorreo(request.correo());
        usuario.setPassword(request.password());        
        usuario.setRol(request.rol());
        
        return usuario;
    }

    public static Usuarios toEntity(UpdateUsuarioRequest request) {
        if (request == null) {
            return null;
        }

        Usuarios usuario = new Usuarios();
        usuario.setCorreo(request.correo());
        usuario.setPassword(request.password());
        usuario.setRol(request.rol());
        
        return usuario;
    }
}