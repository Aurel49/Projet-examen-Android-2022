package com.ynov.examenandroid.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.ynov.examenandroid.R;
import com.ynov.examenandroid.adapter.CustomGridAdapter;
import com.ynov.examenandroid.bo.detail_logement.DetailsDate;
import com.ynov.examenandroid.bo.logement.Logement;

import java.util.ArrayList;
import java.util.List;


public class DateDeReservation extends Fragment {

    // GridView pour l'affichage des dates
    private GridView maGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // La view
        View view = inflater.inflate(R.layout.fragment_date_de_reservation, container, false);

        // Change le titre de la barre de menu
        getActivity().setTitle("Dates de réservation :");

        // Instance de la GridView
        maGridView = view.findViewById(R.id.idGridViewdate);

        // Récupère les données de la transition du fragment_details_logement vers le fragment_date_reservation
        Logement dataLogementRecue =
                DateDeReservationArgs.fromBundle(getArguments()).getArgsDetailsLogementToDateReservation();

        // Construction de l'objet à afficher dans la grid
        List<DetailsDate> lst = new ArrayList<DetailsDate>();


        for (int i = 0; i < dataLogementRecue.getListDateAvailable().size() - 1; i++) {
            lst.add(new DetailsDate(
                    dataLogementRecue.getIllustrations().getFormats().getThumbnail().getUrl(),
                    dataLogementRecue.getListDateAvailable().get(i),
                    dataLogementRecue.getPlace().getName()
            ));
        }


        //  ArrayAdapter<DetailsDate> arrayAdapter =                new ArrayAdapter<DetailsDate>(getContext(), R.layout.row_layout_date_reservation, lst);

        maGridView.setAdapter(new CustomGridAdapter(getContext(), lst));

        maGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = maGridView.getItemAtPosition(position);
                DetailsDate details = (DetailsDate) o;
                Toast.makeText(getContext(),
                        "Vous avez réservé pour le " + details.get_date() + "\n à " + details.get_lieu(),
                        Toast.LENGTH_LONG).show();
                //  logement.getPlace().getName()
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}