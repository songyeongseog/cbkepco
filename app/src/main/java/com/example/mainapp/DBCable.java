package com.example.mainapp;

public class DBCable {

    public String mainGroup;
    public String subGroup;
    public String powerNumber;
    public String powerName;
    public double lat;
    public double lon;
    public String address;

    public void setMainGroup(String mainGroup) {
        this.mainGroup = mainGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public void setPowerNumber(String powerNumber) {
        this.powerNumber = powerNumber;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /*** getter */
    public String getMainGroup() {
        return this.mainGroup;
    }
    public String getSubGroup() {
        return this.subGroup;
    }
    public String getPowerNumber() {
        return this.powerNumber;
    }
    public String getPowerName() {
        return this.powerName;
    }

    public double getLat() {
        return this.lat;
    }
    public double getLon() {
        return this.lon;
    }
    public String getAddress() {
        return this.address;
    }
}
