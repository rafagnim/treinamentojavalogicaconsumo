package com.mvcestacoes.entities;

public class Emprestimo {

    private String cpf_cnpj;
    private Double valor;
    private Integer parcelas;

    public Emprestimo() {
    }

    public Emprestimo(String cpf_cnpj, Double valor, Integer parcelas) {
        this.cpf_cnpj = cpf_cnpj;
        this.valor = valor;
        this.parcelas = parcelas;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }
}
