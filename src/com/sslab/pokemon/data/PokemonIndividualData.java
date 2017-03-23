package com.sslab.pokemon.data;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jerry on 2017/3/21.
 */
public class PokemonIndividualData {
    private int id;
    private String nickName;
    private PokemonValueData speciesValue;

    public PokemonIndividualData(int id, String speciesName,PokemonValueData valueData)
    {
        this.id = id;
        this.nickName = speciesName;
        this.speciesValue = valueData;
    }

    public int getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public PokemonValueData getSpeciesValue() {
        return speciesValue;
    }
}
