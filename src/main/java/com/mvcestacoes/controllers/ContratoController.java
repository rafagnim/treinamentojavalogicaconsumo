package com.mvcestacoes.controllers;

import com.mvcestacoes.entities.Contrato;
import com.mvcestacoes.entities.Emprestimo;
import com.mvcestacoes.services.ContratoService;
import com.mvcestacoes.services.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;


@Controller
@RequestMapping("contrato")
public class ContratoController {

    private final ContratoService contratoService;
    private final ItemService itemService;

    public ContratoController(ContratoService contratoService, ItemService itemService) {
        this.contratoService = contratoService;
        this.itemService = itemService;
    }

    @RequestMapping(path = "cadastrar")
    public ModelAndView consultarEstacoes() {
        ModelAndView mv = new ModelAndView("contratos/cadastro.html");
        mv.addObject("contrato", new Contrato());
        return mv;
    }

    @RequestMapping(path = "cadastrar", method = RequestMethod.POST)
    public ModelAndView cadastrarContrato(Contrato contrato, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView("redirect:/item/adicionar");
        Contrato.itens = new ArrayList<>();
        try {
            var ehValido = contratoService.validaCpfCnpj(contrato.getCpf_cnpj()).block();
            if (ehValido) {
                redirectAttributes.addFlashAttribute("contrato_cpf_cnpj", contrato.getCpf_cnpj());
                redirectAttributes.addFlashAttribute("contrato_valor", contrato.getVl_contrato());
                redirectAttributes.addFlashAttribute("contrato_dataVencimento", contrato.formataData());
            } else {
                redirectAttributes.addFlashAttribute("msg", "Informe um CPF ou CNPJ válido.");
            }
        } catch (RuntimeException r) {
            redirectAttributes.addFlashAttribute("msg", (r.getMessage().split("\\[")[1]).split("\\]")[0]);
        }
        return mv;
    }

    @RequestMapping(path = "persistircontrato")
    public ModelAndView persistirContrato(String contrato_cpf_cnpj, String contrato_valor, String dataVencimento, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView("contratos/consultar.html");
        try {
            Contrato c = new Contrato(null, contrato_cpf_cnpj, Double.parseDouble(contrato_valor), dataVencimento);
            var it = Contrato.itens;
            c.setItensContrato(Contrato.itens);
            var contratoSalvo = contratoService.cadastrarContrato(c).block();
            Contrato.itens = new ArrayList<>();
            mv.addObject("itens", contratoSalvo.getItensContrato());
            mv.addObject("contrato", contratoSalvo);
            mv.addObject("msg", "Contrato cadastrado com sucesso!");
            } catch (RuntimeException r) {
            redirectAttributes.addFlashAttribute("msg", (r.getMessage().split("\\[")[1]).split("\\]")[0]);
        }
        return mv;
    }

    @RequestMapping(path = "consultar")
    public ModelAndView consultarContrato(Integer id, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView("contratos/consultar.html");
        try {
            if (id != null) {
                var itens = itemService.listarItens(id)
                        .toStream().collect(Collectors.toList());
                var contrato = contratoService.contratoPorID(id).block();
                mv.addObject("itens", itens);
                mv.addObject("contrato", contrato);
            }
        } catch (RuntimeException r) {
            mv.addObject("msg", (r.getMessage().split("\\[")[1]).split("\\]")[0]);
        }
        return mv;
    }

    @RequestMapping(path = "buscarcontratos")
    public ModelAndView buscarContrato(String cpf_cnpj, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/contrato/consultar");

        if (cpf_cnpj == null) {
            mv.addObject("msg", "Informe um CPF ou CNPJ");
            return mv;
        }

        var idValido = contratoService.validaCpfCnpj(cpf_cnpj).block();

        if (idValido) {
            try {
                var contratos = contratoService.listarContratos(cpf_cnpj)
                        .toStream().collect(Collectors.toList());

                if (contratos.isEmpty()) {
                    redirectAttributes.addFlashAttribute("msg", "Nenhum contrato localizado.");
                } else {
                    redirectAttributes.addFlashAttribute("contratos", contratos);
                    redirectAttributes.addFlashAttribute("contrato_cpf_cnpj", cpf_cnpj);
                }
            } catch (RuntimeException r) {
                redirectAttributes.addFlashAttribute("msg", (r.getMessage().split("\\[")[1]).split("\\]")[0]);
            }
        } else {
            redirectAttributes.addFlashAttribute("msg", "Informe um CPF ou CNPJ válido.");
        }
        return mv;
    }

    @RequestMapping(path = "emprestimo")
    public ModelAndView simularEmprestimo(@RequestParam(required = false) Emprestimo emprestimo) {
        ModelAndView mv = new ModelAndView("contratos/emprestimo.html");
        if (emprestimo == null) {
            mv.addObject("emprestimo", new Emprestimo());
        }
        return mv;
    }

    @RequestMapping(path = "emprestimovalidar", method = RequestMethod.POST)
    public ModelAndView validarIDEmprestimo(Emprestimo emprestimo, RedirectAttributes redirectAttributes) {
        System.out.println(emprestimo.getCpf_cnpj());
        var isValid = contratoService.validaCpfCnpj(emprestimo.getCpf_cnpj()).block();
        ModelAndView mv = new ModelAndView("redirect:/contrato/emprestimo");
        redirectAttributes.addFlashAttribute("valido", isValid);
        redirectAttributes.addFlashAttribute("emprestimo", emprestimo);
        //"redirect:/item/adicionar")
        //mv.addObject("emprestimo", emprestimo);
        return mv;
    }
}
