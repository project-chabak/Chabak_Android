package com.syh4834.chabak.api.data;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlaceDetailData {
    private int placeIdx;
    private int placeCategoryIdx;
    private String placeAddress;
    private String placeTitle;
    private String placeDate;
    private int placeStar;
    private int placeReviewCnt;
    private int placeLikeCnt;
    private String placeContent;
    private int placeStore;
    private int placeCooking;
    private double placeLatitude;
    private double placeLongitude;
    private String placeThumbnail;
    private double placeAvgStar;
    private String placeCategoryName;
    private Array placeImg;
    private ArrayList<Toilet_list> placeToilet;

    public PlaceDetailData(int placeIdx, int placeCategoryIdx, String placeAddress, String placeTitle, String placeDate, int placeStar, int placeReviewCnt, int placeLikeCnt, String placeContent, int placeStore, int placeCooking, double placeLatitude, double placeLongitude, String placeThumbnail, double placeAvgStar, String placeCategoryName, Array placeImg, ArrayList<Toilet_list> placeToilet) {
        this.placeIdx = placeIdx;
        this.placeCategoryIdx = placeCategoryIdx;
        this.placeAddress = placeAddress;
        this.placeTitle = placeTitle;
        this.placeDate = placeDate;
        this.placeStar = placeStar;
        this.placeReviewCnt = placeReviewCnt;
        this.placeLikeCnt = placeLikeCnt;
        this.placeContent = placeContent;
        this.placeStore = placeStore;
        this.placeCooking = placeCooking;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.placeThumbnail = placeThumbnail;
        this.placeAvgStar = placeAvgStar;
        this.placeCategoryName = placeCategoryName;
        this.placeImg = placeImg;
        this.placeToilet = placeToilet;
    }

    public int getPlaceIdx() {
        return placeIdx;
    }

    public int getPlaceCategoryIdx() {
        return placeCategoryIdx;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public String getPlaceDate() {
        return placeDate;
    }

    public int getPlaceStar() {
        return placeStar;
    }

    public int getPlaceReviewCnt() {
        return placeReviewCnt;
    }

    public int getPlaceLikeCnt() {
        return placeLikeCnt;
    }

    public String getPlaceContent() {
        return placeContent;
    }

    public int getPlaceStore() {
        return placeStore;
    }

    public int getPlaceCooking() {
        return placeCooking;
    }

    public double getPlaceLatitude() {
        return placeLatitude;
    }

    public double getPlaceLongitude() {
        return placeLongitude;
    }

    public String getPlaceThumbnail() {
        return placeThumbnail;
    }

    public double getPlaceAvgStar() {
        return placeAvgStar;
    }

    public String getPlaceCategoryName() {
        return placeCategoryName;
    }

    public Array getPlaceImg() {
        return placeImg;
    }

    public ArrayList<Toilet_list> getPlaceToilet() {
        return placeToilet;
    }
}
