package com.mvcestacoes.entities;

import java.time.LocalDate;
import java.util.List;


public class Contrato {

    private Integer id;
    private String cpf_cnpj;
    private Double vl_contrato;
    private String dataVencimento;
    public List<ItemContrato> itensContrato;
    public static List<ItemContrato> itens;

    public Contrato() {
    }

    public Contrato(Integer id, String cpf_cnpj, Double vl_contrato, String dataVencimento) {
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.vl_contrato = vl_contrato;
        this.dataVencimento = dataVencimento;
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

    public List<ItemContrato> getItensContrato() {
        return itensContrato;
    }

    public void setItensContrato(List<ItemContrato> itensContrato) {
        this.itensContrato = itensContrato;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String formataData() {
        String[] data = this.dataVencimento.split("-");
        return data[2] + "/" + data[1] + "/" + data[0];
    }

    public static String formataData(LocalDate data) {

        String dia = data.getDayOfMonth() > 9 ? String.valueOf(data.getDayOfMonth()) : "0" + data.getDayOfMonth();
        String mes = data.getMonthValue() > 9 ? String.valueOf(data.getMonthValue()) : "0" + data.getMonthValue();
        String dataVencimentoRetorno = dia + "/" + mes + "/" + data.getYear();
        return dataVencimentoRetorno;
    }

    public static Integer[] desformataData(String d) {
        String[] data = d.split("/");
        Integer[] retorno = new Integer[3];
        retorno[0] = Integer.parseInt(data[2]);
        retorno[1] = Integer.parseInt(data[1]);
        retorno[2] = Integer.parseInt(data[0]);
        return retorno;
    }
}
