package com.syh4834.chabak.api.data;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlaceDetailData {
    private double placeLongitude;

    private double placeAvgStar;

    private int placeLikeCnt;

    private int placeStore;

    private int placeCategoryIdx;

    private String placeAddress;

    private int placeStar;

    private double placeLatitude;

    private String placeThumbnail;

    private String[] placeImg;

    private PlaceToiletData[] placeToilet;

    private String placeTitle;

    private int placeReviewCnt;

    private String placeCategoryName;

    private String placeContent;

    private Boolean userLike;

    private int placeIdx;

    private int placeCooking;

    private String placeName;

    private String placeDate;

    public double getPlaceLongitude ()
    {
        return placeLongitude;
    }

    public double getPlaceAvgStar ()
    {
        return placeAvgStar;
    }

    public int getPlaceLikeCnt ()
    {
        return placeLikeCnt;
    }

    public int getPlaceStore ()
    {
        return placeStore;
    }

    public String getPlaceAddress ()
    {
        return placeAddress;
    }

    public int getPlaceStar () { return placeStar; }

    public double getPlaceLatitude ()
    {
        return placeLatitude;
    }

    public String[] getPlaceImg ()
    {
        return placeImg;
    }

    public PlaceToiletData[] getPlaceToilet ()
    {
        return placeToilet;
    }

    public String getPlaceTitle ()
    {
        return placeTitle;
    }

    public int getPlaceReviewCnt ()
    {
        return placeReviewCnt;
    }

    public String getPlaceContent ()
    {
        return placeContent.replace("\\n", "\n");
    }

    public Boolean getUserLike ()
    {
        return userLike;
    }

    public int getPlaceIdx ()
    {
        return placeIdx;
    }

    public int getPlaceCooking ()
    {
        return placeCooking;
    }

    public String getPlaceName ()
    {
        return placeName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [placeLongitude = "+placeLongitude+", placeAvgStar = "+placeAvgStar+", placeLikeCnt = "+placeLikeCnt+", placeStore = "+placeStore+", placeCategoryIdx = "+placeCategoryIdx+", placeAddress = "+placeAddress+", placeStar = "+placeStar+", placeLatitude = "+placeLatitude+", placeThumbnail = "+placeThumbnail+", placeImg = "+placeImg+", placeToilet = "+placeToilet+", placeTitle = "+placeTitle+", placeReviewCnt = "+placeReviewCnt+", placeCategoryName = "+placeCategoryName+", placeContent = "+placeContent+", userLike = "+userLike+", placeIdx = "+placeIdx+", placeCooking = "+placeCooking+", placeName = "+placeName+", placeDate = "+placeDate+"]";
    }
}
