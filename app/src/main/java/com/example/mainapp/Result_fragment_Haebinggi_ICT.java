package com.example.mainapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Result_fragment_Haebinggi_ICT extends Fragment {


    private static final int MODE_PRIVATE = 0;
    private RecyclerView mRecyclerView;
    private DataCursorAdapter_Activity_result mAdapter;
    private DBHelper mDBHelper;
    private ImageView result_ImageView;

    private SharedPreferences sharedPreferences;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_result1, container, false);

        mRecyclerView = view.findViewById(R.id.activity_item_result);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Context context = getActivity().getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("result_draw", Context.MODE_PRIVATE);

        // SharedPreferences.Editor를 사용하여 데이터 저장
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();





        // btn1 = 다음페이지
        Button btn1 = (Button) view.findViewById(R.id.frgmentBtn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View rootView = getActivity().findViewById(android.R.id.content);

//                View CaptureView = getView(); // 해당 코드는 현재 프래그먼 내의 뷰만 캡쳐하는 코드 -> 나중에 사용 시 아래 코드에서 get쪽 변경해야함
                Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(), rootView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(Color.WHITE);
                rootView.draw(canvas);

                String fileName = "설비점검 결과 보고서.png";

                File file = new File(getContext().getExternalFilesDir(null), fileName);


                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 이메일 보내기
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]
                        {"hyunjoo.song@kepco.co.kr"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[사업소명] ICT설비점검 결과 송부");  // 메일 제목 (사업소명을 변수로 두고 설정해야함)
                emailIntent.putExtra(Intent.EXTRA_TEXT, "[사업소명] ICT설비점검 결과입니다.");  // 메일 내용

                // 이미지파일 첨부 코드
                File resultDir = new File("storage/self/primary/Android/data/com.example.mainapp/files/설비점검 결과 보고서.png");

                Log.d("resultFile", String.valueOf(resultDir));
                Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".fileprovider", resultDir); // 이미지 결과 파일
                Log.d("resultFile", String.valueOf(uri));
                emailIntent.setType("image/*");

                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

                startActivity(Intent.createChooser(emailIntent, "ICT 설비점검 결과 보고서 메일 보내기"));



            }
        });



        result_ImageView = view.findViewById(R.id.result_image);
        result_ImageView.setImageBitmap(null);



        // ImageView에 Bitmap 설정
        result_ImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CustomDrawView.class);
                startActivity(intent);

//
//                Button drawBtn2 = (Button) view.findViewById(R.id.drawBtn2);
//                drawBtn2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////
////                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Result_fragment1()).commit();
//                        Bitmap bitmap = resultDrawView.getBitmap();
//                        ImageView result_ImageView = (ImageView) view.findViewById(R.id.result_image);
//                        result_ImageView.setImageBitmap(bitmap);
//                        getActivity().getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.fragment_container, new Result_fragment1())
//                                .addToBackStack(null)
//                                .commit();
//
//                        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//                            @Override
//                            public void onBackStackChanged() {
//                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
//                                if (currentFragment instanceof Result_fragment1) {
//                                    ImageView result_ImageView = (ImageView) currentFragment.getView().findViewById(R.id.result_image);
//                                    result_ImageView.setImageBitmap(bitmap);
//                                }
//                            }
//                        });
//
//                    }
//                });




                }
        });


        mDBHelper = new DBHelper(getContext());

        // 데이터베이스를 열고 커서를 가져옴
        Cursor cursor = mDBHelper.getDataHaebinggiICT();


        // 어댑터를 생성하고 커서를 전달하여 데이터를 로드
        mAdapter = new DataCursorAdapter_Activity_result(getContext(), cursor);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // DB 연결 해제
//        mDBHelper.close();
    }

    private Bitmap stringToBitmap(String bitmapString) {
        try {
            byte[] byteArray = Base64.decode(bitmapString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Context context = getActivity().getApplicationContext();

        sharedPreferences = context.getSharedPreferences("result_draw", Context.MODE_PRIVATE);
        String bitmapString = sharedPreferences.getString("result_draw", null);
//        Log.d("bitmapString", bitmapString);

        if (bitmapString != null) {
            byte[] decodedString = Base64.decode(bitmapString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            result_ImageView.setImageBitmap(bitmap);
        }

    }
}


