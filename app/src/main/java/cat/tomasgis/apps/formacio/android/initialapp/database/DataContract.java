package cat.tomasgis.apps.formacio.android.initialapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.SyncStateContract;

import com.google.android.gms.maps.model.LatLng;

import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;
import cat.tomasgis.apps.formacio.android.initialapp.provider.TouristContentProvider;

/**
 * Created by TomasGiS on 20/7/16.
 */
public class DataContract {

    public static final String CONTENT_AUTHORITY = TouristContentProvider.AUTHORITY;

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri BASE_SERVER_URI = Uri.parse("http://" + CONTENT_AUTHORITY);

    interface TouristPlaceFields
    {

        String TITLE = "TITLE";
        String ID = "ID";
        String DESCRIPTION = "DESCRIPTION";
        String APERTURE_TIME = "APERTURE_TIME";
        String PRICE = "PRICE";
        String PLACE = "PLACE";
        String LOCATION_LAT = "LOCATION_LAT";
        String LOCATION_LON = "LOCATION_LON";
        String IMAGE_URL = "IMAGE_URL";
        String RATING ="RATING";
        String FAVORITE ="FAVORITE";
    }

    public static class TouristPlace implements TouristPlaceFields,BaseColumns {


        public static String ALL_FIELDS[] = {
                TouristPlace._ID, //Without this field CursorAdapter crashes
                TouristPlace.TITLE,
                TouristPlace.ID,
                TouristPlace.DESCRIPTION,
                TouristPlace.APERTURE_TIME,
                TouristPlace.PRICE,
                TouristPlace.PLACE,
                TouristPlace.LOCATION_LAT,
                TouristPlace.LOCATION_LON,
                TouristPlace.IMAGE_URL,
                TouristPlace.RATING,
                TouristPlace.FAVORITE
        };
        //Server
        public static final Uri SERVER_URI = BASE_SERVER_URI.buildUpon()
                .appendPath(TouristContentProvider.PLACES_BASE_PATH).build();

        //Content provider
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TouristContentProvider.PLACES_BASE_PATH).build();

        /**It's a kind of {@link TouristPlace}. It don't needs its own class  */
        public static final Uri FAVORITE_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TouristContentProvider.FAVORITES_BASE_PATH).build();

        public static final String CONTENT_TYPE = CONTENT_AUTHORITY+"/vnd.tourist.places";
        public static final String CONTENT_ITEM_TYPE = CONTENT_AUTHORITY+"/vnd.tourist.place";


        /** Default "ORDER BY" clause. */
        public static final String DEFAULT_SORT = TouristPlace.TITLE + " ASC ";

        /** Default "ORDER BY" clause. */
        public static final String ID_SORT = TouristPlace.ID + " ASC ";

        /** Build {@link Uri} for requested ID {@link TouristPlace}. */
        public static Uri buildPlaceUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        /** Build {@link Uri} for requested TITLE {@link TouristPlace}. */
        public static Uri buildPlaceUriWithTitle(String title) {
            return CONTENT_URI.buildUpon().appendPath(title).build();
        }

        /** Build {@link Uri} for all Places */
        public static Uri buildPlaceUri() {
            return CONTENT_URI;
        }

        /** Build {@link Uri} for all Places */
        public static Uri buildPlaceServerUri() {
            return SERVER_URI;
        }

        /** Build {@link Uri} for all Favorite Places */
        public static Uri buildFavoritePlaceUri() {
            return FAVORITE_CONTENT_URI;
        }

        /** Read {@link #ID} from {@link TouristPlace}. */
        public static String getPlaceId(Uri uri) {
            return uri.getPathSegments().get(1);
        }


        public static ContentValues touristPlaceToContentValues(TouristPlaceModel touristPlaceModel)
        {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DataContract.TouristPlace.TITLE, touristPlaceModel.getTitle());
            contentValues.put(DataContract.TouristPlace.ID, touristPlaceModel.getId());
            contentValues.put(DataContract.TouristPlace.DESCRIPTION, touristPlaceModel.getDescription());
            contentValues.put(DataContract.TouristPlace.APERTURE_TIME, touristPlaceModel.getApertureTime());
            contentValues.put(DataContract.TouristPlace.PRICE, touristPlaceModel.getPrice());
            contentValues.put(DataContract.TouristPlace.PLACE, touristPlaceModel.getPlace());

            LatLng location = touristPlaceModel.getLocation();
            contentValues.put(DataContract.TouristPlace.LOCATION_LAT, location.latitude);
            contentValues.put(DataContract.TouristPlace.LOCATION_LON, location.longitude);

            contentValues.put(DataContract.TouristPlace.IMAGE_URL, touristPlaceModel.getImageURL());
            contentValues.put(DataContract.TouristPlace.RATING, touristPlaceModel.getRating());
            contentValues.put(DataContract.TouristPlace.FAVORITE, touristPlaceModel.getFavorite());

            return  contentValues;
        }

        public static TouristPlaceModel cursorToTouristPlace(Cursor cursor) {
            if (cursor.getCount() == 0) return null;

            LatLng location = new LatLng(cursor.getDouble(cursor.getColumnIndexOrThrow(TouristPlace.LOCATION_LAT)),
                                         cursor.getDouble(cursor.getColumnIndexOrThrow(TouristPlace.LOCATION_LON)));


            return new TouristPlaceModel(
                    cursor.getString(cursor.getColumnIndexOrThrow(TouristPlace.ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TouristPlace.TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TouristPlace.DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TouristPlace.APERTURE_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TouristPlace.PLACE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TouristPlace.PRICE)),
                    location,
                    cursor.getString(cursor.getColumnIndexOrThrow(TouristPlace.IMAGE_URL)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(TouristPlace.RATING)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(TouristPlace.FAVORITE))>0
            );
        }
    }
}
