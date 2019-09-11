package com.example.evente;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.evente.helper.Config;
import com.example.evente.helper.MyGson;
import com.example.evente.helper.Sesija;
import com.example.evente.model.Interesovanja;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONObject;


public class EventDetailsFragment extends Fragment {
    Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(13)
            .oval(false)
            .build();
    // TODO: Rename and change types of parameters
    private String imageURL;
    private String naziv;
    private String objekatOdrzavanja;
    private String datumOdrzavanja;
    private String opis;
    private int EventId;
    private Button ZainteresovanBtn;
    private Button IdemBtn;
    private boolean prviPost = false;
    private Interesovanja interesovanja = new Interesovanja();

    private OnFragmentInteractionListener mListener;


    private ImageView image;
    private TextView nazivEventa;
    private TextView objekatEventa;
    private TextView datumEventa;
    private TextView opisEventa;


    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public static EventDetailsFragment newInstance() {
        EventDetailsFragment fragment = new EventDetailsFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageURL = getArguments().getString("imageURL");
            naziv = getArguments().getString("naziv");
            datumOdrzavanja = getArguments().getString("datumOdrzavanja");
            opis = getArguments().getString("opis");
            objekatOdrzavanja = getArguments().getString("objekatOdrzavanja");
            EventId = getArguments().getInt("EventId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        PostaviResurse(view);

        return view;
    }

    private void PostaviResurse(View view) {
        image = view.findViewById(R.id.eventDetailsImageView);
        nazivEventa = view.findViewById(R.id.DetailsEventaNaziv);
        objekatEventa = view.findViewById(R.id.DetailsObjekat);
        datumEventa = view.findViewById(R.id.DetailsEventaDatum);
        opisEventa = view.findViewById(R.id.DetailsEventaOpis);
        ZainteresovanBtn = view.findViewById(R.id.ZainteresovanButton);
        IdemBtn = view.findViewById(R.id.IdemButton);

        ZainteresovanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onZainteresovanClick(v);

            }
        });
        IdemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIdemClick(v);
            }
        });

        Picasso.get().load(imageURL).placeholder(R.drawable.progress_animation).transform(transformation).into(image);

        nazivEventa.setText(naziv);
        objekatEventa.setText(objekatOdrzavanja);
        datumEventa.setText(datumOdrzavanja);
        opisEventa.setText(opis);
        interesovanja.setEventId(EventId);
        interesovanja.setKorisnikId(Sesija.getLogiraniKorisnik().KorisnikId);


        GetInteresovanja(EventId, Sesija.getLogiraniKorisnik().KorisnikId);
    }

    private void GetInteresovanja(int eventId, int korisnikId) {
         AndroidNetworking.get(Config.url + "api/Interesovanja/GetInteresovanja/{EventId}/{KorisnikId}/{AuthToken}").
                 addPathParameter("EventId",Integer.toString(eventId)).
                 addPathParameter("KorisnikId",Integer.toString(korisnikId)).
                 addPathParameter("AuthToken",Sesija.getLogiraniKorisnik().AuthToken).
                 build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                if(!response.toString().isEmpty() && response.toString()!="null")
                {
                    try {
                        Gson gson = new MyGson().build();

                        JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

                        interesovanja = gson.fromJson(jsonObject, Interesovanja.class);
                    }
                    catch (Exception ex){
                        Log.wtf(ex.getCause().toString(), ex.getMessage());
                    }
                }
                else{
                    prviPost=true;
                }
            }

            @Override
            public void onError(ANError anError) {
                prviPost=true;
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }
    public void onZainteresovanClick(final View view)
    {
        if(prviPost==true)
        {
            Toast.makeText(MyApp.getContext(), "Zainteresovani ste!", Toast.LENGTH_SHORT).show();
            interesovanja.setZainteresovan(true);
            interesovanja.setIdem(false);
            PostZainteresovanIdemClick(interesovanja);
            prviPost=false;
        }
        else if(interesovanja.isZainteresovan())
        {
            Toast.makeText(MyApp.getContext(), "Zainteresovanost uklonjena", Toast.LENGTH_SHORT).show();
            DeleteInteresovanjeIdem(interesovanja);
            prviPost=true;
        }
        else if(!interesovanja.isZainteresovan()){
            Toast.makeText(MyApp.getContext(), "Zainteresovani ste!", Toast.LENGTH_SHORT).show();
            interesovanja.setZainteresovan(true);
            interesovanja.setIdem(false);
            UpdateZainteresovanIdemClick(interesovanja);
        }

    }

    private void UpdateZainteresovanIdemClick(Interesovanja interesovanja) {
        AndroidNetworking.patch(Config.url+"api/Interesovanja/{EventId}/{KorisnikId}/{Zainteresovan}/{Idem}/{AuthToken}")
                .addPathParameter("EventId", Integer.toString(interesovanja.getEventId()))
                .addPathParameter("KorisnikId", Integer.toString(interesovanja.getKorisnikId()))
                .addPathParameter("Zainteresovan", Boolean.toString(interesovanja.isZainteresovan()))
                .addPathParameter("Idem", Boolean.toString(interesovanja.isIdem()))
                .addPathParameter("AuthToken", Sesija.getLogiraniKorisnik().AuthToken).build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });
    }

    private void DeleteInteresovanjeIdem(Interesovanja interesovanja) {
        AndroidNetworking.delete(Config.url+"api/Interesovanja/{EventId}/{KorisnikId}/{AuthToken}")
                .addPathParameter("EventId", Integer.toString(interesovanja.getEventId()))
                .addPathParameter("KorisnikId", Integer.toString(interesovanja.getKorisnikId()))
                .addPathParameter("AuthToken", Sesija.getLogiraniKorisnik().AuthToken).build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });
    }

    void onIdemClick(final View view)
    {
        if(prviPost==true)
        {
            Toast.makeText(MyApp.getContext(), "Idete na event!", Toast.LENGTH_SHORT).show();
            interesovanja.setZainteresovan(false);
            interesovanja.setIdem(true);
            PostZainteresovanIdemClick(interesovanja);
            prviPost=false;
        }
        else if(interesovanja.isIdem())
        {
            Toast.makeText(MyApp.getContext(), "Odlazak uklonjen", Toast.LENGTH_SHORT).show();
            DeleteInteresovanjeIdem(interesovanja);
            prviPost=true;
        }
        else if(!interesovanja.isIdem()){
            Toast.makeText(MyApp.getContext(), "Idete na event", Toast.LENGTH_SHORT).show();
            interesovanja.setZainteresovan(false);
            interesovanja.setIdem(true);
            UpdateZainteresovanIdemClick(interesovanja);
        }
    }
    private void PostZainteresovanIdemClick(Interesovanja interesovanjaItem) {
        AndroidNetworking.post(Config.url+"api/Interesovanja/{EventId}/{KorisnikId}/{Zainteresovan}/{Idem}/{AuthToken}")
                .addPathParameter("EventId", Integer.toString(interesovanjaItem.getEventId()))
                .addPathParameter("KorisnikId", Integer.toString(interesovanjaItem.getKorisnikId()))
                .addPathParameter("Zainteresovan", Boolean.toString(interesovanjaItem.isZainteresovan()))
                .addPathParameter("Idem", Boolean.toString(interesovanjaItem.isIdem()))
                .addPathParameter("AuthToken", Sesija.getLogiraniKorisnik().AuthToken)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {

            }
            @Override
            public void onError(ANError error) {

            }
        });
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
