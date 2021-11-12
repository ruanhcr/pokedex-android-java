package com.ruru.pokedex150.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ruru.pokedex150.R;
import com.ruru.pokedex150.common.Common;
import com.ruru.pokedex150.fragment.PokemonList;
import com.ruru.pokedex150.listener.IItemClickListener;
import com.ruru.pokedex150.model.Pokemon;

import java.util.List;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>{

    Context context;
    List<Pokemon> pokemonList;

    public PokemonListAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public PokemonListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonListAdapter.MyViewHolder holder, int position) {
        //carregar imagem
        Glide.with(context).load(pokemonList.get(position).getImg()).into(holder.pokemon_image);

        //carregar nome
        holder.pokemon_name.setText(pokemonList.get(position).getName());

        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(Common.KEY_ENABLE_HOME).putExtra("num", pokemonList.get(position).getNum()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView pokemon_image;
        TextView pokemon_name;
        IItemClickListener iItemClickListener;

        public void setiItemClickListener(IItemClickListener iItemClickListener){
            this.iItemClickListener = iItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemon_image = itemView.findViewById(R.id.pokemon_image);
            pokemon_name = itemView.findViewById(R.id.txt_pokemon_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iItemClickListener.onClick(view, getAdapterPosition());
        }
    }
}
