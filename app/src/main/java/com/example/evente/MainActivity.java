package com.example.evente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.evente.helper.Sesija;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        if(Sesija.getLogiraniKorisnik() == null)
        {
            final Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            final Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        }
        finish();
    }

}





















//JAVA OBICNI NACIN RUNNANJA THREADOVA BEZ ANDROID KLASA
       /* new Thread(new Runnable() {
            ProgressDialog dialog;
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog = ProgressDialog.show(MainActivity.this, "Pristup podacima", "U toku");
                        dialog.show();
                    }
                });


                final Korisnici rezultat = AutentifikacijaApi.Provjera(txtUsername.getText().toString(), txtPassword.getText().toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Dobrodosli " + rezultat.KorisnickoIme, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

               *//* System.out.println(rezultat.Lozinka);*//*
            }
        }).start();*/
