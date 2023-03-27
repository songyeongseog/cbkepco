package com.example.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {
    TMapGpsManager tMapGPS = null;
    TMapView tmapview;
    Context context;

    private TMapDBHelper dbHelper;
    private List<Location> locationList = new ArrayList<>();

    private String tMapApiKey = "VQQGpOnt9S2L8OlXRePml3LvztIGfC2LaZ90P9h0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tmap);

        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.linearLayoutTmap);
        tmapview = new TMapView(this);
        tmapview.setSKTMapApiKey(tMapApiKey);
        linearLayoutTmap.addView(tmapview);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) { //위치 권한 확인
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        tMapGPS = new TMapGpsManager(this);
        tMapGPS.setMinTime(1000);
        tMapGPS.setMinDistance(10);
        tMapGPS.setProvider(tMapGPS.GPS_PROVIDER);
        tMapGPS.OpenGps();
        tMapGPS.setProvider(tMapGPS.NETWORK_PROVIDER);  // 건물에 있으면 gps가 안되는 듯 -> 이럴경우 network로 받아오는게 나음
        tMapGPS.OpenGps();

        dbHelper = new TMapDBHelper(this);
        locationList = dbHelper.getAllLocations();
//        tmapview.invalidate();  // 강제로 맵을 다시그려주는 메소드 -> 마커가 추가되거나 삭제되면 메소드를 호출해야 함.

//        tMapGPS.setProvider(tMapGPS.NETWORK_PROVIDER);
//        tMapGPS.OpenGps();
    }
    @Override
    public void onLocationChange(Location location) {

        // 현재 위치 정보 가져오기
        if (location != null) {
//            TMapPoint tMapPointMy = tMapGPS.getLocation();
//            double latitude = tMapPointMy.getLatitude();
//            double longitude = tMapPointMy.getLongitude();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            tmapview.setLocationPoint(longitude, latitude); // 현재위치로 표시될 좌표의 위도, 경도를 설정
            tmapview.setIconVisibility(true);
            tmapview.setCompassMode(true);      // 나침반 모드
            tmapview.setSightVisible(true);     // 보고있는 방향 표출
            tmapview.setCenterPoint(longitude, latitude, true); // 현재 위치로 이동
        }

        // DB에서 가져온 위치 데이터를 마커로 표시하기
        for (Location loc : locationList) {
            TMapPoint tMapPoint = new TMapPoint(loc.getLatitude(), loc.getLongitude());
            TMapMarkerItem markerItem = new TMapMarkerItem();
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.marker);
            markerItem.setTMapPoint(tMapPoint);
            markerItem.setVisible(TMapMarkerItem.VISIBLE);

            markerItem.setIcon(bitmap);
            markerItem.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
            tmapview.addMarkerItem("marker", markerItem);
        }
        Log.d("마커 데이터", String.valueOf(locationList));
    }
}


//package com.example.mainapp;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.skt.Tmap.TMapGpsManager;
//import com.skt.Tmap.TMapMarkerItem;
//import com.skt.Tmap.TMapPoint;
//import com.skt.Tmap.TMapView;
//
//import net.daum.mf.map.api.MapPoint;
//import net.daum.mf.map.api.MapView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//
//public class MapActivity extends AppCompatActivity implements LocationListener {
//
//    private TMapView tmapview;
//    private TMapGpsManager tmapgps;
//    private LocationManager locationManager;
//    Context context;
//
//
//    private static final int PERMISSIONS_REQUEST_CODE = 100;
//
//    // 카카오맵 API 키를 입력합니다.
//    String tMapApiKey = "VQQGpOnt9S2L8OlXRePml3LvztIGfC2LaZ90P9h0";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.map_main);
//
//
//
//        // 위치 권한이 허용되어 있는지 확인합니다.
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            // 위치 권한을 요청합니다.
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
//        } else {
////            // LocationManager 객체 생성
////            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
////
////            // 위치 업데이트 요청
////            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//
//            // 위치 권한이 이미 허용된 경우 위치 정보를 받아옵니다.
//            LinearLayout linearLayout = new LinearLayout(this);
//            tmapview = new TMapView(this);
//            tmapview.setSKTMapApiKey(tMapApiKey);       // API 키
//            tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);     // 언어설정
//            tmapview.setZoomLevel(18);          // 줌 레벨 (18이 네비로 봤을 때 딱 적당함)
//            tmapview.setMapType(TMapView.MAPTYPE_STANDARD); // 일반지도
//
//
//            tmapview.setIconVisibility(true);   // 현재위치로 표시될 아이콘을 표시
//            tmapview.setCompassMode(true);      // 나침반 모드
//            tmapview.setSightVisible(true);     // 보고있는 방향 표출
//            tmapview.setTrackingMode(true);
//
//
//
//
//            // Todo 좌표값 현재 단말 위치로 함수 받아오기
//            linearLayout.addView(tmapview);
//            setContentView(linearLayout);
//
//            // GPS 초기화 및 위치 갱신
//            tmapgps = new TMapGpsManager(this);
//            tmapgps.setMinTime(1000);
//            tmapgps.setMinDistance(5);
//            tmapgps.setProvider(tmapgps.GPS_PROVIDER);
//            tmapgps.OpenGps();
//        }
//
//
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSIONS_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // 위치 권한이 허용된 경우 위치 정보를 받아옵니다.
//                TMapGpsManager gps = new TMapGpsManager(this);
//                gps.setMinTime(1000);
//                gps.setMinDistance(5);
//                gps.setProvider(gps.GPS_PROVIDER);
//                gps.OpenGps();
//
//            } else {
//                Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//
//    @Override
//    public void onLocationChanged(Location location) {
//        double lat = location.getLatitude();
//        double lon = location.getLongitude();
//        tmapview.setLocationPoint(lat, lon);   // 위치이동
//        tmapview.setCenterPoint(lat, lon);  // 현재 위치 이동
//        tmapview.setIconVisibility(true);   // 현재위치로 표시될 아이콘을 표시
//        tmapview.setCompassMode(true);      // 나침반 모드
//        tmapview.setSightVisible(true);     // 보고있는 방향 표출
//        tmapview.setTrackingMode(true);
//
//
////        TMapPoint point = tmapgps.getLocation();
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//        // 위치 제공자가 비활성화되었을 때 호출됩니다.
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//        // 위치 제공자가 활성화되었을 때 호출됩니다.
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        // 위치 제공자의 상태가 변경되었을 때 호출됩니다.
//    }
//
//
//    public void tMapMakerCreate(){
//        TMapMarkerItem markerItem1 = new TMapMarkerItem();
//
//        TMapPoint tMapPoint1 = new TMapPoint(37.570841, 126.985302); // SKT타워
//
//        // 마커 아이콘
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_r_m_a);
//
//        markerItem1.setIcon(bitmap); // 마커 아이콘 지정
//        markerItem1.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
//        markerItem1.setTMapPoint( tMapPoint1 ); // 마커의 좌표 지정
//        markerItem1.setName("SKT타워"); // 마커의 타이틀 지정
//        tmapview.addMarkerItem("markerItem1", markerItem1); // 지도에 마커 추가
//
//        tmapview.setCenterPoint( 126.985302, 37.570841 );
//
//    }
//
//}
//
//
///***
// *
// 위 코드에서 `getLocation()` 메서드에서는 `LocationManager` 객체를 생성하고 `requestLocationUpdates()` 메서드를 호출하여 위치 정보 업데이트를 요청합니다.
// 이때 `LocationManager.GPS_PROVIDER`를 사용하여 GPS를 이용한 위치 정보 업데이트를 요청합니다.
// `requestLocationUpdates()` 메서드의 두 번째 인자는 위치 정보 업데이트를 요청하는 최소 시간 간격을, 세 번째 인자는 위치 정보 업데이트를 요청하는 최소 거리 간격을 나타냅니다.
// `onLocationChanged()` 메서드에서는 `Location` 객체를 통해 현재 위치 정보를 받아옵니다.
// 이후 `TMapPoint` 객체를 생성하여 현재 위치를 설정하고, `TMapView` 객체의 `setCenterPoint()` 메서드를 호출하여 현재 위치로 지도를 이동시킵니다.
// 또한, `onDestroy()` 메서드에서는 `LocationManager` 객체를 해제합니다. 이는 액티비티가 종료되기 전에 위치 정보 업데이트를 중단시키기 위함입니다.
//
// */
