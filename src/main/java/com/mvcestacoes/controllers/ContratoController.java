package com.mvcestacoes.controllers;

import com.mvcestacoes.entities.*;
import com.mvcestacoes.services.ContratoService;
import com.mvcestacoes.services.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
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
            var ehValido = contratoService.validaCpfCnpj(Contrato.formataID(contrato.getCpf_cnpj())).block();
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
            Integer[] dataSplit = Contrato.desformataData(dataVencimento);
            LocalDate data = LocalDate.of(dataSplit[0], dataSplit[1], dataSplit[2]);
            ContratoAPI c = new ContratoAPI(null, Contrato.formataID(contrato_cpf_cnpj), data, BigDecimal.valueOf(Double.parseDouble(contrato_valor)));
            var it = Contrato.itens.stream().map(i-> {
                Integer[] dataItemSplit = ItemContrato.desformataData(i.getDataVencimento());
                LocalDate dataItem = LocalDate.of(dataItemSplit[0], dataItemSplit[1], dataItemSplit[2]);
                return new ItemContratoAPI(null, i.getId_duplicata(), dataItem, i.getVl_duplicata());
            }).collect(Collectors.toList());
            c.setItensContrato(it);
            var contratoSalvo = contratoService.cadastrarContrato(c).block();
            Contrato contratoRetorno = new Contrato (contratoSalvo.getId(), contratoSalvo.getCpf_cnpj(), contratoSalvo.getVl_contrato(), Contrato.formataData(contratoSalvo.getDataVencimento()));

            Contrato.itens = new ArrayList<>();
            var itensRetorno = contratoSalvo.getItensContrato().stream().map(i-> {
                String dia = i.getDataVencimento().getDayOfMonth() > 9 ? String.valueOf(i.getDataVencimento().getDayOfMonth()) : "0" + i.getDataVencimento().getDayOfMonth();
                String mes = i.getDataVencimento().getMonthValue() > 9 ? String.valueOf(i.getDataVencimento().getMonthValue()) : "0" + i.getDataVencimento().getMonthValue();
                String dataVencimentoItem = dia + "/" + mes + "/" + i.getDataVencimento().getYear();
                return new ItemContrato(i.getId(), i.getId_duplicata(), dataVencimentoItem, i.getVl_duplicata());
            }).collect(Collectors.toList());

            mv.addObject("itens", itensRetorno);
            mv.addObject("contrato", contratoRetorno);
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
                var contratoSalvo = contratoService.contratoPorID(id).block();

                Contrato contratoRetorno = new Contrato (contratoSalvo.getId(), contratoSalvo.getCpf_cnpj(), contratoSalvo.getVl_contrato(), Contrato.formataData(contratoSalvo.getDataVencimento()));

                var itensRetorno = contratoSalvo.getItensContrato().stream().map(i-> {
                    String dia = i.getDataVencimento().getDayOfMonth() > 9 ? String.valueOf(i.getDataVencimento().getDayOfMonth()) : "0" + i.getDataVencimento().getDayOfMonth();
                    String mes = i.getDataVencimento().getMonthValue() > 9 ? String.valueOf(i.getDataVencimento().getMonthValue()) : "0" + i.getDataVencimento().getMonthValue();
                    String dataVencimentoItem = dia + "/" + mes + "/" + i.getDataVencimento().getYear();
                    return new ItemContrato(i.getId(), i.getId_duplicata(), dataVencimentoItem, i.getVl_duplicata());
                }).collect(Collectors.toList());


                mv.addObject("itens", itensRetorno);
                mv.addObject("contrato", contratoRetorno);
            } else {
                //mv.addObject("contrato_cpf_cnpj", contrato_cpf_cnpj);
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

        var idValido = contratoService.validaCpfCnpj(Contrato.formataID(cpf_cnpj)).block();

        if (idValido) {
            try {
                var contratos = contratoService.listarContratos(Contrato.formataID(cpf_cnpj))
                        .toStream().map(c -> {
                            return new Contrato(c.getId(), c.getCpf_cnpj(), c.getVl_contrato(), Contrato.formataData(c.getDataVencimento()));
                        })                    .collect(Collectors.toList());

                if (contratos.isEmpty()) {
                    redirectAttributes.addFlashAttribute("msg", "Nenhum contrato localizado.");
                } else {
                    redirectAttributes.addFlashAttribute("contratos", contratos);
                    //redirectAttributes.addFlashAttribute("contrato_cpf_cnpj", cpf_cnpj);
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
            Integer parcelas[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
            mv.addObject("listaparcelas",parcelas);
            mv.addObject("taxa", contratoService.obterTaxaPadrao().block());
        }
        return mv;
    }

    @RequestMapping(path = "emprestimovalidar", method = RequestMethod.POST)
    public ModelAndView validarIDEmprestimo(Emprestimo emprestimo, RedirectAttributes redirectAttributes) {
        var isValid = contratoService.validaCpfCnpj(Contrato.formataID(emprestimo.getCpf_cnpj())).block();
        ModelAndView mv = new ModelAndView("redirect:/contrato/emprestimo");

        //emprestimo.setDataNascimento(emprestimo.formataData(emprestimo.getDataNascimento()));

        //String cpf_cnpj, BigDecimal valor, Integer parcelas, LocalDate dataNascimento, List<Parcela> parcelasResultado

        var emprestimoRetorno = contratoService.simulaEmprestimo(new EmprestimoAPI(emprestimo.getCpf_cnpj(), emprestimo.getValor(), emprestimo.getParcelas(), emprestimo.formataData(emprestimo.getDataNascimento()), null)).block();
        redirectAttributes.addFlashAttribute("valido", isValid);
        redirectAttributes.addFlashAttribute("emprestimoretorno", emprestimoRetorno);
        emprestimoRetorno.setTaxaMensal(emprestimoRetorno.getTaxaMensal().multiply(BigDecimal.valueOf(100)).setScale(3, RoundingMode.HALF_EVEN));
        redirectAttributes.addFlashAttribute("parcelas", emprestimoRetorno.getParcelasResultado());
        return mv;
    }

//    @GetMapping("taxas")
//    public Integer obterTaxa(@PathVariable Integer idade) {
//        System.out.println("Veio");
//        return idade;
//    }
}
