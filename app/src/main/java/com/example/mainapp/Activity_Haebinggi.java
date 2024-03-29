package com.example.mainapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;


// 해빙기 내 설비 선택화면 (activity_haebinggi.xml)
public class Activity_Haebinggi extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haebinggi);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        // Activity에서 Context 가져오기
        mContext = this;

        // 어플리케이션 전역 Context 가져오기
        mContext = getApplicationContext();


        ImageButton sub1_btn1 = (ImageButton) findViewById(R.id.sub1_btn1);
        sub1_btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent_1 = new Intent(getApplicationContext(), FragmentActivity_Haebinggi.class);
                intent_1.putExtra("SELECTED_TAB", 0); // 프래그먼트1 (보안감시설비) 선택
                startActivity(intent_1);
            }
        });

        ImageButton sub1_btn2 = (ImageButton) findViewById(R.id.sub1_btn2);
        sub1_btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent_2 = new Intent(getApplicationContext(), FragmentActivity_Haebinggi.class);
                intent_2.putExtra("SELECTED_TAB", 1); // 프래그먼트2 (무선통신설비) 선택
                startActivity(intent_2);
            }
        });

        ImageButton sub1_btn3 = (ImageButton) findViewById(R.id.sub1_btn3);
        sub1_btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent_3 = new Intent(getApplicationContext(), FragmentActivity_Haebinggi.class);
                intent_3.putExtra("SELECTED_TAB", 2); // 프래그먼트3 (광전송장치) 선택
                startActivity(intent_3);
            }
        });

        ImageButton sub1_btn4 = (ImageButton) findViewById(R.id.sub1_btn4);
        sub1_btn4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent_4 = new Intent(getApplicationContext(), FragmentActivity_Haebinggi.class);
                intent_4.putExtra("SELECTED_TAB", 3); // 프래그먼트4 (ICT실) 선택
                startActivity(intent_4);
            }
        });


        // 점검결과 송부 버튼
        ImageView sub1_btn5 = (ImageView) findViewById(R.id.sub1_btn5);
        sub1_btn5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(Activity_Haebinggi.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Activity_Haebinggi.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    // 파일 접근 코드 -> 이메일 메소드
                    exportDatabaseToCsvAndSendEmail();
                    Toast emailToast = Toast.makeText(getApplicationContext(), "10초 뒤에 데이터베이스 및 사진정보가 초기화 됩니다", Toast.LENGTH_LONG);
                    emailToast.show();
                    try {
                        Thread.sleep(10000); // 1000 밀리초 = 1초
                        // 레코드 초기화
                        db.beginTransaction();  // 데이터 변경 작업 수행
                        try{
                            db.execSQL("UPDATE checklist SET result = null, edittext = null, image = null, date = null");
                            db.setTransactionSuccessful();  // 변경 내용 커밋
                        }finally {
                            db.endTransaction();    // 트래젝션 종료
                            Toast resetToast = Toast.makeText(getApplicationContext(), "데이터베이스 및 사진정보가 등록 후 초기화 되었습니다.", Toast.LENGTH_LONG);
                            resetToast.show();
                        }
                                            // 이미지 파일 삭제
                        String imagePath = "storage/self/primary/Android/data/com.example.mainapp/files/Pictures/";
                        File imageFile = new File(imagePath);
                        File[] files = imageFile.listFiles();
                        if(files != null) {
                            for (File file : files) {
                                file.delete();
                            }
                        }
                        } catch (InterruptedException e) {
                            // 스레드가 중단될 경우 처리할 예외 처리 코드
                        }
                }
            }
        });

    }

    public void exportDatabaseToCsvAndSendEmail() {

        // 데이터베이스에서 해빙기에 대한 모든 데이터 추출
        Cursor cursor = db.rawQuery("SELECT * FROM checklist WHERE mainarea LIKE '%해빙기%'", null);

        if (cursor.moveToFirst()) {
            // 커서가 비어있지 않은 경우
            do {
                // 커서에서 데이터를 추출하여 처리
            } while (cursor.moveToNext());
        } else {
            // 커서가 비어있는 경우
            Log.d("데이터가 없음", "No data found.");
            Toast.makeText(getApplicationContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT);

        }

        // cursor에 데이터가 있는 경우 CSV 파일로 변환
        if (cursor != null && cursor.moveToFirst()) {

            // CSV 파일로 변환
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer);

            String[] columnNames = cursor.getColumnNames();
            String[] row = new String[columnNames.length];
            for (int i = 0; i < columnNames.length; i++) {
                row[i] = columnNames[i];
            }
            csvWriter.writeNext(row); // 첫 번째 행에 컬럼명 추가

            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
//                String[] row = new String[cursor.getColumnCount()];
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    row[i] = cursor.getString(i);
                }
                csvWriter.writeNext(row);
            }

            // 파일 저장
            String fileName = "ICT 설비점검 결과.csv";
            File path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

            File file = new File(path, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "EUC-KR"); // csv 인코딩은 EUC-KR 로 맞추어야 함. UTF-8 사용불가
                osw.write(writer.toString());
                osw.flush();
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



            // 이미지파일 첨부 코드
            File dir = new File("storage/self/primary/Android/data/com.example.mainapp/files/Pictures");

            // 첨부할 jpg 파일들의 File 객체 리스트 만들기
            File[] files = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".jpg");
                }
            });


            if (files != null) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                emailIntent.setType("multipart/mixed");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]
                        {"hyunjoo.song@kepco.co.kr"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[사업소명] ICT설비점검 결과 송부");  // 메일 제목 (사업소명을 변수로 두고 설정해야함)
                emailIntent.putExtra(Intent.EXTRA_TEXT, "[사업소명] ICT설비점검 결과입니다.");  // 메일 내용

                ArrayList<File> fileList = new ArrayList<>(Arrays.asList(files));

                // File 객체 리스트를 Uri 객체 리스트로 변환하기
                ArrayList<Uri> uriList = new ArrayList<>();
                Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", file); // CSV 파일
                uriList.add(uri);

                    for (File imageFile : fileList) {
                        Uri uri2 = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", imageFile);
                        uriList.add(uri2);
                    }

                emailIntent.setType("image/*");
                emailIntent.setType("text/csv");
                emailIntent.putExtra(Intent.EXTRA_STREAM, uriList);
                startActivity(Intent.createChooser(emailIntent, "ICT 설비점검 결과 메일 보내기"));
            } else {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                // csv파일 첨부 코드
                Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", file);

                emailIntent.setType("text/csv");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]
                        {"syc0106@kepco.co.kr"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[사업소명] ICT설비점검 결과 송부");  // 메일 제목 (사업소명을 변수로 두고 설정해야함)
                emailIntent.putExtra(Intent.EXTRA_TEXT, "[사업소명] ICT설비점검 결과입니다.");  // 메일 내용
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(emailIntent, "ICT 설비점검 결과 메일 보내기"));

            }
        }else {
                // cursor가 비어있는 경우 보고서 생성하지 않음
                Toast.makeText(this, "데이터가 없습니다.", Toast.LENGTH_SHORT).show();

        }
    }

}


// https://sourceforge.net/projects/opencsv/
// CSVWriter.jar 다운로드 경로

//            // 공유 보내기 (파워톡 등)
//
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            shareIntent.setType("text/csv");
//            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "제목");
//            shareIntent.putExtra(Intent.EXTRA_TEXT, "내용");
//            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", file);
//            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//            startActivity(Intent.createChooser(shareIntent, "Share CSV file"));