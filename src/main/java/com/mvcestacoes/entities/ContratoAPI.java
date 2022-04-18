package com.mvcestacoes.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ContratoAPI {

    private Long id;
    private String cpf_cnpj;
    private LocalDate dataVencimento;
    private BigDecimal vl_contrato;

    public List<ItemContratoAPI> itensContrato;

    public ContratoAPI() {
    }

    public ContratoAPI(Long id, String cpf_cnpj, LocalDate dataVencimento, BigDecimal vl_contrato) {
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.vl_contrato = vl_contrato;
        this.dataVencimento = dataVencimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public BigDecimal getVl_contrato() {
        return vl_contrato;
    }

    public void setVl_contrato(BigDecimal vl_contrato) {
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
