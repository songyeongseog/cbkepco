package com.example.mainapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapMainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_main);

        // 카카오맵 API 키를 입력합니다.
        String kakaoApiKey = "2b58ed4fe9ee73631b1069dee03c7de7";

        // MapView 객체를 생성합니다.
        MapView mapView = new MapView(this);


        // MapView 객체를 레이아웃에 추가합니다.
        setContentView(mapView);

        // 위치 권한이 허용되어 있는지 확인합니다.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 위치 권한을 요청합니다.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
        } else {
            // 현재 위치를 중심으로 지도를 보여줍니다.
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
        }


        // 카카오맵 API 키를 지정합니다.
        mapView.setDaumMapApiKey(kakaoApiKey);




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 위치 권한이 허용되어 있으면 현재 위치를 중심으로 지도를 보여줍니다.
                MapView mapView = (MapView) findViewById(R.id.map_view);
                if (mapView != null) {
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                }
            } else {
                // 위치 권한이 거부되었을 경우 안내 메시지를 띄웁니다.
                Toast.makeText(this, "위치 권한을 허용해야 지도를 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}