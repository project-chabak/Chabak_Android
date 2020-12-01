package com.syh4834.chabak.api.data;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class PlaceToiletData implements Parcelable {
    private double toiletLatitude;
    private double toiletLongitude;
    protected PlaceToiletData(Parcel in) {
        toiletLatitude = in.readDouble();
        toiletLongitude = in.readDouble();
    }
    public static final Creator<PlaceToiletData> CREATOR = new Creator<PlaceToiletData>() {
        @Override
        public PlaceToiletData createFromParcel(Parcel in) {
            return new PlaceToiletData(in);
        }

        @Override
        public PlaceToiletData[] newArray(int size) {
            return new PlaceToiletData[size];
        }
    };
    public double getToiletLatitude ()
    {
        return toiletLatitude;
    }
    public void setToiletLatitude (double toiletLatitude)
    {
        this.toiletLatitude = toiletLatitude;
    }
    public double getToiletLongitude ()
    {
        return toiletLongitude;
    }
    public void setToiletLongitude (double toiletLongitude)
    {
        this.toiletLongitude = toiletLongitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.toiletLatitude);
        parcel.writeDouble(this.toiletLongitude);
    }
}

