package com.mvcestacoes.controllers;

import com.mvcestacoes.entities.Contrato;
import com.mvcestacoes.entities.Emprestimo;
import com.mvcestacoes.services.ContratoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;


@Controller
@RequestMapping("contrato")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
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
            } else {
                redirectAttributes.addFlashAttribute("msg", "Informe um CPF ou CNPJ válido.");
            }
        } catch (RuntimeException r) {
            redirectAttributes.addFlashAttribute("msg", (r.getMessage().split("\\[")[1]).split("\\]")[0]);
        }
        return mv;
    }

    @RequestMapping(path = "persistircontrato")
    public ModelAndView persistirContrato(String contrato_cpf_cnpj, String contrato_valor, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView("contratos/consultar.html"); //<< TODO:

        Contrato c = new Contrato(null, contrato_cpf_cnpj, Double.parseDouble(contrato_valor));
        var it = Contrato.itens;
        c.setItensContrato(Contrato.itens);
        var contratoSalvo = contratoService.cadastrarContrato(c).block();

        mv.addObject("itens", contratoSalvo.getItensContrato());
        mv.addObject("contrato", contratoSalvo);
//        Contrato.itens = new ArrayList<>();
//        try {
//            var ehValido = contratoService.validaCpfCnpj(contrato.getCpf_cnpj()).block();
//            if (ehValido) {
//                redirectAttributes.addFlashAttribute("contrato_cpf_cnpj", contrato.getCpf_cnpj());
//                redirectAttributes.addFlashAttribute("contrato_valor", contrato.getVl_contrato());
//            } else {
//                redirectAttributes.addFlashAttribute("msg", "Informe um CPF ou CNPJ válido.");
//            }
//        } catch (RuntimeException r) {
//            redirectAttributes.addFlashAttribute("msg", (r.getMessage().split("\\[")[1]).split("\\]")[0]);
//        }
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
