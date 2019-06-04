package kr.hs.dgsw.adeyeo.connect;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
            Log.e("TAG", "PARSING ERROR");
        }

        return address;
    }
}
