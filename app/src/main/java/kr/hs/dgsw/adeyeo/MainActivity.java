package kr.hs.dgsw.adeyeo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static LocationManager locationManager;
    public final static int REQUEST_CODE_LOCATION = 37;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent i = new Intent(this, ResultActivity.class);
                i.putExtra("address", editTextSearch.getText().toString());
                startActivity(i);
            }
            return false;
        });

        Button buttonGoMap = findViewById(R.id.buttonGoMap);
        buttonGoMap.setOnClickListener(v -> {

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this, "GPS 켜달라 이말이야.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(i);
            }
            else {
                Intent i = new Intent(this, MapsActivity.class);
                startActivity(i);
            }
        });

        ImageButton imageButtonGoResult = findViewById(R.id.imageButtonGoResult);
        imageButtonGoResult.setOnClickListener(v -> {
            Intent i = new Intent(this, ResultActivity.class);
            i.putExtra("address", editTextSearch.getText().toString());
            startActivity(i);
        });
    }

}
