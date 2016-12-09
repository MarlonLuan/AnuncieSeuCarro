package com.marlonluan.anuncieseucarro.carro;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.marlonluan.anuncieseucarro.*;

import java.util.List;

public class CarroActivity extends AppCompatActivity
        implements
        CarroListFragment.AoClicarNoCarro,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener,
        CarroDialogFragment.AoSalvarCarro,
        CarolDetalheFragment.AoEditarCarro,
        CarroListFragment.AoExcluirHoteis {

    public static final int REQUEST_EDITAR_Carro = 0;
    private long mIdSelecionado;

    private FragmentManager mFragmentManager;
    private CarroListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        mFragmentManager = getSupportFragmentManager();
        mListFragment = (CarroListFragment)
                mFragmentManager.findFragmentById(R.id.fragmentLista);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDITAR_Carro && resultCode == RESULT_OK) {
            mListFragment.limparBusca();
        }
    }

    public void clicouNoCarro(Carro carro) {
        mIdSelecionado = carro.id;
        if (isTablet()) {
            CarolDetalheFragment fragment =
                    CarolDetalheFragment.novaInstancia(carro);
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.detalhe, fragment,
                    CarolDetalheFragment.TAG_DETALHE);
            ft.commit();
        } else {

            Intent it = new Intent(this, CarroDetalheActivity.class);
            it.putExtra(CarroDetalheActivity.EXTRA_Carro, carro);
            startActivityForResult(it, REQUEST_EDITAR_Carro);
        }
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.tablet);
    }
    private boolean isSmartphone() {
        return getResources().getBoolean(R.bool.smartphone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_carro, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.hint_busca));
        MenuItemCompat.setOnActionExpandListener(searchItem, this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                SobreDialogFragment dialogFragment = new SobreDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "sobre");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }
    @Override
    public boolean onQueryTextChange(String s) {
        mListFragment.buscar(s);
        return false;
    }
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true; // para expandir a view
    }
    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        mListFragment.limparBusca();
        return true; // para voltar ao normal
    }

    public static class SobreDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == DialogInterface.BUTTON_NEGATIVE){
                        Intent it = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.nglauber.com.br"));
                        startActivity(it);
                    }
                }
            };
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.sobre_titulo)
                    .setMessage(R.string.sobre_mensagem)
                    .setPositiveButton(android.R.string.ok, null)
                    .setNegativeButton(R.string.sobre_botao_site, listener)
                    .create();
            return dialog;
        }
    }

    @Override
    public void salvouCarro(Carro carro) {
        CarroRepositorio repo = new CarroRepositorio(this);
        repo.salvar(carro);
        mListFragment.limparBusca();
        if (isTablet()) {
            clicouNoCarro(carro);
        }
    }

    public void adicionarCarroClick(View v){
        CarroDialogFragment carroDialogFragment =
                CarroDialogFragment.newInstance(null);
        carroDialogFragment.abrir(getSupportFragmentManager());
    }

    @Override
    public void aoEditarcarro(Carro carro) {
        CarroDialogFragment editNameDialog = CarroDialogFragment.newInstance(carro);
        editNameDialog.abrir(getSupportFragmentManager());
    }
    @Override
    public void exclusaoCompleta(List<Carro> excluidos) {
        CarolDetalheFragment f = (CarolDetalheFragment)
                mFragmentManager.findFragmentByTag(CarolDetalheFragment.TAG_DETALHE);
        if (f != null) {
            boolean encontrou = false;
            for (Carro h : excluidos) {
                if (h.id == mIdSelecionado) {
                    encontrou = true;
                    break;
                }
            }
            if (encontrou) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.remove(f);
                ft.commit();
            }
        }
    }

}
