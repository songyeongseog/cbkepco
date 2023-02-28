package com.example.mainapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {


    // DB 정보
    private static final String DATABASE_NAME = "ICT 설비점검.db";
    private static final int DATABASE_VERSION = 1;

//    private String DB_PATH;
//    private final Context context;


    // 테이블 정보
    public static String TABLE_NAME = "checklist";      // 라디오 버튼 클릭
    private static final String COLUMN_NAME_MAINAREA = "mainarea";
    private static final String COLUMN_NAME_SUBAREA = "subarea";
    private static final String COLUMN_NAME_DETAILAREA = "detailarea";
    private static final String COLUMN_NAME_LIST = "list";
    private static final String COLUMN_NAME_RESULT = "result";
    private static final String COLUMN_NAME_EDITTEXT = "edittext";
    private static final String COLUMN_NAME_IMAGE = "image";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MAINAREA = "mainarea";
    public static final String COLUMN_SUBAREA = "subarea";
    public static final String COLUMN_DETAILAREA = "detailarea";
    public static final String COLUMN_LIST = "list";
    public static final String COLUMN_RESULT = "result";
    public static final String COLUMN_EDITTEXT = "edittext";
    public static final String COLUMN_IMAGE = "image";

    // 생성자

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = getWritableDatabase();
//
        File dbFile = new File(db.getPath());
        db.close();
//        this.context = context;
//        this.DB_PATH = context.getFilesDir().getPath();

        if (!dbFile.exists()) {
            // assets 폴더에서 데이터베이스 파일을 복사하여 생성
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                inputStream = context.getAssets().open(DATABASE_NAME);
                outputStream = new FileOutputStream(context.getDatabasePath(DATABASE_NAME));

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


    // DB 생성 시 호출
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
                    COLUMN_MAINAREA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_SUBAREA + " TEXT, " +
                    COLUMN_DETAILAREA + " TEXT, " +
                    COLUMN_LIST + " TEXT, " +
                    COLUMN_RESULT + " TEXT, " +
                    COLUMN_EDITTEXT + " TEXT, " +
                    COLUMN_IMAGE + " TEXT" +
                    ")";
            db.execSQL(createTableQuery);
        }
    }


    // DB 업그레이드 시 호출
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 기존 테이블 삭제
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // 테이블 재생성
        onCreate(db);
    }

    // DB 내 데이터 조회 메소드
    // getData 함수를 여러개 만들어야 할 것 같음 (쿼리문을 각각 설정)


    // getData1() : 해빙기 프래그먼트 내 보안감시 설비 조회 함수
    public Cursor getData1() {
        // DB 열기
        SQLiteDatabase db = getReadableDatabase();

        // 테이블 전체 조회 쿼리
//        String query = "SELECT * FROM " + TABLE_NAME ;


        // 해빙기 프래그먼트 내 보안감시 설비 조회 쿼리
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                ")";


        // 조회 결과 반환
        return db.rawQuery(query, null);
    }


    // getData2() : 해빙기 프래그먼트 내 무선통신 설비 조회 함수
    public Cursor getData2() {
        // DB 열기
        SQLiteDatabase db = getReadableDatabase();


        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'무선통신설비'" +
                ")";


        // 조회 결과 반환
        return db.rawQuery(query, null);


    }


//    public void updateData(Context context, ContentValues values, String mainarea, String subarea, String detailarea, String list, String result, String editGetText) {
//        SQLiteDatabase db = null;
//
//        try {
//            db = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null , SQLiteDatabase.OPEN_READWRITE);
//            ContentValues values = new ContentValues();
//            values.put(COLUMN_EDITTEXT, editGetText);
//            db.update(TABLE_NAME, values, "mainarea=? AND subarea=? AND detailarea=? AND list=? AND result=?",
//                    new String[] {mainarea, subarea, detailarea, list, result});
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (db !=null) {
//                db.close();
//            }
//        }
//    }


}



//
//    public Cursor getRadioButtons() {
//        SQLiteDatabase db = getReadableDatabase();
//        String[] projection = {
//                COLUMN_RESULT
//        };
//        String sortOrder = COLUMN_RESULT + " ASC";
//        return db.query(
//                TABLE_NAME,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                sortOrder
//        );
//    }




// DB 내 데이터 조회 메소드
//    public Cursor getData() {
//        // DB 열기
//        SQLiteDatabase db = SQLiteDatabase.openDatabase(
//                context.getDatabasePath(DATABASE_NAME).getPath(),
//                null,
//                SQLiteDatabase.OPEN_READONLY
//        );
//
//        // 테이블 조회 쿼리
//        String query = "SELECT * FROM " + TABLE_NAME;
//
//        // 조회 결과 반환
//        return db.rawQuery(query, null);
//    }
