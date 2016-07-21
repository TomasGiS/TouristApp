package cat.tomasgis.apps.formacio.android.initialapp.database;

import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import com.google.android.gms.maps.model.LatLng;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;

import static org.junit.Assert.*;

/**
 * Created by TomasGiS on 21/7/16.
 */
public class TouristPlaceDataSourceTest {


    private final String TAG = TouristPlaceDataSourceTest.class.getSimpleName();
    TouristPlaceDataSource instance = null;

    private static final String TPM_NAME_BASE = "Lloc turístic";
    private static final String TPM_DESCRIPTION_BASE = "DESCRIPTION";
    private static final String TPM_APERTURE_BASE = "TPM_APERTURE_BASE";
    private static final String TPM_PLACE_BASE = "TPM_PLACE_BASE";
    private static final String TPM_PRICE_BASE = "TPM_PRICE_BASE";
    private static final String TPM_URL_BASE = "TPM_URL_BASE";

    private static final int NUM_FIELDS = 9; /** Number of fields of the table {@Link DataContract.TouristPlaceFields}*/

    private static final int NUM_ELEMENTS = 10;

    @Before
    public void prepareEnvironment()
    {
        instance = TouristPlaceDataSource.getInstance(InstrumentationRegistry.getTargetContext());
        assertNotNull(TAG + "Instance created",instance);
    }

    @Test
    public void testGetInstance() throws Exception {
        instance = TouristPlaceDataSource.getInstance(InstrumentationRegistry.getTargetContext());
        assertNotNull(TAG + "Instance created",instance);
    }

    @Test
    public void testOpen() throws Exception {
        instance.open();
        assertTrue(TAG + "Database opening... done",true);

    }

    @Test
    public void testClose() throws Exception {
        instance.close();
        assertTrue(TAG + "Database closing... done",true);
    }

    @Test
    public void testCreate() throws Exception {
        long id = 0;
        for (int i=0;i<NUM_ELEMENTS;i++)
        {
             id = instance.create( new TouristPlaceModel(TPM_NAME_BASE+String.valueOf(i),
                    TPM_DESCRIPTION_BASE+i,
                    TPM_APERTURE_BASE+i,
                    TPM_PLACE_BASE+i,
                    TPM_PRICE_BASE+i,
                    new LatLng(41.113060, 1.242497),
                    TPM_URL_BASE+i,
                    (float)i%5));

            assertFalse(String.format("%s :: creation of the element %d returns %d",TAG,i,id),id<=0);
        }
    }

    @Test
    public void testQuery() throws Exception {
        TouristPlaceModel touristPlaceModel = null;

        testCreate();

        for (int i = 0;i<NUM_ELEMENTS;i++)
        {
            touristPlaceModel= instance.query(TPM_NAME_BASE+i);
            assertNotNull(TAG+" query method" ,touristPlaceModel);
        }


    }



    @Test
    public void testGetAllTouristPlaces() throws Exception {

        //this.testCreate();

        //LinkedHashMap<String, TouristPlaceModel> list = instance.getAllTouristPlaces();
        //assertFalse(TAG+"Get all data",list.values().size()!=NUM_ELEMENTS);
    }


    @Test
    public void testGetTitles() throws Exception {
        this.testClearData();
        this.testCreate();
        String titles[] = instance.getTitles();
        assertFalse(TAG+"No enough elements at list",titles.length!=NUM_ELEMENTS);
    }

    @Test
    public void testGetNumberOfPlaces() throws Exception {
        this.testClearData();
        this.testCreate();
        int num = this.instance.getNumberOfPlaces();
        assertFalse(TAG+"No enough elements at list",num!=NUM_ELEMENTS);
    }

    @Test
    public void testGetTouristPlaceModel() throws Exception {
        this.testCreate();

        TouristPlaceModel touristPlaceModel = instance.getTouristPlaceModel(TPM_NAME_BASE+"0");
        assertNotNull(TAG+"No element at list",touristPlaceModel);
    }

    @Test
    public void testAddTouristPlace() throws Exception {
        this.testCreate();
        boolean check = this.instance.addTouristPlace(new TouristPlaceModel(TPM_NAME_BASE+11,
                TPM_DESCRIPTION_BASE+11,
                TPM_APERTURE_BASE+11,
                TPM_PLACE_BASE+11,
                TPM_PRICE_BASE+11,
                new LatLng(41.113060, 1.242497),
                TPM_URL_BASE+11,
                (float)11));

        assertTrue(TAG+"Inserting element... failed",check);
        TouristPlaceModel touristPlaceModel = this.instance.query(TPM_NAME_BASE+11);
        assertNotNull(TAG+"No element at list",touristPlaceModel);
    }

    @Test
    public void testGetIterableCursor() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {
        int id = 0;
        for (int i = 0;i<NUM_ELEMENTS;i++)
        {
            id = instance.delete(TPM_NAME_BASE+i);
            assertFalse(TAG+" delete method" ,i<0);
        }


    }

    @Test
    public void testClearData() throws Exception {
        instance.clearData();
    }

    @After
    public void tearDown()
    {
        try {
            this.testDelete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}