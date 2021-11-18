package com.phd_client_java;

import java.util.Date;

public class Tag {
    String name;
    Date datestamp;
    float value;
    float minValue;
    float maxValue;

    public Tag(String name, Date datestamp, float value) {
        this.name = name;
        this.datestamp = datestamp;
        this.value = value;
    }

    public Tag(String name, Date datestamp, float value, float minValue, float maxValue) {
        this.name = name;
        this.datestamp = datestamp;

        if (value > maxValue || value < minValue) {
            this.value = 0.0F;
        } else {
            this.value = value;
        }

        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}
