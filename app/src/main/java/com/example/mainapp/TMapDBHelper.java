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
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertLocation(String mainGroup, String subGroup, String powerNumber, String powerName, double lat, double lon, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAINGROUP, mainGroup);
        values.put(COLUMN_SUBGROUP, subGroup);
        values.put(COLUMN_POWERNUMBER, powerNumber);
        values.put(COLUMN_POWERNAME, powerName);
        values.put(COLUMN_LAT, lat);
        values.put(COLUMN_LON, lon);
        values.put(COLUMN_ADDRESS, address);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Location location = new Location("");
                /*** cursor.getColumnindex() 선언 시 자료형이 달라서 그냥 하드코딩 함*/
                location.setLatitude(cursor.getDouble(5));  // 5번 : lat 컬럼 위치
                location.setLongitude(cursor.getDouble(6)); // 6번 : lon 컬럼 위치
                locationList.add(location);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        return locationList;
    }
}



//public class TMapDBHelper extends SQLiteOpenHelper {
//
//
////    private Context mContext;
//
//
//    // DB 정보
//    private static final String DATABASE_NAME = "광케이블 선로순시.db";
//    private static final int DATABASE_VERSION = 1;
//
//
//    // DB 경로
////    private static String DB_PATH = "/data/data/com.example.mainapp/assets/ICT 설비점검.db";
//
//    // 테이블 정보
//    public static String TABLE_NAME = "navi";
//
//    // 컬럼 정보
//    public static final String COLUMN_ID = "id";
//    public static final String COLUMN_MAINGROUP = "maingroup";
//    public static final String COLUMN_SUBGROUP = "subgroup";
//    public static final String COLUMN_POWERNUMBER = "powernumber";
//    public static final String COLUMN_POWERNAEM = "powername";
//    public static final String COLUMN_LON = "lon";
//    public static final String COLUMN_LAT = "lat";
//    public static final String COLUMN_ADDRESS = "address";
//
//
//    public TMapDBHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//
//        File dbFile = context.getDatabasePath(DATABASE_NAME);
//
//        if (!dbFile.exists()) {
//            // assets 폴더에서 데이터베이스 파일을 복사하여 생성
//            InputStream inputStream = null;
//            OutputStream outputStream = null;
//
//            try {
//                inputStream = context.getAssets().open(DATABASE_NAME);
//                outputStream = new FileOutputStream(dbFile);
//
//                byte[] buffer = new byte[1024];
//                int length;
//                while ((length = inputStream.read(buffer)) > 0) {
//                    outputStream.write(buffer, 0, length);
//                }
//                outputStream.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (inputStream != null) {
//                        inputStream.close();
//                    }
//                    if (outputStream != null) {
//                        outputStream.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    // DB 생성 시 호출
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        // 테이블 생성 쿼리
//        // Check if the table exists before creating it
//        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_NAME + "'";
//        Cursor cursor = db.rawQuery(query, null);
//        boolean tableExists = false;
//        if (cursor != null) {
//            tableExists = cursor.getCount() > 0;
//            cursor.close();
//        }
//
//        if (!tableExists) {
//            // Create the table if it doesn't exist
//            String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
//                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_MAINGROUP + " TEXT, " +
//                    COLUMN_SUBGROUP + " TEXT, " +
//                    COLUMN_POWERNUMBER + " TEXT, " +
//                    COLUMN_POWERNAEM + " TEXT, " +
//                    COLUMN_LON + " TEXT, " +
//                    COLUMN_LAT + " TEXT, " +
//                    COLUMN_ADDRESS +
//                    ")";
//            db.execSQL(createTableQuery);
//        }
//    }
//
//
//    // DB 업그레이드 시 호출
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // 기존 테이블 삭제
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//
//        // 테이블 재생성
//        onCreate(db);
//    }
//
//    /*** DB 조회 메소드 ***/
//
////    public Cursor getData1() {
////        // DB 열기
////        SQLiteDatabase db = getWritableDatabase();
////
////        String query = "SELECT * FROM " + TABLE_NAME +
////                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
////                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
////                ")";
////
////
////        // 조회 결과 반환
////        return db.rawQuery(query, null);
////    }
//}