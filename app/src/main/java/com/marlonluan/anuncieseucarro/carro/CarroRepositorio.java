package com.marlonluan.anuncieseucarro.carro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class CarroRepositorio {
    private CarroSQLHelper helper;
    public CarroRepositorio(Context ctx) {
        helper = new CarroSQLHelper(ctx);
    }
    private long inserir(Carro carro) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CarroSQLHelper.COLUNA_NOME, carro.nome);
        cv.put(CarroSQLHelper.COLUNA_ENDERECO, carro.endereco);
        cv.put(CarroSQLHelper.COLUNA_ESTRELAS, carro.estrelas);
        cv.put(CarroSQLHelper.COLUNA_VALOR, carro.valor);
        long id = db.insert(CarroSQLHelper.TABELA_CARRO, null, cv);
        if (id != -1) {
            carro.id = id;
        }
        db.close();
        return id;
    }
    private int atualizar(Carro carro) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CarroSQLHelper.COLUNA_ID, carro.id);
        cv.put(CarroSQLHelper.COLUNA_NOME, carro.nome);
        cv.put(CarroSQLHelper.COLUNA_ENDERECO, carro.endereco);
        cv.put(CarroSQLHelper.COLUNA_ESTRELAS, carro.estrelas);
        cv.put(CarroSQLHelper.COLUNA_VALOR, carro.valor);
        int linhasAfetadas = db.update(
                CarroSQLHelper.TABELA_CARRO,
                cv,
                CarroSQLHelper.COLUNA_ID +" = ?",
                new String[]{ String.valueOf(carro.id)});
        db.close();
        return linhasAfetadas;
    }
    public void salvar(Carro carro) {
        if (carro.id == 0) {
            inserir(carro);
        } else {
            atualizar(carro);
        }
    }
    public int excluir(Carro carro) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int linhasAfetadas = db.delete(
                CarroSQLHelper.TABELA_CARRO,
                CarroSQLHelper.COLUNA_ID +" = ?",
                new String[]{ String.valueOf(carro.id)});
        db.close();
        return linhasAfetadas;
    }
    public List<Carro> buscarCarro(String filtro) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM "+ CarroSQLHelper.TABELA_CARRO;
        String[] argumentos = null;
        if (filtro != null) {
            sql += " WHERE "+ CarroSQLHelper.COLUNA_NOME +" LIKE ?";
            argumentos = new String[]{ filtro };
        }
        sql += " ORDER BY "+ CarroSQLHelper.COLUNA_NOME;
        Cursor cursor = db.rawQuery(sql, argumentos);
        List<Carro> carros = new ArrayList<Carro>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(
                    cursor.getColumnIndex(
                            CarroSQLHelper.COLUNA_ID));
            String nome = cursor.getString(
                    cursor.getColumnIndex(
                            CarroSQLHelper.COLUNA_NOME));
            String endereco = cursor.getString(
                    cursor.getColumnIndex(
                            CarroSQLHelper.COLUNA_ENDERECO));
            float estrelas = cursor.getFloat(
                    cursor.getColumnIndex(
                            CarroSQLHelper.COLUNA_ESTRELAS));
            double valor = cursor.getDouble(
                    cursor.getColumnIndex(
                            CarroSQLHelper.COLUNA_VALOR));
            Carro carro = new Carro(id, nome, endereco, estrelas, valor);
            carros.add(carro);
        }
        cursor.close();
        db.close();
        return carros;
    }
}
