package com.example.td3.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.td3.Singletons;
import com.example.td3.controller.MainController;
import com.example.td3.R;
import com.example.td3.model.PokeEvolution;

import java.util.List;

public class MainActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MainController controller;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController(
                this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        controller.onStart();

    }

    public void showList(List<PokeEvolution> pokeEvolutionList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new ListAdapter(pokeEvolutionList, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PokeEvolution item) {
                controller.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void showError() {
        Toast.makeText(getApplicationContext(), "API RIP ERROR", Toast.LENGTH_SHORT).show();
    }

    public void navigateToDetails(PokeEvolution pokeEvolution) {
        Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
        myIntent.putExtra("pokeEvolutionKey", Singletons.getGson().toJson(pokeEvolution));
        MainActivity.this.startActivity(myIntent);
    }
}


