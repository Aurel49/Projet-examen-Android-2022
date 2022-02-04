package com.ynov.examenandroid.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.ynov.examenandroid.R;
import com.ynov.examenandroid.activity.RechercheLogementActivity;
import com.ynov.examenandroid.adapter.LogementAdapter;
import com.ynov.examenandroid.bo.detail_logement.Coordonnees;
import com.ynov.examenandroid.bo.logement.Format;
import com.ynov.examenandroid.bo.logement.Illustration;
import com.ynov.examenandroid.bo.logement.Thumbnail;
import com.ynov.examenandroid.bo.ville.CPic;
import com.ynov.examenandroid.bo.ville.ImageBnb;
import com.ynov.examenandroid.bo.logement.Logement;
import com.ynov.examenandroid.bo.logement.Place;
import com.ynov.examenandroid.bo.ville.Ville;
import com.ynov.examenandroid.databinding.FragmentDetailsLogementBinding;
import com.ynov.examenandroid.viewmodel.DetailsLogementViewModel;
import com.ynov.examenandroid.viewmodel.ListeLogementViewModel;
import com.ynov.examenandroid.viewmodel.ListeVilleViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsLogementFragment extends Fragment {

    /*
     *   Classe DetailsLogementFragment: controller du fragment fragment_details_logement
     *
     * */

    // Constante de débeugage
    private static final String TAG = "DETAILS_LOGEMENT_ADAPTER";

    // Référence HTTP
    OkHttpClient client;
    // Logement à afficher sur l'écarn avec ses détails
    Logement lg;
    // ViewModel pour sauvegarder les données d'un fragment
    // row_layout_message.xml
    private DetailsLogementViewModel vmDetailsLogement;
    private ListeLogementViewModel vmLogement;

    private TextView txt_ville, txt_titre, txt_prix, txt_owner, txt_lat, txt_lng;
    private Button bt_reserver, bt_accesMap;
    private ImageView imgDetLogement;

    // Variables pour ville
    int idVilleAAfficher;
    String nomville;


    View view;


    // Les méthode overridées suivent le lifecycle du fragemnt
    // Méthodes
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialise une logement
        Place p = new Place(0, "", new CPic(0, 0, ""));
        List<String> lst = new ArrayList<>();
        Illustration ill = new Illustration(new Format(new Thumbnail("")));
        Logement lg = new Logement(0, p, "", 0.0, "", 0.0, 0.0, lst, ill);
        // On créer une instance d'un client htpp
        client = new OkHttpClient();
        // On déclare un viewmodel pour maintenir en mémoire les données du fragment
        vmLogement = new ViewModelProvider(this).get(ListeLogementViewModel.class);
        vmDetailsLogement = new ViewModelProvider(this).get(DetailsLogementViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Change le titre de la barre de menu
        getActivity().setTitle("Details du logement :");

        view = inflater.inflate(R.layout.fragment_details_logement, container, false);

        // Récupère les données de la transition du frament_liste_ville vers le fragment_details_logement
        Logement dataLogementRecue =
                DetailsLogementFragmentArgs.fromBundle(getArguments()).getArgsLogementToDetailLogement();


        //
        if (dataLogementRecue != null && dataLogementRecue.getId() != 0) {
            // On récupère l'id et le nom de la ville reçu par la route du navGraph
            idVilleAAfficher = dataLogementRecue.getId();
            nomville = dataLogementRecue.getTitle();
            imgDetLogement = view.findViewById(R.id.idImageViewDetailsLogement);

            // Instances
            txt_ville = view.findViewById(R.id.idDetailsNomVilleV);
            txt_titre = view.findViewById(R.id.idDetailsTitreAnnonceV);
            txt_prix = view.findViewById(R.id.idDetailsPriceV);
            txt_owner = view.findViewById(R.id.idDetailsOwnerV);
            txt_lat = view.findViewById(R.id.idDetailsLatV);
            txt_lng = view.findViewById(R.id.idDetailsLngV);

            bt_reserver = view.findViewById(R.id.idBtReserver);
            bt_accesMap = view.findViewById(R.id.idAccesMap);

            // Initialisations
            Picasso.get().load("https://flutter-learning.mooo.com" + dataLogementRecue.getIllustrations().getFormats().getThumbnail().getUrl())
                    .resize(350, 350).into(imgDetLogement);

            txt_ville.setText(dataLogementRecue.getPlace().getName());
            txt_titre.setText(dataLogementRecue.getTitle());
            txt_prix.setText(String.valueOf(dataLogementRecue.getPrice()));
            txt_owner.setText(dataLogementRecue.getOwner());
            txt_lat.setText(String.valueOf(dataLogementRecue.getLat()));
            txt_lng.setText(String.valueOf(dataLogementRecue.getLng()));

        } else {
            Toast.makeText(getContext(), "Echec réception données du fragment Logement vers Details Logement ", Toast.LENGTH_SHORT).show();
        }

        // Accès à la page des dates de réservation
        bt_reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Arguments à envoyer à la page dates de réservation
                DetailsLogementFragmentDirections.ActionDetailsLogementFragmentToDateDeReservation uneAction =
                        DetailsLogementFragmentDirections.actionDetailsLogementFragmentToDateDeReservation(dataLogementRecue);

                // On change de page pour afficher la page des détails du Logement
                Navigation.findNavController(view).navigate((NavDirections) uneAction);

            }
        });

        // Accès au fragment Map pour localiser le logement
        bt_accesMap.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                // Coordonnées du logement à localiser
                Coordonnees coord = new Coordonnees(dataLogementRecue.getLat(), dataLogementRecue.getLng(), 18.0);

                // Arguments à envoyer à la page dates de réservation
                DetailsLogementFragmentDirections.ActionDetailsLogementFragmentToMap uneAction =
                        DetailsLogementFragmentDirections.actionDetailsLogementFragmentToMap(coord);

                // On change de page pour afficher la page des détails du Logement
                Navigation.findNavController(v).navigate((NavDirections) uneAction);

            }
        });

        //return binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }




    // Mise à jour d'un textview
    private void setText(final TextView text, final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    // Affiche une toast
    private void affToast(final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
            }
        });
    }    // Affiche une toast

    private void affImageLogement(String url) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Récupère l'url du json sous pic et on l'affiche sur le fragment details logement
                Picasso.get().load("https://flutter-learning.mooo.com" + url).resize(350, 350).into(imgDetLogement);
            }
        });
    }
}
