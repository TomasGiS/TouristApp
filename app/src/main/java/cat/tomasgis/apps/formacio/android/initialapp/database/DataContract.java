package cat.tomasgis.apps.formacio.android.initialapp.database;

import android.provider.BaseColumns;
import android.provider.SyncStateContract;

import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;

/**
 * Created by TomasGiS on 20/7/16.
 */
public class DataContract {



    interface TouristPlaceFields
    {
        public static final String TITLE = "TITLE";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String APERTURE_TIME = "APERTURE_TIME";
        public static final String PRICE = "PRICE";
        public static final String PLACE = "PLACE";
        public static final String LOCATION_LAT = "LOCATION_LAT";
        public static final String LOCATION_LON = "LOCATION_LON";
        public static final String IMAGE_URL = "IMAGE_URL";
        public static final String RATING ="RATING";
    }

    public class TouristPlace implements TouristPlaceFields,BaseColumns {

    }
}
