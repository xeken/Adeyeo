package kr.hs.dgsw.adeyeo.connect;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;

public class Address {

    private String url;

    public Address(String url){
        this.url = url;
    }

    public String getAddress(){

        ConnectThread connection = new ConnectThread(this.url);
        connection.start();

        String address = "";

        try {

            connection.join();
            JsonParser jsonParser = new JsonParser();

            JsonObject json = (JsonObject) jsonParser.parse(connection.connectionReturn);

            JsonArray json_results = json.getAsJsonArray("results");
            JsonObject object = (JsonObject) json_results.get(0);
            address = object.get("formatted_address").getAsString();

        }catch (Exception e){
            Log.e("TAG", "PARSING ERROR", e);
            address = "주소를 확인해주세요.";
        }

        return address;
    }

    public double[] getLatLng(){

        ConnectThread connection = new ConnectThread(this.url);
        connection.start();

        double[] latLng ={0, 0};
        try {

            connection.join();
            JsonParser jsonParser = new JsonParser();

            JsonObject json = (JsonObject) jsonParser.parse(connection.connectionReturn);

            JsonArray json_results = json.getAsJsonArray("results");
            JsonObject json_results_result = (JsonObject) json_results.get(0);
            Log.e("어드레쓰 쓰레드 안 리졸츠 리졸트", String.valueOf(json_results_result));
            JsonObject geometry = json_results_result.getAsJsonObject("geometry");
            Log.e("어드레쓰 쓰레드 안 지오메트리", String.valueOf(geometry));
            JsonObject location = geometry.getAsJsonObject("location");
            latLng[0] = location.get("lat").getAsDouble();
            latLng[1] = location.get("lng").getAsDouble();

        }catch (Exception e){
            Log.e("TAG", "어드레스 쓰레드 파씽 에러", e);
            latLng[0] = 35.6629059;
            latLng[1] = 128.4137486;
        }
        Log.e("주소 투 좌표", "좌표" + Arrays.toString(latLng));
        return latLng;

    }
}
