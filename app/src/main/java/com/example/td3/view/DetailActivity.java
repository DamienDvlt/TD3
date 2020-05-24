package com.example.td3.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.td3.R;
import com.example.td3.Singletons;
import com.example.td3.model.PokeEvolution;

public class DetailActivity extends AppCompatActivity {

    private TextView textDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textDetail = findViewById(R.id.detail_text);
        Intent intent = getIntent();
        String pokeEvolutionJson = intent.getStringExtra("pokeEvolutionKey");
        PokeEvolution pokeEvolution = Singletons.getGson().fromJson(pokeEvolutionJson, PokeEvolution.class);
        showDetail(pokeEvolution);
    }

    private void showDetail(PokeEvolution pokeEvolution) {
        textDetail.setText(pokeEvolution.getUrl());
    }
}