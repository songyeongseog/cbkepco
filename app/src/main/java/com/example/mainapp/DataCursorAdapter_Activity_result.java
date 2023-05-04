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


public class DataCursorAdapter_Activity_result extends RecyclerView.Adapter<DataCursorAdapter_Activity_result.DataViewHolder> {

    private DataViewHolder mViewHolder;

    private Context mContext;
    private Cursor mCursor;

    private DBHelper mDBHelper;


    private SQLiteDatabase db;


    public DataCursorAdapter_Activity_result(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mDBHelper = new DBHelper(context);

//        db = mDBHelper.getWritableDatabase();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.activity_item_result, parent, false);
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
        holder.subareaTextView.setText(subarea);

        if(mainarea.equals("해빙기")) {
            if (subarea.equals("보안감시설비")) {
                Cursor subCursor = mDBHelper.getDataHaebinggiBoanSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter = new DataCursorAdapter_Activity_result_sub(mContext, subCursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter);
            } else if (subarea.equals("무선통신설비")) {
                Cursor sub2Cursor = mDBHelper.getDataHaebinggiMuseonSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter2 = new DataCursorAdapter_Activity_result_sub(mContext, sub2Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter2);
            }else if (subarea.equals("광전송장치")) {
                Cursor sub3Cursor = mDBHelper.getDataHaebinggiGwangSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter3 = new DataCursorAdapter_Activity_result_sub(mContext, sub3Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter3);
            }else if (subarea.equals("ICT실")) {
                Cursor sub4Cursor = mDBHelper.getDataHaebinggiICTSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter4 = new DataCursorAdapter_Activity_result_sub(mContext, sub4Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter4);
            }


        }else if(mainarea.equals("여름철")){
            if (subarea.equals("계통운영센터")) {
                Cursor subCursor = mDBHelper.getDataYeouremcheolGyetongSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter = new DataCursorAdapter_Activity_result_sub(mContext, subCursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter);
            } else if (subarea.equals("전력제어설비")) {
                Cursor sub2Cursor = mDBHelper.getDataYeouremcheolJeonryeokSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter2 = new DataCursorAdapter_Activity_result_sub(mContext, sub2Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter2);
            }else if (subarea.equals("무선통신설비")) {
                Cursor sub3Cursor = mDBHelper.getDataYeouremcheolMuseonSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter3 = new DataCursorAdapter_Activity_result_sub(mContext, sub3Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter3);
            }else if (subarea.equals("광통신설비")) {
                Cursor sub4Cursor = mDBHelper.getDataYeouremcheolGwangSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter4 = new DataCursorAdapter_Activity_result_sub(mContext, sub4Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter4);
            }else if (subarea.equals("비상통신설비")) {
                Cursor sub5Cursor = mDBHelper.getDataYeouremcheolBisangSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter5 = new DataCursorAdapter_Activity_result_sub(mContext, sub5Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter5);
            }else if (subarea.equals("전력구통신")) {
                Cursor sub6Cursor = mDBHelper.getDataYeouremcheolJeonryeokguSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter6 = new DataCursorAdapter_Activity_result_sub(mContext, sub6Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter6);
            }else if (subarea.equals("전원 및 공조설비")) {
                Cursor sub7Cursor = mDBHelper.getDataYeouremcheolJeonwonSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter7 = new DataCursorAdapter_Activity_result_sub(mContext, sub7Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter7);
            }else if (subarea.equals("ICT실")) {
                Cursor sub8Cursor = mDBHelper.getDataYeouremcheolICTSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter8 = new DataCursorAdapter_Activity_result_sub(mContext, sub8Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter8);
            }
        }else if(mainarea.equals("추석")){
            // Todo : 추석 쿼리문 작성

            if (subarea.equals("보안감시설비")) {
                Cursor subCursor = mDBHelper.getDataChuseokBoanSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter = new DataCursorAdapter_Activity_result_sub(mContext, subCursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter);
            } else if (subarea.equals("전원설비 · ICT실")) {
                Cursor sub2Cursor = mDBHelper.getDataChuseokJeonwonSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter2 = new DataCursorAdapter_Activity_result_sub(mContext, sub2Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter2);
            }else if (subarea.equals("비상통신설비")) {
                Cursor sub2Cursor = mDBHelper.getDataChuseokBisangSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter3 = new DataCursorAdapter_Activity_result_sub(mContext, sub2Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter3);
            }

        }else if(mainarea.equals("겨울철")){
            if (subarea.equals("전력제어설비")) {
                Cursor subCursor = mDBHelper.getDataGyeoulcheolJeonryeokSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter = new DataCursorAdapter_Activity_result_sub(mContext, subCursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter);
            } else if (subarea.equals("전력구통신")) {
                Cursor sub2Cursor = mDBHelper.getDataGyeoulcheolJeonryeokguSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter2 = new DataCursorAdapter_Activity_result_sub(mContext, sub2Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter2);
            }else if (subarea.equals("비상통신설비")) {
                Cursor sub3Cursor = mDBHelper.getDataGyeoulcheolBisangSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter3 = new DataCursorAdapter_Activity_result_sub(mContext, sub3Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter3);
            }else if (subarea.equals("보안감시설비")) {
                Cursor sub4Cursor = mDBHelper.getDataGyeoulcheolBoanSub();
                DataCursorAdapter_Activity_result_sub subareaAdapter4 = new DataCursorAdapter_Activity_result_sub(mContext, sub4Cursor);
                holder.subareaRecyclerView.setAdapter(subareaAdapter4);
            }        }
//        Cursor subCursor = mDBHelper.getDataHaebinggiBoanSub();

//        DataCursorAdapter_Activity_result_sub subareaAdapter = new DataCursorAdapter_Activity_result_sub(mContext, subCursor);
//        holder.subareaRecyclerView.setAdapter(subareaAdapter);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    // ViewHolder 클래스
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView subareaTextView;
        public RecyclerView subareaRecyclerView;

        // 컬럼 조회 뷰홀더

        public DataViewHolder(View itemView) {
            super(itemView);
            subareaTextView = itemView.findViewById(R.id.subarea_result);
            subareaRecyclerView = itemView.findViewById(R.id.activity_item_result_sub);
            subareaRecyclerView.setLayoutManager(new LinearLayoutManager(subareaRecyclerView.getContext()));


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
