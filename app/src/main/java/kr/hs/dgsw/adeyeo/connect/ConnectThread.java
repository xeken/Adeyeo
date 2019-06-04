package kr.hs.dgsw.adeyeo.connect;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ConnectThread extends Thread{

    private String url;
    String connectionReturn;

    ConnectThread(String url){
        this.url = url;
    }

    @Override
    public void run() {

        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            String result;
            while ((result = bufferedReader.readLine()) != null)
                stringBuilder.append(result).append("\n");

            this.connectionReturn = stringBuilder.toString();
            bufferedReader.close();

        } catch (Exception e){
            Log.e("connect", "Connect Error", e);
        }
    }
}
