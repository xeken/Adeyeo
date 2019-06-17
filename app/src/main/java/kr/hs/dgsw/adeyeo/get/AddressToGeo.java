package kr.hs.dgsw.adeyeo.get;

import android.content.Context;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.hs.dgsw.adeyeo.R;
import kr.hs.dgsw.adeyeo.connect.Address;

public class AddressToGeo {

    private String address;
    private Context context;

    public AddressToGeo(String address, Context context) {
        this.address = address;
        this.context = context;
    }

    public double[] get(){
        String requestUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=";
        try { requestUrl += URLEncoder.encode(this.address, "UTF-8"); }
        catch (UnsupportedEncodingException e) { Log.e("AddressToGeo", "get: ", e);}
        requestUrl += "&key=" + this.context.getResources().getString(R.string.google_maps_key);
        requestUrl += "&language=ko";

        Address threadAddress = new Address(requestUrl);
        return threadAddress.getLatLng();
    }
}
