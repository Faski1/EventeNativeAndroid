package com.example.evente.api;

import com.example.evente.model.Eventi;
import com.example.evente.model.Korisnici;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("api/Korisnici/ProvjeraLogin/{username}/{password}")
    Call<Korisnici> provjeraLogin(@Path("username") String korisnickoIme, @Path("password") String lozinka);

    @GET("api/Eventi/")
    Call<List<Eventi>> getEvente();
}
