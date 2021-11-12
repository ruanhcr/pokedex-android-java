package com.ruru.pokedex150.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;
import com.ruru.pokedex150.R;
import com.ruru.pokedex150.common.Common;
import com.ruru.pokedex150.listener.IItemClickListener;
import com.ruru.pokedex150.model.Evolution;

import java.util.ArrayList;
import java.util.List;

public class PokemonEvolutionAdapter extends RecyclerView.Adapter<PokemonEvolutionAdapter.MyViewHolder> {

    Context context;
    List<Evolution> evolutions;

    public PokemonEvolutionAdapter(Context context, List<Evolution> evolutions) {
        this.context = context;
        if(evolutions != null){
            this.evolutions = evolutions;
        }else{
            this.evolutions = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public PokemonEvolutionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chip_item, parent, false);

        return new PokemonEvolutionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonEvolutionAdapter.MyViewHolder holder, int position) {
        holder.chip.setChipText(evolutions.get(position).getName());
        holder.chip.changeBackgroundColor(Common.getColorByType(
                Common.findPokemonByNum(
                        evolutions.get(position).getNum()
                ).getType()
                .get(0)));
    }

    @Override
    public int getItemCount() {
        return evolutions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = (Chip) itemView.findViewById(R.id.chip);
            chip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    LocalBroadcastManager.getInstance(context)
                            .sendBroadcast(new Intent(Common.KEY_NUM_EVOLUTION).putExtra("num",
                                    evolutions.get(getAdapterPosition()).getNum()));
                }
            });
        }
    }
}
