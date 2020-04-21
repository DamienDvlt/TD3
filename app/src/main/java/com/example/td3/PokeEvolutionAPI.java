package com.example.td3;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeEvolutionAPI {

    @GET("/api/v2/evolution-chain")
    Call<RestPokeResponseServeur> getPokeResponseServeur();
}
