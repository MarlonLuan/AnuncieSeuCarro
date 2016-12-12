package com.marlonluan.anuncieseucarro.carro;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.marlonluan.anuncieseucarro.*;

import com.marlonluan.anuncieseucarro.mapas.MapsActivity;
import com.marlonluan.anuncieseucarro.sms.SmsActivity;
import com.marlonluan.anuncieseucarro.util.*;

public class CarroDetalheFragment extends Fragment {
    public static final String TAG_DETALHE = "tagDetalhe";
    private static final String EXTRA_CARRO = "carro";
    private static final String EXTRA_ENDERECO = "endereco";
    private static final String EXTRA_TELEFONE = "telefone";

    private static final String NUMERO_TELEFONE = "(27) 99999-9999";

    TextView mTextNome;
    TextView mTextEndereco;
    RatingBar mRatingEstrelas;
    TextView mTextTelefone;
    TextView mTextValor;
    Carro mCarro;

    ShareActionProvider mShareActionProvider;

    public static CarroDetalheFragment novaInstancia(Carro carro) {
        Bundle parametros = new Bundle();
        parametros.putSerializable(EXTRA_CARRO, carro);
        CarroDetalheFragment fragment = new CarroDetalheFragment();
        fragment.setArguments(parametros);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCarro = (Carro)
                getArguments().getSerializable(EXTRA_CARRO);
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
        mTextTelefone = (TextView)
                layout.findViewById(R.id.txtTelefone);
        mTextValor = (TextView)
                layout.findViewById(R.id.txtValor);
        if (mCarro != null) {
            mTextNome.setText(mCarro.nome);
            mTextEndereco.setText(mCarro.endereco);
            mRatingEstrelas.setRating(mCarro.estrelas);
            mTextTelefone.setText(NUMERO_TELEFONE);
            mTextValor.setText(Auxiliar.FormataDinheiro(mCarro.valor));
        }

        Button mBtnLocalizacao = (Button) layout.findViewById(R.id.btnLocalizacao);
        mBtnLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartMapsActivity();
            }
        });

        Button mBtnSms = (Button) layout.findViewById(R.id.btnSms);
        mBtnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSmsActivity();
            }
        });
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

    public void StartMapsActivity(){
        final Context context = getContext();
        Intent it = new Intent(getContext(), MapsActivity.class);
        it.putExtra(EXTRA_ENDERECO, mCarro.endereco);
        startActivity(it);
    }

    public void StartSmsActivity(){
        final Context context = getContext();
        Intent it = new Intent(getContext(), SmsActivity.class);
        //it.putExtra(EXTRA_TELEFONE, mCarro.telefone);
        it.putExtra(EXTRA_TELEFONE, String.valueOf(mTextTelefone.getText()));
        startActivity(it);
    }
}