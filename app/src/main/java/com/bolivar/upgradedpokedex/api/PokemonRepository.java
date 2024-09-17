package com.bolivar.upgradedpokedex.api;

import android.database.Cursor;

import com.bolivar.upgradedpokedex.model.dto.pokemon;
import com.bolivar.upgradedpokedex.sqlite.contract.PokemonContract;
import com.bolivar.upgradedpokedex.sqlite.helper.AppDbHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class PokemonRepository {

    private static final String BASE_URL = "https://a508-202-90-134-36.ngrok-free.app";

    private PokemonApi apiService;

    public PokemonRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(PokemonApi.class);
    }

    public void fetchPokemonList() {
        Call<List<pokemon>> call = apiService.getPokemonList();

        call.enqueue(new Callback<List<pokemon>>() {
            @Override
            public void onResponse(Call<List<pokemon>> call, Response<List<pokemon>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<pokemon> pokemonList = response.body();

                    // Insert Pokémon into the secure database
                    savePokemonsToDatabase(pokemonList);
                }
            }

            @Override
            public void onFailure(Call<List<pokemon>> call, Throwable t) {
                // Handle failure
                t.printStackTrace();
            }
        });
    }

    private void savePokemonsToDatabase(List<pokemon> pokemonList) {
        net.zetetic.database.sqlcipher.SQLiteDatabase db = AppDbHelper.getInstance().getSecureDatabase();

        for (pokemon pokemon : pokemonList) {
            String sqlInsert = "INSERT INTO " + PokemonContract.PokemonEntry.TABLE_NAME + " (name, type) VALUES (?, ?)";
            db.execSQL(sqlInsert, new Object[]{pokemon.getName(), pokemon.getType()});
        }

        db.close();
    }

    public List<pokemon> getPokemonsFromDatabase() {
        List<pokemon> pokemonList = new ArrayList<>();
        net.zetetic.database.sqlcipher.SQLiteDatabase db = AppDbHelper.getInstance().getSecureDatabase();

        String sqlSelect = "SELECT * FROM " + PokemonContract.PokemonEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(sqlSelect, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(PokemonContract.PokemonEntry.COLUMN_NAME));
                String type = cursor.getString(cursor.getColumnIndex(PokemonContract.PokemonEntry.COLUMN_TYPE));
                pokemonList.add(new pokemon(name, type));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return pokemonList;
    }
}
