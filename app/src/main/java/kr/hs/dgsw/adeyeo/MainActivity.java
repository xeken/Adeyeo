package kr.hs.dgsw.adeyeo;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Arrays;

import kr.hs.dgsw.adeyeo.get.AddressToGeo;

public class MainActivity extends AppCompatActivity {

    protected static final int REQUEST_CODE_LOCATION = 200;
    public static LocationManager locationManager;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        editTextSearch = findViewById(R.id.editTextSearch);
        ImageButton goResult = findViewById(R.id.imageButtonGoResult);
        Button goMap = findViewById(R.id.buttonGoMap);

        editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent i = new Intent(this, ResultActivity.class);
                i.putExtra("address", v.getText().toString());
                startActivity(i);
            }
            return false;
        });
        goResult.setOnClickListener(v -> {

            AddressToGeo addressToGeo = new AddressToGeo(editTextSearch.getText().toString(), this);
            double[] latLng = addressToGeo.get();
            double lat = latLng[0];
            double lng = latLng[1];

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("address", editTextSearch.getText().toString());
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            startActivity(intent);
        });

        goMap.setOnClickListener(v -> {

            Intent intent;

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);
                Toast.makeText(this, "GPS 기능을 ON 해주세요", Toast.LENGTH_SHORT).show();
            }
            else {

                intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

}
