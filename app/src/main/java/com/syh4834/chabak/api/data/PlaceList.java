package com.syh4834.chabak.api.data;

public class PlaceList {
    private int placeIdx;
    private String placeTitle;
    private String placeAddress;
    private double placeAvgStar;
    private String placeThumbnail;
    private boolean userLike;


    public int getPlaceIdx ()
    {
        return placeIdx;
    }

    public String getPlaceTitle ()
    {
        return placeTitle;
    }

    public String getPlaceAddress ()
    {
        return placeAddress;
    }

    public double getPlaceAvgStar ()
    {
        return placeAvgStar;
    }

    public String getPlaceThumbnail ()
    {
        return placeThumbnail;
    }

    public boolean getUserLike () { return userLike; }
}
