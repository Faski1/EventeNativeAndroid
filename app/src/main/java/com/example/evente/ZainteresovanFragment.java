package com.example.evente;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.evente.helper.Config;
import com.example.evente.helper.MyGson;
import com.example.evente.helper.Sesija;
import com.example.evente.model.Eventi;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ZainteresovanFragment extends Fragment {

    Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(13)
            .oval(false)
            .build();
    private ListView interesovanjaListView;
    private BaseAdapter adapterInteresovanja;
    //private int myLastVisiblePos;
    private ZainteresovanFragment.OnFragmentInteractionListener mListener;
    private TextView noItemsTextView;
    private ProgressDialog dialog;
    private List<Eventi> interesovanjaList = new ArrayList<>();


    public ZainteresovanFragment(){
        //mora imati default prazan konstruktor
    }
    public static MainPageFragment newInstance() {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidNetworking.enableLogging();



        //BlockUI();
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zainteresovan, container, false);
        PostaviResurse(view);



        //BlockUI();
        /*if(eventiList.size()==0){
            GetEvente();
        }*/


        return view;
    }

    @SuppressLint("NewApi")
    private void PostaviResurse(View view) {
        interesovanjaListView = view.findViewById(R.id.InteresovanjaListaEvenata);
        noItemsTextView = view.findViewById(R.id.noItemsZainteresovan);


        interesovanjaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Eventi pomocni = interesovanjaList.get(position);

            mListener.onFragmentInteraction(pomocni.getImgUrl(), pomocni.getNaziv(), pomocni.getObjekatOdrzavanja(), pomocni.getDatumOdrzavanja().toLocaleString(), pomocni.getOpis(), pomocni.getEventId());

        }});

        //dialog = ProgressDialog.show(this, "Pristup podacima", "U toku");

    }


    @Override
    public void onResume() {
        super.onResume();
        if (interesovanjaList.isEmpty()) {
            AndroidNetworking.get(Config.url + "api/Eventi/GetInteresovanja/{KorisnikId}/{AuthToken}")
                    .addPathParameter("KorisnikId", Integer.toString(Sesija.getLogiraniKorisnik().KorisnikId))
                    .addPathParameter("AuthToken", Sesija.getLogiraniKorisnik().AuthToken)
                    .build().getAsJSONArray(new JSONArrayRequestListener() {
                @Override
                public void onResponse(JSONArray response) {
                    Gson gson = new MyGson().build();
                    try {
                        Log.d("Response:", response.getString(0));

                        JSONArray array = response;
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject myData = array.getJSONObject(i);
                            JsonObject element = gson.fromJson(myData.toString(), JsonObject.class);


                            Eventi eventi = gson.fromJson(element, Eventi.class);


                            interesovanjaList.add(eventi);
                        }

                        ImaRezultataInteresovanja();
                        interesovanjaListView.requestLayout();
                        PostaviListuInteresovanja();
                        PostaviAdapterInteresovanja();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                /*for (int i = 0; i < eventiList.size(); i++) {
                    Log.wtf("TEST2", "onResponse: " + i + eventiList.get(i).getNaziv());
                    Log.wtf("TEST2", "onResponse: " + i + eventiList.get(i).getOpis());

                }*/


                    //UnblockUI();

                    Log.wtf("Velicina", Integer.toString(interesovanjaList.size()));
                }

                @Override
                public void onError(ANError anError) {
                    noItemsTextView.setVisibility(View.VISIBLE);
                }

            });
        } else {
            PostaviListuInteresovanja();
            PostaviAdapterInteresovanja();
        }


    }





    private void PostaviAdapterInteresovanja() {
        interesovanjaListView.setAdapter(adapterInteresovanja);
        adapterInteresovanja.notifyDataSetChanged();
    }

    private void PostaviListuInteresovanja() {

        adapterInteresovanja = new BaseAdapter() {
            @Override
            public int getCount() {
                return interesovanjaList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = (View) inflater.inflate(R.layout.eventistavka, null);
                }
                final Eventi row = interesovanjaList.get(position);


                ImageView eventiImage = (ImageView) convertView.findViewById(R.id.eventImageView);
                Picasso.get().load(row.getImgUrl()).placeholder(R.drawable.progress_animation).transform(transformation).into(eventiImage);
                TextView eventiName = (TextView) convertView.findViewById(R.id.EventNaziv);
                eventiName.setText(row.getNaziv());
                TextView eventiDesc = (TextView) convertView.findViewById(R.id.OpisEventa);
                eventiDesc.setText(row.getOpis());
                TextView eventiLokacija = (TextView) convertView.findViewById(R.id.lokacijaTextView);
                eventiLokacija.setText(row.getDrzava()+": "+ row.getGrad()); //promijeniti u grad kad se dodaju modeli
                TextView eventiVrijeme = (TextView) convertView.findViewById(R.id.vrijemeTextView);
                eventiVrijeme.setText(row.getDatumOdrzavanja().toLocaleString());
                return convertView;
            }

        };


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onStart() {
        super.onStart();
        try {
            mListener = (ZainteresovanFragment.OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String imageURL, String naziv, String objekatOdrzavanja, String datumOdrzavanja,  String opis, int EventId );
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void BlockUI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        }).start();

    }

    private void UnblockUI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }).start();

    }
    private void ImaRezultataInteresovanja(){
        if(interesovanjaList.size()==0)
            noItemsTextView.setVisibility(View.VISIBLE);
        else
            noItemsTextView.setVisibility(View.GONE);
    }

}
