package com.marlonluan.anuncieseucarro.mapas;

import android.os.AsyncTask;

import com.marlonluan.anuncieseucarro.util.Auxiliar;

import org.json.JSONObject;

public class EnderecoFormatado extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        JSONObject json = new JSONObject();
        String result = "";

        if (params.length > 0)
            json = Auxiliar.getEndereco(params[0]);
        try {
            if (json != null) {
                result = json.getString("formatted_address");

                result = result.trim();
            }
        } catch (Exception e) {

        }

        return result;
    }
}