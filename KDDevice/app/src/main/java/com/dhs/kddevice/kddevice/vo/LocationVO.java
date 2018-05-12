package com.dhs.kddevice.kddevice.vo;

/**
 * Created by Chinnaraj on 3/14/2018.
 */

public class LocationVO {
    private String name;
    private double lattitude;
    private  double longitude;
    private String vicinty;
    private String mapReference;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getVicinty() {
        return vicinty;
    }

    public void setVicinty(String vicinty) {
        this.vicinty = vicinty;
    }

    public String getMapReference() {
        return mapReference;
    }

    public void setMapReference(String mapReference) {
        this.mapReference = mapReference;
    }
}
