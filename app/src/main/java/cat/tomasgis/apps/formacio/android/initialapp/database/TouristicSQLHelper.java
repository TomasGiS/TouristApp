package cat.tomasgis.apps.formacio.android.initialapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TomasGiS on 20/7/16.
 */
public class TouristicSQLHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "touristic.db";
    public static final int DATABASE_VERSION = 1;

    //TABLES
    private static final String TABLE_PLACES = "PLACES";

    //TABLES_FIELDS
    private static final String PLACES_TABLE_CREATE = "create table "
            + TABLE_PLACES + "( "
            + DataContract.TouristPlace._ID + " integer primary key autoincrement, "
            + DataContract.TouristPlace.TITLE + " text not null,"
            + DataContract.TouristPlace.DESCRIPTION + " text not null,"
            + DataContract.TouristPlace.APERTURE_TIME + " text not null,"
            + DataContract.TouristPlace.PRICE + " text not null,"
            + DataContract.TouristPlace.PLACE + " text not null,"
            + DataContract.TouristPlace.LOCATION_LAT + " text not null,"
            + DataContract.TouristPlace.LOCATION_LON + " text not null,"
            + DataContract.TouristPlace.IMAGE_URL + " text not null,"
            + DataContract.TouristPlace.RATING + " text not null"
            + ");";

    public TouristicSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PLACES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        onCreate(db);
    }
}
