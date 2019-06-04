package kr.hs.dgsw.adeyeo.connect;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.hs.dgsw.adeyeo.domain.Results;

public class SearchAddress {

    private String url;

    public SearchAddress(String url){

        this.url = url;
    }

    public Results getSearched(){

        ConnectThread connection = new ConnectThread(this.url);
        connection.start();

        Results results = new Results();

        try{

            connection.join();
            JsonParser jsonParser = new JsonParser();

            JsonObject json = (JsonObject) jsonParser.parse(connection.connectionReturn);
            Log.e("TAG", String.valueOf(json));
            JsonObject result = json.getAsJsonObject("results");
            JsonObject juso = (JsonObject) result.getAsJsonArray("juso").get(0);

            results.setZip(juso.get("zipNo").getAsString());
            results.setEng(juso.get("roadAddr").getAsString());
            results.setKor(juso.get("korAddr").getAsString());

        }catch (Exception e){
            Log.d("TAG", "PARSING ERROR");
            return null;
        }
        return results;
    }
}
