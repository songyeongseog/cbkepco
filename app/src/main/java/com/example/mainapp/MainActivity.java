package com.example.mainapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 3;

//    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int PERMISSION_REQUEST_CODE = 200;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecetpage);


        // 첫 번째 권한 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }


        // main_btn1(해빙기) 클릭 시 해빙기 액티비티로 이동(해빙기 액티비티명 : SubActivity_selecetpage_1)
        ImageButton main_btn1 = (ImageButton) findViewById(R.id.main_btn1);
        main_btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), Activity_Haebinggi.class);
                startActivity(intent1);
            }
        });


        ImageButton main_btn2 = (ImageButton) findViewById(R.id.main_btn2);
        main_btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(), Activity_Yeoreumcheol.class);
                startActivity(intent2);

//                /*** 재사용 예정 (DB 작업 완료 후)
//                 Intent intent2 = new Intent(getApplicationContext(), Activity_Yeoreumcheol.class);
//                startActivity(intent2);
//                 */
            }
        });

        ImageButton main_btn3 = (ImageButton) findViewById(R.id.main_btn3);
        main_btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(getApplicationContext(), Activity_Chuseok.class);
                startActivity(intent3);

            }
        });

        ImageButton main_btn4 = (ImageButton) findViewById(R.id.main_btn4);
        main_btn4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent4 = new Intent(getApplicationContext(), Activity_Gyeoulcheol.class);
                startActivity(intent4);
            }
        });
    }

    // 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 두 번째 권한 요청
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
                    }
                }
                break;
        }
    }
}