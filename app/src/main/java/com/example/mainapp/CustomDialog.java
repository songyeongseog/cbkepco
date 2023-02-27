package com.example.mainapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    private DBHelper dbHelper;
    private Context mContext;
    private SQLiteDatabase db;


    public Activity c;

    public Dialog d;
    public Button btnUpload;
    public Button saveButton;
    public EditText editText;
    public ImageView imageView;


    private String mainarea;
    private String subarea;
    private String detailarea;
    private String list;
    private String result;



// 로그를 찍어보니 인스턴스가 다름, 어댑터랑 인스턴스를 맞춰야함
    // context 로 선언된 것 같음 (어댑터, 핼퍼)
    // 여기도 맞춰야할듯

    public CustomDialog(Context context, String mainarea, String subarea, String detailarea, String list) {

        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;

        dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();

        // this.dbHelper = dbHelper; // DBHelper 인스턴스 저장
        this.mainarea = mainarea;
        this.subarea = subarea;
        this.detailarea = detailarea;
        this.list = list;
        // this.result = result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        btnUpload = findViewById(R.id.btn_upload);
        editText = findViewById(R.id.edit_text);
        imageView = findViewById(R.id.image_view);
        saveButton = findViewById(R.id.saveButton);

        btnUpload.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }




    // 사진 버튼 클릭 시 동작
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_upload:
                // 이미지 업로드 버튼 클릭 시 동작
                // 사진촬영 및 갤러리를 불러오는 코드를 구현해야함
                // TODO: 이미지 업로드 기능 구현
                break;

            case R.id.saveButton:
                String editGetText = editText.getText().toString(); // edittext 값 가져오기
                if(!TextUtils.isEmpty(editGetText)){

                    ContentValues values = new ContentValues();
                    values.put("edittext", editGetText);

                    Log.d("editText", String.valueOf(values));
                    Log.d("editText값", String.valueOf(db.update("checklist", values,
                            "mainarea=? AND subarea=? AND detailarea=? AND list=?",
                            new String[] {mainarea, subarea, detailarea, list})));
                    Log.d("라디오버튼 1 인스턴스",String.valueOf(db));


                    db.update("checklist", values, "mainarea=? AND subarea=? AND detailarea=? AND list=?",
                            new String[] {mainarea, subarea, detailarea, list});



                    Log.d("다이얼로그 인스턴스",String.valueOf(db));
//                    db.close();
                }
                Log.d("editText값", String.valueOf(editText.getText().toString()));

                dismiss(); // 다이얼로그 닫기
            default:
                break;

        }
        dismiss();
    }
}



//package com.example.mainapp;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.ContentValues;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//public class CustomDialog extends Dialog implements
//        android.view.View.OnClickListener {
//
//    private DBHelper dbHelper;
//
//    public Activity c;
//    public Dialog d;
//    public Button btnUpload;
//    public Button saveButton;
//    public EditText editText;
//    public ImageView imageView;
//
//
//    private String mainarea;
//    private String subarea;
//    private String detailarea;
//    private String list;
//    private String result;
//
//
//// 로그를 찍어보니 인스턴스가 다름, 어댑터랑 인스턴스를 맞춰야함
//    // context 로 선언된 것 같음 (어댑터, 핼퍼)
//    // 여기도 맞춰야할듯
//
//    public CustomDialog(Activity a, String mainarea, String subarea, String detailarea, String list, String result) {
//        super(a);
//        // TODO Auto-generated constructor stub
//        this.c = a;
//        this.dbHelper = new DBHelper(a);
//
//        this.dbHelper = dbHelper; // DBHelper 인스턴스 저장
//        this.mainarea = mainarea;
//        this.subarea = subarea;
//        this.detailarea = detailarea;
//        this.list = list;
//        this.result = result;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.custom_dialog);
//        btnUpload = findViewById(R.id.btn_upload);
//        editText = findViewById(R.id.edit_text);
//        imageView = findViewById(R.id.image_view);
//        saveButton = findViewById(R.id.saveButton);
//
//        btnUpload.setOnClickListener(this);
//        saveButton.setOnClickListener(this);
//    }
//
//
//
//
//    // 사진 버튼 클릭 시 동작
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.btn_upload:
//                // 이미지 업로드 버튼 클릭 시 동작
//                // 사진촬영 및 갤러리를 불러오는 코드를 구현해야함
//                // TODO: 이미지 업로드 기능 구현
//                break;
//
//            case R.id.saveButton:
//                String editGetText = editText.getText().toString(); // edittext 값 가져오기
//                if(!TextUtils.isEmpty(editGetText)){
//
//                    DBHelper dbHelper = new DBHelper((getContext()));
//
//                    SQLiteDatabase db = dbHelper.getWritableDatabase();
//                    ContentValues values = new ContentValues();
//                    values.put(DBHelper.COLUMN_EDITTEXT, editGetText);
//                    db.update(DBHelper.TABLE_NAME, values, "mainarea=? AND subarea=? AND detailarea=? AND list=? AND result=?",
//                            new String[] {mainarea, subarea, detailarea, list, result});
//
//                    Log.d("editText값",String.valueOf(db.update(DBHelper.TABLE_NAME, values,
//                            "mainarea=? AND subarea=? AND detailarea=? AND list=?",
//                            new String[] {mainarea, subarea, detailarea, list})));
//
//                    Log.d("다이얼로그 인스턴스",String.valueOf(dbHelper));
////                    db.close();
//                }
//                Log.d("editText값", String.valueOf(editText.getText().toString()));
//
//                this.dismiss(); // 다이얼로그 닫기
//            default:
//                break;
//
//        }
//        dismiss();
//    }
//}
