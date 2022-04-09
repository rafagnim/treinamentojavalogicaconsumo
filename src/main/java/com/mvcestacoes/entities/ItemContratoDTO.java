package com.mvcestacoes.entities;

import java.util.List;

public class ItemContratoDTO {

    private Integer id_item;
    private String id_duplicata;
    private Double vl_duplicata;

    private String cpf_cnpj;
    private Double vl_contrato;
    private List<ItemContratoDTO> itens;

    public ItemContratoDTO() {
    }

    public Integer getId_item() {
        return id_item;
    }

    public void setId_item(Integer id_item) {
        this.id_item = id_item;
    }

    public String getId_duplicata() {
        return id_duplicata;
    }

    public void setId_duplicata(String id_duplicata) {
        this.id_duplicata = id_duplicata;
    }

    public Double getVl_duplicata() {
        return vl_duplicata;
    }

    public void setVl_duplicata(Double vl_duplicata) {
        this.vl_duplicata = vl_duplicata;
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

    public List<ItemContratoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemContratoDTO> itens) {
        this.itens = itens;
    }
}
