package com.example.livros;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableLivros implements BaseColumns {
    public static final String CAMPO_TITULO = "titulo";
    public static final String CAMPO_CATEGORIA = "categoria";
    public static final String[] TODOS_CAMPOS = new String[]{_ID, CAMPO_TITULO, CAMPO_CATEGORIA};
    public static final String NOME_TABELA = "livros";
    private SQLiteDatabase db;
    public BdTableLivros(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL("CREATE TABLE " + NOME_TABELA + "(" +
                _ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_TITULO + " TEXT NOT NULL," +
                CAMPO_CATEGORIA + " INTEGER NOT NULL," +
                " FOREIGN KEY (" + CAMPO_CATEGORIA + ") REFERENCES " +
                BdTableCategorias.NOME_DA_TABELA + "("+ BdTableCategorias._ID + ")"+
                ")");

    }
    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA,null,values);
    }
    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy){
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
    public int update(ContentValues values, String whereClause, String[] whereArgs){
        return db.update(NOME_TABELA,values,whereClause,whereArgs);
    }
    public int delete(String whereClause, String[] whereArgs){
        return db.delete(NOME_TABELA,whereClause,whereArgs);
    }
}
