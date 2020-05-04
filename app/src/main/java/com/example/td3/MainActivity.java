package com.example.td3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static final String BASE_URL = "https://pokeapi.co/";

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Appli_mobile_3A", Context.MODE_PRIVATE);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<PokeEvolution> pokeEvolutionList = getDataFine();
        if(pokeEvolutionList != null){
            showList(pokeEvolutionList);
        } else {
            makeAPIGreatAgain();
        }
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

    private void showList(List<PokeEvolution> pokeEvolutionList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new ListAdapter(pokeEvolutionList);
        recyclerView.setAdapter(mAdapter);
    }


    private void makeAPIGreatAgain(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PokeEvolutionAPI PokeEvolutionAPI = retrofit.create(PokeEvolutionAPI.class);

        Call<RestPokeResponseServeur> call = PokeEvolutionAPI.getPokeResponseServeur();
        call.enqueue(new Callback<RestPokeResponseServeur>() {
            @Override
            public void onResponse(Call<RestPokeResponseServeur> call, Response<RestPokeResponseServeur> response) {
                if(response.isSuccessful() && response.body() != null) {
                        List<PokeEvolution> pokeEvolutionList = response.body().getResults();
                        saveList(pokeEvolutionList);
                        showList(pokeEvolutionList);
                    } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestPokeResponseServeur> call, Throwable t) {
                showError();
            }
        });

    }

    private void saveList(List<PokeEvolution> pokeEvolutionList) {
        String jsonString = gson.toJson(pokeEvolutionList);
        sharedPreferences
                .edit()
                .putString(Constants.Key_PokeEvolution_List, jsonString)
                .apply();

        Toast.makeText(getApplicationContext(), "List OK", Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API RIP ERROR", Toast.LENGTH_SHORT).show();
    }
}
