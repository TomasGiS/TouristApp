package cat.tomasgis.apps.formacio.android.initialapp.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import cat.tomasgis.apps.formacio.android.initialapp.database.DataContract;
import cat.tomasgis.apps.formacio.android.initialapp.database.TouristicSQLHelper;

/**
 * Created by TomasGiS on 24/7/16.
 */
public class TouristContentProvider extends ContentProvider{


    // database
    private TouristicSQLHelper database;

    // used for the UriMacher
    private static final int PLACES = 100;
    private static final int PLACES_ID = 101;
    private static final int PLACES_TITLE = 102;

    private static final int FAVORITES = 105;

    public static final String AUTHORITY = "contentprovider.tourist.android.tomasgis.cat";//"cat.tomasgis.android.tourist.contentprovider";

    public static final String PLACES_BASE_PATH = "places";
    public static final String FAVORITES_BASE_PATH = "favorites";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + PLACES_BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/places";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/place";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, PLACES_BASE_PATH, PLACES);
        sURIMatcher.addURI(AUTHORITY, PLACES_BASE_PATH + "/#", PLACES_ID);
        sURIMatcher.addURI(AUTHORITY, PLACES_BASE_PATH + "/*", PLACES_TITLE);
        sURIMatcher.addURI(AUTHORITY, FAVORITES_BASE_PATH, FAVORITES);
    }


    @Override
    public boolean onCreate() {
        database = new TouristicSQLHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        //checkColumns(projection);

        // Set the table
        queryBuilder.setTables(TouristicSQLHelper.TABLE_PLACES);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case FAVORITES:
                // adding the Favorite condition to the original query
                queryBuilder.appendWhere(DataContract.TouristPlace.FAVORITE + "> 0");
            case PLACES:
                break;
            case PLACES_TITLE:
                // adding the TITLE to the original query
                queryBuilder.appendWhere(DataContract.TouristPlace.TITLE + "="
                        + uri.getLastPathSegment());
                break;
            case PLACES_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(DataContract.TouristPlace.ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case PLACES:
                id = sqlDB.insert(TouristicSQLHelper.TABLE_PLACES, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(PLACES_BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case PLACES:
                rowsDeleted = sqlDB.delete(TouristicSQLHelper.TABLE_PLACES, selection,
                        selectionArgs);
                break;
            case PLACES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(
                            TouristicSQLHelper.TABLE_PLACES,
                            DataContract.TouristPlace.ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            TouristicSQLHelper.TABLE_PLACES,
                            DataContract.TouristPlace.ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case PLACES:
                rowsUpdated = sqlDB.update(TouristicSQLHelper.TABLE_PLACES,
                        values,
                        selection,
                        selectionArgs);
                break;
            case PLACES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(TouristicSQLHelper.TABLE_PLACES,
                            values,
                            DataContract.TouristPlace.ID + "=" + id,
                            null);
                } else {
                    throw new IllegalArgumentException("ID not present at URI: " + uri);
                }

                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
