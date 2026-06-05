
package cl.GestionDrones.v1.usuarios.mapper;

import cl.GestionDrones.v1.usuarios.dto.CreateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.dto.UpdateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.model.Usuarios;

public class UsuariosMapper {

    public static Usuarios toUsuario(CreateUsuarioRequest request) {
        return new Usuarios(
            null,
            request.email(),
            request.password(),
            request.rol(),
            request.run()
        );
    }

    public static void updateFromDto(UpdateUsuarioRequest request, Usuarios usuario) {
        usuario.setEmail(request.email());
        usuario.setPassword(request.password());
        usuario.setRol(request.rol());
        usuario.setRun(request.run());
    }
}