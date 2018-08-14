package com.example.loanshark;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncHTTPPost extends AsyncTask<String, String, String> {
    String address;
    String parameters;
    public AsyncHTTPPost(String address) {
        this.address = address;
        this.parameters = parameters;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(address);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String response = br.readLine();
            br.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    @Override
    protected void onPostExecute(String output){
//do Something with the output
    }
}