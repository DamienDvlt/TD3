package com.example.td3;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.td3.data.PokeEvolutionAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static PokeEvolutionAPI pokeEvolutionAPIInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson() {
        if(gsonInstance == null){
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static PokeEvolutionAPI getPokeEvolutionAPI() {
        if(pokeEvolutionAPIInstance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            pokeEvolutionAPIInstance = retrofit.create(PokeEvolutionAPI.class);
        }
        return pokeEvolutionAPIInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        if(sharedPreferencesInstance == null){
            sharedPreferencesInstance = context.getSharedPreferences("Appli_mobile_3A", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
