package com.ruru.pokedex150.retrofit;

import com.ruru.pokedex150.model.Pokedex;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IPokemonDex {
    @GET("pokedex.json")
    Observable<Pokedex> getListPokemon();
}
