package kr.hs.dgsw.adeyeo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.hs.dgsw.adeyeo.get.GeoToAddress;

import static kr.hs.dgsw.adeyeo.MainActivity.REQUEST_CODE_LOCATION;
import static kr.hs.dgsw.adeyeo.MainActivity.locationManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private Location GPSLocation;
    private Location NetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ImageButton buttonGoMain = findViewById(R.id.buttonGoMain2);
        buttonGoMain.setOnClickListener(v -> finish());

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        } else {

            GPSLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            NetLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                GPSLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                NetLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                mapFragment.getMapAsync(this);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        LatLng latLng;

        try {
            latLng = new LatLng(GPSLocation.getLatitude(), GPSLocation.getLongitude());
        } catch (Exception e) {
            latLng = new LatLng(NetLocation.getLatitude(), NetLocation.getLongitude());
            Toast.makeText(this, "GPS 신호가 원할하지 않습니다", Toast.LENGTH_SHORT).show();
        }
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

        MarkerOptions markerOptions = new MarkerOptions();
        //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_placehorder));
        this.googleMap.addMarker(markerOptions.position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        this.googleMap.setOnMapClickListener(v -> {
            markerOptions.position(new LatLng(v.latitude, v.longitude));
            googleMap.clear();
            googleMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        });

        this.googleMap.setOnMarkerClickListener(v -> {
            LatLng mark = v.getPosition();
            GeoToAddress geoToAddress = new GeoToAddress(mark.latitude, mark.longitude, this);
            Toast.makeText(this, geoToAddress.get(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("address", geoToAddress.get());
            intent.putExtra("lat", mark.latitude);
            intent.putExtra("lng", mark.longitude);
            startActivity(intent);

            return true;
        });
    }

    public void onClick_myLocation(View view) {

        LatLng recent;
        try { recent = new LatLng(GPSLocation.getLatitude(), GPSLocation.getLongitude()); }
        catch (Exception e) {
            recent = new LatLng(NetLocation.getLatitude(), NetLocation.getLongitude());
            Toast.makeText(this, "GPS 신호가 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(recent, 17));
        googleMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        this.googleMap.addMarker(markerOptions.position(recent).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }

}
