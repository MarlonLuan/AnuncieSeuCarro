package com.marlonluan.anuncieseucarro.carro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.marlonluan.anuncieseucarro.*;

public class CarroDetalheActivity extends AppCompatActivity
        implements CarroDetalheFragment.AoEditarCarro,
        CarroDialogFragment.AoSalvarCarro {

    public static final String EXTRA_Carro = "carro";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro_detalhe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Carro carro = (Carro)intent.getSerializableExtra(EXTRA_Carro);
        exibirCarroFragment(carro);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void exibirCarroFragment(Carro carro) {
        CarroDetalheFragment fragment = CarroDetalheFragment.novaInstancia(carro);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.detalhe, fragment, CarroDetalheFragment.TAG_DETALHE);
        ft.commit();
    }
    @Override
    public void aoEditarcarro(Carro carro) {
        CarroDialogFragment editNameDialog = CarroDialogFragment.newInstance(carro);
        editNameDialog.abrir(getSupportFragmentManager());
    }
    @Override
    public void salvouCarro(Carro carro) {
        CarroRepositorio repo = new CarroRepositorio(this);
        repo.salvar(carro);
        exibirCarroFragment(carro);
        setResult(RESULT_OK);
    }
}
