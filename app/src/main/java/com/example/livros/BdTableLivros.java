package com.example.livros;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Arrays;

public class BdTableLivros implements BaseColumns {
    public static final String NOME_TABELA = "livros";
    public static final String CAMPO_TITULO = "titulo";
    public static final String CAMPO_ID_CATEGORIA = "id_categoria";
    public static final String CAMPO_CATEGORIA = "categoria";

    public static final String CAMPO_ID_COMPLETO = NOME_TABELA + "." + _ID;
    public static final String CAMPO_TITULO_COMPLETO = NOME_TABELA + "." + CAMPO_TITULO;
    public static final String CAMPO_ID_CATEGORIA_COMPLETO = NOME_TABELA + "." + CAMPO_ID_CATEGORIA;
    public static final String CAMPO_CATEGORIA_COMPLETO = BdTableCategorias.NOME_DA_TABELA + "." +
            BdTableCategorias.CAMPO_DESCRICAO + " AS " + CAMPO_CATEGORIA;

    public static final String[] TODOS_CAMPOS = new String[]{CAMPO_ID_COMPLETO, CAMPO_TITULO_COMPLETO, CAMPO_ID_CATEGORIA_COMPLETO, CAMPO_CATEGORIA_COMPLETO};

    private SQLiteDatabase db;
    public BdTableLivros(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL("CREATE TABLE " + NOME_TABELA + "(" +
                _ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_TITULO + " TEXT NOT NULL," +
                CAMPO_ID_CATEGORIA + " INTEGER NOT NULL," +
                " FOREIGN KEY (" + CAMPO_ID_CATEGORIA + ") REFERENCES " +
                BdTableCategorias.NOME_DA_TABELA + "("+ BdTableCategorias._ID + ")"+
                ")");

    }
    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA,null,values);
    }
    public Cursor query(String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having,
            String orderBy){

        if(!Arrays.asList(columns).contains(CAMPO_CATEGORIA_COMPLETO)){
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
        }

        String campos = TextUtils.join(",", columns);

        String sql = "SELECT " + campos;
        sql += " FROM " + NOME_TABELA + " INNER JOIN " + BdTableCategorias.NOME_DA_TABELA;
        sql += " ON " + CAMPO_ID_CATEGORIA_COMPLETO + "=" + BdTableCategorias.CAMPO_ID_COMPLETO;

        if(selection != null){
            sql += " WHERE " + selection;
        }
        if(groupBy != null){
            sql += " GROUP BY " + groupBy;
        }
        if (having != null){
            sql += " HAVING " + having;
        }
        if(orderBy != null){
            sql += " ORDER BY " + orderBy;
        }

        return db.rawQuery(sql, selectionArgs);
    }
    public int update(ContentValues values, String whereClause, String[] whereArgs){
        return db.update(NOME_TABELA,values,whereClause,whereArgs);
    }
    public int delete(String whereClause, String[] whereArgs){
        return db.delete(NOME_TABELA,whereClause,whereArgs);
    }
}
