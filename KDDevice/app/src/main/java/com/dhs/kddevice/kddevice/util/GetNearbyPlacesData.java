package com.dhs.kddevice.kddevice.util;

import android.os.AsyncTask;


/**
 * Created by Chinnaraj on 3/11/2018.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {
    String googlePlacesData;
    String url;
    @Override
    protected String doInBackground(Object... objects) {
        url = (String)objects[0];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlacesData= downloadUrl.redUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        PreferenceHolder.getInstance().setLocationsList(new DataParser().parse(s));
    }
 }
