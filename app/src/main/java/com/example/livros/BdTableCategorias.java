package com.example.livros;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableCategorias implements BaseColumns {
    public static final String NOME_DA_TABELA = "categorias";
    public static final String CAMPO_DESCRICAO = "descricao";


    public static final String CAMPO_ID_COMPLETO = NOME_DA_TABELA + "." + _ID;
    public static final String CAMPO_TITULO_COMPLETO = NOME_DA_TABELA + "." + CAMPO_DESCRICAO;

    public static final String[] TODOS_CAMPOS = new String[]{_ID,CAMPO_DESCRICAO};

    private SQLiteDatabase db;
    public BdTableCategorias(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL("CREATE TABLE " + NOME_DA_TABELA + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_DESCRICAO + " TEXT NOT NULL" +
                ")");
    }



    public long insert(ContentValues values) {
        return db.insert(NOME_DA_TABELA,null,values);
    }
    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy){
        return db.query(NOME_DA_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
    public int update(ContentValues values, String whereClause, String[] whereArgs){
        return db.update(NOME_DA_TABELA,values,whereClause,whereArgs);
    }
    public int delete(String whereClause, String[] whereArgs){
        return db.delete(NOME_DA_TABELA,whereClause,whereArgs);
    }
}
