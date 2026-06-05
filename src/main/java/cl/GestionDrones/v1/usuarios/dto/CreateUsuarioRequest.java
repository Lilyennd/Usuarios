package cl.GestionDrones.v1.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CreateUsuarioRequest(

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "Debe ingresar un formato de correo válido")
    @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
    String email,

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 20, message = "La contraseña debe tener entre 6 y 20 caracteres")
    String password,

    @NotBlank(message = "El rol es obligatorio")
    @Size(max = 30, message = "El rol no puede superar los 30 caracteres")
    String rol,

    @NotBlank(message = "El RUN es obligatorio")
    @Size(max = 15, message = "El RUN no puede superar los 15 caracteres")
    String run

) {}
