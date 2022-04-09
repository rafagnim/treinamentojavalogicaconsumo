package com.mvcestacoes.services;

import com.mvcestacoes.entities.EstacaoConsulta;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EstacoesService {

    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:9090/estacoes")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    public Mono<String[]> consultaListaEstacoes() {
        return webClient.get().uri("/listaestacoes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(e -> e.bodyToMono(String[].class));
    }

    public Mono<String[]> obtemRota(EstacaoConsulta estacoes) {
        String[] consulta = {estacoes.getEstacaoOrigem(), estacoes.getEstacaoDestino()};
        return webClient.post().uri("/rotas")
                .body(Mono.just(consulta), String[].class)
                .retrieve()
                .bodyToMono(String[].class);
    }
}
