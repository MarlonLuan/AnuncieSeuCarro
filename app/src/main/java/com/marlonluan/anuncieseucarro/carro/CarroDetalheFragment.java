package com.marlonluan.anuncieseucarro.carro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.marlonluan.anuncieseucarro.*;

import com.marlonluan.anuncieseucarro.util.*;

public class CarroDetalheFragment extends Fragment {
    public static final String TAG_DETALHE = "tagDetalhe";
    private static final String EXTRA_Carro = "carro";
    TextView mTextNome;
    TextView mTextEndereco;
    RatingBar mRatingEstrelas;
    TextView mTextValor;
    Carro mCarro;

    ShareActionProvider mShareActionProvider;

    public static CarroDetalheFragment novaInstancia(Carro carro) {
        Bundle parametros = new Bundle();
        parametros.putSerializable(EXTRA_Carro, carro);
        CarroDetalheFragment fragment = new CarroDetalheFragment();
        fragment.setArguments(parametros);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCarro = (Carro)
                getArguments().getSerializable(EXTRA_Carro);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(
                R.layout.fragment_detalhe_carro, container, false);
        mTextNome = (TextView)layout.findViewById(R.id.txtNome);
        mTextEndereco = (TextView)
                layout.findViewById(R.id.txtEndereco);
        mRatingEstrelas = (RatingBar)
                layout.findViewById(R.id.rtbEstrelas);
        mTextValor = (TextView)
                layout.findViewById(R.id.txtValor);
        if (mCarro != null) {
            mTextNome.setText(mCarro.nome);
            mTextEndereco.setText(mCarro.endereco);
            mRatingEstrelas.setRating(mCarro.estrelas);
            mTextValor.setText(Auxiliar.FormataDinheiro(mCarro.valor));
        }
        return layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_carro_detalhe, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(shareItem);
        String texto = getString(R.string.texto_compartilhar,
                mCarro.nome, mCarro.valor);
        Intent it = new Intent(Intent.ACTION_SEND);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        it.setType("text/plain");
        it.putExtra(Intent.EXTRA_TEXT, texto);
        mShareActionProvider.setShareIntent(it);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.acao_editar) {
            Activity activity = getActivity();
            if (activity instanceof AoEditarCarro) {
                AoEditarCarro aoEditarCarro = (AoEditarCarro)activity;
                aoEditarCarro.aoEditarcarro(mCarro);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public Carro getCarro() {
        return mCarro;
    }
    public interface AoEditarCarro {
        void aoEditarcarro(Carro carro);
    }
}

