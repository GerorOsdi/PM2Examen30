package com.app.pm2examen30.configuraciones;

public class Transacciones {
    // Nombre de la base de datos
    public static final String DATA_BASE = "dbContactos";
    // Tablas a utilizar dentro de la base
    public static final String TABLA_CONTACTOS = "tbContactos";
    public static final String TABLA_PAISES = "tbPaises";


    //Campo de la tabla contactos
    public static final String CONTAC_ID = "codCont";
    public static final String CONTAC_NOMBRE = "nombCont";
    public static final String CONTAC_CEL = "celCont";
    public static final String CONTAC_PAIS = "paisCont";
    public static final String CONTAC_NOTA = "notaCont";
    public static final String CONTAC_IMG = "imgCont";


    //Campos de la tabla tbPaises
    public static final String PAIS_ID = "idPais";
    public static final String PAIS_COD = "codPais";
    public static final String PAIS_DESC = "descPais";


    //Creación de la tabla contactos
    public static final String CREATE_TABLE_CONTACTOS = "CREATE TABLE " + TABLA_CONTACTOS +
            "(codCont INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "nombCont TEXT NOT NULL, " +
            "celCont INTEGER NOT NULL, " +
            "paisCont TEXT NOT NULL, " +
            "notaCont TEXT NOT NULL, " +
            "imgCont LONGBLOB)";


    //Creación de la tabla paises
    public static final String CREATE_TABLE_PAIS = "CREATE TABLE " + TABLA_PAISES +
            "(idPais INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "codPais INTEGER NOT NULL, " +
            "descPais TEXT NOT NULL) ";



    public static final String DROP_TABLE_CONTACTOS = "DROP TABLE IF EXISTS  "+ TABLA_CONTACTOS;

    public static final String DELETE_CONTAC = "DELETE FROM "+ TABLA_CONTACTOS +" WHERE codCont = ";

    public static final String SELECT_TABLE_PAISES = "SELECT * FROM " + TABLA_PAISES;

    public static final String SELECT_TABLE_CONTACTOS = "SELECT * FROM " + TABLA_CONTACTOS;
}
