package com.example.td3.data;

import com.example.td3.model.RestPokeResponseServeur;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeEvolutionAPI {

    @GET("/api/v2/evolution-chain")
    Call<RestPokeResponseServeur> getPokeResponseServeur();
}
