package com.mvcestacoes.controllers;

import com.mvcestacoes.entities.Contrato;
import com.mvcestacoes.entities.ItemContrato;
import com.mvcestacoes.services.ContratoService;
import com.mvcestacoes.services.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("item")
public class ItemController {

    private final ContratoService contratoService;
    private final ItemService itemService;

    public ItemController(ContratoService contratoService, ItemService itemService) {
        this.contratoService = contratoService;
        this.itemService = itemService;
    }

//    @RequestMapping(path = "cadastrar")
//    public ModelAndView cadastrarItem() {
//        ModelAndView mv = new ModelAndView("item/cadastro.html");
//        //mv.addObject("itemcontrato", new ItemContrato());
//        return mv;
//    }

//    @RequestMapping(path = "buscarcontrato")
//    public ModelAndView buscarContrato(String cpf_cnpj) {
//        ModelAndView mv = new ModelAndView("item/cadastro.html");
//
//        var idValido = contratoService.validaCpfCnpj(cpf_cnpj).block();
//
//        if (idValido) {
//            try {
//                var contratos = contratoService.listarContratos(cpf_cnpj)
//                                .flatMapIterable(i -> {return Arrays.asList(i);})
//                                .toIterable();
//                mv.addObject("listaContratos", contratos);
//                mv.addObject("contrato_cpf_cnpj", cpf_cnpj);
//            } catch (RuntimeException r) {
//                mv.addObject("msg", (r.getMessage().split("\\[")[1]).split("\\]")[0]);
//            }
//        } else {
//            mv.addObject("msg", "Informe um CPF ou CNPJ válido");
//        }
//        return mv;
//    }

    @RequestMapping(path = "adicionar")
    public ModelAndView adicionarItemVazio() {
        ModelAndView mv = new ModelAndView("item/cadastro.html");

//        System.out.println(teste);
//        System.out.println(itens.get(0).getVl_duplicata());
       // if(id != null) {


//            var itens = itemService.listarItens(id)
//                    .toStream().collect(Collectors.toList());


//                    .flatMapIterable(i -> {return Arrays.asList(i);})
//                    .toIterable();

            //var contrato = contratoService.contratoPorID(id).block();

            //mv.addObject("itens", itens);
           mv.addObject("itemcontrato", new ItemContrato());
           if (Contrato.itens != null) mv.addObject(Contrato.itens);
           // mv.addObject("contrato_id", id);
            //mv.addObject("contrato_cpf_cnpj", contrato.getCpf_cnpj());
           // contrato.addItemLista(new ItemContrato(0, "oi", 23D));
            //mv.addObject("contrato", contrato);

        //} else {
           // mv.addObject("itemcontrato", new ItemContrato());
       // }
        //redirectAttributes.addAttribute(itens);
        return mv;
    }

    @RequestMapping(path = "adicionarpreenchido", method = RequestMethod.POST)
    public ModelAndView adicionarItemPreenchido(ItemContrato item, String contrato_cpf_cnpj, String contrato_valor, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/item/adicionar");
        Contrato.itens.add(item);
        try {
            redirectAttributes.addFlashAttribute("itens", Contrato.itens);
            redirectAttributes.addFlashAttribute("contrato_cpf_cnpj", contrato_cpf_cnpj);
            redirectAttributes.addFlashAttribute("contrato_valor", contrato_valor);

        } catch (RuntimeException r) {
            //mv.addObject("msg", (r.getMessage().split("\\[")[1]).split("\\]")[0]);
            System.out.println(r.getMessage());
        }
        return mv;
    }

    @RequestMapping(path = "excluir", method = RequestMethod.GET)
    public ModelAndView excluir(String item_id, String vl_id, String contrato_cpf_cnpj, String contrato_valor, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/item/adicionar");
        Contrato.itens = Contrato.itens.stream()
                .filter(i -> !(i.getId_duplicata().equals(item_id) && i.getVl_duplicata() == Double.parseDouble(vl_id)))
                .collect(Collectors.toList());
        redirectAttributes.addFlashAttribute("itens", Contrato.itens);
        redirectAttributes.addFlashAttribute("contrato_cpf_cnpj", contrato_cpf_cnpj);
        redirectAttributes.addFlashAttribute("contrato_valor", contrato_valor);
        return mv;
    }
}
