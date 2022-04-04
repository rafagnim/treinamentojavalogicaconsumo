package com.mvcestacoes.mvcestacoes.controllers;

import com.mvcestacoes.mvcestacoes.entities.Contrato;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("contrato")
public class ContratoController {

    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:9090/contrato")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @RequestMapping(path = "cadastrar")
    public ModelAndView consultarEstacoes() {
        ModelAndView mv = new ModelAndView("contratos/cadastro.html");
        mv.addObject("contrato", new Contrato());
        return mv;
    }

    @RequestMapping(path = "cadastrar", method = RequestMethod.POST)
    public ModelAndView cadastrarContrato(Contrato contrato) {

        ModelAndView mv = new ModelAndView("contratos/cadastro.html");

        try {
            var objetoRetorno = webClient.post().uri("/cadastrar")
                    .body(Mono.just(contrato), Contrato.class)
                    .retrieve()
                    .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                            .flatMap(error -> Mono.error(new RuntimeException(error))))
                    .bodyToMono(Contrato.class)
                    .map(c -> {
                        return "Contrato " + c.getCpf_cnpj() + " R$ " + c.getVl_contrato() + " cadastrado com sucesso";
                    });
            mv.addObject("msg", objetoRetorno.block());
        } catch (RuntimeException r) {
            mv.addObject("msg", (r.getMessage().split("\\[")[1]).split("\\]")[0]);
        }
        return mv;
    }
}
