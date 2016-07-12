package cat.tomasgis.apps.formacio.android.initialapp.provider;

import java.util.ArrayList;
import java.util.List;

import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;

/**
 * Created by TomasGiS on 12/7/16.
 */
public class DataProvider {


    private static List<TouristPlaceModel> placesData;

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

    private  List<TouristPlaceModel> loadData() {

        placesData = new ArrayList<>();

        TouristPlaceModel tpm1 = new TouristPlaceModel("Modernisme","Some Description", "Dilluns a Divendres\n8:00 a 18:00","5 €");
        TouristPlaceModel tpm2 = new TouristPlaceModel("Cubisme","Some Description", "Dilluns a Divendres\n8:00 a 18:00","5 €");
        TouristPlaceModel tpm3 = new TouristPlaceModel("Impresionisme","Some Description", "Dilluns a Divendres\n8:00 a 18:00","5 €");
        TouristPlaceModel tpm4 = new TouristPlaceModel("Neoclàsic","Some Description", "Dilluns a Divendres\n8:00 a 18:00","5 €");


        placesData.add(tpm1);
        placesData.add(tpm2);
        placesData.add(tpm3);
        placesData.add(tpm4);

        return placesData;
    }

    public  String[] getTitles()
    {

        String touristTitle[] = new String[placesData.size()];

        List<String> titles = new ArrayList<>();
        for (TouristPlaceModel place: placesData)
            titles.add(place.getTitle());

        return  titles.toArray(touristTitle);
    }

    public TouristPlaceModel getTouristPlaceModel(int index)
    {
        if (index >= placesData.size()) return null;
        else return placesData.get(index);
    }
}
