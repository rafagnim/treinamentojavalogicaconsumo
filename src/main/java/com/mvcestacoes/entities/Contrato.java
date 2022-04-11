package com.mvcestacoes.entities;

import java.util.List;


public class Contrato {

    private Integer id;
    private String cpf_cnpj;
    private Double vl_contrato;
    public List<ItemContrato> itensContrato;
    public static List<ItemContrato> itens;

    public Contrato() {
    }

    public Contrato(Integer id, String cpf_cnpj, Double vl_contrato) {
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.vl_contrato = vl_contrato;
    }

    public void addItemLista(ItemContrato i) {
        this.itens.add(i);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public Double getVl_contrato() {
        return vl_contrato;
    }

    public void setVl_contrato(Double vl_contrato) {
        this.vl_contrato = vl_contrato;
    }

//    public List<ItemContrato> getItens() {
//        return itens;
//    }
//
//    public void setItens(List<ItemContrato> itens) {
//        this.itens = itens;
//    }


    public List<ItemContrato> getItensContrato() {
        return itensContrato;
    }

    public void setItensContrato(List<ItemContrato> itensContrato) {
        this.itensContrato = itensContrato;
    }
}
