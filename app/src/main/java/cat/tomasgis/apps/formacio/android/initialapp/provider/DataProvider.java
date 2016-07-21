package cat.tomasgis.apps.formacio.android.initialapp.provider;

import com.google.android.gms.maps.model.LatLng;

import java.util.Iterator;
import java.util.LinkedHashMap;

import cat.tomasgis.apps.formacio.android.initialapp.interfaces.ITouristDataAccess;
import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;

/**
 * Created by TomasGiS on 12/7/16.
 */
public class DataProvider implements Iterable<TouristPlaceModel>, ITouristDataAccess {

    public static String SERIALIZABLE_DATA_KEY = "SERIALIZABLE_DATA_KEY";

    private static LinkedHashMap<String,TouristPlaceModel> placesData;

    private static DataProvider instance = null;

    private DataProvider(){}

    public static DataProvider getInstance()
    {
        if (instance == null) {
            instance = new DataProvider();
            instance.placesData = instance.loadData();
        }

        return instance;
    }

    private LinkedHashMap<String,TouristPlaceModel> loadData() {

        placesData = new LinkedHashMap<>();

        String imageURL = "http://www.sidorme.com/uploads/46182-GAUDI.jpg";

        TouristPlaceModel tpm1 = new TouristPlaceModel("Modernisme","Some Description", "Dilluns a Divendres\n8:00 a 18:00","Somewhere","5",new LatLng(41.113060, 1.242497),imageURL,1.5f);
        TouristPlaceModel tpm2 = new TouristPlaceModel("Cubisme","Some Description", "Dilluns a Divendres\n10:00 a 18:00","Somewhere","9",new LatLng(41.114879, 1.241049),imageURL,2.0f);
        TouristPlaceModel tpm3 = new TouristPlaceModel("Impresionisme","Some Description", "Dilluns a Divendres\n14:00 a 18:00","Somewhere","15", new LatLng(41.114507, 1.243540),imageURL,3.5f);
        TouristPlaceModel tpm4 = new TouristPlaceModel("Neoclàsic","Some Description", "Dilluns a Divendres\n17:00 a 18:00","Somewhere","50", new LatLng(41.112300, 1.240997),imageURL,0.5f);


        placesData.put(tpm1.getTitle(),tpm1);
        placesData.put(tpm2.getTitle(),tpm2);
        placesData.put(tpm3.getTitle(),tpm3);
        placesData.put(tpm4.getTitle(),tpm4);

        return placesData;
    }

    @Override
    public boolean clearData()
    {
        if (placesData == null) return false;
        placesData.clear();
        return true;
    }

    @Override
    public  String[] getTitles()
    {
        String touristTitle[] = new String[placesData.size()];

        return placesData.keySet().toArray(touristTitle);
    }

    @Override
    public int getNumberOfPlaces()
    {
        if (placesData == null) return 0;
        return placesData.size();
    }

    /*
     * Allows access to a {@link TouristPlaceModel} object with the index
     * @param index the index of the {@link TouristPlaceModel} object
     * @return if the index is lower than the number of the places stored return the {@link TouristPlaceModel}
     */
    /*
    public TouristPlaceModel getTouristPlaceModel(int index)
    {
        TouristPlaceModel tpm = null;

        if (index >= placesData.values().size()) tpm= null;
        else tpm =  placesData.get(index);
        return  tpm;
    }
*/
    @Override
    public TouristPlaceModel getTouristPlaceModel(String key)
    {
        TouristPlaceModel tpm =  placesData.get(key);
        return  tpm;
    }


    /***
     * Insert data into de presistent data framework
     * @param touristPlaceModel
     */
    @Override
    public boolean addTouristPlace(TouristPlaceModel touristPlaceModel)
    {
        if (placesData == null) return false;
        DataProvider.placesData.put(touristPlaceModel.getTitle(),touristPlaceModel);

        return true;
    }

    @Override
    public Iterator<TouristPlaceModel> iterator() {
        if (placesData == null) return null;
        return placesData.values().iterator();
    }
}
