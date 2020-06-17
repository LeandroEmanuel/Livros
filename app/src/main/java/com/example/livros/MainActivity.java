package com.example.livros;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private Fragment fragmentActual = null;

    private int menuAtual = R.menu.menu_lista_livros;

    public void setFragmentActual(Fragment fragmentActual) {
        this.fragmentActual = fragmentActual;
    }

    public void setMenuAtual(int menuAtual) {
        if(menuAtual != this.menuAtual){
            this.menuAtual = menuAtual;
            invalidateOptionsMenu();
        }
        this.menuAtual = menuAtual;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void novoLivro() {
        Fragment navHostFrag = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        FragmentManager childeFragmentManager = navHostFrag.getChildFragmentManager();
        Fragment fragment = childeFragmentManager.getFragments().get(0);

        NavHostFragment.findNavController(fragment)
                .navigate(R.id.action_lista_livros_to_adicionar_livro);
    }
    private void eliminaLivro() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menuAtual, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(menuAtual == R.menu.menu_lista_livros){
            if (processaOpcoesMenuListaLivros(id)) return true;

        }else if(menuAtual == R.menu.menu_inserir_livro){
            if(processaOpcoesMenuInserirLivro(id)) return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean processaOpcoesMenuInserirLivro(int id) {

         AdicionarLivroFragment adicionarLivroFragment = (AdicionarLivroFragment) this.fragmentActual;
        if(id == R.id.action_guardar_livro){
            adicionarLivroFragment.gauardar();
            return true;
        }else if(id == R.id.action_cancelar_livro){
            adicionarLivroFragment.cancelar();
            return true;
        }
        return false;
    }

    private boolean processaOpcoesMenuListaLivros(int id) {
        ListaLivrosFragment listaLivrosFragment = (ListaLivrosFragment) this.fragmentActual;
        if(id == R.id.action_inserir_livro){
            listaLivrosFragment.novoLivro();
            return true;
        }else if(id == R.id.action_alterar_livro){
            listaLivrosFragment.alteraLivro();
            return true;
        }else if(id == R.id.action_eliminar_livro){
            eliminaLivro();
            return true;
        }

        return false;
    }



}
