package com.ynov.examenandroid.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ynov.examenandroid.R;
import com.ynov.examenandroid.bo.detail_logement.Coordonnees;
import com.ynov.examenandroid.bo.logement.Logement;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;


public class map extends Fragment {

    private MapView myOpenMapView;
    IMapController mapController;

    private Button bt_initPosition;

    public static final int REQUEST_CODE_OSM = 1234;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getContext();

        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Instance bouton init position de la map par rapport au logement recherché
        bt_initPosition = view.findViewById(R.id.idInitMap);


        // Récupère les données de la transition du frament_liste_ville vers le fragment_details_logement
        Coordonnees dataLogementRecue =
                mapArgs.fromBundle(getArguments()).getArgsDetailsLogementToMap();

        //
        myOpenMapView = view.findViewById(R.id.idMap);
        //
        mapController = myOpenMapView.getController();

        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setClickable(true);
        //mMapController = (MapController) myOpenMapView.getController();
        myOpenMapView.setTileSource(TileSourceFactory.MAPNIK); // DEFAULT_TILE_SOURCE

        // Niveau de zoom
        myOpenMapView.getController().setZoom(dataLogementRecue.getHaut());

        // Initialisation de la vue sur les coordonnées du logement
        GeoPoint startPoint = new GeoPoint(dataLogementRecue.getLat(), dataLogementRecue.getLng());
        mapController.setCenter(startPoint);

        // ajoute un marqueur
        Marker tec = new Marker(myOpenMapView);
        tec.setPosition(new GeoPoint(dataLogementRecue.getLat(), dataLogementRecue.getLng()));
        tec.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        tec.setIcon(getResources().getDrawable(R.drawable.person)); //
        tec.setTitle("Logement");
        myOpenMapView.getOverlays().add(tec);

        bt_initPosition.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                retourCoordonneesLogement(dataLogementRecue.getLat(), dataLogementRecue.getLng(), dataLogementRecue.getHaut());
            }
        });

        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION, // utilise le réseau pour la triangulation
                        Manifest.permission.ACCESS_FINE_LOCATION, // utilise le GPS
                }, REQUEST_CODE_OSM
        );

        myOpenMapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });


        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (REQUEST_CODE_OSM == requestCode) {
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED ||
                    grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                Configuration.getInstance().load(
                        getContext(),
                        PreferenceManager.getDefaultSharedPreferences(getContext())
                );
                showMyLocation();
            }
        }
    }

    // Montrer la position
    private void showMyLocation() {

        MyLocationNewOverlay mlno = new MyLocationNewOverlay(myOpenMapView);
        mlno.enableMyLocation();
        myOpenMapView.getOverlayManager().add(mlno);
    }

    private void retourCoordonneesLogement(double lat, double lng, double haut) {

        // Initialisation de la vue sur les coordonnées du logement
        GeoPoint startPoint = new GeoPoint(lat, lng);
        mapController.setCenter(startPoint);
    }

}