package com.mvcestacoes.entities;

import java.time.LocalDate;

public class ItemContratoAPI {

    private Integer id;
    private String id_duplicata;
    private LocalDate dataVencimento;
    private Double vl_duplicata;

    public ItemContratoAPI() {
    }

    public ItemContratoAPI(Integer id, String id_duplicata, LocalDate dataVencimento, Double vl_duplicata) {
        this.id = id;
        this.id_duplicata = id_duplicata;
        this.dataVencimento = dataVencimento;
        this.vl_duplicata = vl_duplicata;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getId_duplicata() {
        return id_duplicata;
    }

    public void setId_duplicata(String id_duplicata) {
        this.id_duplicata = id_duplicata;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getVl_duplicata() {
        return vl_duplicata;
    }

    public void setVl_duplicata(Double vl_duplicata) {
        this.vl_duplicata = vl_duplicata;
    }
}
