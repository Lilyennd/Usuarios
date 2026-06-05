package cl.GestionDrones.v1.usuarios.client;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.GestionDrones.v1.usuarios.dto.PilotoResponse;

@Service
public class PilotosWebClient {

    private final WebClient webClient;

    public PilotosWebClient(@Qualifier("pilotosWC") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<PilotoResponse> obtenerTodosPilotos() {
        try {
            return webClient.get()
                    .uri("")
                    .retrieve()
                    .bodyToFlux(PilotoResponse.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            System.out.println("Error al obtener pilotos: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // GET piloto por RUN
    public PilotoResponse obtenerPilotoPorRun(String run) {
        try {
            return webClient.get()
                    .uri("/run/" + run)
                    .retrieve()
                    .bodyToMono(PilotoResponse.class)
                    .block();
        } catch (Exception e) {
            System.out.println("Error al obtener piloto con RUN " + run + ": " + e.getMessage());
            return null;
        }
    }
}