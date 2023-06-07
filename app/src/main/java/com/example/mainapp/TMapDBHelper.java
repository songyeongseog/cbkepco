package com.example.mainapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TMapDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "광케이블 선로순시.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "navi";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MAINGROUP = "mainGroup";
    private static final String COLUMN_SUBGROUP = "subGroup";
    private static final String COLUMN_POWERNUMBER = "powerNumber";
    private static final String COLUMN_POWERNAME = "powerName";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LON = "lon";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_RESULT1 = "result1";
    private static final String COLUMN_RESULT2 = "result2";
    private static final String COLUMN_RESULT3 = "result3";
    private static final String COLUMN_RESULT4 = "result4";
    private static final String COLUMN_RESULT5 = "result5";
    private static final String COLUMN_RESULT6 = "result6";
    private static final String COLUMN_RESULT7 = "result7";
    private static final String COLUMN_RESULTTEXT = "resultText";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MAINGROUP + " TEXT, "
            + COLUMN_SUBGROUP + " TEXT, "
            + COLUMN_POWERNUMBER + " TEXT, "
            + COLUMN_POWERNAME + " TEXT, "
            + COLUMN_LAT + " REAL, "
            + COLUMN_LON + " REAL, "
            + COLUMN_ADDRESS + " TEXT)";

    public TMapDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        File dbFile = context.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            // assets 폴더에서 데이터베이스 파일을 복사하여 생성
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                inputStream = context.getAssets().open(DATABASE_NAME);
                outputStream = new FileOutputStream(dbFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 테이블 생성 쿼리
        // Check if the table exists before creating it
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_NAME + "'";
        Cursor cursor = db.rawQuery(query, null);
        boolean tableExists = false;
        if (cursor != null) {
            tableExists = cursor.getCount() > 0;
            cursor.close();
        }

        if (!tableExists) {
            // Create the table if it doesn't exist
            String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MAINGROUP + " TEXT, " +
                    COLUMN_SUBGROUP + " TEXT, " +
                    COLUMN_POWERNUMBER + " TEXT, " +
                    COLUMN_POWERNAME + " TEXT, " +
                    COLUMN_LON + " REAL, " +
                    COLUMN_LAT + " REAL, " +
                    COLUMN_ADDRESS + " TEXT " +
                    COLUMN_RESULT1 + " TEXT " +
                    COLUMN_RESULT2 + " TEXT " +
                    COLUMN_RESULT3 + " TEXT " +
                    COLUMN_RESULT4 + " TEXT " +
                    COLUMN_RESULT5 + " TEXT " +
                    COLUMN_RESULT6 + " TEXT " +
                    COLUMN_RESULT7 + " TEXT " +
                    COLUMN_RESULTTEXT + " TEXT " +
                    ")";
            db.execSQL(createTableQuery);
        }
//        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertLocation(String mainGroup, String subGroup, String powerNumber, String powerName, double lat, double lon, String address,
                               String result1, String result2, String result3, String result4, String result5, String result6, String result7,
                               String resultText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAINGROUP, mainGroup);
        values.put(COLUMN_SUBGROUP, subGroup);
        values.put(COLUMN_POWERNUMBER, powerNumber);
        values.put(COLUMN_POWERNAME, powerName);
        values.put(COLUMN_LAT, lat);
        values.put(COLUMN_LON, lon);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_RESULT1, address);
        values.put(COLUMN_RESULT2, address);
        values.put(COLUMN_RESULT3, address);
        values.put(COLUMN_RESULT4, address);
        values.put(COLUMN_RESULT5, address);
        values.put(COLUMN_RESULT6, address);
        values.put(COLUMN_RESULT7, address);
        values.put(COLUMN_RESULTTEXT, address);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_MAINGROUP + " LIKE " + "'%KepCIT%'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("MyApp", "getAllLocations() - 데이터 개수: " + cursor.getCount());


        if (cursor.moveToFirst()) {
            do {
                Location location = new Location("");
                /*** cursor.getColumnindex() 선언 시 자료형이 달라서 그냥 하드코딩 함*/
//                location.setLatitude(cursor.getDouble(5));  // 5번 : lat 컬럼 위치
//                location.setLongitude(cursor.getDouble(6)); // 6번 : lon 컬럼 위치
                location.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LAT)));  // 5번 : lat 컬럼 위치
                location.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LON))); // 6번 : lon 컬럼 위치
//                String mainGroup = cursor.getString(cursor.getColumnIndex(COLUMN_MAINGROUP));
//                String subGroup = cursor.getString(cursor.getColumnIndex(COLUMN_SUBGROUP));
//                String powerNumber = cursor.getString(cursor.getColumnIndex(COLUMN_POWERNUMBER));
//                String powerName = cursor.getString(cursor.getColumnIndex(COLUMN_POWERNAME));

                locationList.add(location);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return locationList;
    }

    public List<Location> getTestLocations() {
        List<Location> locationList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + "(" + COLUMN_MAINGROUP + "=" + "'KepCIT'" +
                " AND " + COLUMN_SUBGROUP + "=" + "'수안보#1'" + ")";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("MyApp", "getAllLocations() - 데이터 개수: " + cursor.getCount());


        if (cursor.moveToFirst()) {
            do {
                Location location = new Location("");
                /*** cursor.getColumnindex() 선언 시 자료형이 달라서 그냥 하드코딩 함*/
                location.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LAT)));  // 5번 : lat 컬럼 위치
                location.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LON))); // 6번 : lon 컬럼 위치


                locationList.add(location);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return locationList;
    }

    public String[] getSpecificColumnValue(double loc, double lat) {
        SQLiteDatabase db = getReadableDatabase();

        // Define the query to retrieve the specific column value based on loc and lat
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_LON + " = ? "+ " AND " + COLUMN_LAT + " = ? ";
        String[] selectionArgs = {String.valueOf(loc), String.valueOf(lat)};

        Cursor cursor = db.rawQuery(query, selectionArgs);


        String[] columnValues = new String[4];  // Assuming you want to retrieve 3 columns



        if (cursor.moveToFirst()) {
            // Retrieve the specific column value from the cursor
            columnValues[0] = cursor.getString(cursor.getColumnIndex("mainGroup"));
            columnValues[1] = cursor.getString(cursor.getColumnIndex("subGroup"));
            columnValues[2] = cursor.getString(cursor.getColumnIndex("powerNumber"));
            columnValues[3] = cursor.getString(cursor.getColumnIndex("powerName"));

        }

        cursor.close();
        db.close();

        return columnValues;
    }

}