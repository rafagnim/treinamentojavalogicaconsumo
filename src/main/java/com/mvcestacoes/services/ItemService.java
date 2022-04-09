package com.mvcestacoes.services;

import com.mvcestacoes.entities.ItemContrato;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ItemService {

    WebClient webClientItem = WebClient.builder()
            .baseUrl("http://localhost:9090/item/")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    public Flux<ItemContrato> listarItens(Integer id) {
        return webClientItem.get().uri("/listar/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToFlux(e -> e.bodyToFlux(ItemContrato.class));
    }
}
