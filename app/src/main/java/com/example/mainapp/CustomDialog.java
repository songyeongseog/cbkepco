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



    public CustomDialog(Context context, String mainarea, String subarea, String detailarea, String list) {

        super(context);
        mContext = context;

        this.mainarea = mainarea;
        this.subarea = subarea;
        this.detailarea = detailarea;
        this.list = list;
//        this.result = result;

        dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();


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


//        dbHelper = new DBHelper(mContext);
//        db = dbHelper.getWritableDatabase();
    }




    // ?????? ?????? ?????? ??? ??????
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_upload:
                // ????????? ????????? ?????? ?????? ??? ??????
                // ???????????? ??? ???????????? ???????????? ????????? ???????????????
                // TODO: ????????? ????????? ?????? ??????
                break;

            case R.id.saveButton:

                long currentTime = System.currentTimeMillis();
                db.beginTransaction();  // ????????? ???????????? ??????
                try{
                    String editGetText = editText.getText().toString(); // edittext ??? ????????????
                    if(!TextUtils.isEmpty(editGetText)) {

                        ContentValues values = new ContentValues();
                        values.put("edittext", editGetText);

                        values.put("date", convertTimeToDateTime(currentTime)); // ?????? ?????? DateTime ???????????? ??????

                        db.update("checklist", values, "mainarea=? AND subarea=? AND detailarea=? AND list=?",
                                new String[] {mainarea, subarea, detailarea, list});
                        db.setTransactionSuccessful();  // ?????? ?????? ??????
                    }
                } finally {
                    db.endTransaction();    // ???????????? ??????
//                    db.close();     // ?????????????????? ??????
                }

                dismiss(); // ??????????????? ??????
                break;
            default:
                break;
        }
    }
    // ????????? ?????? ?????? ?????? DATETIME ???????????? ???????????? ?????????
    private String convertTimeToDateTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(time);
        return dateFormat.format(date);
    }

}







