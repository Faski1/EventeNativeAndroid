package com.example.evente;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.evente.helper.Config;
import com.example.evente.helper.Validation;

import org.json.JSONObject;

import java.util.Random;

public class RegistracijaActivity extends AppCompatActivity {
    EditText korIme;
    EditText email;
    EditText ime;
    EditText prezime;
    EditText lozinka;
    CardView registracijaBtn;
    TextView sendToLogin;
    private Validation validation = new Validation(this);
    private TextInputLayout imeInputLayout, prezimeInputLayout, emailInputLayout, korImeInputLayout, lozinkaInputLayout;
    ProgressDialog dialog;
    Random random = new Random();
    String authToken =  String.format("%09d", random.nextInt(1000000000));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        setResurse();
    }
    private void setResurse() {
        korIme = findViewById(R.id.regKorImeEditText);
        email = findViewById(R.id.regEmailEditText);
        ime = findViewById(R.id.regImeEditText);
        prezime = findViewById(R.id.regPrezimeEditText);
        lozinka = findViewById(R.id.regLozinkaEditText);
        registracijaBtn = findViewById(R.id.regLoginCardView);
        sendToLogin = findViewById(R.id.regImaRacunLoginTextView);
        imeInputLayout = findViewById(R.id.imeInputLayout);
        prezimeInputLayout = findViewById(R.id.prezimeInputLayout);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        korImeInputLayout = findViewById(R.id.korImeInputLayout);
        lozinkaInputLayout = findViewById(R.id.lozinkaInputLayout);
        registracijaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ValidateInputs())
                    return;
                dialog = ProgressDialog.show(v.getContext(), "Registracija", "U toku");
                dialog.show();
                AndroidNetworking.post(Config.url+"api/Korisnici/{KorisnickoIme}/{Email}/{Ime}/{Prezime}/{Lozinka}/{AuthToken}")
                        .addPathParameter("KorisnickoIme", korIme.getText().toString())
                        .addPathParameter("Email", email.getText().toString())
                        .addPathParameter("Ime", ime.getText().toString())
                        .addPathParameter("Prezime", prezime.getText().toString())
                        .addPathParameter("Lozinka", lozinka.getText().toString())
                        .addPathParameter("AuthToken", authToken)
                        .build()
                       .getAsJSONObject(new JSONObjectRequestListener() {
                           @Override
                           public void onResponse(JSONObject response) {
                                dialog.dismiss();
                           }

                           @Override
                           public void onError(ANError anError) {
                               dialog.dismiss();
                               if(anError.getErrorCode()==409)
                               {
                                   Toast.makeText(MyApp.getContext(), anError.getResponse().message(), Toast.LENGTH_SHORT).show();
                               }

                               else if(anError.getErrorCode()==400){
                                   Toast.makeText(MyApp.getContext(), "Podaci nisu validni!", Toast.LENGTH_SHORT).show();
                               }
                               else if(anError.getErrorCode()==452)
                               {
                                   Toast.makeText(MyApp.getContext(), "Greska, pokusajte ponovo", Toast.LENGTH_SHORT).show();
                               }
                               else if(anError.getErrorCode()==500)
                               {
                                   Toast.makeText(MyApp.getContext(), "Greska sa povezivanjem na server", Toast.LENGTH_SHORT).show();
                               }
                               else{
                                   Toast.makeText(MyApp.getContext(), "Uspjesno ste se registrovali!", Toast.LENGTH_SHORT).show();
                                   final Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                                   startActivity(intent);
                                   finish();
                               }
                           }
                       });
            }
        });

        sendToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private boolean ValidateInputs() {
        if (!validation.validateLength20(korIme, korImeInputLayout))
            return false;
        if(!validation.validateEmpty(korIme, korImeInputLayout))
            return false;
        if (!validation.validateEmpty(email, emailInputLayout))
            return false;
        if (!validation.validateLength50(email, emailInputLayout))
            return false;
        if(!validation.validateEmail(email, emailInputLayout))
            return false;
        if (!validation.validateEmpty(ime, imeInputLayout))
            return false;
        if(!validation.validateLength50(ime, imeInputLayout))
            return false;
        if(!validation.validateEmpty(prezime, prezimeInputLayout))
            return false;
        if(!validation.validateLength50(prezime, imeInputLayout))
            return false;
        if (!validation.validateEmpty(lozinka, lozinkaInputLayout))
            return false;
        if (!validation.validateLength50(lozinka, lozinkaInputLayout))
            return false;
        if(!validation.validatePassword(lozinka, lozinkaInputLayout))
            return false;

        return true;
    }
   }
