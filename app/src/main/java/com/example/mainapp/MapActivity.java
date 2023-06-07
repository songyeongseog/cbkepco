package com.example.mainapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.poi_item.TMapPOIItem;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback, ResultBottomSheetDialog.BottomSheetListener {
    TMapGpsManager tMapGPS = null;
    TMapView tMapView;
    Context context;

    private TMapDBHelper dbHelper;
    private List<Location> locationList = new ArrayList<>();

    private String tMapApiKey = "VQQGpOnt9S2L8OlXRePml3LvztIGfC2LaZ90P9h0";



    private DrawerLayout drawerLayout;
    private View drawerView;

    TMapMarkerItem markerItem = new TMapMarkerItem();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tmap);


        /*** ★★★ 네비게이션 관련 ★★★*/
        // todo 여기서부턴 네비게이션 코드

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

        Button navi_open = findViewById(R.id.navi_open);
        navi_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

//        drawerLayout.setDrawerListener(listener);
        View drawerView = (View) findViewById(R.id.drawer_layout);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        // 빈공간을 클릭 시 네비게이션 닫히기
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float x = event.getX();
                    float y = event.getY();
                    if (x > drawerView.getWidth() || y > drawerView.getHeight()) {
                        drawerLayout.closeDrawer(drawerView);
                        return true;
                    }
                }
                return false;
            }
        });

        /*** kepCIT 드롭다운 메뉴 */

        Spinner kepCIT_spinner = findViewById(R.id.spinner_kepCIT);


        // 드롭다운에 표시할 항목 데이터
        String[] kepCIT_items = {"점검구간을 선택하세요.","직할#1", "직할#2", "동청주#1", "동청주#2", "진천#1", "진천#2", "보은#1", "보은#2",
                            "옥천#1", "옥천#2", "증평괴산#1","증평괴산#2", "괴산S/C", "음성#1", "음성#2", "영동#1", "영동#2",
                            "충주#1", "충주#2", "제천#1", "제천#2", "단양#1", "단양#2", "수안보#1", "수안보#2", "자재창고"};

        // 어댑터 생성
        ArrayAdapter<String> kepCIT_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, kepCIT_items);

        // 어댑터 설정
        kepCIT_spinner.setAdapter(kepCIT_adapter);

        kepCIT_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                // 선택된 값에 따라 원하는 동작 수행
                if (selectedItem.equals("직할#1")) {
                    Toast.makeText(MapActivity.this, "쿼리 준비 중입니다.", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("직할#2")) {
                    Toast.makeText(MapActivity.this, "쿼리 준비 중입니다.", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("동청주#1")) {
                    locationList = dbHelper.getTestLocations();

                } else if (selectedItem.equals("동청주#2")) {

                } else if (selectedItem.equals("진천#1")) {

                } else if (selectedItem.equals("진천#1")) {

                }

                Toast.makeText(MapActivity.this, "선택된 항목: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 선택된 항목이 없을 때의 동작
            }
        });


        /*** ★★★ Tmap 관련 ★★★*/
        // todo 여기서부턴 tmap 쪽 코드
        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.linearLayoutTmap);
//        RelativeLayout linearLayoutTmap = (RelativeLayout) findViewById(R.id.linearLayoutTmap);

        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey(tMapApiKey);

        TMapTapi tmaptapi = new TMapTapi(this);
        tmaptapi.setSKTMapAuthentication (tMapApiKey);


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
//        locationList = dbHelper.getAllLocations();
        locationList = dbHelper.getTestLocations();


        markerItem.setAutoCalloutVisible(true);


        ArrayList<TMapPoint> alTMapPoint = new ArrayList<TMapPoint>();

        Log.d("locationList",String.valueOf(locationList));
        // DB에서 가져온 위치 데이터를 마커로 표시하기
        for (int i = 0; i < locationList.size(); i++) {
            Location loc = locationList.get(i);

            String[] markerInfo = dbHelper.getSpecificColumnValue(loc.getLongitude(), loc.getLatitude());
            /***
             * powerName[0] = 대분류(mainGroup)
             * powerName[1] = 소분류(subGroup)
             * powerName[2] = 전산화번호(powerNumber)
             * powerName[3] = 전주명(powerName)
             */

            String mainGroup = markerInfo[0]; // 대분류
            String subGroup = markerInfo[1]; // 중분류
            String powerNumber = markerInfo[2]; // 전산화번호
            String powerName = markerInfo[3]; // 전주명, 고유한 마커 식별자 생성


            String markerId = markerInfo[3]; // 전주명, 고유한 마커 식별자 생성
            Log.d("powerName", String.valueOf(markerInfo));


            Log.d("loc",String.valueOf(loc));
            TMapPoint tMapPoint = new TMapPoint(loc.getLatitude(), loc.getLongitude());
            Bitmap markerImage = BitmapFactory.decodeResource(getResources(), R.drawable.marker2);

            TMapMarkerItem markerItem = new TMapMarkerItem();


            markerItem.setTMapPoint(tMapPoint);
            markerItem.setVisible(TMapMarkerItem.VISIBLE);
            markerItem.setIcon(markerImage);
            markerItem.setPosition(0.5f, 1.0f); // 마커의 중심점을 중앙, 하단으로 설정
            markerItem.setName(mainGroup + ":" + subGroup + ":"+ powerNumber + ":" + powerName);
            markerItem.setCalloutTitle(markerInfo[3]);  // 마커 제목 설정
            markerItem.setAutoCalloutVisible(true);



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
            public boolean onPressEvent(ArrayList arrayList, ArrayList poilist, TMapPoint tMappoint, PointF pointf) {


                if (arrayList != null && !arrayList.isEmpty()) {
                    // 클릭한 마커의 정보를 가져옵니다.
                    TMapMarkerItem markerItemData = (TMapMarkerItem) arrayList.get(0);

                    String markerInfo = markerItemData.getName(); // 마커의 모든 정보를 가져옵니다.

                    String markerId = markerItemData.getID(); // 마커 ID를 가져옵니다.


                    // 마커 ID를 토스트 메시지로 출력합니다.
                    Toast.makeText(getApplicationContext(), "마커 ID: " + markerId, Toast.LENGTH_SHORT).show();

                    // 바텀시트다이어로그 호출
//                    showDialog();     //메소드 코드 현재 미사용
                    ResultBottomSheetDialog dialog = new ResultBottomSheetDialog();
                    dialog.show(getSupportFragmentManager(), "Bottom");

                    Bundle bundle = new Bundle();
//                    bundle.putString("mainGroup", mainGroup);
//                    bundle.putString("subGroup", subGroup);
//                    bundle.putString("powerNumber", powerNumber);
                    bundle.putString("markerInfo", markerInfo);
                    Log.d("번들1", markerInfo);
                    dialog.setArguments(bundle);

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


        tmaptapi.setOnAuthenticationListener(new TMapTapi.OnAuthenticationListenerCallback() {
            @Override
            public void SKTMapApikeySucceed() {
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
            }
            @Override
            public void SKTMapApikeyFailed(String errorMsg) {
                Toast.makeText(getApplicationContext(), "경로 찾기에 실패하였습니다.", Toast.LENGTH_SHORT).show();

            }
        });


        /*** ★★★ 네비게이션 관련 ★★★*/
        //네비게이션 관련 함수
        DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };

        drawerLayout.addDrawerListener(listener);








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




    // 바텀다이얼로그 호출
    public void showDialog() {

        ResultBottomSheetDialog dialog = new ResultBottomSheetDialog();
        dialog.show(getSupportFragmentManager(), "Bottom");

        Bundle bundle = new Bundle();
        String test = markerItem.getID();
        bundle.putString("powerName", markerItem.getID());
        Log.d("번들1", test);
        dialog.setArguments(bundle);
    }


    // 바텀다이얼로그 버튼 클릭 이벤트
    @Override
    public void onButtonClicked(String text) {

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
