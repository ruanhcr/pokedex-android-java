package com.ruru.pokedex150.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruru.pokedex150.R;
import com.ruru.pokedex150.adapter.PokemonEvolutionAdapter;
import com.ruru.pokedex150.adapter.PokemonTypeAdapter;
import com.ruru.pokedex150.common.Common;
import com.ruru.pokedex150.model.Pokemon;

public class PokemonDetail extends Fragment {

    ImageView pokemon_img;
    TextView pokemon_name, pokemon_height, pokemon_weight;
    RecyclerView recycler_type, recycler_weakness, recyler_next_evolution, recycler_prev_evolution;

    static PokemonDetail instace;

    public static PokemonDetail getInstance() {
        if(instace == null)
            instace = new PokemonDetail();
        return instace;
    }

    public PokemonDetail() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);

        Pokemon pokemon = Common.findPokemonByNum(getArguments().getString("num"));
            pokemon_img = itemView.findViewById(R.id.pokemon_image);
            pokemon_name = itemView.findViewById(R.id.name);
            pokemon_height = itemView.findViewById(R.id.height);
            pokemon_weight = itemView.findViewById(R.id.weight);

            recycler_type = itemView.findViewById(R.id.recyler_type);
            recycler_type.setHasFixedSize(true);
            recycler_type.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            recycler_weakness = itemView.findViewById(R.id.recyler_weakness);
            recycler_weakness.setHasFixedSize(true);
            recycler_weakness.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            recycler_type = itemView.findViewById(R.id.recyler_type);
            recycler_type.setHasFixedSize(true);
            recycler_type.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            recycler_prev_evolution = itemView.findViewById(R.id.recyler_prev_evolution);
            recycler_prev_evolution.setHasFixedSize(true);
            recycler_prev_evolution.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            recyler_next_evolution = itemView.findViewById(R.id.recyler_next_evolution);
            recyler_next_evolution.setHasFixedSize(true);
            recyler_next_evolution.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            setDetailPokemon(pokemon);

        return itemView;
    }

    private void setDetailPokemon(Pokemon pokemon) {
        //carregar a imagem
        Glide.with(getActivity()).load(pokemon.getImg()).into(pokemon_img);
        pokemon_name.setText(pokemon.getName());
        pokemon_weight.setText("Weight: " + pokemon.getWeight());
        pokemon_height.setText("Height: " + pokemon.getHeight());

        //set Type
        PokemonTypeAdapter typeAdapter = new PokemonTypeAdapter(getActivity(), pokemon.getType());
        recycler_type.setAdapter(typeAdapter);

        //set Weakness
        PokemonTypeAdapter weaknessAdapter = new PokemonTypeAdapter(getActivity(), pokemon.getWeaknesses());
        recycler_weakness.setAdapter(weaknessAdapter);

        //set Evolutions
        PokemonEvolutionAdapter prevEvolutionAdapter = new PokemonEvolutionAdapter(getActivity(), pokemon.getPrev_evolution());
        recycler_prev_evolution.setAdapter(prevEvolutionAdapter);

        PokemonEvolutionAdapter nextEvolutionAdapter = new PokemonEvolutionAdapter(getActivity(), pokemon.getNext_evolution());
        recyler_next_evolution.setAdapter(nextEvolutionAdapter);
    }
}