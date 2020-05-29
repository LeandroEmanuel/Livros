package com.example.livros;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListaLivrosFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_livros, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();
        RecyclerView recyclerViewLivros = (RecyclerView) view.findViewById(R.id.recycleViewLivros);
        AdaptadorLivros adaptadorLivros = new AdaptadorLivros(context);
        recyclerViewLivros.setAdapter(adaptadorLivros);
        recyclerViewLivros.setLayoutManager(new LinearLayoutManager(context));

        // todo: este codigo é obsuleto e tem que ser substituido
        BdLivrosOpenHelper openHelper = new BdLivrosOpenHelper(context);
        SQLiteDatabase bdLivros = openHelper.getReadableDatabase();// abrir a bd para escrita
        BdTableLivros tableLivros = new BdTableLivros(bdLivros);
        Cursor cursor = tableLivros.query(BdTableLivros.TODOS_CAMPOS, null, null, null, null, null);
        getActivity().startManagingCursor(cursor);
        adaptadorLivros.setCursor(cursor);


    }

    private void alteraLivro() {
    }

    private void novoLivro() {
        NavController navController = NavHostFragment.findNavController(ListaLivrosFragment.this);
        navController.navigate(R.id.action_lista_livros_to_adicionar_livro);
    }
}
