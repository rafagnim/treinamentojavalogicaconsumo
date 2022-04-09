package com.mvcestacoes.controllers;

import com.mvcestacoes.entities.EstacaoConsulta;
import com.mvcestacoes.services.EstacoesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@RequestMapping(path = "estacoes")
public class EstacoesMVCController {

    private final EstacoesService estacoesService;

    public EstacoesMVCController(EstacoesService estacoesService) {
        this.estacoesService = estacoesService;
    }

    @RequestMapping(path = "consultar", method = RequestMethod.GET)
    public ModelAndView consultarEstacoes() {
        ModelAndView mv = new ModelAndView("estacoes/consulta.html");
        mv.addObject("estacoes", new EstacaoConsulta());
        var estacoes = estacoesService.consultaListaEstacoes()
                        .flatMapIterable(i -> {return Arrays.asList(i);})
                .toIterable();
        mv.addObject("listaEstacoes", estacoes);
        mv.addObject("estacoes", new EstacaoConsulta());
        return mv;
    }

        @RequestMapping(path = "rotas", method = RequestMethod.POST)
        public ModelAndView exibeMenorRota(EstacaoConsulta estacoes, RedirectAttributes redirectAttributes) {
            ModelAndView mv = new ModelAndView("redirect:/estacoes/consultar");
            var rota = estacoesService.obtemRota(estacoes)
                            .flatMapIterable(i -> {
                                return Arrays.asList(i);
                            })
                            .toIterable();
            redirectAttributes.addFlashAttribute("rota", rota);
            redirectAttributes.addFlashAttribute("estacaoSelected1", estacoes.getEstacaoOrigem());
            redirectAttributes.addFlashAttribute("estacaoSelected2", estacoes.getEstacaoDestino());
            return mv;
        }
}
