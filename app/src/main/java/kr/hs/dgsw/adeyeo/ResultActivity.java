package kr.hs.dgsw.adeyeo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.hs.dgsw.adeyeo.domain.ResultValues;
import kr.hs.dgsw.adeyeo.get.AddressToSearch;
import kr.hs.dgsw.adeyeo.recycler.RecyclerAdapter;

public class ResultActivity extends AppCompatActivity implements OnMapReadyCallback {

    private RecyclerAdapter adapter;
    private GoogleMap googleMap;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        intent = getIntent();
        String address = intent.getStringExtra("address");

        TextView test = findViewById(R.id.textViewAddress);
        test.setText(address);

        ImageButton buttonGoMap = findViewById(R.id.buttonGoMain);
        buttonGoMap.setOnClickListener(v -> finish());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        init();
        getData(address);
    }

    private void init() {

        RecyclerView recyclerView = findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(String address){

        AddressToSearch addressToSearch = new AddressToSearch(address, this);
        ResultValues resultValues = addressToSearch.get();
        adapter.addItem(resultValues);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        LatLng recent = new LatLng(intent.getDoubleExtra("lat",35.6629059), intent.getDoubleExtra("lon", 128.4137486));
        MarkerOptions makerOptions = new MarkerOptions();
        this.googleMap.addMarker(makerOptions.position(recent).title("현재 위치"));
        this.googleMap.getUiSettings().setAllGesturesEnabled(false);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(recent, 18));
    }
}
