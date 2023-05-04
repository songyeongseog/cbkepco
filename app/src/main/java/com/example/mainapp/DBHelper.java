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
//    private static String DB_PATH = "/data/data/com.example.mainapp/assets/ICT 설비점검(기존).db";

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


    /********   아래 코드는 리사이클러뷰에 데이터를 보여주는 SQL 쿼리문    *********/

    /***  쿼리문 종류
     * ------------ 분류 없이 전체를 표출하는 테스트 -------------
     *  getData1() : 데이터를 낱개로 출력할 때 데이터베이스에서 '해빙기', '보안감시설비'에 해당하는 모든 값을 추출
     *  getData2() : 데이터를 낱개로 출력할 때 데이터베이스에서 '해빙기', '무선감시설비'에 해당하는 모든 값을 추출
     *
     *  ----------- 분류로 묶은 쿼리문 ----------
     *  getDataHaebinggiBoan() : 데이터를 대분류로 리사이클러뷰에 출력할 때 데이터베이스에서 '해빙기', '보안감시설비'에 해당하는 그룹화한 값을 추출
     *  getDataHaebinggiBoanSub() : 데이터를 대분류로 리사이클러뷰에 출력할 때 데이터베이스에서 '해빙기', '보안감시설비'에 해당하는 그룹화한 값을 추출
     *  getDataSub1_3
     * ***/

    // TODO : getDataMain() -> 보안감시설비 프래그먼트로 묶었어서
    //          getDataSub -> getDataMain으로 변경 필요
    //          getDataSub1_1 -> getDataSub 로 변경 필요

//   // getData1() : 해빙기 프래그먼트 내 보안감시 설비 조회 함수
//    public Cursor getData1() {
//        // DB 열기
//        SQLiteDatabase db = getWritableDatabase();
//
//        // 해빙기 프래그먼트 내 보안감시 설비 조회 쿼리
//        String query = "SELECT * FROM " + TABLE_NAME +
//                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
//                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
//                ")";
//
//        // 조회 결과 반환
//        return db.rawQuery(query, null);
//    }
//
//
//    // getData2() : 해빙기 프래그먼트 내 무선통신 설비 조회 함수
//    public Cursor getData2() {
//        // DB 열기
//        SQLiteDatabase db = getWritableDatabase();
//
//
//        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리
//        String query = "SELECT * FROM " + TABLE_NAME +
//                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
//                " AND " + COLUMN_SUBAREA + "=" + "'무선통신설비'" +
//                ")";
//
//
//        // 조회 결과 반환
//        return db.rawQuery(query, null);
//    }

    /********  해빙기 전체 쿼리문 ********/

    /***  ① 해빙기 프래그먼트 보안감시설비 분야 ***/

    public Cursor getDataHaebinggiBoan() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }


    public Cursor getDataHaebinggiBoanSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'화상감시/CCTV'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'출입통제'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'울타리감지'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'침투감지'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }


    public Cursor getDataHaebinggiBoanSubChurip() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'출입통제'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataHaebinggiBoanSubChimtu() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'침투감지'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataHaebinggiBoanSubHwasang() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'화상감시/CCTV'" +
                ")" +
                ")";

                return db.rawQuery(query, null);
    }

    /***  ② 해빙기 프래그먼트 무선통신설비 분야 ***/

    public Cursor getDataHaebinggiMuseon() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리 (중복값 컬럼 예외)
        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'무선통신설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataHaebinggiMuseonSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'무선통신설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'TRS 기지국'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'레이더 설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataHaebinggiMuseonSubTRS() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT +" FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'무선통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'TRS 기지국'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataHaebinggiMuseonSubRadar() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'무선통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'레이더 설비'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    /***  ③ 해빙기 프래그먼트 광전송장치 분야 ***/

    public Cursor getDataHaebinggiGwang() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리 (중복값 컬럼 예외)
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'광전송장치'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataHaebinggiGwangSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'광전송장치'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'광케이블'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataHaebinggiGwangSubGwang() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'광전송장치'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'광케이블'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    /***  ④ 해빙기 프래그먼트 ICT실 분야 ***/

    public Cursor getDataHaebinggiICT() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리 (중복값 컬럼 예외)
        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'ICT실'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataHaebinggiICTSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'ICT실'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'ICT실'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataHaebinggiICTSubICT() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'해빙기'" +
                " AND " + COLUMN_SUBAREA + "=" + "'ICT실'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'ICT실'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }


    /********  여름철 전체 쿼리문 ********/

    /***  ① 여름철 프래그먼트 계통운영센터 분야 ***/

    public Cursor getDataYeouremcheolGyetong() {

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'계통운영센터'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolGyetongSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'계통운영센터'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'계통운영센터'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolGyetongSubGyetong() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'계통운영센터'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'계통운영센터'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    /***  ② 여름철 프래그먼트 전력제어설비(계통보호 전송장치, DAS 통신설비) 분야 ***/

    public Cursor getDataYeouremcheolJeonryeok() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리 (중복값 컬럼 예외)
        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력제어설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }


    public Cursor getDataYeouremcheolJeonryeokSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력제어설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'원격소장치'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'계통보호 전송장치'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'DAS 통신설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }


    public Cursor getDataYeouremcheolJeonryeokSubWongyeok() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력제어설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'원격소장치'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolJeonryeokSubGyetong() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력제어설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'계통보호 전송장치'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolJeonryeokSubDas() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력제어설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'DAS 통신설비'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    /***  ③ 여름철 프래그먼트 무선통신설비 분야 ***/

    public Cursor getDataYeouremcheolMuseon() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        // 해빙기 프래그먼트 내 무선통신설비 조회 쿼리 (중복값 컬럼 예외)
        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'무선통신설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }


    public Cursor getDataYeouremcheolMuseonSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'무선통신설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'TRS 기지국'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'레이더 설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolMuseonSubTRS() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'무선통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'TRS 기지국'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolMuseonSubRadar() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'무선통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'레이더 설비'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }


    /***  ④ 여름철 프래그먼트 광통신설비 분야 ***/

    public Cursor getDataYeouremcheolGwang() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'광통신설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolGwangSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'광통신설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'광케이블'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'광전송장치'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolGwangSubCable() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'광통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'광케이블'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolGwangSubJangchi() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'광통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'광전송장치'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    /***  ⑤ 여름철 프래그먼트 비상통신설비 분야 ***/

    public Cursor getDataYeouremcheolBisang() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolBisangSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'계통운영통신시스템'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'직통전화'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolBisnagSubGyetong() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'계통운영통신시스템'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolBisnagSubJiktong() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'직통전화'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    /***  ⑥ 여름철 프래그먼트 전력구통신 분야 ***/

    public Cursor getDataYeouremcheolJeonryeokgu() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolJeonryeokguSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'WIFI 주장치'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'WIFI 중계기'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'WIFI 전원부'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'WIFI 증폭기'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'WIFI 단말기'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'TRS'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }


    public Cursor getDataYeouremcheolJeonryeokguSubWifiJu() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'WIFI 주장치'" +
                ")" +
                ")";
        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolJeonryeokguSubWifijunggye() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'WIFI 중계기'" +
                ")" +
                ")";
        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolJeonryeokguSubWifijeonwon() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'WIFI 전원부'" +
                ")" +
                ")";
        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolJeonryeokguSubWifijeungpok() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'WIFI 증폭기'" +
                ")" +
                ")";
        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolJeonryeokguSubWifidanmal() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'WIFI 단말기'" +
                ")" +
                ")";
        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolJeonryeokguSubTRS() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'TRS'" +
                ")" +
                ")";
        return db.rawQuery(query, null);
    }

    /***  ⑦ 여름철 프래그먼트 전원 및 공조설비(전원, 공조) 분야 ***/

    public Cursor getDataYeouremcheolJeonwon() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전원 및 공조설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolJeonwonSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전원 및 공조설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'전원설비'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'공조설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolJeonwonSubJeonwon() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전원 및 공조설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'전원설비'" +
                ")" +
                ")";
        return db.rawQuery(query, null);
    }


    public Cursor getDataYeouremcheolJeonwonSubGongjo() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전원 및 공조설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'공조설비'" +
                ")" +
                ")";
        return db.rawQuery(query, null);
    }

    /***  ⑧ 여름철 프래그먼트 ICT실 분야 ***/


    public Cursor getDataYeouremcheolICT() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'ICT실'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolICTSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'ICT실'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'ICT실'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataYeouremcheolICTSubICT() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'여름철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'ICT실'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'ICT실'" +
                ")" +
                ")";
        return db.rawQuery(query, null);
    }

    /********  추석 전체 쿼리문 ********/

    /***  ① 추석 프래그먼트 보안감시설비 분야 ***/

    public Cursor getDataChuseokBoan() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokBoanSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'화상감시/CCTV'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'출입통제장치'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'울타리감지설비'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'침투감지설비'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'기타'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokBoanSubHwasang() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'화상감시/CCTV'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokBoanSubChurip() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'출입통제장치'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokBoanSubUltari() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'울타리감지설비'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokBoanSubChimtu() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'침투감지설비'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokBoanSubGita() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'기타'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }


    /***  ② 추석 프래그먼트 전원설비, ICT실 분야 ***/

    public Cursor getDataChuseokJeonwon() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전원설비 · ICT실'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokJeonwonSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전원설비 · ICT실'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'ICT실/옥외설비'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'전원설비'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'공조설비'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'기타'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokJeonwonSubICT() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전원설비 · ICT실'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'ICT실/옥외설비'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokJeonwonSubJeonwon() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전원설비 · ICT실'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'전원설비'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokJeonwonSubGongjo() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전원설비 · ICT실'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'공조설비'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokJeonwonSubGita() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전원설비 · ICT실'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'기타'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    /***  ③ 추석 프래그먼트 비상통신설비 분야 ***/

    public Cursor getDataChuseokBisang() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokBisangSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'계통운영시스템(녹음장치)'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'직통전화(핫라인)'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'기타'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokBisangSubGyetong() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'계통운영시스템(녹음장치)'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokBisangSubHotline() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'직통전화(핫라인)'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataChuseokBisangSubGita() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'추석'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'기타'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    /********  겨울철 전체 쿼리문 ********/

    /***  ① 겨울철 프래그먼트 전력제어설비 분야 ***/

    public Cursor getDataGyeoulcheolJeonryeok() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력제어설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력제어설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'계통운영센터'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'원격소장치'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'계통보호전송장치'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokSubGyetong() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력제어설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'계통운영센터'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokSubWongyeok() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력제어설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'원격소장치'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokSubGyetongboho() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력제어설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'계통보호전송장치'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokSubDAS() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력제어설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'DAS 통신설비'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    /***  ② 겨울철 프래그먼트 전력구통신 분야 ***/

    public Cursor getDataGyeoulcheolJeonryeokgu() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokguSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'WIFI 주장치'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'WIFI 중계기'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'WIFI 전원부'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'WIFI 증폭기'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'WIFI 단말기'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'TRS'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokguSubWifiJu() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'WIFI 주장치'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokguSubWifiJung() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'WIFI 중계기'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokguSubWifiJeonwon() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'WIFI 전원부'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokguSubWifiJeungpok() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'WIFI 증폭기'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokguSubWifiDanmal() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'WIFI 단말기'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolJeonryeokguSubTRS() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'전력구통신'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'TRS'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    /***  ③ 겨울철 프래그먼트 비상통신설비 분야 ***/

    public Cursor getDataGyeoulcheolBisang() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolBisangSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'계통운영통신시스템'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'직통전화'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'화상감시/CCTV'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'출입통제'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'울타리 감지'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'침투감지'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolBisangSubGyetong() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'계통운영통신시스템'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolBisangSubjiktong() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'비상통신설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'직통전화'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    /***  ④ 겨울철 프래그먼트 보안감시설비 분야 ***/

    public Cursor getDataGyeoulcheolBoan() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();


        String query = "SELECT *  FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA ;

        // 조회 결과 반환
        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolBoanSub() {
        // DB 열기
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                "AND" + "(" + COLUMN_DETAILAREA + "=" + "'화상감시/CCTV'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'출입통제'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'울타리 감지'" +
                " OR " + COLUMN_DETAILAREA + "=" + "'침투감지'" +
                ")" +
                " GROUP BY " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA ;

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolBoanSubHwasang() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'화상감시/CCTV'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolBoanSubChurip() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'출입통제'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolBoanSubUltari() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'울타리 감지'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

    public Cursor getDataGyeoulcheolBoanSubChimtu() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT DISTINCT " + COLUMN_MAINAREA + "," + COLUMN_SUBAREA + "," + COLUMN_DETAILAREA + ","+
                COLUMN_DETAILAREA + "," + COLUMN_LIST  +"," + COLUMN_RESULT + ","+ COLUMN_EDITTEXT + " FROM " + TABLE_NAME +
                " WHERE " + "(" + COLUMN_MAINAREA + "=" + "'겨울철'" +
                " AND " + COLUMN_SUBAREA + "=" + "'보안감시설비'" +
                " AND " + COLUMN_DETAILAREA + " IN " + "(" +
                "'침투감지'" +
                ")" +
                ")";

        return db.rawQuery(query, null);
    }

}