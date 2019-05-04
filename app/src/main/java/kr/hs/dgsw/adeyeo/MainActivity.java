package kr.hs.dgsw.adeyeo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent i = new Intent(this, ResultActivity.class);
                i.putExtra("address", editTextSearch.getText().toString());
                startActivity(i);
            }
            return false;
        });

        Button buttonGoMap = findViewById(R.id.buttonGoMap);
        buttonGoMap.setOnClickListener(v -> {
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
        });

        ImageButton imageButtonGoResult = findViewById(R.id.imageButtonGoResult);
        imageButtonGoResult.setOnClickListener( v -> {
            Intent i = new Intent(this, ResultActivity.class);
            i.putExtra("address", editTextSearch.getText().toString());
            startActivity(i);
        });
    }
}
