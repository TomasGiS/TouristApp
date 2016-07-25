package cat.tomasgis.apps.formacio.android.initialapp;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cat.tomasgis.apps.Utils.Utils;
import cat.tomasgis.apps.formacio.android.initialapp.database.DataContract;
import cat.tomasgis.apps.formacio.android.initialapp.interfaces.ITouristDataAccess;
import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;
import cat.tomasgis.apps.formacio.android.initialapp.provider.DataProvider;
import cat.tomasgis.apps.formacio.android.initialapp.provider.DataProviderFactory;

//TODO:check rotation. Save data?
public class TouristicPlaceInsertActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText imageURLView;
    private ImageView imageView;
    private EditText nameView;
    private EditText apertureTimeView;
    private EditText priceView;
    private EditText addressView;
    private RatingBar ratingView;
    private EditText descriptionView;
    private MapFragment mapFragment;
    private ImageView favoriteView;

    private LatLng location;
    private boolean isFavorite = false;

    TouristPlaceModel touristPlaceModel;

    //ITouristDataAccess instance= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touristic_place_insert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = getString(R.string.place_not_added);

                if (location!= null) {

                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    long time = calendar.getTime().getTime();

                    touristPlaceModel = new TouristPlaceModel(
                            String.valueOf(time),
                            nameView.getText().toString(),
                            descriptionView.getText().toString(),
                            apertureTimeView.getText().toString(),
                            addressView.getText().toString(),
                            priceView.getText().toString(),
                            location,
                            imageURLView.getText().toString(),
                            ratingView.getRating(),isFavorite

                    );
                    msg = getString(R.string.place_added);
                    //Insert using database SQLOpenHelper
                    //instance.addTouristPlace(touristPlaceModel);

                    //TODO: First check update
                    //Insert using contentprovider
                    getContentResolver().insert(DataContract.TouristPlace.buildPlaceUri(),DataContract.TouristPlace.touristPlaceToContentValues(touristPlaceModel));
                }
                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                        .show();
                finish();

            }
        });

        //Data provider connect
        //instance = DataProviderFactory.getDataSource(this, DataProviderFactory.TouristicDataSourceType.DabaseData);

        //Load data for edit
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
    }

    private void initData(TouristPlaceModel touristPlaceModel)
    {
        if (touristPlaceModel != null) {
            nameView.setText(touristPlaceModel.getTitle());
            Picasso.with(imageView.getContext().getApplicationContext()).load(touristPlaceModel.getImageURL()).into(imageView);
            apertureTimeView.setText(touristPlaceModel.getApertureTime());
            priceView.setText(touristPlaceModel.getPrice());
            addressView.setText(touristPlaceModel.getPlace());
            ratingView.setRating(touristPlaceModel.getRating());
            descriptionView.setText(touristPlaceModel.getDescription());
            if (touristPlaceModel.getFavorite())
                favoriteView.setImageResource(R.drawable.ic_star_white);
            else
                favoriteView.setImageResource(R.drawable.ic_star_border);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        //Check all fields filled
        showAddButton(checkAllFields());

        //Show

    }

    @Override
    protected void onStart() {
        super.onStart();

        imageURLView = (EditText) this.findViewById(R.id.tourist_insert_image_name);
        imageView = (ImageView) this.findViewById(R.id.tourist_insert_image);
        nameView = (EditText) this.findViewById(R.id.tourist_insert_name);
        apertureTimeView = (EditText) this.findViewById(R.id.tourist_insert_aperture);
        priceView = (EditText) this.findViewById(R.id.tourist_insert_price);
        addressView = (EditText) this.findViewById(R.id.tourist_insert_place);
        ratingView = (RatingBar)this.findViewById(R.id.tourist_insert_rate);
        descriptionView = (EditText)this.findViewById(R.id.tourist_insert_description);
        favoriteView = (ImageView) this.findViewById(R.id.tourist_insert_favorite);

        mapFragment = (MapFragment)this.getFragmentManager().findFragmentById(R.id.map);
        //TODO: show map if the address is valid
        mapFragment.getView().setVisibility(View.GONE);

        Locale locale = this.getResources().getConfiguration().locale;
        ((TextView)this.findViewById(R.id.tourist_insert_currency)).setText(this.getString(R.string.currency,
                Utils.getCurrencySymbol(locale),
                Utils.getCurrencyCode(locale)));


        nameView.setOnFocusChangeListener(this);
        apertureTimeView.setOnFocusChangeListener(this);
        priceView.setOnFocusChangeListener(this);
        descriptionView.setOnFocusChangeListener(this);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkAllFields()) {
                    if (s.length() > 0) showAddButton(true);
                    else showAddButton(false);
                }
                else showAddButton(false);
            }
        };

        //TextWatcher
        imageURLView.addTextChangedListener(watcher);
        nameView.addTextChangedListener(watcher);
        apertureTimeView.addTextChangedListener(watcher);
        priceView.addTextChangedListener(watcher);
        addressView.addTextChangedListener(watcher);
        descriptionView.addTextChangedListener(watcher);

        //Show image
        imageURLView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    String urlStr = ((EditText) v).getText().toString();
                    if (!urlStr.isEmpty()) {
                        Picasso.with(v.getContext().getApplicationContext()).load(urlStr).into(imageView);
                        showAddButton(checkAllFields());
                    }
                }
            }
        });

        //Show map

        addressView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String addressStr = ((EditText) v).getText().toString();
                    if (!addressStr.isEmpty()) {

                        location = getGeocodingAddress(addressStr);
                        showMapData(location);
                        showAddButton(checkAllFields());
                    }
                }
            }
            });

        //Rating bar
        //Do nothing

        //Favorite
        favoriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFavoriteState();
            }
        });

        initData(touristPlaceModel);


    }

    private void changeFavoriteState()
    {
        isFavorite = !isFavorite;
        if (isFavorite) favoriteView.setImageResource(R.drawable.ic_star_white);
        else favoriteView.setImageResource(R.drawable.ic_star_border);
    }

    private void showMapData(LatLng location) {
        if (location != null) {
            GoogleMap map = mapFragment.getMap();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    location, 16));
            map.clear();
            map.addMarker(new MarkerOptions()
                    .anchor(0.0f, 1.0f)
                    .position(location));
            mapFragment.getView().setVisibility(View.VISIBLE);
            //Check all fields data
            showAddButton(checkAllFields());
        } else {
            mapFragment.getView().setVisibility(View.GONE);
        }
    }

    private void showAddButton(boolean show) {
        int visible = View.INVISIBLE;
        if (show)
            visible = View.VISIBLE;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(visible);
    }


    private LatLng getGeocodingAddress(String addressStr) {

        Geocoder geocoder = new Geocoder(this);
        LatLng location = null;
        try {
            List<Address> addressList = geocoder.getFromLocationName(addressStr,1);
            if (addressList.size() == 0) addressView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            else {
                addressView.setTextColor(getResources().getColor(android.R.color.primary_text_light));
                location = new LatLng(addressList.get(0).getLatitude(),addressList.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return location;
    }




    private boolean checkAllFields()
    {
        if (imageURLView.getText().toString().isEmpty()) return false;
        //Image how check
        if (apertureTimeView.getText().toString().isEmpty())return false;
        if (priceView.getText().toString().isEmpty())return false;
        if (nameView.getText().toString().isEmpty()) return false;
        if (addressView.getText().toString().isEmpty()) return false;
        if (descriptionView.getText().toString().isEmpty()) return false;
        return true;
    }



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        showAddButton(checkAllFields());
    }
}
