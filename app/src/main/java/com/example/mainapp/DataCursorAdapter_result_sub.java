package com.example.mainapp;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class DataCursorAdapter_result_sub extends RecyclerView.Adapter<DataCursorAdapter_result_sub.DataViewHolder> {

    private DataViewHolder mViewHolder;

    private Context mContext;
    private Cursor mCursor;
    private DBHelper mDBHelper;


    private SQLiteDatabase db;

//    List<String> detailareaList = new ArrayList<>();




    public DataCursorAdapter_result_sub(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;

        mDBHelper = new DBHelper(context);
//        db = dbHelper.getWritableDatabase();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclerview_item_result_sub, parent, false);
        return new DataViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String mainarea = mCursor.getString(mCursor.getColumnIndexOrThrow("mainarea"));
        // 커서에서 데이터를 가져와서 뷰에 설정
        String subarea = mCursor.getString(mCursor.getColumnIndexOrThrow("subarea"));
        String detailarea = mCursor.getString(mCursor.getColumnIndexOrThrow("detailarea"));
        holder.detailareaTextView.setText(detailarea);

//        String detailareaData = holder.detailareaTextView.getText().toString();
//        detailareaList.add(detailarea);



//        Log.d("detailareaData" , detailareaData);
//        Log.d("list값", String.valueOf(detailareaList));
//        String get1 = detailareaList.get(0);

        /*** 해빙기 구분 ***/
        if (mainarea.equals("해빙기")) {
            /*** 아래 코드에서 if문 작성 시 쿼리해서 나오는 순서대로 조건을 걸어줘야 인식함.
             순서를 안지킬 시 안그러면 패싱하여 데이터 null 발생 ***/
            if (subarea.equals("보안감시설비")) {

                if (detailarea.equals("출입통제")) {    // detailarea에 출입통제인 건에 대해서만 값을 출력할 것 -> 하위는 조건문임
                    Cursor sub1_Cursor = mDBHelper.getDataHaebinggiBoanSubChurip();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);

                } else if (detailarea.equals("침투감지")) {
                    Cursor sub1_Cursor = mDBHelper.getDataHaebinggiBoanSubChimtu();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);

                } else if (detailarea.equals("화상감시/CCTV")) {
                    Cursor sub_Cursor = mDBHelper.getDataHaebinggiBoanSubHwasang();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }

            } else if (subarea.equals("무선통신설비")) {
                if (detailarea.equals("TRS 기지국")) {
                    Cursor sub2_Cursor = mDBHelper.getDataHaebinggiMuseonSubTRS();

                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                } else if (detailarea.equals("레이더 설비")) {
                    Cursor sub2_Cursor = mDBHelper.getDataHaebinggiMuseonSubRadar();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }

            } else if (subarea.equals("광전송장치")) {

                if (detailarea.equals("광케이블")) {
                    Cursor sub3_Cursor = mDBHelper.getDataHaebinggiGwangSubGwang();
                    DataCursorAdapter_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }
            } else if (subarea.equals("ICT실")) {

                if (detailarea.equals("ICT실")) {
                    Cursor sub4_Cursor = mDBHelper.getDataHaebinggiICTSubICT();
                    DataCursorAdapter_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }
            }

            /*** 여름철 구분 ***/
        }else if(mainarea.equals("여름철")) {
            if (subarea.equals("계통운영센터")){
                if (detailarea.equals("계통운영센터")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolGyetongSubGyetong();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }
            }else if(subarea.equals("전력제어설비")){
                if (detailarea.equals("원격소장치")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolJeonryeokSubWongyeok();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("계통보호 전송장치")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolJeonryeokSubGyetong();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("DAS 통신설비")){
                    Cursor sub3_Cursor = mDBHelper.getDataYeouremcheolJeonryeokSubDas();
                    DataCursorAdapter_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }
            }else if (subarea.equals("무선통신설비")){
                if (detailarea.equals("TRS 기지국")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolMuseonSubTRS();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("레이더 설비")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolMuseonSubRadar();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }else if(subarea.equals("광통신설비")){
                if (detailarea.equals("광케이블")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolGwangSubCable();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("광전송장치")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolGwangSubJangchi();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }else if(subarea.equals("비상통신설비")){
                if (detailarea.equals("계통운영통신시스템")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolBisnagSubGyetong();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("직통전화")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolBisnagSubJiktong();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }else if(subarea.equals("전력구통신")){
                if (detailarea.equals("WIFI 주장치")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubWifiJu();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("WIFI 중계기")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubWifijunggye();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("WIFI 전원부")){
                    Cursor sub3_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubWifijeonwon();
                    DataCursorAdapter_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }else if (detailarea.equals("WIFI 증폭기")){
                    Cursor sub4_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubWifijeungpok();
                    DataCursorAdapter_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }else if (detailarea.equals("WIFI 단말기")){
                    Cursor sub5_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubWifidanmal();
                    DataCursorAdapter_result_sub_sub sub5_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub5_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub5_areaAdapter);
                }else if (detailarea.equals("TRS")){
                    Cursor sub6_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubTRS();
                    DataCursorAdapter_result_sub_sub sub6_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub6_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub6_areaAdapter);
                }
            }else if(subarea.equals("전원 및 공조설비")){
                if (detailarea.equals("전원설비")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolJeonwonSubJeonwon();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("공조설비")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolJeonwonSubGongjo();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }else if(subarea.equals("ICT실")){
                if (detailarea.equals("ICT실")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolICTSubICT();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }
            }
        }else if(mainarea.equals("추석")) {
            if (subarea.equals("보안감시설비")) {
                if (detailarea.equals("화상감시/CCTV")) {
                    Cursor sub1_Cursor = mDBHelper.getDataChuseokBoanSubHwasang();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("출입통제장치")){
                    Cursor sub2_Cursor = mDBHelper.getDataChuseokBoanSubChurip();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("울타리감지설비")){
                    Cursor sub3_Cursor = mDBHelper.getDataChuseokBoanSubUltari();
                    DataCursorAdapter_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }else if (detailarea.equals("침투감지설비")){
                    Cursor sub4_Cursor = mDBHelper.getDataChuseokBoanSubChimtu();
                    DataCursorAdapter_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }else if (detailarea.equals("기타")){
                    Cursor sub5_Cursor = mDBHelper.getDataChuseokBoanSubGita();
                    DataCursorAdapter_result_sub_sub sub5_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub5_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub5_areaAdapter);
                }
            }else if (subarea.equals("전원설비 · ICT실")) {
                if (detailarea.equals("ICT실/옥외설비")) {
                    Cursor sub1_Cursor = mDBHelper.getDataChuseokJeonwonSubICT();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("전원설비")){
                    Cursor sub2_Cursor = mDBHelper.getDataChuseokJeonwonSubJeonwon();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("공조설비")){
                    Cursor sub3_Cursor = mDBHelper.getDataChuseokJeonwonSubGongjo();
                    DataCursorAdapter_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }else if (detailarea.equals("기타")){
                    Cursor sub4_Cursor = mDBHelper.getDataChuseokJeonwonSubGita();
                    DataCursorAdapter_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }

            }else if (subarea.equals("비상통신설비")) {
                if (detailarea.equals("계통운영통신시스템(녹음장치)")) {
                    Cursor sub1_Cursor = mDBHelper.getDataChuseokBisangSubGyetong();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("직통전화(핫라인)")){
                    Cursor sub2_Cursor = mDBHelper.getDataChuseokBisangSubHotline();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("기타")){
                    Cursor sub2_Cursor = mDBHelper.getDataChuseokBisangSubGita();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }
        }else if(mainarea.equals("겨울철")) {
            if (subarea.equals("전력제어설비")) {
                if (detailarea.equals("계통운영센터")) {
                    Cursor sub1_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokSubGyetong();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("원격소장치")){
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokSubWongyeok();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("계통보호전송장치")){
                    Cursor sub3_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokSubGyetongboho();
                    DataCursorAdapter_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }else if (detailarea.equals("DAS 통신설비")){
                    Cursor sub4_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokSubDAS();
                    DataCursorAdapter_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }
            }else if (subarea.equals("전력구통신")) {
                if (detailarea.equals("WIFI 주장치")) {
                    Cursor sub1_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubWifiJu();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("WIFI 중계기")){
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubWifiJung();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("WIFI 전원부")){
                    Cursor sub3_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubWifiJeonwon();
                    DataCursorAdapter_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }else if (detailarea.equals("WIFI 증폭기")){
                    Cursor sub4_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubWifiJeungpok();
                    DataCursorAdapter_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }else if (detailarea.equals("WIFI 단말기")){
                    Cursor sub4_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubWifiDanmal();
                    DataCursorAdapter_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }else if (detailarea.equals("TRS")){
                    Cursor sub4_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubTRS();
                    DataCursorAdapter_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }
            }else if (subarea.equals("비상통신설비")) {
                if (detailarea.equals("계통운영통신시스템")) {
                    Cursor sub1_Cursor = mDBHelper.getDataGyeoulcheolBisangSubGyetong();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("직통전화")){
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolBisangSubjiktong();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }else if (subarea.equals("보안감시설비")) {
                if (detailarea.equals("화상감시/CCTV")) {
                    Cursor sub1_Cursor = mDBHelper.getDataGyeoulcheolBoanSubHwasang();
                    DataCursorAdapter_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                } else if (detailarea.equals("출입통제")) {
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolBoanSubChurip();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                } else if (detailarea.equals("울타리 감지")) {
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolBoanSubUltari();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                } else if (detailarea.equals("침투감지")) {
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolBoanSubChimtu();
                    DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    // ViewHolder 클래스
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView detailareaTextView;
        public RecyclerView sub2_areaRecyclerView;


        // 컬럼 조회 뷰홀더

        public DataViewHolder(View itemView) {
            super(itemView);
            detailareaTextView = itemView.findViewById(R.id.detailarea_result);
            sub2_areaRecyclerView = itemView.findViewById(R.id.recyclerview_result_sub_sub);
            sub2_areaRecyclerView.setLayoutManager(new LinearLayoutManager(sub2_areaRecyclerView.getContext()));
        }
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
