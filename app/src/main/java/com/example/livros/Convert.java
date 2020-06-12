package com.example.livros;

import android.content.ContentValues;
import android.database.Cursor;

public class Convert {
    public static ContentValues categoriaToContentValues(Categoria categoria){
        ContentValues values = new ContentValues();
        values.put(BdTableCategorias.CAMPO_DESCRICAO,categoria.getDescricao());
        return values;
    }

    public static Categoria contentValuesToCategoria(ContentValues values){
        Categoria categoria = new Categoria();

        categoria.setId( values.getAsLong(BdTableCategorias._ID));
        categoria.setDescricao(values.getAsString(BdTableCategorias.CAMPO_DESCRICAO));

        return categoria;
    }
    public static ContentValues livroToContentValues(Livro livro){
        ContentValues values = new ContentValues();
        values.put(BdTableLivros.CAMPO_TITULO, livro.getTitulo());
        values.put(BdTableLivros.CAMPO_ID_CATEGORIA, livro.getIdCategoria());
        return values;
    }
    public static Livro contentValuesToLivro(ContentValues values){
        Livro livro = new Livro();

        livro.setId( values.getAsLong(BdTableLivros._ID));
        livro.setTitulo(values.getAsString(BdTableLivros.CAMPO_TITULO));
        livro.setIdCategoria(values.getAsLong(BdTableLivros.CAMPO_ID_CATEGORIA));
        livro.setCategoria(values.getAsString(BdTableLivros.CAMPO_CATEGORIA));

        return livro;
    }

    public static Livro cursorToLivro(Cursor cursor){
        Livro livro = new Livro();

        livro.setId(cursor.getLong(cursor.getColumnIndex(BdTableLivros._ID)));
        livro.setTitulo(cursor.getString(cursor.getColumnIndex(BdTableLivros.CAMPO_TITULO)));
        livro.setIdCategoria(cursor.getLong(cursor.getColumnIndex(BdTableLivros.CAMPO_ID_CATEGORIA)));
        livro.setCategoria(cursor.getString(cursor.getColumnIndex(BdTableLivros.CAMPO_CATEGORIA)));

        return livro;
    }


}
