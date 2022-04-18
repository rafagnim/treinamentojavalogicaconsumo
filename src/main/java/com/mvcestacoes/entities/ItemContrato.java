package com.mvcestacoes.entities;

public class ItemContrato {

    private Integer id;
    private String id_duplicata;
    private String dataVencimento;
    private Double vl_duplicata;

    public ItemContrato() {
    }

    public ItemContrato(Integer id, String id_duplicata, String dataVencimento, Double vl_duplicata) {
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

    public Double getVl_duplicata() {
        return vl_duplicata;
    }

    public void setVl_duplicata(Double vl_duplicata) {
        this.vl_duplicata = vl_duplicata;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String formataData() {
        String[] data = this.dataVencimento.split("-");
        return data[2] + "/" + data[1] + "/" + data[0];
    }

    public static Integer[] desformataData(String d) {
        String[] data = d.split("/");
        Integer[] retorno = new Integer[3];
        retorno[0] = Integer.parseInt(data[2]);
        retorno[1] = Integer.parseInt(data[1]);
        retorno[2] = Integer.parseInt(data[0]);
        return retorno;
    }
}
