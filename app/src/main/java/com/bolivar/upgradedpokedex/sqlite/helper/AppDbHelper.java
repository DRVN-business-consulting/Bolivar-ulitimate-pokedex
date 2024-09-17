package com.bolivar.upgradedpokedex.sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.bolivar.upgradedpokedex.sqlite.contract.PokemonContract;

import java.io.File;

public class AppDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "app.db";

    private static net.zetetic.database.sqlcipher.SQLiteDatabase secureDatabase;

    private static AppDbHelper instance;
    public static void initialize(@NonNull Context context) {
        instance = new AppDbHelper(context);
    }

    public static AppDbHelper getInstance() {
        return instance;
    }

    private AppDbHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        String password = "password";
        File databaseFile = context.getDatabasePath("secure.db");
        secureDatabase = net.zetetic.database.sqlcipher.SQLiteDatabase.openOrCreateDatabase(databaseFile, password, null, null, null);
        secureDatabase.execSQL(PokemonContract.SQL_CREATE_TABLE);
    }

    public net.zetetic.database.sqlcipher.SQLiteDatabase getSecureDatabase() {
        return secureDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PokemonContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PokemonContract.SQL_DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // Fetch Pokémon data
    public Cursor getAllPokemons() {
        return secureDatabase.query(
                PokemonContract.PokemonEntry.TABLE_NAME,
                null, // Get all columns
                null,
                null,
                null,
                null,
                null
        );
    }
}
