package cat.tomasgis.apps.formacio.android.initialapp.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by TomasGiS on 12/7/16.
 */
public class TouristPlaceModel implements Parcelable {

    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String APERTURE_TIME = "APERTURE_TIME";
    public static final String PRICE = "PRICE";
    public static final String PLACE = "PLACE";
    public static final String LOCATION = "LOCATION";
    public static final String IMAGE_URL = "IMAGE_URL";
    public static final String RATING ="RATING";
    public static final String FAVORITE = "FAVORITE";


    private String mTitle;
    private String mDescription;
    private String mApertureTime;
    private String mPrice;
    private String mPlace;
    private LatLng mLocation;
    private String mImageURL;
    private float mRating;
    private boolean mFavorite;

    public TouristPlaceModel(String title, String description, String apertureTime,
                             String place,
                             String price, LatLng location, String imageURL, float rating, boolean favorite) {
        this.mTitle = title;
        this.mDescription = description;
        this.mApertureTime = apertureTime;
        this.mPrice = price;
        this.mPlace = place;
        this.mLocation = location;
        this.mRating = rating;
        this.mImageURL = imageURL;
        this.mFavorite = favorite;
    }


    protected TouristPlaceModel(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
        mApertureTime = in.readString();
        mPrice = in.readString();
        mPlace = in.readString();
        mLocation = in.readParcelable(LatLng.class.getClassLoader());
        mImageURL = in.readString();
        mRating = in.readFloat();
        mFavorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mApertureTime);
        dest.writeString(mPrice);
        dest.writeString(mPlace);
        dest.writeParcelable(mLocation, flags);
        dest.writeString(mImageURL);
        dest.writeFloat(mRating);
        dest.writeByte((byte) (mFavorite ? 1 : 0));
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

    public void setImageURL(String imageURL) {
        this.mImageURL = imageURL;
    }

    public void setRating(float rating) {
        this.mRating = rating;
    }

    public void setFavorite (boolean favorite){this.mFavorite =favorite;}

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

    public String getImageURL(){return this.mImageURL;}

    public float getRating(){return this.mRating;}

    public boolean getFavorite() {return mFavorite;}

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
        bundle.putString(TouristPlaceModel.IMAGE_URL,this.mImageURL);
        bundle.putFloat(TouristPlaceModel.RATING,this.mRating);
        bundle.putBoolean(TouristPlaceModel.FAVORITE, this.mFavorite);

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
        this.mImageURL = bundle.getString(TouristPlaceModel.IMAGE_URL);
        this.mRating = bundle.getFloat(TouristPlaceModel.RATING);
        this.mFavorite = bundle.getBoolean(TouristPlaceModel.FAVORITE);
    }


}
