package com.bolivar.upgradedpokedex.sqlite.contract;

import android.provider.BaseColumns;

public class PokemonContract {

    private PokemonContract() {}

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + PokemonEntry.TABLE_NAME + " (" +
                    PokemonEntry._ID + " INTEGER PRIMARY KEY," +
                    PokemonEntry.COLUMN_NAME + " TEXT," +
                    PokemonEntry.COLUMN_TYPE + " TEXT," +
                    PokemonEntry.COLUMN_DESCRIPTION + " TEXT" + // Example additional field
                    ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + PokemonEntry.TABLE_NAME;

    public static class PokemonEntry implements BaseColumns {
        public static final String TABLE_NAME = "pokemon";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DESCRIPTION = "description";
    }
}
