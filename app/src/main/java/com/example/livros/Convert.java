package com.example.livros;

import android.content.ContentValues;

public class Convert {
    public static ContentValues categoriaToContentValues(Categoria categoria){
        ContentValues valores = new ContentValues();
        valores.put(BdTableCategorias.CAMPO_DESCRICAO,categoria.getDescricao());
        return valores;
    }

    public static Categoria contentValuesToCategoria(ContentValues valores){
        Categoria categoria = new Categoria();

        categoria.setId( valores.getAsLong(BdTableCategorias._ID));
        categoria.setDescricao(valores.getAsString(BdTableCategorias.CAMPO_DESCRICAO));

        return categoria;
    }
    public static ContentValues livroToContentValues(Livro livro){
        ContentValues valores = new ContentValues();
        valores.put(BdTableLivros.CAMPO_TITULO, livro.getTitulo());
        valores.put(BdTableLivros.CAMPO_CATEGORIA, livro.getIdCategoria());
        return valores;
    }
    public static Livro contentValuesToLivro(ContentValues valores){
        Livro livro = new Livro();

        livro.setId( valores.getAsLong(BdTableLivros._ID));
        livro.setTitulo(valores.getAsString(BdTableLivros.CAMPO_TITULO));
        livro.setIdCategoria(valores.getAsLong(BdTableLivros.CAMPO_CATEGORIA));

        return livro;
    }


}
