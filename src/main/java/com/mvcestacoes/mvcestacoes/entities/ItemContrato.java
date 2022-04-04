package com.mvcestacoes.mvcestacoes.entities;

public class ItemContrato {

    private Integer id;
    private String id_duplicata;
    private Double vl_duplicata;
    private Integer contrato_id;

    public ItemContrato() {
    }

    public ItemContrato(Integer id, String id_duplicata, Double vl_duplicata, Integer contrato_id) {
        this.id = id;
        this.id_duplicata = id_duplicata;
        this.vl_duplicata = vl_duplicata;
        this.contrato_id = contrato_id;
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

    public Double getVl_duplicata() {
        return vl_duplicata;
    }

    public void setVl_duplicata(Double vl_duplicata) {
        this.vl_duplicata = vl_duplicata;
    }

    public Integer getContrato() {
        return contrato_id;
    }

    public void setContrato(Integer contrato_id) {
        this.contrato_id = contrato_id;
    }
}
