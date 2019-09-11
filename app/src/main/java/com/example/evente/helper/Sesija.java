package com.example.evente.helper;

import android.content.SharedPreferences;

import com.example.evente.MyApp;
import com.example.evente.model.Korisnici;

public class Sesija {

    private static String PREFS_NAME = "DatotekaZaSharedPreferences";

    public static Korisnici getLogiraniKorisnik() {
        SharedPreferences settings = MyApp.getContext().getSharedPreferences(PREFS_NAME, 0);
        String str = settings.getString("logiraniKorisnikJson", "");

        if(str.length()==0)
            return null;

        final Korisnici kc = MyGson.build().fromJson(str, Korisnici.class);
        return kc;

    }

    public static void setLogiraniKorisnik(Korisnici logiraniKorisnik) {
        final String str = MyGson.build().toJson(logiraniKorisnik);

        SharedPreferences settings = MyApp.getContext().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("logiraniKorisnikJson", str);

        editor.commit();
    }
}
