package cl.GestionDrones.v1.usuarios.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.GestionDrones.v1.usuarios.dto.CreateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.dto.PilotoResponse;
import cl.GestionDrones.v1.usuarios.dto.UpdateUsuarioRequest;
import cl.GestionDrones.v1.usuarios.model.Usuarios;
import cl.GestionDrones.v1.usuarios.service.UsuariosService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuarios>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.getUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(usuarioService.getUsuarioId(id));
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No encontrado");
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping
        public ResponseEntity<?> crearUsuario(
        @Valid @RequestBody CreateUsuarioRequest request, 
        BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
        }

        try {
            Usuarios nuevoUsuario = usuarioService.saveUsuario(request);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear");
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateUsuarioRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
        }

        try {
            Usuarios usuarioActualizado = usuarioService.updateUsuario(id, request);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar");
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        try {
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", usuarioService.deleteUsuario(id));
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar");
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // ENDPOINTS DE PILOTOS
    @GetMapping("/pilotos")
    public ResponseEntity<?> listarTodosPilotos() {
        try {
            List<PilotoResponse> pilotos = usuarioService.obtenerTodosPilotos();
            if (pilotos.isEmpty()) {
                Map<String, String> mensaje = new HashMap<>();
                mensaje.put("mensaje", "No hay pilotos registrados");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
            }
            return ResponseEntity.ok(pilotos);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al obtener pilotos");
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/pilotos/run/{run}")
    public ResponseEntity<?> obtenerPilotoPorRun(@PathVariable String run) {
        try {
            PilotoResponse piloto = usuarioService.obtenerPilotoPorRun(run);
            return ResponseEntity.ok(piloto);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No encontrado");
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}

