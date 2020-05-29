package com.example.livros;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class AdaptadorLivros extends RecyclerView.Adapter<AdaptadorLivros.ViewHolderLivro> {
    private final Context context;

    private Cursor cursor = null;

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
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

        public ViewHolderLivro(@NonNull View itemView) {
            super(itemView);
        }
    }
}
