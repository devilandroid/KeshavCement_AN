package com.loyaltyworks.keshavcement.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;


/**
 * Created by Manoj on 2018-02-28.
 */


public class RegistrationAsyncTask extends AsyncTask<String, String, String> {

    private String URL ;
    private Context context;

    //if you experience a problem with url remove the '?wsdl' ending
    BufferedReader reader;

    private String response;

    RegistrationResponse registrationResponse;
    Boolean isForUpadte = false ;

    JSONObject jsonObject ;

    //Soap Client network response interface :
    public interface RegistrationResponse{
        void onSuccess(Boolean status, String response) throws ParserConfigurationException, IOException, SAXException, JSONException;
        void onFailure(Boolean status, String response);
    }

    public RegistrationAsyncTask(Context context, String URL, JSONObject jsonObject , RegistrationResponse registrationResponse) {
        this.context=context;
        this.URL = URL;
        this.jsonObject = jsonObject ;
        this.registrationResponse = registrationResponse;
    }


    public RegistrationAsyncTask(Context context, String URL, boolean isForUpadte, RegistrationResponse registrationResponse) {
        this.context=context;
        this.URL = URL ;
        this.isForUpadte = isForUpadte ;
        this.registrationResponse = registrationResponse;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        try {

            try {

                response=null;

                java.net.URL url = new URL(URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(20000);
                if(!isForUpadte) {
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setReadTimeout(20000);
                    conn.setDefaultUseCaches(false);
                    conn.setRequestProperty("Content-type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");

                    //push the request to the server address

                    Log.d("URL_REQ :", url.toString());
                    Log.d("SOAP_REQ :", jsonObject.toString());
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(jsonObject.toString());
                    wr.flush();
                    wr.close();


                }else conn.setRequestMethod("GET");

                //get the server response
                conn.connect();

                // Get the response code
                int statusCode = conn.getResponseCode();
                Log.d("statusCode", "doInBackground: "+statusCode);

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder builder1 = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    builder1.append(line);
                    response = builder1.toString();//this is the response, parse it in onPostExecute
                }


            } catch (Exception e) {
                e.printStackTrace();
                Log.d("SOAP_REQ_FAIL",e.toString());
                //registrationResponse.onFailure(false,e.toString());

            } finally {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("SOAP_REQ_FAIL",e.toString());
                    //registrationResponse.onFailure(false,e.toString());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SOAP_REQ_FAIL",e.toString());
            //registrationResponse.onFailure(false,e.toString());
        }

        return response;
    }

    /**
     * @see AsyncTask#onPostExecute(Object)
     */
    @Override
    protected void onPostExecute(String result) {
        try {
//                Log.d("SOAP_REQ_SUCCESS",result);

            if(result==null)
                registrationResponse.onFailure(false,"Something went wrong, Please try after sometime.");
            else
                registrationResponse.onSuccess(true,result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

