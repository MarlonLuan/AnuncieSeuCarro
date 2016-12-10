package com.marlonluan.anuncieseucarro.carro;

import android.util.Log;

import java.io.Serializable;

public class Carro implements Serializable {
    public long id;
    public String nome;
    public String endereco;
    public float estrelas;
    public double valor;

    public Carro(long id, String nome, String endereco, float estrelas, double valor) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.estrelas = estrelas;
        this.valor = valor;
        Log.i("ML", String.valueOf(valor));
    }
    public Carro(String nome, String endereco, float estrelas, double valor) {
        this(0, nome, endereco, estrelas, valor);
    }
    @Override
    public String toString() {
        return nome;
    }
}
