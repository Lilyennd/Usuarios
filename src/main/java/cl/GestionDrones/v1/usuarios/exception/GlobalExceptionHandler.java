package cl.GestionDrones.v1.usuarios.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        System.out.println("✅ GlobalExceptionHandler de Usuarios REGISTRADO CORRECTAMENTE");
    }


    @ExceptionHandler(EmailException.class)
    public ResponseEntity<Map<String, Object>> handleEmailException(EmailException ex) {
        System.out.println("📧 ERROR DE EMAIL DETECTADO: " + ex.getMessage());

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Email Conflict");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        System.out.println("骨 Error de validación detectado en los datos del Usuario");

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "Error de validación en los datos enviados");

        // Mapeamos cada campo con su respectivo error
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String campo = error.getField();
            String mensaje = error.getDefaultMessage() != null ? error.getDefaultMessage() : "Valor inválido";
            errors.put(campo, mensaje);
        }
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleJsonParseError(HttpMessageNotReadableException ex) {
        System.out.println("🟡 Error de lectura en el JSON enviado");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Error al procesar el JSON. Asegúrate de que las fechas tengan el formato ISO (AAAA-MM-DD)"
        );

        problem.setTitle("JSON Parse Error");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("detalle_tecnico", ex.getMostSpecificCause().getMessage());
        return problem;
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        System.out.println("🟡 RECURSO NO ENCONTRADO: " + ex.getMessage());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, // Código 404: Not Found
                ex.getMessage()
        );

        problem.setTitle("Resource Not Found");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }


}