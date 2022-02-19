package com.app.pm2examen30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.pm2examen30.configuraciones.SQLiteConexion;
import com.app.pm2examen30.configuraciones.Transacciones;

public class Pais_Activity extends AppCompatActivity {

    EditText txtCod, txtCodPais, txtNomPais;
    ImageButton btnAgregar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);

        //txtCod = (EditText) findViewById(R.id.txtCod);
        txtCodPais = (EditText)  findViewById(R.id.txtCodPais);
        txtNomPais = (EditText) findViewById(R.id.txtNombrePais);

        btnAgregar = (ImageButton) findViewById(R.id.btnAddPais);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveNewPais();
            }
        });

    }

    public void SaveNewPais(){
        SQLiteConexion conex = new SQLiteConexion(this, Transacciones.DATA_BASE,null,1);
        SQLiteDatabase db = conex.getWritableDatabase();

        try {
            ContentValues dat = new ContentValues();
            dat.put(Transacciones.PAIS_COD, txtCodPais.getText().toString());
            dat.put(Transacciones.PAIS_DESC, txtNomPais.getText().toString());

            Long resul = db.insert(Transacciones.TABLA_PAISES, Transacciones.PAIS_ID, dat);
            //Long resul = db.execSQL("");

            Toast.makeText(getApplicationContext(), "Registro con exito!"+resul.toString(), Toast.LENGTH_SHORT).show();

            db.close();
        }catch (Exception exc){
            exc.toString();
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}