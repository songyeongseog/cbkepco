package com.example.mainapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;


// 해빙기 내 설비 선택화면 (activity_selecetpage_1.xml)
public class SubActivity1 extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecetpage_1);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();


        Button sub1_btn1 = (Button) findViewById(R.id.sub1_btn1);
        sub1_btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent sub1_intent = new Intent(getApplicationContext(), FragmentActivity1.class);   // ★ 자바 클래스 수정해야함
                startActivity(sub1_intent);
            }
        });


        // 뒤로가기 버튼(MainActivity 이동) --> 처음 설비점검 선택하는 화면
        Button sub1_btn5 = (Button) findViewById(R.id.sub1_btn5);
        sub1_btn5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent5);
            }
        });


        // 뒤로가기 버튼(MainActivity 이동) --> 처음 설비점검 선택하는 화면
        Button sub1_btn6 = (Button) findViewById(R.id.sub1_btn6);
        sub1_btn6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                exportDatabaseToCsvAndSendEmail();

            }
        });

    }

    public void exportDatabaseToCsvAndSendEmail() {
        // 데이터베이스에서 데이터 추출
        Cursor cursor = db.rawQuery("SELECT * FROM checklist", null);

        // CSV 파일로 변환
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeNext(cursor.getColumnNames());
        while (cursor.moveToNext()) {
            String[] row = new String[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row[i] = cursor.getString(i);
            }
            csvWriter.writeNext(row);
        }

        // 파일 저장
        String fileName = "ICT 설비점검 결과.csv";
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, fileName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(writer.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 이메일 보내기
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/csv");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Database");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Attached is a CSV file of my database.");
//        Uri uri = Uri.fromFile(file);
        Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", file);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
