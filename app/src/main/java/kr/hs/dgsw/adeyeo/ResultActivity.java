package kr.hs.dgsw.adeyeo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.hs.dgsw.adeyeo.connect.SearchAddress;
import kr.hs.dgsw.adeyeo.domain.Results;

public class ResultActivity extends AppCompatActivity {

    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        address = intent.getStringExtra("address");

        TextView test = findViewById(R.id.textViewAddress);
        test.setText(address);

        ImageButton buttonGoMap = findViewById(R.id.buttonGoMain);
        buttonGoMap.setOnClickListener(v -> finish());
    }

    private Results getSearchAddress(String address) {

        String requestUrl = "http://www.juso.go.kr/addrlink/addrEngApi.do?";
        requestUrl += "currentPage=1&countPerPage=10";
        try { requestUrl += "&keyword="+ URLEncoder.encode(address,"UTF-8"); }
        catch (UnsupportedEncodingException e) { Log.e("TAG", "getSearchAddress: ", e); }
        requestUrl += "&confmKey=" +getResources().getString(R.string.juso_api_key);
        requestUrl += "&resultType=json";

        SearchAddress searchAddress = new SearchAddress(requestUrl);
        return searchAddress.getSearched();
    }
}
