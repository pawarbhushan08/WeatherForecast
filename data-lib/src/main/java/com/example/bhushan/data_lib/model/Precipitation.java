package com.example.bhushan.data_lib.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Precipitation implements Serializable {

    /*
    "_unit": "3h",
            "_value": "0.03125",
            "_type": "snow"
    * */

    @SerializedName("_unit")
    @Expose
    private String unit;

    @SerializedName("_value")
    @Expose
    private Double value;

    @SerializedName("_type")
    @Expose
    private String type;

    public Precipitation(String unit, Double value, String type) {
        this.unit = unit;
        this.value = value;
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
