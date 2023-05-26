package com.example.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapInfo;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.poi_item.TMapPOIItem;


import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class MapActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {
    TMapGpsManager tMapGPS = null;
    TMapView tMapView;
    Context context;

    private TMapDBHelper dbHelper;
    private List<Location> locationList = new ArrayList<>();

    private String tMapApiKey = "VQQGpOnt9S2L8OlXRePml3LvztIGfC2LaZ90P9h0";

    TMapMarkerItem markerItem = new TMapMarkerItem();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tmap);
        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey(tMapApiKey);
        linearLayoutTmap.addView(tMapView);

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


        markerItem.setAutoCalloutVisible(true);


        ArrayList<TMapPoint> alTMapPoint = new ArrayList<TMapPoint>();


        // DB에서 가져온 위치 데이터를 마커로 표시하기
        for (int i = 0; i < locationList.size(); i++) {
            Location loc = locationList.get(i);
            TMapPoint tMapPoint = new TMapPoint(loc.getLatitude(), loc.getLongitude());
            Bitmap markerImage = BitmapFactory.decodeResource(getResources(), R.drawable.marker2);

            TMapMarkerItem markerItem = new TMapMarkerItem();
            markerItem.setTMapPoint(tMapPoint);
            markerItem.setVisible(TMapMarkerItem.VISIBLE);
            markerItem.setIcon(markerImage);
            markerItem.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
            markerItem.setCalloutTitle("marker_" + i);  // 마커 제목 설정

            markerItem.setAutoCalloutVisible(true);
            String markerId = "marker_" + i; // 고유한 마커 식별자 생성
            alTMapPoint.add(tMapPoint);


            tMapView.addMarkerItem(markerId, markerItem);
            Log.d("포인트", String.valueOf(tMapPoint));
        }

        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {

            @Override
            public boolean onPressUpEvent(ArrayList arrayList, ArrayList poilist, TMapPoint tMapPoint, PointF pointf) {

                return false;
            }

            @Override
            public boolean onPressEvent(ArrayList arrayList, ArrayList poilist, TMapPoint point, PointF pointf) {


                if (arrayList != null && !arrayList.isEmpty()) {
                    // 클릭한 마커의 정보를 가져옵니다.
                    TMapMarkerItem markerItemData = (TMapMarkerItem) arrayList.get(0);
                    String markerId = markerItemData.getID(); // 마커 ID를 가져옵니다.


                    // 마커 ID를 토스트 메시지로 출력합니다.
                    Toast.makeText(getApplicationContext(), "마커 ID: " + markerId, Toast.LENGTH_SHORT).show();


                    //Todo 바텀 다이얼로그 시트 생성

                    // 바텀시트 다이얼로그 생성
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                    // 레이아웃 파일 인플레이션
                    View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, null);
                    // 바텀시트 다이얼로그에 레이아웃 설정
                    bottomSheetDialog.setContentView(view);
//                    bottomSheetDialog.show();

                }
                return false;
            }
        });

        tMapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
            @Override
            public void onCalloutRightButton(TMapMarkerItem markerItem) {
                Log.d("마커ID", markerItem.getID());
                Toast.makeText(getApplicationContext(), "마커를 클릭했습니다.", Toast.LENGTH_SHORT).show();

            }
        });


        /***
         * Tmap api를 사용하여 마커간 선을 만드는 코드
         */

        TMapPolyLine tMapPolyLine = new TMapPolyLine();
        TMapData tMapData = new TMapData();


        TMapPoint firstElement = alTMapPoint.get(0);    // DB 내 Top 1 값 가져오기
        TMapPoint lastElement = alTMapPoint.get(alTMapPoint.size() - 1);    // DB 내 가장 마지막 값 가져오기

        TMapPoint tMapPointStart = new TMapPoint(firstElement.getLatitude(), firstElement.getLongitude());
        TMapPoint tMapPointEnd = new TMapPoint(lastElement.getLatitude(), lastElement.getLongitude());

        double tMapPointStart_Lat = firstElement.getLatitude();
        double tMapPointStart_Lon = firstElement.getLongitude();
        Log.d("시작Lat", String.valueOf(tMapPointStart_Lat));
        Log.d("시작Lon", String.valueOf(tMapPointStart_Lon));


        tMapPolyLine.setLineColor(Color.BLACK);
        tMapPolyLine.setLineWidth(10);
        for (int j = 0; j < alTMapPoint.size(); j++) {
            tMapPolyLine.addLinePoint(alTMapPoint.get(j));
            Log.d("시작값", String.valueOf(alTMapPoint.get(0)));
            Log.d("종료값", String.valueOf(alTMapPoint.get(alTMapPoint.size()-1)));
        }
        tMapView.addTMapPolyLine("Line1", tMapPolyLine);



        try {
            tMapData.findMultiPointPathData(tMapPointStart, tMapPointEnd, alTMapPoint, 0,
                    new TMapData.FindPathDataListenerCallback() {
                        @Override
                        public void onFindPathData(TMapPolyLine carPolyLine) {
                            carPolyLine.setLineColor(Color.BLUE);
                            carPolyLine.setLineWidth(30);
                            tMapView.setCenterPoint(tMapPointStart_Lon, tMapPointStart_Lat);  // 위치 이동
                            tMapView.zoomToTMapPoint(tMapPointStart, tMapPointEnd);    // 줌레벨 조정
                            Log.d("성공2", "성공2");
                            tMapView.addTMapPath(carPolyLine);
                        }
                    });
        }catch(Exception e) {
            e.printStackTrace();
            while(true) {
                tMapData.findMultiPointPathData(tMapPointStart, tMapPointEnd, alTMapPoint, 0,
                        new TMapData.FindPathDataListenerCallback() {
                            @Override
                            public void onFindPathData(TMapPolyLine carPolyLine) {
                                carPolyLine.setLineColor(Color.BLUE);
                                carPolyLine.setLineWidth(30);
                                tMapView.setCenterPoint(tMapPointStart_Lon, tMapPointStart_Lat);  // 위치 이동
                                tMapView.zoomToTMapPoint(tMapPointStart, tMapPointEnd);    // 줌레벨 조정
                                tMapView.addTMapPath(carPolyLine);
                            }
                        }
                );
                int i = 1;

                if (i == 1) {
                    break;
                }
            }
            Log.d("실패2","실패2");
        }






        /*** 여기까지
         *
         */

//        TMapPoint tMapPointStart = new TMapPoint(37.570841, 126.985302); // SKT타워(출발지)
//        TMapPoint tMapPointEnd = new TMapPoint(37.551135, 126.988205); // N서울타워(목적지)
//
//        try {
//            tMapData.findPathData(tMapPointStart, tMapPointEnd, new TMapData.FindPathDataListenerCallback() {
//                @Override
//                public void onFindPathData(TMapPolyLine resultPolyLine) {
//                    resultPolyLine.setLineColor(Color.RED);
//                    resultPolyLine.setLineWidth(30);
//                    tMapView.addTMapPath(resultPolyLine);
////                    tMapView.zoomToTMapPoint(tMapPointStart, tMapPointEnd);
//                    Log.d("성공", "성공");
//                }
//            });
//            Log.d("성공","성공");
//
//        }catch(Exception e) {
//            e.printStackTrace();
//            Log.d("실패","실패");
//        }


        Log.d("DB 확인",String.valueOf(locationList));
//        tmapview.invalidate();  // 강제로 맵을 다시그려주는 메소드 -> 마커가 추가되거나 삭제되면 메소드를 호출해야 함.

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
            tMapView.setLocationPoint(longitude, latitude); // 현재위치로 표시될 좌표의 위도, 경도를 설정
            tMapView.setIconVisibility(true);
            tMapView.setCompassMode(true);      // 나침반 모드
            tMapView.setSightVisible(true);     // 보고있는 방향 표출
//            tMapView.setCenterPoint(longitude, latitude, true); // 현재 위치로 이동
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TMapMarkerItem markerItem = new TMapMarkerItem();

        markerItem.setAutoCalloutVisible(true);
    }


}


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
