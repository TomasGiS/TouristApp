package cat.tomasgis.apps.formacio.android.initialapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TouristPlacesListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String titleData[] = {"Modernisme","Cubisme"
            ,"Impresionisme", "Neoclàsic"};
    String timeData[]= {"","","",""};
    String priceData[]= {"","","",""};
    String locationData[]= {"","","",""};
    String descriptionData[]= {"","","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_places_list);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ListView placesList = (ListView) this.findViewById(R.id.tourist_places_list_listview);
        ArrayAdapter<String> adapter;

        adapter= new ArrayAdapter<String>(this,R.layout.places_list_item, titleData);

        placesList.setAdapter(adapter);
        placesList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TextView textView = (TextView)view.findViewById(android.R.id.text1);
        if (textView != null) {
            //String msg = String.valueOf(position);
            String msg = (String) textView.getText();
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext().getApplicationContext(),TouristPlaceDetailActivity.class);
            /*
            intent.putExtra("DATA_NAME",(String) textView.getText());


            Bundle parameter = new Bundle();
            parameter.putString("DATA_NAME",(String) textView.getText());
            intent.putExtras(parameter);
*/
            this.startActivity(intent);
        }
    }
}
