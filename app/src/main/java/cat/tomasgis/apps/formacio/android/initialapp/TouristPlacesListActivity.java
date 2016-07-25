package cat.tomasgis.apps.formacio.android.initialapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import cat.tomasgis.apps.formacio.android.initialapp.database.DataContract;
import cat.tomasgis.apps.formacio.android.initialapp.interfaces.ITouristDataAccess;
import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;
import cat.tomasgis.apps.formacio.android.initialapp.provider.DataProvider;
import cat.tomasgis.apps.formacio.android.initialapp.provider.DataProviderFactory;

public class TouristPlacesListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    //ITouristDataAccess dataProvider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_places_list);

        //dataProvider = DataProviderFactory.getDataSource(this,DataProviderFactory.TouristicDataSourceType.DabaseData);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        ListView placesList = (ListView) this.findViewById(R.id.tourist_places_list_listview);

        //ArrayAdapter + ITouristDataAccess
        /*
        ArrayAdapter<String> adapter;
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataProvider.getTitles());
        */

        Cursor cursor = this.getContentResolver().query(DataContract.TouristPlace.buildPlaceUri(),DataContract.TouristPlace.ALL_FIELDS,
                                                        null,null, DataContract.TouristPlace.DEFAULT_SORT);
        String from[]={DataContract.TouristPlace.TITLE};
        int to[]={android.R.id.text1};
        CursorAdapter adapter = new android.support.v4.widget.SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,
                cursor,
                from,to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        placesList.setAdapter(adapter);
        placesList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TextView textView = (TextView)view.findViewById(android.R.id.text1);
        if (textView != null) {
            //String msg = String.valueOf(position);
            //String msg = (String) textView.getText();
            //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext().getApplicationContext(),TouristPlaceDetailActivity.class);


            //intent.putExtra(TouristPlaceModel.TITLE,dataProvider.getTouristPlaceModel(position).getTitle());

            Bundle bundle = new Bundle();

            String title = "\'"+(String)textView.getText()+"\'";

            //TouristPlaceModel from TouristContentProvider
            Cursor cursor = getContentResolver().query(DataContract.TouristPlace.buildPlaceUriWithTitle(title),
                                                DataContract.TouristPlace.ALL_FIELDS,
                                                null,null,
                                                DataContract.TouristPlace.DEFAULT_SORT);
            cursor.moveToFirst();
            TouristPlaceModel tpm = DataContract.TouristPlace.cursorToTouristPlace(cursor);

            //TouristPlaceModel from Dataprovider
            //TouristPlaceModel tpm= dataProvider.getTouristPlaceModel(title);

            //tpm cannot be null from previous call because the object is inserted at ListView
            bundle.putParcelable(DataProvider.SERIALIZABLE_DATA_KEY,tpm);

            intent.putExtras(bundle);
            this.startActivity(intent);
        }
    }
}
