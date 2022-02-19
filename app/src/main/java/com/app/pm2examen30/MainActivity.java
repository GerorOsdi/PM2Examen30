package com.app.pm2examen30;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.pm2examen30.configuraciones.SQLiteConexion;
import com.app.pm2examen30.configuraciones.Transacciones;
import com.app.pm2examen30.tablas.Pais;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtNota;
    ImageButton btnAgenda, btnSave, btnImage;
    FloatingActionButton btnAddPais;
    ImageView imgContacto;
    Spinner spPaises;
    ArrayList<Pais> PaisLista;
    ArrayList<String> ArrayPais;
    SQLiteConexion conexion;
    String CurrentPhotoPath;
    static final int PETICION_ACCESO_CAM = 100;
    static final int TAKE_PIC_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cast de las variables de PlainText
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtNota = (EditText) findViewById(R.id.txtNota);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        //Cast de los botones
        btnAgenda = (ImageButton) findViewById(R.id.btnAgenda);
        btnSave = (ImageButton) findViewById(R.id.btnGuardar);
        btnAddPais = (FloatingActionButton) findViewById(R.id.btnAgrePais);
        btnImage = (ImageButton) findViewById(R.id.btnImage);

        imgContacto = (ImageView) findViewById(R.id.imgContacto);
        spPaises = (Spinner) findViewById(R.id.spPaises);

        //Llenar Spinner con la información de la base de datos
        ObtenerBasePais();
        ArrayAdapter adp =new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,ArrayPais);
        spPaises.setAdapter(adp);


        // Evento Click para consular la todos los contactos guardados
        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agendaContactos();
            }
        });

        //Evento click para administrar los paises
        btnAddPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPais();
            }
        });

        //Evento para guardar un nuevo contacto
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar();
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Permisos();
            }
        });
    }

    public void agendaContactos(){
        Intent agen = new Intent(this,Agenda_Activity.class);
        startActivity(agen);
    }

    public void addPais(){
        Intent pais = new Intent(this,Pais_Activity.class);
        startActivity(pais);
    }

    public void agregar(){
        validar();
    }

    public void validar(){
        String spSelect = spPaises.getSelectedItem().toString();
        if(spSelect == null || spSelect ==""){
            Toast.makeText(this, "Es necesario seleciones un pais", Toast.LENGTH_LONG).show();
        }else if(txtNombre.getText().toString().isEmpty()){
            Toast.makeText(this, "Es necesario un nombre", Toast.LENGTH_LONG).show();
        }else if (txtTelefono.getText().toString().isEmpty()){
            Toast.makeText(this, "Es necesario un numero de celular", Toast.LENGTH_LONG).show();
        }else if (txtNota.getText().toString().isEmpty()){
            Toast.makeText(this, "Es necesario una nota", Toast.LENGTH_LONG).show();
        }else{
            AddNewContact();
        }
    }

    public void ObtenerBasePais(){
        conexion = new SQLiteConexion(this, Transacciones.DATA_BASE,null,1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        PaisLista = new ArrayList<Pais>();

        Pais lstPais = null;

        Cursor nextPais = db.rawQuery(Transacciones.SELECT_TABLE_PAISES,null);

        //System.out.println(nextPais.moveToFirst());
        while (nextPais.moveToNext()){
            lstPais = new Pais();
            lstPais.setID(nextPais.getInt(0));
            lstPais.setID(nextPais.getInt(1));
            lstPais.setNOM_PAIS(nextPais.getString(2));

            PaisLista.add(lstPais);
        }
        nextPais.close();

        ArrayPais = new ArrayList<String>();
        for (int i=0; i < PaisLista.size(); i++){
            ArrayPais.add(PaisLista.get(i).getNOM_PAIS()+" ("+PaisLista.get(i).getID()+")");
        }


    }

    private void Permisos(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PETICION_ACCESO_CAM);

        }
        else
        {
            tomarfoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PETICION_ACCESO_CAM) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tomarfoto();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Se necesitan permisos de acceso a la camara", Toast.LENGTH_LONG).show();
        }
    }

    private void tomarfoto(){
        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takepic.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takepic,TAKE_PIC_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PIC_REQUEST && requestCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap IMG = (Bitmap) extras.get("data");
            imgContacto.setImageBitmap(IMG);
        }
    }

    public void AddNewContact(){
        SQLiteConexion conex = new SQLiteConexion(this, Transacciones.DATA_BASE,null,1);
        SQLiteDatabase db = conex.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.CONTAC_NOMBRE,txtNombre.getText().toString());
        valores.put(Transacciones.CONTAC_CEL,txtTelefono.getText().toString());
        valores.put(Transacciones.CONTAC_NOTA, txtNota.getText().toString());
        valores.put(Transacciones.CONTAC_PAIS, spPaises.getSelectedItem().toString());

        Long resl = db.insert(Transacciones.TABLA_CONTACTOS,Transacciones.CONTAC_ID,valores);

        Toast.makeText(getApplicationContext(), "Registro con exito!"+resl.toString(), Toast.LENGTH_SHORT).show();

        db.close();
        limpiar();
    }

    public void limpiar(){
        txtNombre.setText("");
        txtNota.setText("");
        txtTelefono.setText("");
    }
}