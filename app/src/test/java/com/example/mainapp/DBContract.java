package com.example.mainapp;

public final class DBContract {
    // 데이터베이스 이름
    public static final String DATABASE_NAME = "my_db";

    // 데이터베이스 버전
    public static final int DATABASE_VERSION = 1;

    // 데이터베이스 테이블 이름
    public static final String TABLE_NAME = "my_table";

    // 테이블 열 이름
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";

    // private constructor to prevent instantiation
    private DBContract() {}
}

