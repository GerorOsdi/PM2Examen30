package com.app.pm2examen30.tablas;

public class Pais {
    private Integer ID;
    private Integer ID_CODIGO_PAIS;
    private  String NOM_PAIS;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID_CODIGO_PAIS() {
        return ID_CODIGO_PAIS;
    }

    public void setID_CODIGO_PAIS(Integer ID_CODIGO_PAIS) {
        this.ID_CODIGO_PAIS = ID_CODIGO_PAIS;
    }

    public String getNOM_PAIS() {
        return NOM_PAIS;
    }

    public void setNOM_PAIS(String NOM_PAIS) {
        this.NOM_PAIS = NOM_PAIS;
    }

    public String toString(){
        return "("+ID_CODIGO_PAIS+")"+NOM_PAIS;
    }
}
