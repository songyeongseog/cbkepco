package com.example.mainapp;

import android.provider.ContactsContract;

public class DatabaseItem {

    public String mainarea; // 점검 시즌 구분 : ex) 해빙기
    public String subarea;  // 각 분야 구분 : ex) 보안감시설비
    public String detailarea;   // 세부 분야 구분 : ex) 울타리 감지
    public String list; // 설비점검 내역 : ex) 카메라 점검 상태 등
    public String result; // 점검결과 입력 : ex) 양호, 불량
    public String edittext; // Database 내 특이사항 결과 입력
    public int image;   // 점검 사진 입력

    public DatabaseItem(String mainarea, String subarea, String detailarea, String list, String result, String edittext, int image){

        this.mainarea = mainarea;
        this.subarea = subarea;
        this.detailarea = detailarea;
        this.list = list;
        this.result = result;
        this.edittext = edittext;
        this.image = image;

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
}