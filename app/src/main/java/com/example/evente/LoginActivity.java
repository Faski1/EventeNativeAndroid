package com.example.evente;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evente.helper.Config;
import com.example.evente.helper.MyRunnable;
import com.example.evente.helper.Sesija;
import com.example.evente.helper.Validation;
import com.example.evente.helper.url_connection.HttpManager;
import com.example.evente.helper.url_connection.MojApiRezultat;
import com.example.evente.model.Korisnici;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUsername;
    private EditText txtPassword;
    private TextView sendToRegistracija;
    private Validation validation = new Validation(this);
    private TextInputLayout loginKorImeLayout, loginPwLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        CardView btnLogin = findViewById(R.id.loginCardView);
        txtUsername = findViewById(R.id.usernameEditText);
        txtPassword = findViewById(R.id.passwordEditText);
        sendToRegistracija = findViewById(R.id.textView2);
        loginKorImeLayout = findViewById(R.id.loginKorImeInputLayout);
        loginPwLayout = findViewById(R.id.loginPwInputLayout);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                if(ValidateInputs())
                    do_Btn_Login_Click();
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Molimo unesite username i lozinku!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        sendToRegistracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MyApp.getContext(), RegistracijaActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private void do_Btn_Login_Click() {

        Provjera(this,txtUsername.getText().toString(), txtPassword.getText().toString(), new MyRunnable<Korisnici>() {
            @Override
            public void run(Korisnici result) {
                Sesija.setLogiraniKorisnik(result);

                if (result == null)
                    Toast.makeText(LoginActivity.this, "Pogresna lozinka!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(LoginActivity.this, "Dobrodosli " + result.KorisnickoIme, Toast.LENGTH_SHORT).show();
                    final Intent intent = new Intent(MyApp.getContext(), Main2Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean ValidateInputs() {
        if (!validation.validateLength20(txtUsername, loginKorImeLayout))
            return false;
        if(!validation.validateEmpty(txtUsername, loginKorImeLayout))
            return false;
        if (!validation.validateEmpty(txtPassword, loginPwLayout))
            return false;
        if (!validation.validateLength50(txtPassword, loginPwLayout))
            return false;


        return true;
    }
        public static void Provjera(final Context context, final String username, final String password, final MyRunnable<Korisnici> onSuccess) {

            new AsyncTask<Void, Void, MojApiRezultat<Korisnici>>()
            {
                ProgressDialog dialog;
                @Override
                protected void onPreExecute() {
                    dialog = ProgressDialog.show(context, "Pristup podacima", "U toku");
                    dialog.show();
                }

                @Override
                protected MojApiRezultat<Korisnici> doInBackground(Void... voids) {
                    return  HttpManager.get(Config.url + "api/Korisnici/ProvjeraLogin/"+username+"/"+password, Korisnici.class );
                }

                @Override
                protected void onPostExecute(MojApiRezultat<Korisnici> rezultat) {
                    if(rezultat.isError)
                        Toast.makeText(MyApp.getContext(), "Greska u komunikaciji sa serverom: "+rezultat.errorMessage, Toast.LENGTH_SHORT).show();
                    else {
                        onSuccess.run(rezultat.value);
                    }
                    dialog.dismiss();
                }
            }.execute();
        }

    }
