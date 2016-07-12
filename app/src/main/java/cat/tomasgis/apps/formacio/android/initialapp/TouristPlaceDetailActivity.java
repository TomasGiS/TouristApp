package cat.tomasgis.apps.formacio.android.initialapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cat.tomasgis.apps.formacio.android.initialapp.model.TouristPlaceModel;
import cat.tomasgis.apps.formacio.android.initialapp.provider.DataProvider;

public class TouristPlaceDetailActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private final String TAG = TouristPlaceDetailActivity.class.getSimpleName();
    String dataName;
    TouristPlaceModel touristPlaceModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_place_detail);

        Intent intent = this.getIntent();

        Bundle bundle = intent.getExtras();
        if (savedInstanceState != null)
        {
            this.touristPlaceModel = (TouristPlaceModel)savedInstanceState.getSerializable(DataProvider.SERIALIZABLE_DATA_KEY);
            dataName = this.touristPlaceModel.getTitle();
        }
        else if (bundle != null){
            dataName = bundle.getString(TouristPlaceModel.TITLE);
            touristPlaceModel = (TouristPlaceModel) bundle.getSerializable(DataProvider.SERIALIZABLE_DATA_KEY);
            Log.d(TAG,"Get data bundle");
        }
        else
        {
            Log.e(TouristPlaceDetailActivity.class.getSimpleName(),
                    "Falta la variable DATA_NAME");

            Toast.makeText(this,"Falta la variable DATA_NAME",Toast.LENGTH_LONG).show();
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
        //if (placeText != null) placeText.setText(this.touristPlaceModel.getPlace());
        if (priceText != null) priceText.setText(this.touristPlaceModel.getPrice());
        if (timeText != null) timeText.setText(this.touristPlaceModel.getApertureTime());
        if (descriptionText != null)  descriptionText.setText(this.touristPlaceModel.getDescription());


        ImageView placeIcon = (ImageView) this.findViewById(R.id.main_place_icon);
        LinearLayout  placeLayout = (LinearLayout)this.findViewById(R.id.main_place_layout);

        ImageView mainHeader = (ImageView) this.findViewById(R.id.main_header);
        if (mainHeader != null)
            mainHeader.setOnTouchListener(this);

        if (placeIcon != null)
            placeIcon.setOnClickListener(this);

        if (placeLayout != null)
            placeLayout.setOnClickListener(this);


        /*

        placeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Place text");
            }
        });

        placeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Place Icon");
            }
        });

        placeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Place Layout");
            }
        });
    */

    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.touristPlaceModel = (TouristPlaceModel) savedInstanceState.getSerializable(DataProvider.SERIALIZABLE_DATA_KEY);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DataProvider.SERIALIZABLE_DATA_KEY,this.touristPlaceModel);
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
    public boolean onTouch(View v, MotionEvent event) {

        if (v.getId() == R.id.main_header)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                showMessage("Going Down");
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP)
            {
                showMessage("Going UP");
                return true;
            }
        }
        return false;
    }
}
