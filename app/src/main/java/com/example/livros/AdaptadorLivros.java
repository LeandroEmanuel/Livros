package com.example.livros;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class AdaptadorLivros extends RecyclerView.Adapter<AdaptadorLivros.ViewHolderLivro> {
    private final Context context;

    private Cursor cursor = null;

    public void setCursor(Cursor cursor) {
        if(cursor != this.cursor){
            this.cursor = cursor;
            notifyDataSetChanged();
        }

    }

    public AdaptadorLivros(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolderLivro onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View intemLivro = LayoutInflater.from(context).inflate(R.layout.item_livro, parent, false);

        return new ViewHolderLivro(intemLivro);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLivro holder, int position) {
        cursor.moveToPosition(position);
        Livro livro = Convert.cursorToLivro(cursor);
        holder.setLivro(livro);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(cursor == null){
            return 0;
        }
        return cursor.getCount();
    }
    public class ViewHolderLivro extends RecyclerView.ViewHolder{

        private Livro livro = null;
        private final TextView textViewTitulo;
        private final TextView textViewCategoria;

        public ViewHolderLivro(@NonNull View itemView) {
            super(itemView);

            textViewTitulo = (TextView)itemView.findViewById(R.id.textViewTitulo);
            textViewCategoria = (TextView)itemView.findViewById(R.id.textViewCategoria);
        }

        public void setLivro(Livro livro) {
            this.livro = livro;
            textViewTitulo.setText(livro.getTitulo());
            textViewCategoria.setText(String.valueOf(livro.getIdCategoria()));
        }
    }
}
