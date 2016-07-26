package cat.tomasgis.apps.formacio.android.initialapp.provider;

import android.content.Context;

import cat.tomasgis.apps.formacio.android.initialapp.interfaces.ITouristDataAccess;

/**
 * Created by TomasGiS on 21/7/16.
 */
public class DataProviderFactory {

    public enum TouristicDataSourceType {StaticData, Database};

    public static ITouristDataAccess getDataSource(Context context, TouristicDataSourceType type)
    {
        if (type == TouristicDataSourceType.StaticData) return DataProvider.getInstance();
        else if (type == TouristicDataSourceType.Database) return TouristPlaceDBProvider.getInstance(context);
        else return null;
    }
}
