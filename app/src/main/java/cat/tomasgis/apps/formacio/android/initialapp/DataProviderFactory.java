package cat.tomasgis.apps.formacio.android.initialapp;

import android.content.Context;

import cat.tomasgis.apps.formacio.android.initialapp.database.TouristPlaceDBProvider;
import cat.tomasgis.apps.formacio.android.initialapp.interfaces.ITouristDataAccess;
import cat.tomasgis.apps.formacio.android.initialapp.provider.DataProvider;

/**
 * Created by TomasGiS on 21/7/16.
 */
public class DataProviderFactory {

    public enum TouristicDataSourceType {StaticData, DabaseData};

    public static ITouristDataAccess getDataSource(Context context, TouristicDataSourceType type)
    {
        if (type == TouristicDataSourceType.StaticData) return DataProvider.getInstance();
        else if (type == TouristicDataSourceType.DabaseData) return TouristPlaceDBProvider.getInstance(context);
        else return null;
    }
}
