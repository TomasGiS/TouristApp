package cat.tomasgis.apps.formacio.android.initialapp;

import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;

public class TouristicPlaceInsertActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText imageURLView;
    private ImageView imageView;
    private EditText nameView;
    private EditText apertureTimeView;
    private EditText priceView;
    private EditText addressView;
    private MapFragment mapFragment;

    RequestQueue queue;
    TouristPlaceModel touristPlaceModel;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
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

        mapFragment = (MapFragment)this.getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getView().setVisibility(View.GONE);


        nameView.setOnFocusChangeListener(this);
        apertureTimeView.setOnFocusChangeListener(this);
        priceView.setOnFocusChangeListener(this);

        //Show image

        imageURLView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    String urlStr = ((EditText) v).getText().toString();
                    if (!urlStr.isEmpty())
                        Picasso.with(v.getContext().getApplicationContext()).load(urlStr).into(imageView);

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

                        LatLng location = getGeocodingAddress(addressStr);
                        showMapData(location);
                    }
                }
            }
            });
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
            checkAllFields();
        } else {
            mapFragment.getView().setVisibility(View.GONE);
        }
    }


    private LatLng getGeocodingAddress(String addressStr) {

        Geocoder geocoder = new Geocoder(this);
        LatLng location = null;
        try {
            List<Address> addressList = geocoder.getFromLocationName(addressStr,1);
            if (addressList.size() == 0) addressView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            else {
                addressView.setTextColor(getResources().getColor(android.R.color.holo_green_light));
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
        return true;
    }



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int visible = View.INVISIBLE;
        if (checkAllFields())
            visible = View.VISIBLE;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(visible);
    }
}
