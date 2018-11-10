package com.appspot.airpeepee.airpeepee.model;

import java.util.List;

public class DataHolder {
    public List<Toilet> getData() {
        return data;
    }

    public void setData(List<Toilet> data) {
        this.data = data;
    }

    private List<Toilet> data;

    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance(){return holder;}
}
