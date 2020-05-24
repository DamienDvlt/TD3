package com.example.td3.controller;

import android.content.SharedPreferences;

import com.example.td3.Constants;
import com.example.td3.Singletons;
import com.example.td3.model.PokeEvolution;
import com.example.td3.model.RestPokeResponseServeur;
import com.example.td3.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {


    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;


    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences) {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart() {

        List<PokeEvolution> pokeEvolutionList = getDataFine();
        if(pokeEvolutionList != null){
            view.showList(pokeEvolutionList);
        } else {
            makeAPIGreatAgain();
        }
    }

    private void makeAPIGreatAgain(){

        Call<RestPokeResponseServeur> call = Singletons.getPokeEvolutionAPI().getPokeResponseServeur();
        call.enqueue(new Callback<RestPokeResponseServeur>() {
            @Override
            public void onResponse(Call<RestPokeResponseServeur> call, Response<RestPokeResponseServeur> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<PokeEvolution> pokeEvolutionList = response.body().getResults();
                    saveList(pokeEvolutionList);
                    view.showList(pokeEvolutionList);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestPokeResponseServeur> call, Throwable t) {
                view.showError();
            }
        });

    }

    private void saveList(List<PokeEvolution> pokeEvolutionList) {
        String jsonString = gson.toJson(pokeEvolutionList);
        sharedPreferences
                .edit()
                .putString(Constants.Key_PokeEvolution_List, jsonString)
                .apply();

    }

    private List<PokeEvolution> getDataFine() {
        String jsonPokeEvolution = sharedPreferences.getString(Constants.Key_PokeEvolution_List, null);

        if(jsonPokeEvolution == null) {
            return null;
        } else {
            Type listType = new TypeToken<List<PokeEvolution>>() {
            }.getType();
            return gson.fromJson(jsonPokeEvolution, listType);
        }
    }

    public void onItemClick (PokeEvolution pokeEvolution) {

    }

}
