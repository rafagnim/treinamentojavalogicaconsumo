package com.mvcestacoes.mvcestacoes.controllers;

import com.mvcestacoes.mvcestacoes.entities.EstacaoConsulta;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Controller
@RequestMapping(path = "estacoes")
public class EstacoesMVCController {

    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:9090/estacoes")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @RequestMapping(path = "consultar", method = RequestMethod.GET)
    public ModelAndView consultarEstacoes() {
        ModelAndView mv = new ModelAndView("estacoes/consulta.html");
        mv.addObject("estacoes", new EstacaoConsulta());
        var estacoes =  webClient.get().uri("/listaestacoes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(e -> e.bodyToMono(String[].class))
                .flatMapIterable(i -> {return Arrays.asList(i);})
                .toIterable();
        mv.addObject("listaEstacoes", estacoes);
        mv.addObject("estacoes", new EstacaoConsulta());
        return mv;
    }

        @RequestMapping(path = "rotas", method = RequestMethod.POST)
        public ModelAndView exibeMenorRota(EstacaoConsulta estacoes) {
            ModelAndView mv = new ModelAndView("estacoes/rota.html");
            String[] consulta = {estacoes.getEstacaoOrigem(), estacoes.getEstacaoDestino()};
            var rota = webClient.post().uri("/rotas")
                    //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(consulta), String[].class)
                    .retrieve()
                    .bodyToMono(String[].class)
                    .flatMapIterable(i -> {
                        return Arrays.asList(i);
                    })
                    .toIterable();

            mv.addObject("rota", rota);
            return mv;
        }
}
