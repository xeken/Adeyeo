package kr.hs.dgsw.adeyeo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private String address;
    private String latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");

        TextView test = findViewById(R.id.textViewAddress);
        test.setText(address);

        ImageButton buttonGoMap = findViewById(R.id.buttonGoMain);
        buttonGoMap.setOnClickListener(v -> finish());
    }
}
