package com.mvcestacoes.entities;

public class Itens {
    private ItemContrato[] itens;

    public Itens() {
    }

    public Itens(ItemContrato[] itens) {
        this.itens = itens;
    }

    public ItemContrato[] getItens() {
        return itens;
    }

    public void setItens(ItemContrato[] itens) {
        this.itens = itens;
    }
}

