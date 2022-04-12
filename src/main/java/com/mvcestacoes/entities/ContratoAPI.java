package com.mvcestacoes.entities;

import java.time.LocalDate;
import java.util.List;

public class ContratoAPI {

    private Integer id;
    private String cpf_cnpj;
    private LocalDate dataVencimento;
    private Double vl_contrato;

    public List<ItemContratoAPI> itensContrato;

    public ContratoAPI() {
    }

    public ContratoAPI(Integer id, String cpf_cnpj, LocalDate dataVencimento, Double vl_contrato) {
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.vl_contrato = vl_contrato;
        this.dataVencimento = dataVencimento;
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

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public List<ItemContratoAPI> getItensContrato() {
        return itensContrato;
    }

    public void setItensContrato(List<ItemContratoAPI> itensContrato) {
        this.itensContrato = itensContrato;
    }
}
