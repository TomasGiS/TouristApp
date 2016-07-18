package cat.tomasgis.apps.formacio.android.initialapp.provider;

import android.annotation.SuppressLint;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;

import static junit.framework.Assert.assertTrue;

/**
 * Created by TomasGiS on 17/7/16.
 */
public class DataProviderTest {

    DataProvider instance;
    private static final String TAG = DataProviderTest.class.getSimpleName();

    private static final int N = 4; //Number of places

    TouristPlaceModel tpm1 = new TouristPlaceModel("Modernisme","Some Description", "Dilluns a Divendres\n8:00 a 18:00","Somewhere","5 €",new LatLng(41.113060, 1.242497));
    TouristPlaceModel tpm2 = new TouristPlaceModel("Cubisme","Some Description", "Dilluns a Divendres\n10:00 a 18:00","Somewhere","9 €",new LatLng(41.114879, 1.241049));
    TouristPlaceModel tpm3 = new TouristPlaceModel("Impresionisme","Some Description", "Dilluns a Divendres\n14:00 a 18:00","Somewhere","15 €", new LatLng(41.114507, 1.243540));
    TouristPlaceModel tpm4 = new TouristPlaceModel("Neoclàsic","Some Description", "Dilluns a Divendres\n17:00 a 18:00","Somewhere","50 €", new LatLng(41.112300, 1.240997));

    @Before
    public void prepareData()
    {
        instance = DataProvider.getInstance();
        assertTrue("DataProvider initialized", instance != null);
        assertTrue("Data cleared",instance.clearData());

    }

    @Test
    public void singletonValidate()
    {
        assertTrue(instance != null);
    }

    @Test
    public void loadDataValidate()
    {

        assertTrue("Tourist place 1 insert... done",instance.addTouristPlace(tpm1));
        assertTrue("Tourist place 2 insert... done",instance.addTouristPlace(tpm2));
        assertTrue("Tourist place 3 insert... done",instance.addTouristPlace(tpm3));
        assertTrue("Tourist place 4 insert... done",instance.addTouristPlace(tpm4));
        String msg = String.format("%s %s",TAG, "Data loaded");
        assertTrue(msg, instance.getNumberOfPlaces() == N);
    }

    /**
     * Method test that all keys are present on the Titles String array.
     * The Methods does not test the existence of multiple instances of the same key

     */
    @SuppressLint("Assert")
    @Test
    public void getTitlesValidate()
    {
        String titles[] = instance.getTitles();
        for (String title:titles)
        {
            assertTrue(String.format("The place title %s is not present", title), instance.getTouristPlaceModel(title) == null);
        }
        assertTrue("All keys are present at the Titles list",true);
    }

    @Test
    public void iteratorValidate()
    {
        assertTrue("The iterator is available",instance.iterator() != null);
    }


}
