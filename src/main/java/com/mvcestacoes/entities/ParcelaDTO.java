package com.mvcestacoes.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class ParcelaDTO {

    private int parcela;
    private String data;
    private BigDecimal amortizacao;
    private BigDecimal juros;
    private BigDecimal totalParcela;
    private BigDecimal saldoDevedor;

    public ParcelaDTO() {
    }

    public ParcelaDTO(int parcela, String data, BigDecimal amortizacao, BigDecimal juros, BigDecimal totalParcela, BigDecimal saldoDevedor) {
        this.parcela = parcela;
        this.data = data;
        this.amortizacao = amortizacao;
        this.juros = juros;
        this.totalParcela = totalParcela;
        this.saldoDevedor = saldoDevedor;
    }

    public int getParcela() {
        return parcela;
    }

    public void setParcela(int parcela) {
        this.parcela = parcela;
    }

    public BigDecimal getAmortizacao() {
        return amortizacao;
    }

    public void setAmortizacao(BigDecimal amortizacao) {
        this.amortizacao = amortizacao;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getSaldoDevedor() {
        return saldoDevedor;
    }

    public void setSaldoDevedor(BigDecimal saldoDevedor) {
        this.saldoDevedor = saldoDevedor;
    }

    public BigDecimal getTotalParcela() {
        return totalParcela;
    }

    public void setTotalParcela(BigDecimal totalParcela) {
        this.totalParcela = totalParcela;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static String formataData(LocalDate data) {

        String dia = data.getDayOfMonth() > 9 ? String.valueOf(data.getDayOfMonth()) : "0" + data.getDayOfMonth();
        String mes = data.getMonthValue() > 9 ? String.valueOf(data.getMonthValue()) : "0" + data.getMonthValue();
        String dataRetorno = dia + "/" + mes + "/" + data.getYear();
        return dataRetorno;
    }
}
