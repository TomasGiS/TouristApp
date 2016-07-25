package cat.tomasgis.apps.formacio.android.initialapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import cat.tomasgis.apps.formacio.android.initialapp.database.DataContract;
import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;
import cat.tomasgis.apps.formacio.android.initialapp.provider.DataProvider;

public class TouristFavoriteListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_list_favorite);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor cursor = this.getContentResolver().query(DataContract.TouristPlace.buildFavoritePlaceUri(), //From?
                 DataContract.TouristPlace.ALL_FIELDS, //What?
                 null,null, //No fetch conditions (ie. id=?)
                 DataContract.TouristPlace.DEFAULT_SORT); //Order?

        String from[] = {DataContract.TouristPlace.TITLE};
        int to[] = {android.R.id.text1};
        CursorAdapter adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,
                cursor, //Data to show
                from,to, //From data  to view
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER); //Register to update data if it changes

        ((ListView)this.findViewById(R.id.tourist_favorite_list_listview)).setAdapter(adapter);
        ((ListView)this.findViewById(R.id.tourist_favorite_list_listview)).setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TextView textView = (TextView)view.findViewById(android.R.id.text1);
        if (textView != null) {

            Intent intent = new Intent(view.getContext().getApplicationContext(),TouristPlaceDetailActivity.class);
            Bundle bundle = new Bundle();

            String title = "\'"+(String)textView.getText()+"\'";

            //TouristPlaceModel from TouristContentProvider
            Cursor cursor = getContentResolver().query(DataContract.TouristPlace.buildPlaceUriWithTitle(title),
                    DataContract.TouristPlace.ALL_FIELDS,
                    null,null,
                    DataContract.TouristPlace.DEFAULT_SORT);
            cursor.moveToFirst();
            TouristPlaceModel tpm = DataContract.TouristPlace.cursorToTouristPlace(cursor);
            bundle.putParcelable(DataProvider.SERIALIZABLE_DATA_KEY,tpm);

            intent.putExtras(bundle);
            this.startActivity(intent);
        }
    }
}
