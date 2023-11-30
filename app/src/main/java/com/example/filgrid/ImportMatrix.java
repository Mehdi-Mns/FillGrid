package com.example.filgrid;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ImportMatrix extends AsyncTask<String, Void, String> {

    Activity myActivity;

    public ImportMatrix(Activity activity) {
        myActivity = activity;
    }

    @Override
    protected String doInBackground(String... uri) {
        String responseString = null;
        try {
            HttpsTrustManager.allowAllSSL();
            URL url = new URL(uri[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) { builder.append(inputString); }
            responseString = builder.toString();
            urlConnection.disconnect();
        } catch (Exception e) { e.printStackTrace(); }
        return responseString;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        TextView mat = myActivity.findViewById(R.id.matrice);
        mat.setText(result);

    }
}

