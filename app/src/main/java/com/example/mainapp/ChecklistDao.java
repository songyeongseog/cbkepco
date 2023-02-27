package com.example.mainapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// 데이터 어댑터 객체 클래스


@Dao
public interface ChecklistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Checklist checklist);

    @Update
    void update(Checklist checklist);

    @Delete
    void delete(Checklist checklist);

    @Query("SELECT * FROM checklist")
    LiveData<List<Checklist>> getAllChecklists();

//    @Query("SELECT * FROM checklist WHERE _id = :_id")
//    Checklist getChecklistById(int _id);



    /*** 여기부터 쿼리문 작성 시작 ***/
//    @Query("SELECT * FROM checklist " +
//            "WHERE mainarea LIKE '해빙기' " +
//             "AND + subarea LIKE '보안감시설비'")
    @Query("SELECT * FROM checklist WHERE mainarea = :mainarea")

//    List<Checklist> getChecklistById(String mainarea, String subarea);
    List<Checklist> getChecklistById(String mainarea);

}
