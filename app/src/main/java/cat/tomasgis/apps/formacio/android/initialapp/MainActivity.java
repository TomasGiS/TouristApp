package cat.tomasgis.apps.formacio.android.initialapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cat.tomasgis.apps.formacio.android.initialapp.communication.RequestManager;
import cat.tomasgis.apps.formacio.android.initialapp.database.DataContract;
import cat.tomasgis.apps.formacio.android.initialapp.interfaces.ITouristDataAccess;
import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;
import cat.tomasgis.apps.formacio.android.initialapp.provider.DataProviderFactory;
import cat.tomasgis.apps.formacio.android.initialapp.services.FetchPlacesIntentService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{

    private final String TAG = MainActivity.class.getSimpleName();
    private static Context mContext = null;


    //ITouristDataAccess instance = null;
    //DataProvider instance = DataProvider.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this.getApplicationContext();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */

                Intent intent = new Intent(view.getContext().getApplicationContext(),
                                    TouristicPlaceInsertActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Connect to Database provider
        //instance = DataProviderFactory.getDataSource(MainActivity.this.getApplicationContext(), DataProviderFactory.TouristicDataSourceType.Database);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Map Listener

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            //updateFromServer();
            FetchPlacesIntentService.startActionFetchPlaces(this,DataContract.TouristPlace.buildPlaceServerUri().toString());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tourist_places) {
            Intent intent = new Intent(this,TouristPlacesListActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_tourist_favorite_places) {
            Intent intent = new Intent(this,TouristFavoriteListActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(41.119470, 1.245471), 16));

        //Iterator<TouristPlaceModel> iterator= instance.iterator();
        //Get ContentProvider Data

        Cursor cursor = null;
        cursor = getContentResolver().query(DataContract.TouristPlace.buildPlaceUri(),
                DataContract.TouristPlace.ALL_FIELDS,
                null,null,
                DataContract.TouristPlace.DEFAULT_SORT);

        cursor.moveToFirst();
        TouristPlaceModel tpm;
        while(!cursor.isAfterLast())
        {
            tpm = DataContract.TouristPlace.cursorToTouristPlace(cursor);
            map.addMarker(new MarkerOptions()
                    .anchor(0.0f, 1.0f)
                    .position(tpm.getLocation())
                    .title(tpm.getTitle()));
            cursor.moveToNext();
        }
        cursor.close();

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(new LatLng(41.113650, 1.240783))
                .zoom(15)
                .bearing(0)
                .build();


        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }
    AsyncTask<JSONObject, Float,Integer> asyncTask = new AsyncTask<JSONObject, Float, Integer>() {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(JSONObject... params) {

            if (params.length==1)
            {
                JSONObject response = params[0];
                Gson gson = new Gson();
                try{
                    JSONArray array = response.getJSONArray("locations");
                    JSONObject jsonObject;
                    for (int index=0;index<array.length();index++)
                    {
                        jsonObject = array.getJSONObject(index);
                        TouristPlaceModel touristPlaceModel = gson.fromJson(jsonObject.toString(),TouristPlaceModel.class);
                        Log.e(TAG,"Response? "+ touristPlaceModel.getTitle());
                    }
                }catch (JSONException e)
                {e.printStackTrace();}
            }

            return 100;
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            Toast.makeText(mContext,"Actualització", Toast.LENGTH_SHORT).show();
        }
    };

}
