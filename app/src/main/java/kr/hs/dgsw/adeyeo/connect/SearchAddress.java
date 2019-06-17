package kr.hs.dgsw.adeyeo.connect;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.hs.dgsw.adeyeo.domain.ResultValues;

public class SearchAddress {

    private String url;

    public SearchAddress(String url){

        this.url = url;
    }

    public ResultValues getSearched(){

        ConnectThread connection = new ConnectThread(this.url);
        connection.start();

        ResultValues results = new ResultValues();

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
            results.setZip("");
            results.setEng("주소를 확인해주세요");
            results.setKor("");
        }
        return results;
    }
}
