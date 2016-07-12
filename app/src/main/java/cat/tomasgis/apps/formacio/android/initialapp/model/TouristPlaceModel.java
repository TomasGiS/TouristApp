package cat.tomasgis.apps.formacio.android.initialapp.model;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by TomasGiS on 12/7/16.
 */
public class TouristPlaceModel implements Serializable{

    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String APERTURE_TIME = "APERTURE_TIME";
    public static final String PRICE = "PRICE";


    private String mTitle;
    private String mDescription;
    private String mApertureTime;
    private String mPrice;

    public TouristPlaceModel(String title, String description, String apertureTime, String price) {
        this.mTitle = title;
        this.mDescription = description;
        this.mApertureTime = apertureTime;
        this.mPrice = price;
    }

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

    public Bundle getDataBundle()
    {
        Bundle bundle = new Bundle();
        bundle.putString(TouristPlaceModel.TITLE,this.mTitle);
        bundle.putString(TouristPlaceModel.PRICE,this.mPrice);
        bundle.putString(TouristPlaceModel.DESCRIPTION,this.mDescription);
        bundle.putString(TouristPlaceModel.APERTURE_TIME,this.mApertureTime);

        return bundle;
    }

    public void loadFromBundle(Bundle bundle)
    {
        this.mTitle = bundle.getString(TouristPlaceModel.TITLE);
        this.mPrice = bundle.getString(TouristPlaceModel.PRICE);
        this.mDescription = bundle.getString(TouristPlaceModel.DESCRIPTION);
        this.mApertureTime = bundle.getString(TouristPlaceModel.APERTURE_TIME);
    }


}
