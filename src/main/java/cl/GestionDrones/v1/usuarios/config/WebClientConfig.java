package cl.GestionDrones.v1.usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient pilotosWC(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8081/api/v1/pilotos").build();
    }
}
