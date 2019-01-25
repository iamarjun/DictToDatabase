package com.example.dicttodatabase.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AHMED on 22-07-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "wordDic";

    private static final String TABLE_NAME = "words";

    private static final String WORD_NAME = "word";
    private static final String WORD_SPEECH = "speech";
    private static final String WORD_MEAN = "meaning";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + WORD_NAME + " TEXT," + WORD_SPEECH + " TEXT," + WORD_MEAN
                + " TEXT,unique (" + WORD_NAME + "," + WORD_SPEECH + "," + WORD_MEAN + "))";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void addWords(String[] word, String[] speech, String[] mean) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i < word.length; i++) {
                values.put(WORD_NAME, word[i]);
                values.put(WORD_SPEECH, speech[i]);
                values.put(WORD_MEAN, mean[i]);
                database.insert(TABLE_NAME, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

    }

    public void addWord(String word, String speech, String mean) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WORD_NAME, word);
        values.put(WORD_SPEECH, speech);
        values.put(WORD_MEAN, mean);
        database.insert(TABLE_NAME, null, values);
        database.close();

    }

    public List<String> getdData() {
        List<String> data = new ArrayList<String>();

        SQLiteDatabase database = this.getWritableDatabase();
        try {
            String[] projection = {
                    WORD_NAME,
                    WORD_SPEECH,
                    WORD_MEAN
            };
            Cursor cursor = database.query(TABLE_NAME, projection, null, null, null, null, null);

            while (cursor.moveToNext()) {
                String word = cursor.getString(cursor.getColumnIndexOrThrow(WORD_NAME));
                String speech = cursor.getString(cursor.getColumnIndexOrThrow(WORD_SPEECH));
                String mean = cursor.getString(cursor.getColumnIndexOrThrow(WORD_MEAN));
                data.add(word + "  (" + speech + ")\n" + mean);
            }
        } catch (Exception e) {
        }
        return data;
    }


    public String getMeaning(String word) {
        String mean = "";
        SQLiteDatabase database = this.getReadableDatabase();

        try {

            String[] projection = {
                    WORD_MEAN
            };

            String selection = WORD_NAME + " = ?";
            String[] selectionArgs = {word};


            Cursor cursor = database.query(
                    TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            cursor.moveToNext();
            mean = cursor.getString(cursor.getColumnIndexOrThrow(WORD_MEAN));
            cursor.close();
        } catch (Exception e) {
        }

        return mean;
    }

    public void removeData() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}
