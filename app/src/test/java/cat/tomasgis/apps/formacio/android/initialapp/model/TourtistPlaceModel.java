package cat.tomasgis.apps.formacio.android.initialapp.model;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by TomasGiS on 17/7/16.
 */
public class TourtistPlaceModel {

    TouristPlaceModel touristPlaceModel;

    //Constructor values
    private String TITLE = "Modernisme";
    private String DESCRIPTION = "Some Description";
    private String APERTURE_TIME = "Dilluns a Divendres\n8:00 a 18:00";
    private String PRICE = "5 €";
    private LatLng LOCATION = new LatLng(41.113060, 1.242497);
    private String PLACE = "Some location place";

    //Getter And Setter values
    private String TITLE_SG = "Cubisme";
    private String DESCRIPTION_SG = "The default Description";
    private String APERTURE_TIME_SG = "Dimarts a Divendres\n9:00 a 20:00";
    private String PRICE_SG = "15 €";
    private LatLng LOCATION_SG = new LatLng(45, 1.3);
    private String PLACE_SG = "The second place";

    @Before
    public void prepareData()
    {


        touristPlaceModel = new TouristPlaceModel(TITLE,DESCRIPTION, APERTURE_TIME,PLACE,PRICE,LOCATION);
    }

    @Test
    public void titleValidate()
    {
        //Constructor and Getter
        assertTrue(touristPlaceModel.getTitle().equals(TITLE));

        //Setter And Getter Check
        touristPlaceModel.setTitle(TITLE_SG);
        assertTrue(touristPlaceModel.getTitle().equals(TITLE_SG));
        assertFalse(touristPlaceModel.getTitle().equals(TITLE));
    }

    @Test
    public void descriptionValidate()
    {
        //Constructor and Getter
        assertTrue(touristPlaceModel.getDescription().equals(DESCRIPTION));
        //Setter And Getter Check
        touristPlaceModel.setDescription(DESCRIPTION_SG);
        assertTrue(touristPlaceModel.getDescription().equals(DESCRIPTION_SG));
        assertFalse(touristPlaceModel.getDescription().equals(DESCRIPTION));
    }

    @Test
    public void apertureTimeValidate()
    {
        //Constructor and Getter
        assertTrue(touristPlaceModel.getApertureTime().equals(APERTURE_TIME));
        //Setter And Getter Check
        touristPlaceModel.setApertureTime(APERTURE_TIME_SG);
        assertTrue(touristPlaceModel.getApertureTime().equals(APERTURE_TIME_SG));
        assertFalse(touristPlaceModel.getApertureTime().equals(APERTURE_TIME));
    }

    @Test
    public void priceValidate()
    {
        //Constructor and Getter
        assertTrue(touristPlaceModel.getPrice().equals(PRICE));
        //Setter And Getter Check
        touristPlaceModel.setPrice(PRICE_SG);
        assertTrue(touristPlaceModel.getPrice().equals(PRICE_SG));
        assertFalse(touristPlaceModel.getPrice().equals(PRICE));
    }

    @Test
    public void locationValidate()
    {
        //Constructor and Getter
        boolean latitudeCheck = (touristPlaceModel.getLocation().latitude == LOCATION.latitude);
        boolean longitudeCheck = (touristPlaceModel.getLocation().longitude == LOCATION.longitude);
        assertTrue((latitudeCheck && longitudeCheck));


        //Setter And Getter Check
        touristPlaceModel.setLocation(LOCATION_SG);
        latitudeCheck = (touristPlaceModel.getLocation().latitude == LOCATION_SG.latitude);
        longitudeCheck = (touristPlaceModel.getLocation().longitude == LOCATION_SG.longitude);
        assertTrue((latitudeCheck && longitudeCheck));

        latitudeCheck = (touristPlaceModel.getLocation().latitude == LOCATION.latitude);
        longitudeCheck = (touristPlaceModel.getLocation().longitude == LOCATION.longitude);
        assertFalse((latitudeCheck && longitudeCheck));
    }

    @Test
    public void placeValidate()
    {
        //Constructor and Getter
        assertTrue(touristPlaceModel.getPlace().equals(PLACE));

        //Setter And Getter Check
        touristPlaceModel.setPlace(PLACE_SG);
        assertTrue(touristPlaceModel.getPlace().equals(PLACE_SG));
        assertFalse(touristPlaceModel.getPlace().equals(PLACE));
    }

    /*
    @Test
    public void bundleValidate()
    {
        this.touristPlaceModel = new TouristPlaceModel(TITLE,DESCRIPTION, APERTURE_TIME,PLACE,PRICE,LOCATION);
        Bundle bundle = touristPlaceModel.getDataBundle();
        checkBundle(bundle);

        touristPlaceModel.loadFromBundle(bundle);
        bundle = touristPlaceModel.getDataBundle();
        checkBundle(bundle);


    }

    public void checkBundle(Bundle bundle)
    {
        assertTrue(bundle.getString(TouristPlaceModel.TITLE,this.TITLE).equals(TITLE));
        assertTrue(bundle.getString(TouristPlaceModel.DESCRIPTION,this.DESCRIPTION).equals(DESCRIPTION));
        assertTrue(bundle.getString(TouristPlaceModel.APERTURE_TIME,this.APERTURE_TIME).equals(APERTURE_TIME));
        assertTrue(bundle.getString(TouristPlaceModel.PLACE,this.PLACE).equals(PLACE));
        assertTrue(bundle.getString(TouristPlaceModel.PRICE,this.PRICE).equals(PRICE));
        try {

            LatLng location = bundle.getParcelable(TouristPlaceModel.LOCATION);
            assertTrue(location.latitude == this.LOCATION.latitude);
            assertTrue(location.longitude == this.LOCATION.longitude);
        }catch (NullPointerException e)
        {
            assertFalse(true);
        }
    }
    */
}
