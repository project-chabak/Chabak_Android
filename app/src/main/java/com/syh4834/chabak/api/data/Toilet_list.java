package com.syh4834.chabak.api.data;

public class Toilet_list {
    private final Double toiletLatitude;
    private final Double toiletLongitude;

    public Toilet_list(Double toiletLatitude, Double toiletLongitude) {
        this.toiletLatitude = toiletLatitude;
        this.toiletLongitude = toiletLongitude;
    }

    public Double getToiletLatitude() {
        return toiletLatitude;
    }

    public Double getToiletLongitude() {
        return toiletLongitude;
    }
}

