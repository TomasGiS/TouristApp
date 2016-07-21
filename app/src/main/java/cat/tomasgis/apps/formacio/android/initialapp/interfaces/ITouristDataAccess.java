package cat.tomasgis.apps.formacio.android.initialapp.interfaces;

import java.util.Iterator;

import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;

/**
 * Created by TomasGiS on 21/7/16.
 */
public interface ITouristDataAccess  {
    boolean clearData();

    String[] getTitles();

    @Deprecated
    int getNumberOfPlaces();

    TouristPlaceModel getTouristPlaceModel(String key);

    boolean addTouristPlace(TouristPlaceModel touristPlaceModel);

    //public Iterator<ITouristDataAccess> Iterator();

}
