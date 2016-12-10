package com.marlonluan.anuncieseucarro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.marlonluan.anuncieseucarro.carro.*;

public class MenuPrincipalActivity extends AppCompatActivity {

    Button mBtnMeusAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        mBtnMeusAnuncios = (Button) findViewById(R.id.btnMeusAnuncios);
        mBtnMeusAnuncios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartCarroActivity();
            }
        });
    }

    public void StartCarroActivity(){
        Intent it = new Intent(this, CarroActivity.class);
        startActivity(it);
    }
}
