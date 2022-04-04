package com.mvcestacoes.mvcestacoes.entities;

public class EstacaoConsulta {

    private String estacaoOrigem;
    private String estacaoDestino;

    public EstacaoConsulta() {
    }

    public EstacaoConsulta(String estacaoOrigem, String estacaoDestino) {
        this.estacaoOrigem = estacaoOrigem;
        this.estacaoDestino = estacaoDestino;
    }

    public String getEstacaoOrigem() {
        return estacaoOrigem;
    }

    public void setEstacaoOrigem(String estacaoOrigem) {
        this.estacaoOrigem = estacaoOrigem;
    }

    public String getEstacaoDestino() {
        return estacaoDestino;
    }

    public void setEstacaoDestino(String estacaoDestino) {
        this.estacaoDestino = estacaoDestino;
    }
}
