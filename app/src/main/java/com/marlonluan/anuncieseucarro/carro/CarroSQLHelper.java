package com.marlonluan.anuncieseucarro.carro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarroSQLHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "dbCarro";
    private static final int VERSAO_BANCO = 1;

    public static final String TABELA_CARRO = "carro";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_ENDERECO = "endereco";
    public static final String COLUNA_ESTRELAS = "estrelas";
    public static final String COLUNA_VALOR = "valor";

    public CarroSQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE "+ TABELA_CARRO +" (" +
                        COLUNA_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        COLUNA_NOME     +" TEXT NOT NULL, "+
                        COLUNA_ENDERECO +" TEXT, " +
                        COLUNA_ESTRELAS +" REAL," +
                        COLUNA_VALOR +" REAL)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // para as próximas versões
    }
}
