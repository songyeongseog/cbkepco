package com.example.mainapp;

public class RecyclerViewItem {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public RecyclerViewItem(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    String mobile;
}