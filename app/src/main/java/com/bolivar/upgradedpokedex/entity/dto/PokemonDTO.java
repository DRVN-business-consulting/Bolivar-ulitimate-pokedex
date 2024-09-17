package com.bolivar.upgradedpokedex.entity.dto;

import android.database.Cursor;
import com.bolivar.upgradedpokedex.sqlite.contract.PokemonContract;

public class PokemonDTO {

    private int id;
    private String name;
    private String type;
    private String description; // Example additional field, adjust based on your schema

    public PokemonDTO(int id, String name, String type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description; // Adjust according to your actual fields
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static PokemonDTO fromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(PokemonContract.PokemonEntry._ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(PokemonContract.PokemonEntry.COLUMN_NAME));
        String type = cursor.getString(cursor.getColumnIndexOrThrow(PokemonContract.PokemonEntry.COLUMN_TYPE));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(PokemonContract.PokemonEntry.COLUMN_DESCRIPTION));

        return new PokemonDTO(id, name, type, description);
    }
}
