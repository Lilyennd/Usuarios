package cl.GestionDrones.v1.usuarios.client;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.GestionDrones.v1.usuarios.dto.PilotoResponse;

@Component
public class PilotosWebClient {

    private final WebClient webClient;

    
    public PilotosWebClient(@Qualifier("pilotosWC") WebClient webClient) {
        this.webClient = webClient;
    }

    // GET - Obtener todos los pilotos
    public List<PilotoResponse> obtenerTodosPilotos() {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.build()) 
                    .retrieve()
                    .bodyToFlux(PilotoResponse.class)
                    .collectList()
                    .block(); 
        } catch (Exception e) {
            System.err.println("🔴 Error al obtener pilotos: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public PilotoResponse obtenerPilotoPorRun(String run) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/run/{run}").build(run)) // ◄ Construcción limpia y segura de parámetros variables
                    .retrieve()
                    .bodyToMono(PilotoResponse.class)
                    .block();
        } catch (Exception e) {
            System.err.println("🔴 Error al obtener piloto con RUN " + run + ": " + e.getMessage());
            return null;
        }
    }
}