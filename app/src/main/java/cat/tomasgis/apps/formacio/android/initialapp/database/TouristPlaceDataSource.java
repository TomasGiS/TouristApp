package cat.tomasgis.apps.formacio.android.initialapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedHashMap;

import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;

/**
 * Created by TomasGiS on 20/7/16.
 */
public class TouristPlaceDataSource {

    final String[] FIELDS = {DataContract.TouristPlace._ID,
            DataContract.TouristPlace.TITLE,
            DataContract.TouristPlace.DESCRIPTION,
            DataContract.TouristPlace.APERTURE_TIME,
            DataContract.TouristPlace.PLACE,
            DataContract.TouristPlace.PRICE,
            DataContract.TouristPlace.LOCATION_LAT,
            DataContract.TouristPlace.LOCATION_LON,
            DataContract.TouristPlace.IMAGE_URL,
            DataContract.TouristPlace.RATING};
    private final String TAG = TouristPlaceDataSource.class.getSimpleName();

    // Database fields
    private SQLiteDatabase database;
    private TouristicSQLHelper dbHelper;

    private static TouristPlaceDataSource instance;

    private TouristPlaceDataSource(){};


    public static TouristPlaceDataSource getInstance(Context context)
    {

        if (instance == null)
        {
            instance = new TouristPlaceDataSource(context);
        }

        return instance;
    }

    /**
     * The constructor creates a connection with the DataBase Delegate ({@link TouristicSQLHelper})
     * @param context application or activity context
     */
    public TouristPlaceDataSource(Context context) {
        dbHelper = new TouristicSQLHelper(context);
    }

    /***
     * Toggle write access to Tourist database through the class {@link TouristicSQLHelper}
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Close database access
     */
    public void close() {
        dbHelper.close();
    }

    public long create(TouristPlaceModel touristPlace) {
        ContentValues values = new ContentValues();
        values.put(DataContract.TouristPlace.TITLE, touristPlace.getTitle());

        LatLng location = touristPlace.getLocation();
        values.put(DataContract.TouristPlace.LOCATION_LAT,location.latitude);

        long insertId = database.insert(TouristicSQLHelper.TABLE_PLACES, null, values);

        return insertId;
    }

    public TouristPlaceModel query(String title)
    {



        Cursor cursor = database.query(TouristicSQLHelper.TABLE_PLACES,
                FIELDS, DataContract.TouristPlace.TITLE + " = " + title, null,
                null, null, null);

        TouristPlaceModel touristPlaceModel = cursorToPlaceModel(cursor);

        return touristPlaceModel;
    }

    public int delete(TouristPlaceModel touristPlaceModel) {
        String title = touristPlaceModel.getTitle();
        System.out.println("TouristPlaceModel deleted with title: " + touristPlaceModel.getTitle());
        int id = database.delete(TouristicSQLHelper.TABLE_PLACES, DataContract.TouristPlace.TITLE
                + " = " + touristPlaceModel.getTitle(), null);
        return id;
    }

    private TouristPlaceModel cursorToPlaceModel(Cursor cursor) {

        cursor.moveToFirst();

        LatLng location = new LatLng(cursor.getDouble(cursor.getColumnIndexOrThrow(DataContract.TouristPlace.LOCATION_LAT)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(DataContract.TouristPlace.LOCATION_LON)));

        TouristPlaceModel touristPlaceModel = new TouristPlaceModel(
                cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TouristPlace.TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TouristPlace.DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TouristPlace.APERTURE_TIME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TouristPlace.PLACE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TouristPlace.PRICE)),
                location,
                cursor.getString(cursor.getColumnIndexOrThrow(DataContract.TouristPlace.IMAGE_URL)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DataContract.TouristPlace.RATING))
        );

        return touristPlaceModel;
    }

    public LinkedHashMap<String,TouristPlaceModel> getAllTouristPlaces() {
        LinkedHashMap<String,TouristPlaceModel> touristPlacelist = new LinkedHashMap<String,TouristPlaceModel>();

        Cursor cursor = database.query(TouristicSQLHelper.TABLE_PLACES,
                FIELDS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TouristPlaceModel touristPlaceModel = cursorToPlaceModel(cursor);
            touristPlacelist.put(touristPlaceModel.getTitle(),touristPlaceModel);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return touristPlacelist;
    }





}
