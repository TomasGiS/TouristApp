package cat.tomasgis.apps.formacio.android.initialapp.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by TomasGiS on 12/7/16.
 */
public class TouristPlaceModel implements Parcelable{

    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String APERTURE_TIME = "APERTURE_TIME";
    public static final String PRICE = "PRICE";
    public static final String PLACE = "PLACE";
    public static final String LOCATION = "LOCATION";


    private String mTitle;
    private String mDescription;
    private String mApertureTime;
    private String mPrice;
    private String mPlace;
    private LatLng mLocation;

    public TouristPlaceModel(String title, String description, String apertureTime,
                             String place,
                             String price, LatLng location) {
        this.mTitle = title;
        this.mDescription = description;
        this.mApertureTime = apertureTime;
        this.mPrice = price;
        this.mPlace = place;
        this.mLocation = location;
    }

    protected TouristPlaceModel(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
        mApertureTime = in.readString();
        mPrice = in.readString();
        mPlace = in.readString();
        mLocation = in.readParcelable(LatLng.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mApertureTime);
        dest.writeString(mPrice);
        dest.writeString(mPlace);
        dest.writeParcelable(mLocation, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TouristPlaceModel> CREATOR = new Creator<TouristPlaceModel>() {
        @Override
        public TouristPlaceModel createFromParcel(Parcel in) {
            return new TouristPlaceModel(in);
        }

        @Override
        public TouristPlaceModel[] newArray(int size) {
            return new TouristPlaceModel[size];
        }
    };

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setDescription(String description){
        this.mDescription = description;
    }

    public void setApertureTime(String apertureTime){
        this.mApertureTime = apertureTime;
    }

    public void setPrice(String price)
    {
        this.mPrice = price;
    }

    public void setPlace(String place)
    {
        this.mPlace = place;
    }

    public void setLocation(LatLng location)
    {
        this.mLocation = location;
    }


    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getApertureTime() {
        return mApertureTime;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getPlace()
    {
        return this.mPlace;
    }

    public LatLng getLocation()
    {
        return this.mLocation;
    }


    @Deprecated
    public Bundle getDataBundle()
    {
        Bundle bundle = new Bundle();
        bundle.putString(TouristPlaceModel.TITLE,this.mTitle);
        bundle.putString(TouristPlaceModel.PRICE,this.mPrice);
        bundle.putString(TouristPlaceModel.DESCRIPTION,this.mDescription);
        bundle.putString(TouristPlaceModel.APERTURE_TIME,this.mApertureTime);
        bundle.putString(TouristPlaceModel.PLACE,this.mPlace);
        bundle.putParcelable(TouristPlaceModel.LOCATION,this.mLocation);

        return bundle;
    }
    @Deprecated
    public void loadFromBundle(Bundle bundle)
    {
        this.mTitle = bundle.getString(TouristPlaceModel.TITLE);
        this.mPrice = bundle.getString(TouristPlaceModel.PRICE);
        this.mDescription = bundle.getString(TouristPlaceModel.DESCRIPTION);
        this.mApertureTime = bundle.getString(TouristPlaceModel.APERTURE_TIME);
        this.mPlace = bundle.getString(TouristPlaceModel.PLACE);
        this.mLocation = bundle.getParcelable(TouristPlaceModel.LOCATION);
    }


}
