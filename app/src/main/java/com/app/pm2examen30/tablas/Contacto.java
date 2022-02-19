package com.app.pm2examen30.tablas;

import android.content.Intent;

public class Contacto {

    private Integer ID;
    private String Nombre;
    private Intent IMG;
    private String CELULAR;
    private String codPais;
    private String NOTA;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Intent getIMG() {
        return IMG;
    }

    public void setIMG(Intent IMG) {
        this.IMG = IMG;
    }

    public String getCELULAR() {
        return CELULAR;
    }

    public void setCELULAR(String CELULAR) {
        this.CELULAR = CELULAR;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }

    public String getNOTA() {
        return NOTA;
    }

    public void setNOTA(String NOTA) {
        this.NOTA = NOTA;
    }

    public String toString(){
        return Nombre + " " + codPais;
    }
}
