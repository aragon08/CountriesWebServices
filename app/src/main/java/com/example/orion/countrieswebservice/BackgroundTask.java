package com.example.orion.countrieswebservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Orion on 25/03/2017.
 */

public class BackgroundTask extends AsyncTask<String, Void, String> {

    private Context context;
    private ProgressDialog pDialog;
    AsyncResponse response;
    private String loading_message;

    public BackgroundTask(Context context, String loading_message, AsyncResponse response){
        this.context=context;
        this.response=response;
        this.loading_message=loading_message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog=new ProgressDialog(context);
        pDialog.setTitle("Loading...");
        pDialog.setMessage(this.loading_message);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.dismiss();
        response.finishProccess(s);
    }

    @Override
    protected String doInBackground(String[] params) {
        String jsonResult="";
        try{
            Thread.sleep(3000);
            jsonResult=inputStreamToString(OpenHttpConnection(params[0])).toString();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (IOException e1){

        }

        return jsonResult;
    }

    private InputStream OpenHttpConnection(String urlString)
            throws IOException
    {
        InputStream in = null;
        int response = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{

            HttpURLConnection httpConn = (HttpURLConnection) conn;

            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex) {
            throw new IOException("Error connecting..." + urlString + " :" + ex.getMessage());
        }
        return in;
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        }

        catch (IOException e) {
            // e.printStackTrace();
            Toast.makeText(context,"Error..." + e.toString(), Toast.LENGTH_LONG).show();
        }
        return answer;
    }
}
