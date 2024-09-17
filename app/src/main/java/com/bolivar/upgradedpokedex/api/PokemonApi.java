package com.bolivar.upgradedpokedex.api;

import com.bolivar.upgradedpokedex.model.dto.pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface PokemonApi {
    @GET("pokemon")
    Call<List<pokemon>> getPokemonList();
}
