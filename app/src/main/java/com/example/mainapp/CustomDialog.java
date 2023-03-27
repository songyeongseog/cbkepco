package com.example.mainapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.Manifest;

import androidx.core.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context mContext;

//    private Cursor mCursor;
//
//    private DataCursorAdapter dataCursorAdapter;

    public Activity c;
    public Dialog d;
    public ImageButton btnUpload;
    public Button saveButton;
    public EditText editText;
    public ImageView imageView;


    private String mainarea;
    private String subarea;
    private String detailarea;
    private String list;
    private String result;


    // 위치 정보와 촬영 시각을 저장하는 변수
    private String location;    // 위치 정보를 저장할 변수
    private long captureTime;   // 촬영 시각을 저장할 변수

    File file;

    //    private Context mContext;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri photoURI;





    public CustomDialog(Activity activity, Context context, String mainarea, String subarea, String detailarea, String list) {

        super(context);
        mContext = context;
        c = activity;

        this.mainarea = mainarea;
        this.subarea = subarea;
        this.detailarea = detailarea;
        this.list = list;

//        this.imageName = imageName;
//        this.result = result;

//        this.location = location;
//        this.captureTime = captureTime;

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
    }




    // 사진 버튼 클릭 시 동작
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_upload:
                // 이미지 업로드 버튼 클릭 시 동작
                // 사진촬영 및 갤러리를 불러오는 코드를 구현해야함
                // TODO: 카메라 촬영 기능 구현

                // 카메라 권한이 허용되어 있는지 확인
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        c.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                        return;
                    }
                }
                // 카메라 앱 호출
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.resolveActivity(mContext.getPackageManager());
                if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
                    // 이미지 파일 생성
                    try {
                        file = createImageFile();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (file != null) {
                        // 파일에 대한 URI 생성
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            photoURI = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileprovider", file);
                        } else {
                            photoURI = Uri.fromFile(file);
                        }
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        c.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
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

                        db.update("checklist", values, "mainarea=? AND subarea=? AND detailarea=? AND list=?",
                                new String[] {mainarea, subarea, detailarea, list});
                        db.setTransactionSuccessful();  // 변경 내용 커밋
                    }
                } finally {
                    db.endTransaction();    // 트래젝션 종료
                    db.close();     // 데이터베이스 닫기
                }

                dismiss(); // 다이얼로그 닫기
                break;
            default:
                break;
        }
    }



    // 밀리초 단위 시간 값을 DATETIME 형식으로 변환하는 메소드
    private String convertTimeToDateTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(time);
        return dateFormat.format(date);
    }

    // 이미지 파일을 생성하는 메소드
    private File createImageFile() throws Exception {


//        String test = mCursor.getString(mCursor.getColumnIndexOrThrow("list"));
//        Log.d("test", String.valueOf(test));
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        // 파일명에다가 해당 항목에 대한 text 값을 받아야 함.
        /// 경로명 : storage/self/primary/Android/data/com.example.mainapp/files/Pictures/JPEG_20230308_173648_1658900543.jpg
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        Log.d("이미지 경로", String.valueOf(storageDir));
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        return imageFile;
    }

}







