package com.app.pm2examen30.configuraciones;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteConexion extends SQLiteOpenHelper {
    public SQLiteConexion(@Nullable Context context,
                          @Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Transacciones.CREATE_TABLE_CONTACTOS);
        sqLiteDatabase.execSQL(Transacciones.CREATE_TABLE_PAIS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(Transacciones.DROP_TABLE_CONTACTOS);
        onCreate(sqLiteDatabase);
    }
}
