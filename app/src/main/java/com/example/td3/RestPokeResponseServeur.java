package com.example.td3;

import java.util.List;

public class RestPokeResponseServeur {
    private Integer count;
    private String next;
    private List<PokeEvolution> results;

    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public List<PokeEvolution> getResults() {
        return results;
    }
}
