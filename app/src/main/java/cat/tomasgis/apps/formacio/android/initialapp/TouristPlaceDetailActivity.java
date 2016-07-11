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

import org.w3c.dom.Text;

import java.util.zip.DataFormatException;

public class TouristPlaceDetailActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    String dataName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();

        Bundle bundle = intent.getExtras();
        if (bundle != null){
            dataName = bundle.getString("DATA_NAME");
        }
        else
        {
            Log.e(TouristPlaceDetailActivity.class.getSimpleName(),
                    "Falta la variable DATA_NAME");

            Toast.makeText(this,"Falta la variable DATA_NAME",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView placeText = (TextView) this.findViewById(R.id.main_place_text);
        ImageView placeIcon = (ImageView) this.findViewById(R.id.main_place_icon);
        LinearLayout  placeLayout = (LinearLayout)this.findViewById(R.id.main_place_layout);

        ImageView mainHeader = (ImageView) this.findViewById(R.id.main_header);
        mainHeader.setOnTouchListener(this);

        placeIcon.setOnClickListener(this);
        placeLayout.setOnClickListener(this);
        placeText.setOnClickListener(this);
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
        }else if (idRes == R.id.main_place_text)
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
