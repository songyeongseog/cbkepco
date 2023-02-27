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
    private SQLiteDatabase db;
    private Context mContext;


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

    public CustomDialog(Context context, String mainarea, String subarea, String detailarea, String list, String result) {

        super(context);
        mContext = context;
        dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();

//        this.dbHelper = dbHelper; // DBHelper 인스턴스 저장
        this.mainarea = mainarea;
        this.subarea = subarea;
        this.detailarea = detailarea;
        this.list = list;
        this.result = result;
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
                    db.update("checklist", values, "mainarea=? AND subarea=? AND detailarea=? AND list=? AND result=?",
                            new String[] {mainarea, subarea, detailarea, list, result});

                    Log.d("editText값",String.valueOf(db.update("checklist", values, "mainarea=? AND subarea=? AND detailarea=? AND list=? AND result=?",
                            new String[] {mainarea, subarea, detailarea, list, result})));

                    Log.d("다이얼로그 인스턴스",String.valueOf(db));
                    db.close();
                }
                Log.d("editText값", String.valueOf(editText.getText().toString()));

                dismiss(); // 다이얼로그 닫기
            default:
                break;

        }
//        dismiss();
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









/***
 *
 *  Q: 라디오 버튼에서 선택한 값을 SharedPreferences나 Room 데이터베이스와 같은 영구 저장소에 저장하고, 다이얼로그에서 이 값을 불러올 때 이 저장소에서 값을 가져오는 방법을 알려줘
 *
 *
 A :라디오 버튼에서 선택한 값을 SharedPreferences나 Room 데이터베이스와 같은 영구 저장소에 저장하고, 다이얼로그에서 이 값을 불러오는 방법은 다음과 같습니다.

 SharedPreferences를 사용하는 경우:

 라디오 버튼에서 선택한 값을 SharedPreferences에 저장합니다.
 java
 Copy code
 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
 SharedPreferences.Editor editor = prefs.edit();
 editor.putString("selectedValue", selectedValue);
 editor.apply();
 다이얼로그에서 SharedPreferences에서 값을 가져와 사용합니다.
 java
 Copy code
 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
 String selectedValue = prefs.getString("selectedValue", "");
 Room 데이터베이스를 사용하는 경우:

 데이터베이스 엔티티 클래스를 생성합니다.
 java
 Copy code
 @Entity
 public class SelectedValue {
 @PrimaryKey
 public int id;

 public String value;
 }
 데이터베이스 DAO 클래스를 생성합니다.
 java
 Copy code
 @Dao
 public interface SelectedValueDao {
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 void insertSelectedValue(SelectedValue selectedValue);

 @Query("SELECT * FROM SelectedValue WHERE id = 1")
 SelectedValue getSelectedValue();
 }
 라디오 버튼에서 선택한 값을 Room 데이터베이스에 저장합니다.
 java
 Copy code
 SelectedValue selectedValue = new SelectedValue();
 selectedValue.id = 1;
 selectedValue.value = selectedValue;
 AppDatabase.getInstance(context).selectedValueDao().insertSelectedValue(selectedValue);
 다이얼로그에서 Room 데이터베이스에서 값을 가져와 사용합니다.
 java
 Copy code
 SelectedValue selectedValue = AppDatabase.getInstance(context).selectedValueDao().getSelectedValue();
 String value = selectedValue.value;



 ***/