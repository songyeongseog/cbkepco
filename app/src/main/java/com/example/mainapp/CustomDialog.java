package com.example.mainapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context mContext;

//    private DataCursorAdapter.DataViewHolder holder;


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

//        DataCursorAdapter adapter = new DataCursorAdapter(mContext, null);
//        holder = adapter.getViewHolder();

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
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                // 이미지 업로드 버튼 클릭 시 동작
                // 사진촬영 및 갤러리를 불러오는 코드를 구현해야함
                // TODO: 이미지 업로드 기능 구현


                break;

            case R.id.saveButton:

                long currentTime = System.currentTimeMillis();
                db.beginTransaction();  // 데이터 변경작업 수행
                try{
                    String editGetText = editText.getText().toString(); // edittext 값 가져오기
                    if(!TextUtils.isEmpty(editGetText)) {

                        ContentValues values = new ContentValues();
                        values.put("edittext", editGetText);

                        values.put("date", convertTimeToDateTime(currentTime)); // 현재 시간 DateTime 형식으로 저장

                        db.update("checklist", values, "mainarea=? AND subarea=? AND detailarea=? AND list=? AND result=?",
                                new String[] {mainarea, subarea, detailarea, list, result});
                        db.setTransactionSuccessful();  // 변경 내용 커밋
                    }
                } finally {
                    db.endTransaction();    // 트래젝션 종료
                    db.close();     // 데이터베이스 닫기
                }

                dismiss(); // 다이얼로그 닫기
            default:
                break;
        }

//        dismiss();

    }
    // 밀리초 단위 시간 값을 DATETIME 형식으로 변환하는 메소드
    private String convertTimeToDateTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(time);
        return dateFormat.format(date);
    }

}







