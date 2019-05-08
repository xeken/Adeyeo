package kr.hs.dgsw.adeyeo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static kr.hs.dgsw.adeyeo.MainActivity.REQUEST_CODE_LOCATION;
import static kr.hs.dgsw.adeyeo.MainActivity.locationManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private ImageButton buttonGoMain2;
    private ImageButton buttonMyLocation;
    private SupportMapFragment mapFragment;
    private Location myLocation;

    //gps 켜져있는지 확인할 것
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);

        myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        buttonGoMain2 = findViewById(R.id.buttonGoMain2);
        buttonGoMain2.setOnClickListener(v -> finish());

        buttonMyLocation = findViewById(R.id.buttonMyLocation);
        buttonMyLocation.setOnClickListener(v -> googleMap.moveCamera(CameraUpdateFactory
                .newLatLng(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))));

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap gm) {

        this.googleMap = gm;
        LatLng latLng;

        try {
            latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        } catch (Exception e) {
            latLng = new LatLng(35.397860, 128.248090);
            Toast.makeText(this, "GPS 신호가 원할하지 않습니다", Toast.LENGTH_SHORT).show();
        }

        googleMap.setOnMapClickListener(v -> {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title("여기?");
            markerOptions.position(new LatLng(v.latitude, v.longitude));
            googleMap.clear();
            googleMap.addMarker(markerOptions);
        });

        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(latLng)
                .title("현재 위치");
        googleMap.addMarker(makerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

}
