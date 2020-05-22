package com.example.livros;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BdLivrosOpenHelper extends SQLiteOpenHelper {
    public static final String NOME_BASEDE_DADOS = "livros.db";
    private static final int VERSAO_BASE_DADOS = 1;
    private static boolean DESENVOLVIMENTO = true;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the the database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public BdLivrosOpenHelper(@Nullable Context context) {
        super(context, NOME_BASEDE_DADOS, null, VERSAO_BASE_DADOS);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        BdTableCategorias tabelasCategorisa = new BdTableCategorias(db);
        tabelasCategorisa.cria();
        BdTableLivros tabelaLivros = new BdTableLivros(db);
        tabelaLivros.cria();
        if(DESENVOLVIMENTO){
            seedData(db);
        }
    }

    private void seedData(SQLiteDatabase db) {
        BdTableCategorias tabelasCategorisa = new BdTableCategorias(db);

        Categoria categoria = new Categoria();
        categoria.setDescricao("Ação");
        long idCatAcao = tabelasCategorisa.insert(Convert.categoriaToContentValues(categoria));

        categoria = new Categoria();
        categoria.setDescricao("Terror");
        long idCatTerror = tabelasCategorisa.insert(Convert.categoriaToContentValues(categoria));

        categoria = new Categoria();
        categoria.setDescricao("Mistério");
        long idCatMisterio = tabelasCategorisa.insert(Convert.categoriaToContentValues(categoria));

        categoria = new Categoria();
        categoria.setDescricao("Sci-fi");
       tabelasCategorisa.insert(Convert.categoriaToContentValues(categoria));


        BdTableLivros tabelaLivros = new BdTableLivros(db);

        Livro livro = new Livro();
        livro.setTitulo("Lua vermelha");
        livro.setIdCategoria(idCatAcao);
        tabelaLivros.insert(Convert.livroToContentValues(livro));

        livro = new Livro();
        livro.setTitulo("O sobrevivente");
        livro.setIdCategoria(idCatAcao);
        tabelaLivros.insert(Convert.livroToContentValues(livro));

        livro = new Livro();
        livro.setTitulo("O intruso");
        livro.setIdCategoria(idCatTerror);
        tabelaLivros.insert(Convert.livroToContentValues(livro));

        livro = new Livro();
        livro.setTitulo("O mistério do quarto secreto");
        livro.setIdCategoria(idCatMisterio);
        tabelaLivros.insert(Convert.livroToContentValues(livro));
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
