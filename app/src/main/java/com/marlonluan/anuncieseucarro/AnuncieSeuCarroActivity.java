package com.marlonluan.anuncieseucarro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AnuncieSeuCarroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncie_seu_carro);
        StartLoginActivity();
    }

    public void StartLoginActivity(){
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
    }
}
