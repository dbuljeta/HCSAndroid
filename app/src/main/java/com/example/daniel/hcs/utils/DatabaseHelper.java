package com.example.daniel.hcs.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final String TABLE_EVENT_INTAKE = "table_intake";

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
    private static final String KEY_INTAKE_ID = "intakeId";
    private static final String KEY_TAKEN = "taken";

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

    private static final String CREATE_TABLE_EVENT_INTAKE = "CREATE TABLE " + TABLE_EVENT_INTAKE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_PILL_ID + " INTEGER," +
            KEY_INTAKE_ID + " INTEGER," +
            KEY_TAKEN + " INTEGER)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PILLS);
        db.execSQL(CREATE_TABLE_INTAKES);
        db.execSQL(CREATE_TABLE_EVENT_INTAKE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTAKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT_INTAKE);
        onCreate(db);
    }

    public List<Pill> getAllPills() {
        List<Pill> pillList = new ArrayList<Pill>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PILLS;

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
                        Long.valueOf(cursor.getString(4))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return pillList;
    }

    public Pill getPill(Long pillId) {
        // Select All Query
        Pill pill = null;
        String selectQuery = "SELECT * FROM " + TABLE_PILLS + " WHERE " + KEY_SERVER_ID + " = " + pillId;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                pill = new Pill(
                        Long.valueOf(cursor.getString(0)),
                        Long.valueOf(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        Long.valueOf(cursor.getString(4))
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return pill;
    }

    public void deleteAllPills() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PILLS, null, null);
        db.close();
    }

    public List<Intake> getIntakesFromPill(Pill pill) {
        List<Intake> intakeList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INTAKES +
                " WHERE " + TABLE_INTAKES + "." + KEY_PILL_ID + "=" + pill.getServerId();
        Log.e("Server ID", "serverID " + pill.getServerId());
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.e("F", cursor.getString(0));
                Log.e("F", cursor.getString(1));
                Log.e("F", cursor.getString(2));
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

//    public List<Intake> getIntakes(Pill pill) {
//        List<Intake> intakeList = new ArrayList<Intake>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_INTAKES +
//                " INNER JOIN " + TABLE_PILLS +
//                " ON " + TABLE_PILLS + "." + KEY_ID + "=" + TABLE_INTAKES + "." + KEY_PILL_ID +
//                " WHERE " + TABLE_PILLS + "." + KEY_ID + "=" + pill.getServerId();
//
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                intakeList.add(new Intake(
//                        Long.valueOf(cursor.getString(0)),
//                        Long.valueOf(cursor.getString(1)),
//                        Long.valueOf(cursor.getString(2)),
//                        cursor.getString(3)
//                ));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        sqLiteDatabase.close();
//
//        return intakeList;
//    }

    public Long addPill(Pill pill) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Long id;

        ContentValues values = new ContentValues();
        values.put(KEY_SERVER_ID, pill.getServerId());
        values.put(KEY_NAME, pill.getName());
        values.put(KEY_DESCRIPTION, pill.getDescription());
        values.put(KEY_NUMBER_OF_INTAKES, pill.getNumberOfIntakes());

        Log.e("Pill", "Ubacio sam u bazu");
        Log.e("Pill", String.valueOf(values));

        id = sqLiteDatabase.insert(TABLE_PILLS, KEY_SERVER_ID, values);
        sqLiteDatabase.close();
        return id;
    }

    public void addIntake(Intake intake) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Log.e("INTAK C", String.valueOf(intake.getPillId()));
        values.put(KEY_TIME_OF_INTAKE, intake.getTimeOfIntake());
        values.put(KEY_SERVER_ID, intake.getServerId());
        values.put(KEY_PILL_ID, intake.getPillId());
        values.put(KEY_PILL_ID, intake.getPillId());

        sqLiteDatabase.insert(TABLE_INTAKES, null, values);
        sqLiteDatabase.close();
    }

    public void addIntakeEvent(IntakeEvent event) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_INTAKE_ID, event.getIntakeId());
        values.put(KEY_PILL_ID, event.getPillId());
        values.put(KEY_TAKEN, event.getTaken());

        Log.e("EVENT", "CREATING");

        sqLiteDatabase.insert(TABLE_EVENT_INTAKE, KEY_PILL_ID, values);
        sqLiteDatabase.close();
    }

    public List<IntakeEvent> getIntakeEvent(Long pillId){
        List<IntakeEvent> intakeEvents = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENT_INTAKE +
                " WHERE " + KEY_PILL_ID + "=" + pillId;
//        Log.e("Server ID", "serverID " + pill.getServerId());
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.e("F", cursor.getString(0));
                Log.e("F", cursor.getString(1));
                Log.e("F", cursor.getString(2));
                intakeEvents.add(new IntakeEvent(
                        Long.valueOf(cursor.getString(0)),
                        Long.valueOf(cursor.getString(1)),
                        Long.valueOf(cursor.getString(2)),
                        //Boolean.valueOf(cursor.getString(3))
                        cursor.getInt(3) > 0
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return intakeEvents;
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
