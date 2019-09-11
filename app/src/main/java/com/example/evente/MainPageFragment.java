package com.example.evente;

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

public class MainPageFragment extends Fragment {

    Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(13)
            .oval(false)
            .build();
    private ListView sviEventiListView;
    private BaseAdapter adapter;
    //private int myLastVisiblePos;
    private OnFragmentInteractionListener mListener;
    private TextView noItemsTextView;
    private ProgressDialog dialog;
    private List<Eventi> eventiList = new ArrayList<>();
    static String query="";
    public MainPageFragment(){
        //mora imati default prazan konstruktor
    }
    public static MainPageFragment newInstance(String queryy) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putString("query", queryy);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString("query");
        }
        AndroidNetworking.enableLogging();

        //BlockUI();
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        PostaviResurse(view);

        //BlockUI();
        /*if(eventiList.size()==0){
            GetEvente();
        }*/


        return view;
    }

    private void PostaviResurse(View view) {
        noItemsTextView = view.findViewById(R.id.noItems);
        sviEventiListView = view.findViewById(R.id.ListListaEvenata);
        noItemsTextView = view.findViewById(R.id.noItems);
        sviEventiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Eventi pomocni = eventiList.get(position);

                mListener.onFragmentInteraction(pomocni.getImgUrl(), pomocni.getNaziv(), pomocni.getObjekatOdrzavanja(), pomocni.getDatumOdrzavanja().toLocaleString(), pomocni.getOpis(), pomocni.getEventId());

            }});
        //dialog = ProgressDialog.show(this, "Pristup podacima", "U toku");

    }


    @Override
    public void onResume() {
        super.onResume();
if(eventiList.isEmpty()) {
    AndroidNetworking.get(Config.url + "api/Eventi/{AuthToken}/{Query}")
            .addPathParameter("AuthToken", Sesija.getLogiraniKorisnik().AuthToken)
            .addPathParameter("Query", query)
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


                    eventiList.add(eventi);
                }

                ImaRezultata();
                sviEventiListView.requestLayout();
                PostaviListu();
                PostaviAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }

                /*for (int i = 0; i < eventiList.size(); i++) {
                    Log.wtf("TEST2", "onResponse: " + i + eventiList.get(i).getNaziv());
                    Log.wtf("TEST2", "onResponse: " + i + eventiList.get(i).getOpis());

                }*/



            //UnblockUI();

            Log.wtf("Velicina", Integer.toString(eventiList.size()));
        }

        @Override
        public void onError(ANError anError) {
            noItemsTextView.setVisibility(View.VISIBLE);
        }

    });
}
else {
    PostaviListu();
    PostaviAdapter();
}
    }





    private void PostaviAdapter() {
        sviEventiListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // Log.wtf("velicina", Integer.toString(eventiList.size()));
    }

    private void PostaviListu() {

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return eventiList.size();
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
                final Eventi row = eventiList.get(position);


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
            mListener = (OnFragmentInteractionListener) getActivity();
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
    private void ImaRezultata(){
        if(eventiList.size()==0)
            noItemsTextView.setVisibility(View.VISIBLE);
        else
            noItemsTextView.setVisibility(View.GONE);
    }

}
