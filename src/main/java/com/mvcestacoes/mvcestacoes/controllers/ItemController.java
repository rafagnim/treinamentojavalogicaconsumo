package com.mvcestacoes.mvcestacoes.controllers;

import com.mvcestacoes.mvcestacoes.entities.Contrato;
import com.mvcestacoes.mvcestacoes.entities.ItemContrato;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;
import java.util.Arrays;

@Controller
@RequestMapping("item")
public class ItemController {

    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:9090/contrato/")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    WebClient webClientItem = WebClient.builder()
            .baseUrl("http://localhost:9090/item/")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @RequestMapping(path = "cadastrar")
    public ModelAndView cadastrarItem() {
        ModelAndView mv = new ModelAndView("item/cadastro.html");
        //mv.addObject("itemcontrato", new ItemContrato());
        return mv;
    }

    @RequestMapping(path = "buscarcontrato")
    public ModelAndView buscarContrato(String cpf_cnpj) {
        ModelAndView mv = new ModelAndView("item/cadastro.html");

        var idValido = webClient.get().uri("/valida/" + cpf_cnpj)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(e -> e.bodyToMono(Boolean.class));

        if (Boolean.TRUE.equals(idValido.block())) {
            try {
                var contratos =
                        webClient.get().uri("/listar/" +cpf_cnpj)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .exchangeToFlux(e -> e.bodyToFlux(Contrato.class))
                                .flatMapIterable(i -> {return Arrays.asList(i);})
                                .toIterable();
                mv.addObject("listaContratos", contratos);
                mv.addObject("contrato_cpf_cnpj", cpf_cnpj);
            } catch (RuntimeException r) {
                mv.addObject("msg", (r.getMessage().split("\\[")[1]).split("\\]")[0]);
            }
        } else {
            mv.addObject("msg", "Informe um CPF ou CNPJ válido");
        }
        return mv;
    }

    @RequestMapping(path = "adicionar")
    public ModelAndView adicionarItemVazio(@RequestParam(required = false) Integer id) {
        ModelAndView mv = new ModelAndView("item/cadastro.html");
        if(id != null) {
            var itens = webClientItem.get().uri("/listar/" + id)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .exchangeToFlux(e -> e.bodyToFlux(ItemContrato.class))
                    .flatMapIterable(i -> {return Arrays.asList(i);})
                    .toIterable();

            var contrato = webClient.get().uri("/consultar/" + id)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .exchangeToMono(e -> e.bodyToMono(Contrato.class)).block();

            mv.addObject("listaItens", itens);
            mv.addObject("itemcontrato", new ItemContrato());
            mv.addObject("contrato_id", id);
            mv.addObject("contrato_cpf_cnpj", contrato.getCpf_cnpj());

        } else {
            mv.addObject("itemcontrato", new ItemContrato());
        }
        return mv;
    }

    @RequestMapping(path = "adicionarpreenchido", method = RequestMethod.POST)
    public ModelAndView adicionarItemPreenchido(ItemContrato item, Integer contrato_id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/item/adicionar");
        try {
            var contrato = webClient.put().uri("/atualizaritens/" + contrato_id)
                    .body(Mono.just(item), ItemContrato.class)
                    .retrieve()
                    .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                            .flatMap(error -> Mono.error(new RuntimeException(error))))
                    .bodyToMono(Contrato.class).block();

            var itens = webClientItem.get().uri("/listar/" + contrato.getId())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .exchangeToFlux(e -> e.bodyToFlux(ItemContrato.class))
                    .flatMapIterable(i -> {return Arrays.asList(i);})
                    .toIterable();

            redirectAttributes.addFlashAttribute("contrato_id", contrato_id);
            redirectAttributes.addFlashAttribute("listaItens", itens);
            redirectAttributes.addFlashAttribute("contrato_cpf_cnpj", contrato.getCpf_cnpj());

        } catch (RuntimeException r) {
            //mv.addObject("msg", (r.getMessage().split("\\[")[1]).split("\\]")[0]);
        }

        return mv;
    }

    @RequestMapping(path = "excluir", method = RequestMethod.GET)
    public ModelAndView excluir(Integer item_id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/item/adicionar");

        var contrato = webClient.put().uri("/excluiitem/" + item_id)
                .body(Mono.just(item_id), Integer.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(error -> Mono.error(new RuntimeException(error))))
                .bodyToMono(Contrato.class).block();

        var itens = webClientItem.get().uri("/listar/" + contrato.getId())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToFlux(e -> e.bodyToFlux(ItemContrato.class))
                .flatMapIterable(i -> {return Arrays.asList(i);})
                .toIterable();

        redirectAttributes.addFlashAttribute("contrato_id", contrato.getId());
        redirectAttributes.addFlashAttribute("listaItens", itens);
        redirectAttributes.addFlashAttribute("contrato_cpf_cnpj", contrato.getCpf_cnpj());

        return mv;
    }

}
