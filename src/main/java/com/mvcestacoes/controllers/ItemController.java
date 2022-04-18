package com.mvcestacoes.controllers;

import com.mvcestacoes.entities.Contrato;
import com.mvcestacoes.entities.ItemContrato;
import com.mvcestacoes.services.ContratoService;
import com.mvcestacoes.services.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @RequestMapping(path = "adicionar")
    public ModelAndView adicionarItemVazio() {
        ModelAndView mv = new ModelAndView("item/cadastro.html");
        mv.addObject("itemcontrato", new ItemContrato());
        if (Contrato.itens != null) mv.addObject(Contrato.itens);
        return mv;
    }

    @RequestMapping(path = "adicionarpreenchido", method = RequestMethod.POST)
    public ModelAndView adicionarItemPreenchido(ItemContrato item, String contrato_cpf_cnpj, String contrato_valor, String dataVencimentoContrato, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/item/adicionar");
        item.setDataVencimento(item.formataData());
        Contrato.itens.add(item);
        Double somaItens = Contrato.itens.stream().mapToDouble(i -> i.getVl_duplicata()).sum();
        if (somaItens > Double.parseDouble(contrato_valor)) redirectAttributes.addFlashAttribute("msgcontrato", "Valor do contrato ultrapassado.");
        redirectAttributes.addFlashAttribute("itens", Contrato.itens);
        redirectAttributes.addFlashAttribute("contrato_cpf_cnpj", contrato_cpf_cnpj);
        redirectAttributes.addFlashAttribute("contrato_valor", contrato_valor);
        redirectAttributes.addFlashAttribute("contrato_dataVencimento", dataVencimentoContrato);

        return mv;
    }

    @RequestMapping(path = "excluir", method = RequestMethod.GET)
    public ModelAndView excluir(String item_id, String vl_id, String contrato_cpf_cnpj, String contrato_valor, String dataVencimento, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/item/adicionar");
        Contrato.itens = Contrato.itens.stream()
                .filter(i -> !(i.getId_duplicata().equals(item_id) && i.getVl_duplicata() == Double.parseDouble(vl_id)))
                .collect(Collectors.toList());
        redirectAttributes.addFlashAttribute("itens", Contrato.itens);
        redirectAttributes.addFlashAttribute("contrato_cpf_cnpj", contrato_cpf_cnpj);
        redirectAttributes.addFlashAttribute("contrato_valor", contrato_valor);
        redirectAttributes.addFlashAttribute("contrato_dataVencimento", dataVencimento);
        return mv;
    }
}
