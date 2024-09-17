package com.bolivar.upgradedpokedex.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bolivar.upgradedpokedex.PokemonAdapter;
import com.bolivar.upgradedpokedex.R;
import com.bolivar.upgradedpokedex.sqlite.contract.PokemonContract;
import com.bolivar.upgradedpokedex.sqlite.helper.AppDbHelper;

public class PokemonListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PokemonAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_pokemon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadPokemonData();

        return view;
    }

    private void loadPokemonData() {
        Cursor cursor = AppDbHelper.getInstance().getSecureDatabase().query(
                PokemonContract.PokemonEntry.TABLE_NAME,
                null, null, null, null, null, null
        );

        adapter = new PokemonAdapter(cursor);
        recyclerView.setAdapter(adapter);
    }
}

