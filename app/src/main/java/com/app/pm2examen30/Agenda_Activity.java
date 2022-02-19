package com.app.pm2examen30;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.app.pm2examen30.configuraciones.SQLiteConexion;
import com.app.pm2examen30.configuraciones.Transacciones;
import com.app.pm2examen30.tablas.Contacto;

import java.util.ArrayList;
import java.util.Locale;
import java.util.LongSummaryStatistics;

public class Agenda_Activity extends AppCompatActivity {

    SQLiteConexion conexion;
    SQLiteDatabase db;
    Button btnEditar;
    ImageButton btnEliminar, btnCompartir, btnVerImg;
    ListView lstAgenda;
    ArrayList<Contacto> ArryConta;
    ArrayList<Contacto> ArryContacto;
    ArrayList<String> ArrayContList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        //Declaración de variables
        conexion = new SQLiteConexion(this, Transacciones.DATA_BASE,null,1);
        btnEditar = (Button) findViewById(R.id.btnActualizar);
        btnEliminar = (ImageButton) findViewById(R.id.btnEliminar);
        btnCompartir = (ImageButton) findViewById(R.id.btnCompartir);
        btnVerImg = (ImageButton) findViewById(R.id.btnImage);
        lstAgenda = (ListView) findViewById(R.id.lstContactos);

        CreateAgenda();
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,ArrayContList);
        lstAgenda.setAdapter(adp);

        lstAgenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArryContacto = new ArrayList<Contacto>();
                ArryContacto.add(ArryConta.get(i));
            }
        });

        lstAgenda.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gest = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    setAlertDialog();
                    return super.onDoubleTap(e);
                }
            });
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gest.onTouchEvent(motionEvent);
                return false;
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = conexion.getWritableDatabase();
                db.execSQL(Transacciones.DELETE_CONTAC +ArryContacto.get(0).getID().toString());
                //Toast.makeText(Agenda_Activity.this, ArryContacto.get(0).toString(), Toast.LENGTH_LONG).show();
                db.close();

                Intent tmp = new Intent(getApplicationContext(),Agenda_Activity.class);
                startActivity(tmp);
            }
        });

        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Compartir();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarContacto();
            }
        });
    }

    public void CreateAgenda(){
        db = conexion.getWritableDatabase();
        Contacto tmp = null;
        ArryConta = new ArrayList<Contacto>();

        Cursor tmpCursor = db.rawQuery(Transacciones.SELECT_TABLE_CONTACTOS,null);

        while (tmpCursor.moveToNext()){
            tmp = new Contacto();
            tmp.setID(tmpCursor.getInt(0));
            tmp.setNombre(tmpCursor.getString(1));
            tmp.setCELULAR(tmpCursor.getString(2));
            tmp.setCodPais(tmpCursor.getString(3));
            tmp.setNOTA(tmpCursor.getString(4));

            ArryConta.add(tmp);
        }
        tmpCursor.close();

        ArrayContList = new ArrayList<String>();
        for (int i=0; i < ArryConta.size(); i++){
            ArrayContList.add(ArryConta.get(i).toString());
        }
    }

    public void Compartir(){
        Intent comp = new Intent(Intent.ACTION_SEND);
        comp.putExtra(Intent.EXTRA_TEXT,ArryContacto.get(0).getNombre()+" "+ArryContacto.get(0).getCELULAR());
        comp.setType("text/plain");

        Intent createShare = Intent.createChooser(comp, null);
        startActivity(createShare);
    }
    public void setAlertDialog(){
        AlertDialog.Builder dialogCall = new AlertDialog.Builder(this);
        dialogCall.setMessage("Desea llamar a "+ArryContacto.get(0).getNombre());
        dialogCall.setTitle("Llamada");
        dialogCall.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setLlamar();
            }
        });
        dialogCall.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Agenda_Activity.this, "Llamada Cancelada", Toast.LENGTH_LONG).show();
            }
        });
        dialogCall.show();
    }

    public void setLlamar(){
        Intent call = new Intent(Intent.ACTION_CALL);
        call.setData(Uri.parse("tel:" + ArryContacto.get(0).getCELULAR()));
        startActivity(call);
    }

    public void actualizarContacto(){
        Intent edit = new Intent(getApplicationContext(),MainActivity.class);
    }
}