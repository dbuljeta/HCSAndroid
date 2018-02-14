package com.example.daniel.hcs.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 2/14/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final Integer DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "project_management";

    /**
     * Tables
     */
    private static final String TABLE_PILLS = "pills";
    private static final String TABLE_INTAKES = "intakes";

    /**
     * Column names
     */
    //users
    private static final String KEY_ID = "id";
    private static final String KEY_SERVER_ID = "serverId";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_NUMBER_OF_INTAKES = "numberOfIntakes";
    private static final String KEY_TIME_OF_INTAKE = "timeOfIntake";
    private static final String KEY_PILL_ID = "pillId";

    private static DatabaseHelper databaseHelper;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return databaseHelper;
    }

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Table creation queries
     */
    private static final String CREATE_TABLE_PILLS = "CREATE TABLE " + TABLE_PILLS + " (" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_SERVER_ID + " INTEGER," +
            KEY_NAME + " TEXT," +
            KEY_DESCRIPTION + " TEXT," +
            KEY_NUMBER_OF_INTAKES + " INTEGER)";

    private static final String CREATE_TABLE_INTAKES = "CREATE TABLE " + TABLE_INTAKES + " (" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_SERVER_ID + " INTEGER," +
            KEY_PILL_ID + " INTEGER," +
            KEY_TIME_OF_INTAKE + " TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PILLS);
        db.execSQL(CREATE_TABLE_INTAKES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTAKES);
        onCreate(db);
    }

    public List<Pill> getAllPills() {
        List<Pill> pillList = new ArrayList<Pill>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PILLS;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                pillList.add(new Pill(
                        Long.valueOf(cursor.getString(0)),
                        Long.valueOf(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        Long.valueOf(cursor.getString(1))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return pillList;
    }

    public List<Intake> getIntakesFromPill(Pill pill) {
        List<Intake> intakeList = new ArrayList<Intake>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INTAKES +
                " INNER JOIN " + TABLE_PILLS +
                " ON " + TABLE_PILLS + "." + KEY_ID + "=" + TABLE_INTAKES + "." + KEY_PILL_ID +
                " WHERE " + TABLE_PILLS + "." + KEY_ID + "=" + pill.getServerId();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                intakeList.add(new Intake(
                        Long.valueOf(cursor.getString(0)),
                        Long.valueOf(cursor.getString(1)),
                        Long.valueOf(cursor.getString(2)),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return intakeList;
    }

    public void addPill(Pill pill) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SERVER_ID, pill.getServerId());
        values.put(KEY_NAME, pill.getName());
        values.put(KEY_DESCRIPTION, pill.getNumberOfIntakes());
        values.put(KEY_NUMBER_OF_INTAKES, pill.getNumberOfIntakes());

        sqLiteDatabase.insert(TABLE_PILLS, null, values);
        sqLiteDatabase.close();
    }

    public void addIntake(Intake intake) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SERVER_ID, intake.getServerId());
        values.put(KEY_TIME_OF_INTAKE, intake.getTimeOfIntake());
        values.put(KEY_PILL_ID, intake.getPillId());

        sqLiteDatabase.insert(TABLE_INTAKES, null, values);
        sqLiteDatabase.close();
    }

    public void deletePill(Pill pill) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_INTAKES, KEY_PILL_ID + " = ?",
                new String[]{String.valueOf(pill.getServerId())});
        sqLiteDatabase.delete(TABLE_PILLS, KEY_ID + " = ?",
                new String[]{String.valueOf(pill.getId())});
        sqLiteDatabase.close();
    }
}
