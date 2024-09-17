package com.bolivar.upgradedpokedex;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bolivar.upgradedpokedex.sqlite.contract.PokemonContract;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private Cursor cursor;

    public PokemonAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(PokemonContract.PokemonEntry.COLUMN_NAME));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(PokemonContract.PokemonEntry.COLUMN_TYPE));

            holder.nameTextView.setText(name);
            holder.typeTextView.setText(type);
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView typeTextView;

        PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.pokemon_name);
            typeTextView = itemView.findViewById(R.id.pokemon_type);
        }
    }
}
