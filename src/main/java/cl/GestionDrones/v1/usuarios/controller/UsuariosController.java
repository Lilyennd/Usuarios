package cl.GestionDrones.v1.usuarios.controller;


import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.GestionDrones.v1.usuarios.dto.CreateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.dto.UpdateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.model.Usuarios;
import cl.GestionDrones.v1.usuarios.service.UsuariosService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping
    public ResponseEntity<List<Usuarios>> listarTodos() {
        return ResponseEntity.ok(usuariosService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuariosService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CreateUsuarioRequest request, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, Object> respuestaError = new HashMap<>();
            respuestaError.put("timestamp", Instant.now());
            respuestaError.put("status", HttpStatus.BAD_REQUEST.value()); // 400
            respuestaError.put("error", "Bad Request");
            respuestaError.put("message", "Error de validación en los datos enviados");

            Map<String, String> erroresDetallados = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                erroresDetallados.put(error.getField(), error.getDefaultMessage());
            }
            respuestaError.put("errors", erroresDetallados);

            return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
        }

        Usuarios nuevoUsuario = usuariosService.crear(request);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id, 
            @Valid @RequestBody UpdateUsuarioRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            Map<String, Object> respuestaError = new HashMap<>();
            respuestaError.put("timestamp", Instant.now());
            respuestaError.put("status", HttpStatus.BAD_REQUEST.value()); // 400
            respuestaError.put("error", "Bad Request");
            respuestaError.put("message", "Error de validación al actualizar el usuario");

            Map<String, String> erroresDetallados = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                erroresDetallados.put(error.getField(), error.getDefaultMessage());
            }
            respuestaError.put("errors", erroresDetallados);

            return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
        }

        Usuarios usuarioActualizado = usuariosService.actualizar(id, request);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        usuariosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}