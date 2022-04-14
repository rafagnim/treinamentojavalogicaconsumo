package com.mvcestacoes.services;

import com.mvcestacoes.entities.ContratoAPI;
import com.mvcestacoes.entities.Emprestimo;
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

    public Mono<ContratoAPI> cadastrarContrato(ContratoAPI contrato) {
        return webClient.post().uri("/cadastrar")
                .body(Mono.just(contrato), ContratoAPI.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(error -> Mono.error(new RuntimeException(error))))
                .bodyToMono(ContratoAPI.class);
    }

    public Mono<ContratoAPI> contratoPorID(Integer id) {
        return webClient.get().uri("/consultar/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(e -> e.bodyToMono(ContratoAPI.class));
    }

    public Flux<ContratoAPI> listarContratos(String cpf_cnpj) {
        return webClient.get().uri("/listar/" +cpf_cnpj)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .exchangeToFlux(e -> e.bodyToFlux(ContratoAPI.class));
    }

    public Mono<Emprestimo> simulaEmprestimo(Emprestimo emprestimo) {
        return webClient.post().uri("/emprestimo")
                .body(Mono.just(emprestimo), Emprestimo.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(error -> Mono.error(new RuntimeException(error))))
                .bodyToMono(Emprestimo.class);
    }

//    public Mono<ContratoAPI> atualizaItens(ItemContrato item, Integer contrato_id) {
//        return webClient.put().uri("/atualizaritens/" + contrato_id)
//                .body(Mono.just(item), ItemContrato.class)
//                .retrieve()
//                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
//                        .flatMap(error -> Mono.error(new RuntimeException(error))))
//                .bodyToMono(ContratoAPI.class);
//    }

//    public Mono<ContratoAPI> excluiItem(Integer item_id) {
//        return webClient.put().uri("/excluiitem/" + item_id)
//                .body(Mono.just(item_id), Integer.class)
//                .retrieve()
//                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
//                        .flatMap(error -> Mono.error(new RuntimeException(error))))
//                .bodyToMono(ContratoAPI.class);
//    }
}
