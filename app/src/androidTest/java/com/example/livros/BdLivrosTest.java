package com.example.livros;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BdLivrosTest {
    @Before
    public void apagaBaseDados(){
        // apagar a base de dados antes de a tentar criar
        getTargetContext().deleteDatabase(BdLivrosOpenHelper.NOME_BASEDE_DADOS);
    }

    @Test
    public void consegueAbrirBaseDados() {
        // Context of the app under test.
        Context appContext = getTargetContext();
        // testa se a tabela de categorias esta a ser criada corretamente
        BdLivrosOpenHelper openHelper = new BdLivrosOpenHelper(appContext);
        SQLiteDatabase bdLivros = openHelper.getReadableDatabase();
        assertTrue(bdLivros.isOpen());
        bdLivros.close();
    }

    private Context getTargetContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
    private long insereCategoria(BdTableCategorias tableCategorias, Categoria categoria) {
        long idCategoria = tableCategorias.insert(Convert.categoriaToContentValues(categoria));
        assertNotEquals(-1,idCategoria);

        return idCategoria;
    }
    private long insereCategoria(BdTableCategorias tableCategorias, String descricao) {
        Categoria categoria = new Categoria();
        categoria.setDescricao(descricao);

        return insereCategoria(tableCategorias,categoria);
    }
    private long insereLivro(SQLiteDatabase bdLivros, String titulo, String descCategoria){
        BdTableCategorias tableCategorias = new BdTableCategorias(bdLivros);

        long idCategoria = insereCategoria(tableCategorias,descCategoria);

        BdTableLivros tabelaLivros = new BdTableLivros(bdLivros);

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setIdCategoria(idCategoria);

        long idLivro = tabelaLivros.insert(Convert.livroToContentValues(livro));
        assertNotEquals(-1,idLivro);

        return idLivro;
    }

    @Test
    public void consegueInserirCategorias(){
        Context appContext = getTargetContext();

        BdLivrosOpenHelper openHelper = new BdLivrosOpenHelper(appContext);
        SQLiteDatabase bdLivros = openHelper.getWritableDatabase();// abrir a bd para escrita

        BdTableCategorias tableCategorias = new BdTableCategorias(bdLivros);

        insereCategoria(tableCategorias, "Ação");

        bdLivros.close();
    }

    @Test
    public void consegueLerCategorias(){
        Context appContext = getTargetContext();

        BdLivrosOpenHelper openHelper = new BdLivrosOpenHelper(appContext);
        SQLiteDatabase bdLivros = openHelper.getWritableDatabase();// abrir a bd para escrita

        BdTableCategorias tableCategorias = new BdTableCategorias(bdLivros);
        Cursor cursor = tableCategorias.query(BdTableCategorias.TODOS_CAMPOS,null,
                null,null,null,null);
        int registos = cursor.getCount();
        cursor.close();
        insereCategoria(tableCategorias,"Sci-fi");

        cursor = tableCategorias.query(BdTableCategorias.TODOS_CAMPOS,null,
                null,null,null,null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bdLivros.close();
    }
    @Test
    public void consegueAlterarCategorias(){
        Context appContext = getTargetContext();

        BdLivrosOpenHelper openHelper = new BdLivrosOpenHelper(appContext);
        SQLiteDatabase bdLivros = openHelper.getWritableDatabase();
        // abrir a bd para escrita

        BdTableCategorias tableCategorias = new BdTableCategorias(bdLivros);


        Categoria categoria = new Categoria();
        categoria.setDescricao("Romenc");
        long id = insereCategoria(tableCategorias,categoria);

        categoria.setDescricao("Romance");
        int registosAlterados = tableCategorias.update(
                Convert.categoriaToContentValues(categoria),BdTableCategorias._ID+"=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosAlterados);

        bdLivros.close();
    }
    @Test
    public void consegueApagarCategorias(){
        Context appContext = getTargetContext();

        BdLivrosOpenHelper openHelper = new BdLivrosOpenHelper(appContext);
        SQLiteDatabase bdLivros = openHelper.getWritableDatabase();
        // abrir a bd para escrita

        BdTableCategorias tableCategorias = new BdTableCategorias(bdLivros);

        long id = insereCategoria(tableCategorias,"TESTE");

        int registosApagados = tableCategorias.delete(BdTableCategorias._ID+"=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosApagados);


        bdLivros.close();
    }
    @Test
    public void consegueInserirLivros(){
        Context appContext = getTargetContext();

        BdLivrosOpenHelper openHelper = new BdLivrosOpenHelper(appContext);
        SQLiteDatabase bdLivros = openHelper.getWritableDatabase();// abrir a bd para escrita

        insereLivro(bdLivros,"O Intruso", "Terror");

        bdLivros.close();
    }

    @Test
    public void consegueLerLivros(){
        Context appContext = getTargetContext();

        BdLivrosOpenHelper openHelper = new BdLivrosOpenHelper(appContext);
        SQLiteDatabase bdLivros = openHelper.getWritableDatabase();// abrir a bd para escrita

        BdTableLivros tableLivros = new BdTableLivros(bdLivros);
        Cursor cursor = tableLivros.query(BdTableLivros.TODOS_CAMPOS, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();

        insereLivro(bdLivros,"O Intruso II", "Terror/Ação");

        cursor = tableLivros.query(BdTableLivros.TODOS_CAMPOS, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bdLivros.close();
    }

    @Test
    public void consegueAlterarLivros(){
        Context appContext = getTargetContext();

        BdLivrosOpenHelper openHelper = new BdLivrosOpenHelper(appContext);
        SQLiteDatabase bdLivros = openHelper.getWritableDatabase();// abrir a bd para escrita

        long idLivro = insereLivro(bdLivros,"O silêncio dos inoceentes", "Thriller");

        BdTableLivros tabelaLivros = new BdTableLivros(bdLivros);

        Cursor cursor = tabelaLivros.query(BdTableLivros.TODOS_CAMPOS, BdTableLivros._ID +"=?",
                new String[]{String.valueOf(idLivro)},null, null, null);
        assertEquals(1, cursor.getCount());

        assertTrue(cursor.moveToNext());
        Livro livro = Convert.cursorToLivro(cursor);
        cursor.close();

        assertEquals("O silêncio dos inocentes", livro.getTitulo());

        livro.setTitulo("O mistério do quarto secreto");
        int registosAfetados = tabelaLivros.update(Convert.livroToContentValues(livro), BdTableLivros._ID + "=?", new String[]{String.valueOf(livro.getId())});

        bdLivros.close();
    }

    @Test
    public void  consegueEliminarLivros(){
        Context appContext = getTargetContext();
        BdLivrosOpenHelper openHelper = new BdLivrosOpenHelper(appContext);
        SQLiteDatabase bdLivros = openHelper.getWritableDatabase();

        long id = insereLivro(bdLivros, "O silêncio dos inocentes", "Thriller");

        BdTableLivros tabelaLivros = new BdTableLivros(bdLivros);
        int registosEliminados = tabelaLivros.delete(BdTableLivros._ID+"=?", new String[]{String.valueOf(id)});
        assertEquals(1,registosEliminados);

        bdLivros.close();
    }



}
