package cat.tomasgis.apps.formacio.android.initialapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import cat.tomasgis.apps.Utils.Utils;
import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;
import cat.tomasgis.apps.formacio.android.initialapp.provider.DataProvider;

public class TouristPlaceDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = TouristPlaceDetailActivity.class.getSimpleName();
    private final int REQUEST_CODE = 3367;
    //String dataName;
    TouristPlaceModel touristPlaceModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_place_detail);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();


        SharedPreferences sharedPreferences = getSharedPreferences(DataProvider.SERIALIZABLE_DATA_KEY,MODE_PRIVATE);
        String json = sharedPreferences.getString(DataProvider.SERIALIZABLE_DATA_KEY, "");


        if (!json.isEmpty())
        {
            Gson gson = new Gson();
            touristPlaceModel = gson.fromJson(json, TouristPlaceModel.class);

            //Clear data after use it
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(DataProvider.SERIALIZABLE_DATA_KEY,"");
            editor.apply();
        }
        else if (savedInstanceState != null)
        {
            this.touristPlaceModel = (TouristPlaceModel)savedInstanceState.getParcelable(DataProvider.SERIALIZABLE_DATA_KEY);
            //dataName = this.touristPlaceModel.getTitle();
        }
        else if (bundle != null){
            //dataName = bundle.getString(TouristPlaceModel.TITLE);
            touristPlaceModel = (TouristPlaceModel) bundle.getParcelable(DataProvider.SERIALIZABLE_DATA_KEY);
            Log.d(TAG,"Get data bundle");
        }
        else
        {
            Log.e(TouristPlaceDetailActivity.class.getSimpleName(),
                    "Les dades a mostrar no són accessibles");

            Toast.makeText(this,"Les dades a mostrar no són accessibles",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView titleText = (TextView) this.findViewById(R.id.tourist_detail_title);
        TextView placeText = (TextView) this.findViewById(R.id.tourist_detail_place);
        TextView priceText = (TextView) this.findViewById(R.id.tourist_detail_price);
        TextView timeText = (TextView) this.findViewById(R.id.tourist_detail_time);
        TextView descriptionText = (TextView) this.findViewById(R.id.tourist_detail_description);

        if (titleText != null) titleText.setText(this.touristPlaceModel.getTitle());
        if (placeText != null) placeText.setText(this.touristPlaceModel.getPlace());
        if (priceText != null) priceText.setText(this.touristPlaceModel.getPrice());
        if (timeText != null) timeText.setText(this.touristPlaceModel.getApertureTime());
        if (descriptionText != null)  descriptionText.setText(this.touristPlaceModel.getDescription());

        changeFavoriteState((ImageView) this.findViewById(R.id.tourist_detail_favorite), this.touristPlaceModel.getFavorite());


        Picasso.with(this).load(touristPlaceModel.getImageURL()).placeholder(R.color.colorPrimary).into((ImageView)findViewById( R.id.main_header));

        ((RatingBar)this.findViewById(R.id.tourist_detail_ratingbar)).setRating(touristPlaceModel.getRating());

        Locale locale = this.getResources().getConfiguration().locale;
        ((TextView)this.findViewById(R.id.tourist_insert_currency)).setText(this.getString(R.string.currency,
                Utils.getCurrencySymbol(locale),
                Utils.getCurrencyCode(locale)));

    }

    private void changeFavoriteState(ImageView imageView, boolean isFavorite)
    {
        if (isFavorite) imageView.setImageResource(R.drawable.ic_star_white);
        else imageView.setImageResource(R.drawable.ic_star_border);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.touristPlaceModel = savedInstanceState.getParcelable(DataProvider.SERIALIZABLE_DATA_KEY);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(DataProvider.SERIALIZABLE_DATA_KEY,this.touristPlaceModel);
    }

    private void showMessage(String msg)
    {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this.getApplicationContext(), msg, duration);
        toast.show();
    }

    @Override
    public void onClick(View v) {

        int idRes = v.getId();

        if (idRes == R.id.main_place_icon)
        {
            showMessage("Place Icon");
        }else if (idRes == R.id.main_place_layout)
        {
            showMessage("Place Layout");
        }else if (idRes == R.id.tourist_detail_title)
        {
            showMessage("Place Text");
        }


    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            //Save data from edit return
            SharedPreferences sharedPreferences = getSharedPreferences(DataProvider.SERIALIZABLE_DATA_KEY,MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();

            Gson gson = new Gson();
            String json = gson.toJson(touristPlaceModel);
            edit.putString(DataProvider.SERIALIZABLE_DATA_KEY, json);
            edit.apply();


            Intent intent = new Intent();
            intent.setClassName(this, "cat.tomasgis.apps.formacio.android.initialapp.TouristicPlaceEditActivity");

            this.startActivityForResult(intent,REQUEST_CODE);
            return true;
        } else if (id == R.id.action_delete) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //TODO: Refresh view ==> ContentProvider + refresh views
            }
        }
    }
}
