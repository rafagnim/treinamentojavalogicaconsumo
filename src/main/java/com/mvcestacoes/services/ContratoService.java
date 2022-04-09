package com.mvcestacoes.services;

import com.mvcestacoes.entities.Contrato;
import com.mvcestacoes.entities.ItemContrato;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ContratoService {

    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:9090/contrato")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    public Mono<Boolean> validaCpfCnpj(String cpf_cnpj) {
        return webClient.get().uri("/valida/" + cpf_cnpj)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(e -> e.bodyToMono(Boolean.class));
    }

    public Mono<Contrato> cadastrarContrato(Contrato contrato) {
        return webClient.post().uri("/cadastrar")
                .body(Mono.just(contrato), Contrato.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(error -> Mono.error(new RuntimeException(error))))
                .bodyToMono(Contrato.class);
    }

    public Mono<Contrato> contratoPorID(Integer id) {
        return webClient.get().uri("/consultar/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(e -> e.bodyToMono(Contrato.class));
    }

    public Flux<Contrato> listarContratos(String cpf_cnpj) {
        return webClient.get().uri("/listar/" +cpf_cnpj)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .exchangeToFlux(e -> e.bodyToFlux(Contrato.class));
    }

    public Mono<Contrato> atualizaItens(ItemContrato item, Integer contrato_id) {
        return webClient.put().uri("/atualizaritens/" + contrato_id)
                .body(Mono.just(item), ItemContrato.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(error -> Mono.error(new RuntimeException(error))))
                .bodyToMono(Contrato.class);
    }

    public Mono<Contrato> excluiItem(Integer item_id) {
        return webClient.put().uri("/excluiitem/" + item_id)
                .body(Mono.just(item_id), Integer.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(error -> Mono.error(new RuntimeException(error))))
                .bodyToMono(Contrato.class);
    }
}
