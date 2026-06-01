package cl.GestionDrones.v1.usuarios.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UpdateUsuarioRequest(

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "Debe ingresar un formato de correo válido")
    @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
    String correo,

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(max = 100, message = "La contraseña no puede superar los 100 caracteres")
    String password,

    @NotBlank(message = "El rol es obligatorio")
    @Size(max = 50, message = "El rol no puede superar los 50 caracteres")
    String rol  
) {
}