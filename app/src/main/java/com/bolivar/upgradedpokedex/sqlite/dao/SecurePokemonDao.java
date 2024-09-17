package com.bolivar.upgradedpokedex.sqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.zetetic.database.sqlcipher.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.bolivar.upgradedpokedex.entity.dto.PokemonDTO;
import com.bolivar.upgradedpokedex.sqlite.contract.PokemonContract;
import com.bolivar.upgradedpokedex.sqlite.helper.AppDbHelper;

public class SecurePokemonDao {

    private SecurePokemonDao() {}

    @Nullable
    public static PokemonDTO getPokemon(int id) {
        // Get Pokémon
        SQLiteDatabase db = AppDbHelper.getInstance().getSecureDatabase();

        String[] projection = {
                BaseColumns._ID,
                PokemonContract.PokemonEntry.COLUMN_NAME,
                PokemonContract.PokemonEntry.COLUMN_TYPE,
                PokemonContract.PokemonEntry.COLUMN_DESCRIPTION
        };

        String selection = PokemonContract.PokemonEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = db.query(
                PokemonContract.PokemonEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        PokemonDTO pokemonDTO = null;
        if (cursor.moveToFirst()) {
            pokemonDTO = PokemonDTO.fromCursor(cursor);
        }

        cursor.close();

        return pokemonDTO;
    }

    @NonNull
    public static List<PokemonDTO> getPokemons() {
        // Get Pokémon list
        SQLiteDatabase db = AppDbHelper.getInstance().getSecureDatabase();

        String[] projection = {
                BaseColumns._ID,
                PokemonContract.PokemonEntry.COLUMN_NAME,
                PokemonContract.PokemonEntry.COLUMN_TYPE,
                PokemonContract.PokemonEntry.COLUMN_DESCRIPTION
        };

        Cursor cursor = db.query(
                PokemonContract.PokemonEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<PokemonDTO> pokemonDTOs = new ArrayList<>();
        while (cursor.moveToNext()) {
            PokemonDTO pokemonDTO = PokemonDTO.fromCursor(cursor);
            pokemonDTOs.add(pokemonDTO);
        }

        cursor.close();

        return pokemonDTOs;
    }

    public static boolean insertPokemon(String name,
                                        String type,
                                        String description) {
        // Insert Pokémon
        SQLiteDatabase db = AppDbHelper.getInstance().getSecureDatabase();

        ContentValues values = new ContentValues();
        values.put(PokemonContract.PokemonEntry.COLUMN_NAME, name);
        values.put(PokemonContract.PokemonEntry.COLUMN_TYPE, type);
        values.put(PokemonContract.PokemonEntry.COLUMN_DESCRIPTION, description);

        long id = db.insert(PokemonContract.PokemonEntry.TABLE_NAME, null, values);

        return id != -1;
    }

    public static boolean updatePokemon(int id,
                                        String name,
                                        String type,
                                        String description) {
        // Update Pokémon
        SQLiteDatabase db = AppDbHelper.getInstance().getSecureDatabase();

        ContentValues values = new ContentValues();
        values.put(PokemonContract.PokemonEntry.COLUMN_NAME, name);
        values.put(PokemonContract.PokemonEntry.COLUMN_TYPE, type);
        values.put(PokemonContract.PokemonEntry.COLUMN_DESCRIPTION, description);

        String selection = PokemonContract.PokemonEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(
                PokemonContract.PokemonEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        return count > 0;
    }

    public static boolean deletePokemon(int id) {
        // Delete Pokémon
        SQLiteDatabase db = AppDbHelper.getInstance().getSecureDatabase();

        String selection = PokemonContract.PokemonEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.delete(
                PokemonContract.PokemonEntry.TABLE_NAME,
                selection,
                selectionArgs
        );

        return count > 0;
    }

    public static int getFirstPokemonId() {
        // Get first Pokémon id
        SQLiteDatabase db = AppDbHelper.getInstance().getSecureDatabase();

        String[] projection = {
                BaseColumns._ID
        };

        String sortingOrder = PokemonContract.PokemonEntry._ID + " ASC LIMIT 1";

        Cursor cursor = db.query(
                PokemonContract.PokemonEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortingOrder
        );

        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(PokemonContract.PokemonEntry._ID));
        }

        cursor.close();

        return id;
    }
}
