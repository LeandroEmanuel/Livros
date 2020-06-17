package com.example.livros;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.loader.content.Loader;

public class AdicionarLivroFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int _CURSOR_LOADER_CATEGORIAS = 0;
    private Spinner spinnerCategorias;
    private EditText editTextTituloLivro;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adicionar_livro, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuAtual(R.menu.menu_inserir_livro);

        editTextTituloLivro = (EditText) view.findViewById(R.id.editTextTituloLivro);
        spinnerCategorias = (Spinner) view.findViewById(R.id.spinnerCategorias);

        mostraDadosSpinerCategorias(null);


        LoaderManager.getInstance(this).initLoader(_CURSOR_LOADER_CATEGORIAS, null, this);


    }

    public void cancelar() {
        NavController navController = NavHostFragment.findNavController(AdicionarLivroFragment.this);
        navController.navigate(R.id.action_adicionar_livro_to_lista_livros);
    }
    public void gauardar() {

    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), LivrosContentProvider.ENDERECO_CATEGORAS, BdTableCategorias.TODOS_CAMPOS, null, null, BdTableCategorias.CAMPO_DESCRICAO );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraDadosSpinerCategorias(data);
    }



    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraDadosSpinerCategorias(null);
    }
    private void mostraDadosSpinerCategorias(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                data,
                new String[]{BdTableCategorias.CAMPO_DESCRICAO},
                new int []{android.R.id.text1}
        );

        spinnerCategorias.setAdapter(adapter);
    }

}
