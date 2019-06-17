package kr.hs.dgsw.adeyeo.get;

import android.content.Context;

import kr.hs.dgsw.adeyeo.R;
import kr.hs.dgsw.adeyeo.connect.Address;

public class GeoToAddress {

    private Double lat;
    private Double lng;
    private Context context;

    public GeoToAddress(Double lat, Double lng, Context context) {
        this.lat = lat;
        this.lng = lng;
        this.context = context;
    }

    public String get(){

        String requestUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
        requestUrl += this.lat + "," + this.lng;
        requestUrl += "&key=" + this.context.getResources().getString(R.string.google_maps_key);
        requestUrl += "&language=ko";

        Address threadAddress = new Address(requestUrl);
        String address = threadAddress.getAddress();
        String returnStr;

        if (address.substring(0, 4).equals("대한민국"))
            returnStr = address.substring(5);
        else
            returnStr = "주소를 확인해주세요.";

        return returnStr;
    }
}
