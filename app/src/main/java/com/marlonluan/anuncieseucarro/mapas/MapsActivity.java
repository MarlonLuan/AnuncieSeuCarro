package com.marlonluan.anuncieseucarro.mapas;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.marlonluan.anuncieseucarro.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String mEndereco;

    private static final String EXTRA_ENDERECO = "endereco";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mEndereco = getIntent().getStringExtra(EXTRA_ENDERECO);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng localizacaoSydney = new LatLng(-34, 151);
        LatLng localizacao = localizacaoSydney;

        if (mEndereco != null && !mEndereco.trim().equals("")) {
            try {
                localizacao = new CoordenadasEndereco().execute(mEndereco).get();

            } catch (Exception ex) {
            }
        }

        mMap.addMarker(new MarkerOptions().position(localizacao).title("Maps"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacao));
    }
}
