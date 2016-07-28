package cat.tomasgis.apps.formacio.android.initialapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cat.tomasgis.apps.formacio.android.initialapp.communication.RequestManager;
import cat.tomasgis.apps.formacio.android.initialapp.database.DataContract;
import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FetchPlacesIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String FETCH_PLACES = "cat.tomasgis.apps.formacio.android.initialapp.services.action.FETCH_PLACES";
    private static final String EXTRA_URL = "cat.tomasgis.apps.formacio.android.initialapp.services.extra.URL";

    private static final String TAG = "FetchPlacesIntentService";
    public FetchPlacesIntentService() {
        super("FetchPlacesIntentService");
    }


    public static void startActionFetchPlaces(Context context, String url) {
        Intent intent = new Intent(context, FetchPlacesIntentService.class);
        intent.setAction(FETCH_PLACES);
        intent.putExtra(EXTRA_URL, url);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (FETCH_PLACES.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_URL);
                handleActionFetchPlaces(url);
            }
        }
    }


    private void handleActionFetchPlaces(String url) {

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET,
                            url,
                            null, new Response.Listener<JSONObject>() {


                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG,"Refresh request ok");

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
                            //asyncTask.execute(response);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.e(TAG,"Refresh request Error");

                        }
                    });
            RequestManager.getInstance(this.getApplicationContext()).addToRequestQueue(jsObjRequest);

        //throw new UnsupportedOperationException("Not yet implemented");
    }

}
