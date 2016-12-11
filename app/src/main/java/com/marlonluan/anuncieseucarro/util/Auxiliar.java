package com.marlonluan.anuncieseucarro.util;

import com.marlonluan.anuncieseucarro.*;
import com.marlonluan.anuncieseucarro.carro.*;
import com.marlonluan.anuncieseucarro.mapas.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

public final class Auxiliar {

    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static String FormataDinheiro(Double valor) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return "R$ " + decimalFormat.format(valor);
    }

    public static JSONObject getEndereco(String local) {

        JSONObject result = new JSONObject();
        String aux;

        String urlRota = "https://maps.googleapis.com/maps/api/geocode/json?" +
                "address=";
        try {
            String param = URLEncoder.encode(local, "utf-8");
            urlRota += param;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet requisicao = new HttpGet();
            requisicao.setHeader("Content-Type", "text/plain; charset=utf-8");
            requisicao.setURI(new URI(urlRota));
            HttpResponse resposta = client.execute(requisicao);
            BufferedReader br = new BufferedReader(new InputStreamReader(resposta.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");

            while ((aux = br.readLine()) != null) {
                sb.append(aux);
            }
            br.close();

            result = new JSONObject(sb.toString()).getJSONArray("results").getJSONObject(0);

        } catch (Exception e) {

        }

        return result;
    }
}
