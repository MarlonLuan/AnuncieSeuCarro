package com.marlonluan.anuncieseucarro.carro;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marlonluan.anuncieseucarro.*;

import java.util.ArrayList;
import java.util.List;

public class CarroListFragment extends ListFragment
        implements ActionMode.Callback, AdapterView.OnItemLongClickListener {
    ListView mListView;
    ActionMode mActionMode;

    List<Carro> mCarros;
    ArrayAdapter<Carro> mAdapter;
    CarroRepositorio mRepositorio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRepositorio = new CarroRepositorio(getActivity());
        mListView = getListView();
        limparBusca();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mActionMode != null) {
            iniciarModoExclusao();
            atualizarTitulo();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mActionMode == null) {
            Activity activity = getActivity();
            if (activity instanceof AoClicarNoCarro) {
                Carro carro = (Carro) l.getItemAtPosition(position);
                AoClicarNoCarro listener = (AoClicarNoCarro) activity;
                listener.clicouNoCarro(carro);
            }
        } else {
            atualizarItensMarcados(mListView, position);
            if (qtdeItensMarcados() == 0) {
                mActionMode.finish();
            }
        }

    }

    public void buscar(String s) {
        if (s == null || s.trim().equals("")) {
            limparBusca();
            return;
        }
        List<Carro> carrosEncontrados =
                mRepositorio.buscarCarro("%" + s + "%");

        mListView.setOnItemLongClickListener(null);
        mAdapter = new ArrayAdapter<Carro>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                carrosEncontrados);
        setListAdapter(mAdapter);
    }

    public void limparBusca() {
        mCarros = mRepositorio.buscarCarro(null);
        mListView.setOnItemLongClickListener(this);
        mAdapter = new ArrayAdapter<Carro>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                mCarros);
        setListAdapter(mAdapter);
    }

    public interface AoClicarNoCarro {
        void clicouNoCarro(Carro carro);
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.menu_delete_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.acao_delete) {
            remover();
            actionMode.finish();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        mActionMode = null;
        mListView.clearChoices();
        mListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        limparBusca();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        boolean consumed = (mActionMode == null);
        if (consumed) {
            iniciarModoExclusao();
            mListView.setItemChecked(i, true);
            atualizarItensMarcados(mListView, i);
        }
        return consumed;
    }

    private void iniciarModoExclusao() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        mActionMode = activity.startSupportActionMode(this);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    private void atualizarItensMarcados(ListView l, int position) {
        l.setItemChecked(position, l.isItemChecked(position));
        atualizarTitulo();
    }

    private int qtdeItensMarcados() {
        SparseBooleanArray checked = mListView.getCheckedItemPositions();
        int checkedCount = 0;
        for (int i = 0; i < checked.size(); i++) {
            if (checked.valueAt(i)) {
                checkedCount++;
            }
        }
        return checkedCount;
    }

    private void atualizarTitulo() {
        int checkedCount = qtdeItensMarcados();
        String selecionados = getResources().getQuantityString(
                R.plurals.numero_selecionados,
                checkedCount, checkedCount);
        mActionMode.setTitle(selecionados);
    }

    private void remover() {
        final List<Carro> carrosExcluidos = new ArrayList<Carro>();
        SparseBooleanArray checked = mListView.getCheckedItemPositions();
        for (int i = checked.size() - 1; i >= 0; i--) {
            if (checked.valueAt(i)) {
                int position = checked.keyAt(i);
                Carro carro = mCarros.remove(position);
                carrosExcluidos.add(carro);
                mRepositorio.excluir(carro);
            }
        }
        Snackbar.make(mListView,
                getString(R.string.mensagem_excluir, carrosExcluidos.size()),
                Snackbar.LENGTH_LONG)
                .setAction(R.string.desfazer, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (Carro carro : carrosExcluidos) {
                            carro.id = 0;
                            mRepositorio.salvar(carro);
                        }
                        limparBusca();
                    }
                }).show();

        if (carrosExcluidos.size() > 0 && getActivity() instanceof AoExcluirCarros){
            ((AoExcluirCarros)getActivity()).exclusaoCompleta(carrosExcluidos);
        }
    }

    public interface  AoExcluirCarros {
        void exclusaoCompleta(List<Carro> carros);
    }
}
