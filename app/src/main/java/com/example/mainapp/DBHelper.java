package com.example.mainapp;

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


//    private Context mContext;


    // DB 정보
    private static final String DATABASE_NAME = "ICT 설비점검.db";
    private static final int DATABASE_VERSION = 1;


    // DB 경로
//    private static String DB_PATH = "/data/data/com.example.mainapp/assets/ICT 설비점검.db";

    // 테이블 정보
    public static String TABLE_NAME = "checklist";      // 라디오 버튼 클릭

    // 컬럼 정보
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MAINAREA = "mainarea";
    public static final String COLUMN_SUBAREA = "subarea";
    public static final String COLUMN_DETAILAREA = "detailarea";
    public static final String COLUMN_LIST = "list";
    public static final String COLUMN_RESULT = "result";
    public static final String COLUMN_EDITTEXT = "edittext";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_DATE = "date";

    // 생성자

//    public DBHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//
//        SQLiteDatabase db = getWritableDatabase();
//        File dbFile = new File(db.getPath());
//
//        Log.d("경로", String.valueOf(db.getPath()));
//        Log.d("경로2", String.valueOf(context.getDatabasePath(DATABASE_NAME)));
//
////        db.close();
//
//        if (!dbFile.exists()) {
//            // assets 폴더에서 데이터베이스 파일을 복사하여 생성
//            InputStream inputStream = null;
//            OutputStream outputStream = null;
//
//            try {
//                inputStream = context.getAssets().open(DATABASE_NAME);
//                outputStream = new FileOutputStream(context.getDatabasePath(DATABASE_NAME));
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
//                Log.d("inputStraem", String.valueOf(inputStream));
//                Log.d("outputStraem", String.valueOf(outputStream));
//            }
//        }
//    }

    public DBHelper(Context context) {
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
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    // 23.3.15 추가 ID 컬럼 (기존 : COLUMN_MAINAREA + INTEGER PRIMARY KEY AUTOINCREMENT)
                    COLUMN_MAINAREA + " TEXT, " +
                    COLUMN_SUBAREA + " TEXT, " +
                    COLUMN_DETAILAREA + " TEXT, " +
                    COLUMN_LIST + " TEXT, " +
                    COLUMN_RESULT + " TEXT, " +
                    COLUMN_EDITTEXT + " TEXT, " +
                    COLUMN_IMAGE + " TEXT," +
                    COLUMN_DATE + " TEXT" +
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
        SQLiteDatabase db = getWritableDatabase();

        // 테이블 전체 조회 쿼리
//        String query = "SELECT * FROM " + TABLE_NAME ;


        // 해빙기 프래그먼트 내 보안감시 설비 조회 쿼리
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                ")";


        // 조회 결과 반환
//        return db.rawQuery(query, null);
        return db.rawQuery(query, null);
    }


    // getData2() : 해빙기 프래그먼트 내 무선통신 설비 조회 함수
    public Cursor getData2() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'무선통신설비'" +
                ")";


        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    // getDataMain() : 보안감시설비 대분류 묶기 (ex: 보안감시설비 리사이클러뷰 최상단)

    public Cursor getDataMain1() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리 (중복값 컬럼 예외)
        String query = "SELECT  *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }


//    public Cursor getDataMain1() {
//        // DB 열기
//        SQLiteDatabase db = getWritableDatabase();
//
//
//        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리 (중복값 컬럼 예외)
//        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + " FROM " + TABLE_NAME +
//                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
//                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
//                ")";
//
//        // 조회 결과 반환
//        return db.rawQuery(query, null);
//    }

    // getDataMain() : CCTV 대분류 묶기 (ex: 보안감시설비 리사이클러뷰 최상단)
    public Cursor getDataSub1_1() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리
        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA +" FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + "=" + "'화상감시/CCTV'" +
                ")";

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataSub1_2() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리
//        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA +" FROM " + TABLE_NAME +
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'화상감시/CCTV'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'출입통제'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'울타리감지'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'침투감지'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;


        // 조회 결과 반환
        return db.rawQuery(query, null);
    }


//    public Cursor getDataSub1_2() {
//        // DB 열기
//        SQLiteDatabase db = getWritableDatabase();
//
//
//        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리
//        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA +" FROM " + TABLE_NAME +
//                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
//                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
//                " AND " + COLUMN_DETAILAREA + "=" + "'화상감시/CCTV'" +
//                ")"
//                +
//                " UNION "
//                +
//                "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA +" FROM " + TABLE_NAME +
//                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
//                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
//                " AND " + COLUMN_DETAILAREA + "=" + "'출입통제'" +
//                ")"
//                +
//                " UNION "
//                +
//                "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA +" FROM " + TABLE_NAME +
//                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
//                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
//                " AND " + COLUMN_DETAILAREA + "=" + "'울타리 감지'" +
//                ")"
//                +
//                " UNION "
//                +
//                "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA +" FROM " + TABLE_NAME +
//                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
//                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
//                " AND " + COLUMN_DETAILAREA + "=" + "'침투감지'" +
//                ")";
////                + " ORDER BY " + COLUMN_ID + " ASC";
//        // TODO  : ORDER BY 를 컬럼 아이디로 오름차순 하는 것을 확인해봐야 함.
//
//        // 조회 결과 반환
//        return db.rawQuery(query, null);
//    }

    public Cursor getDataSub1_3() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'화상감시/CCTV'"+ "," +
                "'출입통제'" + "," +
                "'울타리 감지'" + "," +
                "'침투감지'" +
                ")" +
                ")";

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

}




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
