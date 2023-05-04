package com.example.mainapp;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DataCursorAdapter_Activity_result_sub extends RecyclerView.Adapter<DataCursorAdapter_Activity_result_sub.DataViewHolder> {

    private DataViewHolder mViewHolder;

    private Context mContext;
    private Cursor mCursor;
    private DBHelper mDBHelper;


    private SQLiteDatabase db;



    public DataCursorAdapter_Activity_result_sub(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;

        mDBHelper = new DBHelper(context);
//        db = dbHelper.getWritableDatabase();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.activity_item_result_sub, parent, false);
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


        /*** 아래 코드에서 if문 작성 시 쿼리해서 나오는 순서대로 조건을 걸어줘야 인식함.
         순서를 안지킬 시 안그러면 패싱하여 데이터 null 발생 ***/

        if (mainarea.equals("해빙기")) {
            if (subarea.equals("보안감시설비")) {

                if (detailarea.equals("출입통제")) {    // detailarea에 출입통제인 건에 대해서만 값을 출력할 것 -> 하위는 조건문임
                    Cursor sub1_Cursor = mDBHelper.getDataHaebinggiBoanSubChurip();

                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                } else if (detailarea.equals("침투감지")) {
                    Cursor sub1_Cursor = mDBHelper.getDataHaebinggiBoanSubChimtu();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);

                } else if (detailarea.equals("화상감시/CCTV")) {
                    Cursor sub_Cursor = mDBHelper.getDataHaebinggiBoanSubHwasang();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }

            } else if (subarea.equals("무선통신설비")) {
                if (detailarea.equals("TRS 기지국")) {
                    Cursor sub2_Cursor = mDBHelper.getDataHaebinggiMuseonSubTRS();

                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                } else if (detailarea.equals("레이더 설비")) {
                    Cursor sub2_Cursor = mDBHelper.getDataHaebinggiMuseonSubRadar();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }

            } else if (subarea.equals("광전송장치")) {

                if (detailarea.equals("광케이블")) {
                    Cursor sub3_Cursor = mDBHelper.getDataHaebinggiGwangSubGwang();
                    DataCursorAdapter_Activity_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }
            } else if (subarea.equals("ICT실")) {

                if (detailarea.equals("ICT실")) {
                    Cursor sub4_Cursor = mDBHelper.getDataHaebinggiICTSubICT();
                    DataCursorAdapter_Activity_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }
            }
        }else if(mainarea.equals("여름철")) {
            if (subarea.equals("계통운영센터")){
                if (detailarea.equals("계통운영센터")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolGyetongSubGyetong();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }
            }else if(subarea.equals("전력제어설비")){
                if (detailarea.equals("원격소장치")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolJeonryeokSubWongyeok();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("계통보호 전송장치")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolJeonryeokSubGyetong();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("DAS 통신설비")){
                    Cursor sub3_Cursor = mDBHelper.getDataYeouremcheolJeonryeokSubDas();
                    DataCursorAdapter_Activity_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }
            }else if (subarea.equals("무선통신설비")){
                if (detailarea.equals("TRS 기지국")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolMuseonSubTRS();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("레이더 설비")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolMuseonSubRadar();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }else if(subarea.equals("광통신설비")){
                if (detailarea.equals("광케이블")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolGwangSubCable();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("광전송장치")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolGwangSubJangchi();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }else if(subarea.equals("비상통신설비")){
                if (detailarea.equals("계통운영통신시스템")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolBisnagSubGyetong();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("직통전화")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolBisnagSubJiktong();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }else if(subarea.equals("전력구통신")){
                if (detailarea.equals("WIFI 주장치")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubWifiJu();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("WIFI 중계기")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubWifijunggye();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("WIFI 전원부")){
                    Cursor sub3_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubWifijeonwon();
                    DataCursorAdapter_Activity_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }else if (detailarea.equals("WIFI 증폭기")){
                    Cursor sub4_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubWifijeungpok();
                    DataCursorAdapter_Activity_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }else if (detailarea.equals("WIFI 단말기")){
                    Cursor sub5_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubWifidanmal();
                    DataCursorAdapter_Activity_result_sub_sub sub5_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub5_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub5_areaAdapter);
                }else if (detailarea.equals("TRS")){
                    Cursor sub6_Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSubTRS();
                    DataCursorAdapter_Activity_result_sub_sub sub6_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub6_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub6_areaAdapter);
                }
            }else if(subarea.equals("전원 및 공조설비")){
                if (detailarea.equals("전원설비")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolJeonwonSubJeonwon();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("공조설비")){
                    Cursor sub2_Cursor = mDBHelper.getDataYeouremcheolJeonwonSubGongjo();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }else if(subarea.equals("ICT실")){
                if (detailarea.equals("ICT실")) {
                    Cursor sub1_Cursor = mDBHelper.getDataYeouremcheolICTSubICT();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }
            }

        }else if(mainarea.equals("추석")) {
            if (subarea.equals("보안감시설비")) {
                if (detailarea.equals("화상감시/CCTV")) {
                    Cursor sub1_Cursor = mDBHelper.getDataChuseokBoanSubHwasang();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("출입통제장치")){
                    Cursor sub2_Cursor = mDBHelper.getDataChuseokBoanSubChurip();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("울타리감지설비")){
                    Cursor sub3_Cursor = mDBHelper.getDataChuseokBoanSubUltari();
                    DataCursorAdapter_Activity_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }else if (detailarea.equals("침투감지설비")){
                    Cursor sub4_Cursor = mDBHelper.getDataChuseokBoanSubChimtu();
                    DataCursorAdapter_Activity_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }else if (detailarea.equals("기타")){
                    Cursor sub5_Cursor = mDBHelper.getDataChuseokBoanSubGita();
                    DataCursorAdapter_Activity_result_sub_sub sub5_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub5_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub5_areaAdapter);
                }
            }else if (subarea.equals("전원설비 · ICT실")) {
                if (detailarea.equals("ICT실/옥외설비")) {
                    Cursor sub1_Cursor = mDBHelper.getDataChuseokJeonwonSubICT();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("전원설비")){
                    Cursor sub2_Cursor = mDBHelper.getDataChuseokJeonwonSubJeonwon();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("공조설비")){
                    Cursor sub3_Cursor = mDBHelper.getDataChuseokJeonwonSubGongjo();
                    DataCursorAdapter_Activity_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }else if (detailarea.equals("기타")){
                    Cursor sub4_Cursor = mDBHelper.getDataChuseokJeonwonSubGita();
                    DataCursorAdapter_Activity_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }

            }else if (subarea.equals("비상통신설비")) {
                if (detailarea.equals("계통운영통신시스템(녹음장치)")) {
                    Cursor sub1_Cursor = mDBHelper.getDataChuseokBisangSubGyetong();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("직통전화(핫라인)")){
                    Cursor sub2_Cursor = mDBHelper.getDataChuseokBisangSubHotline();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("기타")){
                    Cursor sub2_Cursor = mDBHelper.getDataChuseokBisangSubGita();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }

        }else if(mainarea.equals("겨울철")) {
            if (subarea.equals("전력제어설비")) {
                if (detailarea.equals("계통운영센터")) {
                    Cursor sub1_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokSubGyetong();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("원격소장치")){
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokSubWongyeok();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("계통보호전송장치")){
                    Cursor sub3_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokSubGyetongboho();
                    DataCursorAdapter_Activity_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }else if (detailarea.equals("DAS 통신설비")){
                    Cursor sub4_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokSubDAS();
                    DataCursorAdapter_Activity_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }
            }else if (subarea.equals("전력구통신")) {
                if (detailarea.equals("WIFI 주장치")) {
                    Cursor sub1_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubWifiJu();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("WIFI 중계기")){
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubWifiJung();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }else if (detailarea.equals("WIFI 전원부")){
                    Cursor sub3_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubWifiJeonwon();
                    DataCursorAdapter_Activity_result_sub_sub sub3_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub3_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub3_areaAdapter);
                }else if (detailarea.equals("WIFI 증폭기")){
                    Cursor sub4_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubWifiJeungpok();
                    DataCursorAdapter_Activity_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }else if (detailarea.equals("WIFI 단말기")){
                    Cursor sub4_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubWifiDanmal();
                    DataCursorAdapter_Activity_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }else if (detailarea.equals("TRS")){
                    Cursor sub4_Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSubTRS();
                    DataCursorAdapter_Activity_result_sub_sub sub4_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub4_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub4_areaAdapter);
                }
            }else if (subarea.equals("비상통신설비")) {
                if (detailarea.equals("계통운영통신시스템")) {
                    Cursor sub1_Cursor = mDBHelper.getDataGyeoulcheolBisangSubGyetong();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                }else if (detailarea.equals("직통전화")){
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolBisangSubjiktong();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }else if (subarea.equals("보안감시설비")) {
                if (detailarea.equals("화상감시/CCTV")) {
                    Cursor sub1_Cursor = mDBHelper.getDataGyeoulcheolBoanSubHwasang();
                    DataCursorAdapter_Activity_result_sub_sub sub1_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub1_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub1_areaAdapter);
                } else if (detailarea.equals("출입통제")) {
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolBoanSubChurip();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                } else if (detailarea.equals("울타리 감지")) {
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolBoanSubUltari();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                } else if (detailarea.equals("침투감지")) {
                    Cursor sub2_Cursor = mDBHelper.getDataGyeoulcheolBoanSubChimtu();
                    DataCursorAdapter_Activity_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_Activity_result_sub_sub(mContext, sub2_Cursor);
                    holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);
                }
            }
        }


        /*** TODO : if - else 를 통해 쿼리를 각각 짜서 삽입해야함 -> DBHelper 클래스에 각 쿼리문 작성 필요 ***/



//        Cursor sub2_Cursor = mDBHelper.getDataSub1_3();
//
//
//        DataCursorAdapter_result_sub_sub sub2_areaAdapter = new DataCursorAdapter_result_sub_sub(mContext, sub2_Cursor);
//        holder.sub2_areaRecyclerView.setAdapter(sub2_areaAdapter);

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
            sub2_areaRecyclerView = itemView.findViewById(R.id.activity_item_result_sub_sub);
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
