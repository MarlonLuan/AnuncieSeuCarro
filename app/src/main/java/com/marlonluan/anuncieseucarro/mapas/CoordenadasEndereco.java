package com.marlonluan.anuncieseucarro.mapas;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.marlonluan.anuncieseucarro.util.Auxiliar;

import org.json.JSONObject;

public class CoordenadasEndereco extends AsyncTask<String, Void, LatLng> {

    @Override
    protected LatLng doInBackground(String... params) {

        JSONObject json = new JSONObject();
        LatLng result = new LatLng(0, 0);

        if (params.length > 0)
            json = Auxiliar.getEndereco(params[0]);
        try {
            if (json != null) {
                JSONObject location = json.getJSONObject("geometry").getJSONObject("location");
                double latitude = Double.valueOf(location.getString("lat"));
                double longitude = Double.valueOf(location.getString("lng"));

                result = new LatLng(latitude, longitude);
            }
        } catch (Exception e) {

        }

        return result;
    }
}