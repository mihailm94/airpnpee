package com.appspot.airpeepee.airpeepee.model;

import com.google.android.gms.maps.model.LatLng;

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

    public Toilet findToiletbyLatLng(LatLng latlng)
    {
        Toilet temp =new Toilet();
        for (Toilet toilet : this.getData())
        {
            if (toilet.getLocationLat() == latlng.latitude && toilet.getLocationLon()== latlng.longitude)
                return toilet;
        }

        return null;
    }
}
