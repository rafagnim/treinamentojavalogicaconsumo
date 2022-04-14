package com.mvcestacoes.entities;

import java.math.BigDecimal;

public class Parcela {

    private int parcela;
    private BigDecimal amortizacao;
    private BigDecimal juros;
    private BigDecimal totalParcela;
    private BigDecimal saldoDevedor;

    public Parcela() {
    }

    public Parcela(int parcela, BigDecimal amortizacao, BigDecimal juros, BigDecimal totalParcela, BigDecimal saldoDevedor) {
        this.parcela = parcela;
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

    @Override
    public String toString() {
        return "Parcela{" +
                "parcela=" + parcela +
                ", amortizacao=" + amortizacao +
                ", juros=" + juros +
                ", totalParcela=" + totalParcela +
                ", saldoDevedor=" + saldoDevedor +
                '}';
    }
}
