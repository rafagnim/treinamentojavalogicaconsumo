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
        //ItemContrato item, @RequestParam(required = false) String it, String contrato_cpf_cnpj, @RequestParam(required = false) String contrato_valor

        Contrato.itens.add(item);
//        itens.add(contrato);

//        if (contrato.getItens() != null) {
//            List<ItemContratoDTO> itens = contrato.getItens();
//            itens.add(contrato);
//            contrato.setItens(itens);
//        } else {
//            List<ItemContratoDTO> itens = new ArrayList<>();
//            itens.add(contrato);
//            contrato.setItens(itens);
//        }

        try {
            //var contrato = contratoService.contratoPorID(item.getContrato_id()).block();
            //var contrato = contratoService.atualizaItens(item, item.getContrato_id()).block();
            //criar buscar por CPFCNNPF TODO

            //List<ItemContrato> itens = new ArrayList<>();

            //Integer contrato_id = contrato.getId();

//            if (it != "") {
//                String it1[] = it.split(",");
//                for (int i = 0; i < it1.length; i +=4) {
//                    int id = i/4;
//                    String id_duplicata = it1[i+1].substring(15 ,it1[i+1].length() - 1);
//                    Double vl_duplicata = Double.parseDouble(it1[i+2].substring(14));
//                    itens.add (new ItemContrato (id, id_duplicata, vl_duplicata));
//                }
//                item.setId(it1.length/4);
//            } else {
//                item.setId(0);
//            }


            //itens.add(item);

            //contrato.addItemLista(item);

            //redirectAttributes.addFlashAttribute("contrato", contrato);
            //redirectAttributes.addFlashAttribute("itens", itens);
            //redirectAttributes.addFlashAttribute("contrato_id", item.getContrato_id());
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
    public ModelAndView excluir(Integer item_id, String it, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/item/adicionar");
//
//        var contrato = contratoService.excluiItem(item_id).block();

//        var itens = itemService.listarItens(contrato.getId())
//                .flatMapIterable(i -> {return Arrays.asList(i);})
//                .toIterable();

        List<ItemContrato> itens = new ArrayList<>();
        String it1[] = it.split(",");
        Integer contrato_id = 0;
        for (int i = 0; i < it1.length; i +=4) {
            int id = i/4;
            String id_duplicata = it1[i+1].substring(15 ,it1[i+1].length() - 1);
            Double vl_duplicata = Double.parseDouble(it1[i+2].substring(14));
            contrato_id = Integer.parseInt(it1[i+3].substring(13, it1[i+3].length() - 1 ));
            itens.add (new ItemContrato (id, id_duplicata, vl_duplicata));
        }

        List<ItemContrato> itensFiltrados = itens.stream().filter(i -> i.getId() != item_id).collect(Collectors.toList());

        var contrato = contratoService.contratoPorID(contrato_id).block();

        //redirectAttributes.addFlashAttribute("contrato_id", contrato_id);
        redirectAttributes.addFlashAttribute("listaItens", itensFiltrados);
        redirectAttributes.addFlashAttribute("contrato_cpf_cnpj", contrato.getCpf_cnpj());

        return mv;
    }

}
