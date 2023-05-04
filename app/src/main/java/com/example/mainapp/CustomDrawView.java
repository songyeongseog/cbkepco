package com.example.mainapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;


public class CustomDrawView extends AppCompatActivity {

    private ResultDrawView resultDrawView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_draw_view);

        // 레이아웃에서 ResultDrawView를 찾아 인스턴스 생성
        resultDrawView = findViewById(R.id.custom_view);

        /*** '서명 초기화 버튼' 클릭 시 동작 ***/

        Button drawBtn1 = (Button) findViewById(R.id.drawBtn1);
        drawBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultDrawView.clear();
            }
        });

        /*** '저장 및 닫기 버튼' 클릭 시 동작 ***/

        Button drawBtn2 = (Button) findViewById(R.id.drawBtn2);
        drawBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 전달할 Bitmap
                Bitmap bitmap = resultDrawView.getBitmap();

                // SharePreferences 객체 생성
                SharedPreferences sharedPreferences = getSharedPreferences("result_draw", Context.MODE_PRIVATE);

                // SharedPreferences.Editor를 사용하여 데이터 저장
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("result_draw", bitmapToString(bitmap));    //result_draw로 보내는 것에 대해 받겠다.
                editor.apply();

                finish();       // CustomDrawView 액티비티 종료
            }
        });
    }

    private String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
