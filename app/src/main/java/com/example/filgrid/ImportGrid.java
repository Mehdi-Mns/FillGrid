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

class ImportGrille extends AsyncTask<String, Void, String> {

    Activity myActivity;

    public ImportGrille(Activity activity) {
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
        String str = result;

        Pattern p1 = Pattern.compile("<a");
        Pattern p2 = Pattern.compile("</a>");
        Matcher m1 = p1.matcher(str);
        Matcher m2 = p2.matcher(str);
        ArrayList<Integer> debut = new ArrayList<Integer>();
        ArrayList<Integer> fin = new ArrayList<Integer>();
        ArrayList<Integer> debut2 = new ArrayList<Integer>();
        ArrayList<Integer> fin2 = new ArrayList<Integer>();
        ArrayList<String> a = new ArrayList<String>();
        ArrayList<String> img = new ArrayList<String>();
        ArrayList<String> href = new ArrayList<String>();

// Get the group matched using group() method
        while (m1.find())
            debut.add(m1.start());
        while (m2.find())
            fin.add(m2.start());
        for(int i = 0; i<debut.size() - 1; i++)
        {
            a.add(str.substring(debut.get(i), fin.get(i)));
        }

        for(int i = 0; i < a.size(); i++)
        {
            Pattern p21 = Pattern.compile("href");
            Pattern p22 = Pattern.compile("<img");
            Matcher m21 = p21.matcher(a.get(i));
            Matcher m22 = p22.matcher(a.get(i));
            m21.find(); m22.find();
            href.add(a.get(i).substring(m21.start() + 7, m22.start() - 2));
            img.add(a.get(i).substring(m22.start() + 10, a.get(i).length() - 2));
        }

        TextView ref = myActivity.findViewById(R.id.href);
        TextView im = myActivity.findViewById(R.id.img);
        ref.setText(href.toString());
        im.setText(img.toString());


    }
}
