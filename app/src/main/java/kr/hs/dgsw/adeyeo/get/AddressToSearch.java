package kr.hs.dgsw.adeyeo.get;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.hs.dgsw.adeyeo.R;
import kr.hs.dgsw.adeyeo.connect.SearchAddress;
import kr.hs.dgsw.adeyeo.domain.ResultValues;

public class AddressToSearch {

    private String address;
    private Context context;

    public AddressToSearch(String address, Context context) {
        this.address = address;
        this.context = context;
    }

    public ResultValues get() {

        String requestUrl = "http://www.juso.go.kr/addrlink/addrEngApi.do?";
        requestUrl += "currentPage=1&countPerPage=10";
        try { requestUrl += "&keyword=" + URLEncoder.encode(this.address, "UTF-8"); }
        catch (UnsupportedEncodingException e) { e.printStackTrace(); }
        requestUrl += "&confmKey=" +this.context.getResources().getString(R.string.juso_api_key);
        requestUrl += "&resultType=json";

        SearchAddress searchAddress = new SearchAddress(requestUrl);
        return searchAddress.getSearched();
    }
}
