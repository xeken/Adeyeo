package kr.hs.dgsw.adeyeo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
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
    private SupportMapFragment mapFragment;
    private Location GPSLocation;
    private Location NetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);

        GPSLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        NetLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        buttonGoMain2 = findViewById(R.id.buttonGoMain2);
        buttonGoMain2.setOnClickListener(v -> finish());

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap gm) {

        this.googleMap = gm;
        LatLng latLng;

        try {
            latLng = new LatLng(GPSLocation.getLatitude(), GPSLocation.getLongitude());
        } catch (Exception e) {
            latLng = new LatLng(NetLocation.getLatitude(), NetLocation.getLongitude());
            Toast.makeText(this, "GPS 신호가 원할하지 않습니다", Toast.LENGTH_SHORT).show();
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(latLng)
                .title("현재 위치");
        googleMap.addMarker(makerOptions);

        googleMap.setOnMapClickListener(v -> {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title("여기?");
            markerOptions.position(new LatLng(v.latitude, v.longitude));
            googleMap.clear();
            googleMap.addMarker(markerOptions);
        });

        googleMap.setOnMarkerClickListener(v->{
           LatLng mark =  v.getPosition();
           Intent i = new Intent(this, ResultActivity.class);
           i.putExtra("latitude", mark.latitude);
           i.putExtra("longitude", mark.longitude);
           startActivity(i);
           Toast.makeText(this,"mark = "+mark.latitude +", "+mark.longitude ,Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    public void onClick_myLocation(View view){

        try {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(GPSLocation.getLatitude(), GPSLocation.getLongitude())));
        }catch (Exception e){
            Toast.makeText(this, "GPS 신호가 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(NetLocation.getLatitude(), NetLocation.getLongitude())));
        }
    }
}
