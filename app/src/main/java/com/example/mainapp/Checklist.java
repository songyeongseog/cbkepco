package com.example.mainapp;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


// 데이터항목 클래스


/***
 * database Class
 * Data Entity를 참조해서 물리적으로 데이터베이스를 만듬
 *
 *
 * Data Entity
 * 이곳에 테이블 정보를 기록합니다..  이름, 연락처, 주소
 *
 * Data Access Object
 * 여기서는 데이타를 가져오고, 저장하는 것들을 정의 합니다....


 **/

@Entity(tableName = "checklist")
public class Checklist {


    @PrimaryKey(autoGenerate = true)

    private int id;
    private String mainarea;
    private String subarea;
    private String detailarea;
    private String list;
    private String result;
    private String edittext;
    private int image;
    private String date;



    public Checklist(int id, String mainarea, String subarea, String detailarea, String list, String result, String edittext, int image, String date) {
        this.id = id;
        this.mainarea = mainarea;
        this.subarea = subarea;
        this.detailarea = detailarea;
        this.list = list;
        this.result = result;
        this.edittext = edittext;
        this.image = image;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMainarea() {
        return mainarea;
    }

    public void setMainarea(String mainarea) {

        this.mainarea = mainarea;
    }

    public String getSubarea() {

        return subarea;
    }

    public void setSubarea(String subarea) {

        this.subarea = subarea;
    }

    public String getDetailarea() {

        return detailarea;
    }

    public void setDetailarea(String detailarea) {

        this.detailarea = detailarea;
    }

    public String getList() {

        return list;
    }

    public void setList(String list) {

        this.list = list;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {

        this.result = result;
    }

    public String getEdittext() {

        return edittext;
    }

    public void setEdittext(String edittext) {

        this.edittext = edittext;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;

    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }
}
