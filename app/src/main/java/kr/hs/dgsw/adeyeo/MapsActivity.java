package kr.hs.dgsw.adeyeo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private LocationManager locationManager;
    private final static int REQUEST_CODE_LOCATION = 37;
    private ImageButton buttonGoMain2;
    private ImageButton buttonMyLocation;
    private SupportMapFragment mapFragment;

    //gps 켜져있는지 확인할 것
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        buttonGoMain2 = findViewById(R.id.buttonGoMain2);
        buttonGoMain2.setOnClickListener(v -> finish());

        buttonMyLocation = findViewById(R.id.buttonMyLocation);
        buttonMyLocation.setOnClickListener(v -> googleMap.moveCamera(CameraUpdateFactory
                .newLatLng(new LatLng(getMyLocation().getLatitude(), getMyLocation().getLongitude()))));

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap gm) {

        this.googleMap = gm;
        Location currentLocation = getMyLocation();
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        googleMap.setOnMapClickListener(v->{
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

    private Location getMyLocation() {

        Location currentLocation = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        else {
            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
        }
        return currentLocation;
    }

}
