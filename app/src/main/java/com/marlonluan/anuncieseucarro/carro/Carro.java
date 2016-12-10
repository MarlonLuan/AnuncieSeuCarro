package com.marlonluan.anuncieseucarro.carro;

import java.io.Serializable;

public class Carro implements Serializable {
    public long id;
    public String nome;
    public String endereco;
    public float estrelas;
    public float valor;

    public Carro(long id, String nome, String endereco, float estrelas) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.estrelas = estrelas;
    }
    public Carro(String nome, String endereco, float estrelas) {
        this(0, nome, endereco, estrelas);
    }
    @Override
    public String toString() {
        return nome;
    }
}
