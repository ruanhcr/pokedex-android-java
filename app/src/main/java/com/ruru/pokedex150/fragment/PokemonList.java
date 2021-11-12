package com.ruru.pokedex150.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.ruru.pokedex150.R;
import com.ruru.pokedex150.adapter.PokemonListAdapter;
import com.ruru.pokedex150.common.Common;
import com.ruru.pokedex150.common.ItemOffsetDecoration;
import com.ruru.pokedex150.model.Pokedex;
import com.ruru.pokedex150.model.Pokemon;
import com.ruru.pokedex150.retrofit.IPokemonDex;
import com.ruru.pokedex150.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PokemonList extends Fragment {

    IPokemonDex iPokemonDex;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView pokemon_list_recyclerview;

    PokemonListAdapter adapter, search_adapter;
    List<String> last_suggest = new ArrayList<>();

    MaterialSearchBar searchBar;

    static PokemonList instance;

    public static PokemonList getInstance() {
        if(instance == null)
            instance = new PokemonList();
        return instance;
    }

    public PokemonList() {
        Retrofit retrofit = RetrofitClient.getInstace();
        iPokemonDex = retrofit.create(IPokemonDex.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        pokemon_list_recyclerview = view.findViewById(R.id.pokemon_list_recyclerview);
        pokemon_list_recyclerview.setHasFixedSize(true);
        pokemon_list_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.spacing);
        pokemon_list_recyclerview.addItemDecoration(itemOffsetDecoration);

        //inicializar searchbar
        searchBar = (MaterialSearchBar) view.findViewById(R.id.search_bar);
        searchBar.setHint("Enter Pokemon name");
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<>();
                for (String search: last_suggest){
                    if(search.toLowerCase().contains(searchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    pokemon_list_recyclerview.setAdapter(adapter); // voltar pro adapter padrao
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


        fetchData();

        return view;
    }

    private void startSearch(CharSequence text) {
        if(Common.commonPokemonList.size() > 0){
            List<Pokemon> result = new ArrayList<>();
            for(Pokemon pokemon: Common.commonPokemonList)
                if(pokemon.getName().toLowerCase().contains(text.toString().toLowerCase()))
                    result.add(pokemon);
                search_adapter = new PokemonListAdapter(getActivity(), result);
                pokemon_list_recyclerview.setAdapter(search_adapter);
        }
    }

    private void fetchData() {
        compositeDisposable.add(iPokemonDex.getListPokemon()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Pokedex>() {
            @Override
            public void accept(Pokedex pokedex) throws Exception {
                Common.commonPokemonList = pokedex.getPokemon();
                adapter = new PokemonListAdapter(getActivity(), Common.commonPokemonList);

                pokemon_list_recyclerview.setAdapter(adapter);
                last_suggest.clear();
                for(Pokemon pokemon: Common.commonPokemonList)
                    last_suggest.add(pokemon.getName());
                searchBar.setVisibility(View.VISIBLE); //mostra a searchbar depois de carregar todos pokemons
                searchBar.setLastSuggestions(last_suggest);
            }
        }));

    }
}
