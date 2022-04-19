package com.mvcestacoes.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class EmprestimoAPI {


    private String cpf_cnpj;
    private BigDecimal valor;
    private Integer parcelas;
    private LocalDate dataNascimento;
    private String carencia;

    private BigDecimal taxaMensal;
    private String taxaAnual;
    private List<Parcela> parcelasResultado;

    public EmprestimoAPI() {
    }

    public EmprestimoAPI(String cpf_cnpj, BigDecimal valor, Integer parcelas, LocalDate dataNascimento, String carencia, List<Parcela> parcelasResultado) {
        this.cpf_cnpj = cpf_cnpj;
        this.valor = valor;
        this.parcelas = parcelas;
        this.dataNascimento = dataNascimento;
        this.carencia = carencia;
        this.parcelasResultado = parcelasResultado;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public List<Parcela> getParcelasResultado() {
        return parcelasResultado;
    }

    public void setParcelasResultado(List<Parcela> parcelasResultado) {
        this.parcelasResultado = parcelasResultado;
    }

    public BigDecimal getTaxaMensal() {
        return taxaMensal;
    }

    public void setTaxaMensal(BigDecimal taxaMensal) {
        this.taxaMensal = taxaMensal;
    }

    public String getTaxaAnual() {
        return taxaAnual;
    }

    public void setTaxaAnual(String taxaAnual) {
        this.taxaAnual = taxaAnual;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCarencia() {
        return carencia;
    }

    public void setCarencia(String carencia) {
        this.carencia = carencia;
    }

    public LocalDate formataData(String dataJS) {
        String[] data = dataJS.split("-");
        return LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0]));
    }
}
