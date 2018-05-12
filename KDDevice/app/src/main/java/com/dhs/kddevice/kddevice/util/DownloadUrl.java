package com.dhs.kddevice.kddevice.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Chinnaraj on 3/11/2018.
 */

public class DownloadUrl {
    public String redUrl(String myURL) throws IOException
    {
        String data ="";
        InputStream inputstream = null;
        HttpURLConnection urlConnection =null;
        try {
            URL url = new URL(myURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            inputstream = urlConnection.getInputStream();
            BufferedReader br= new BufferedReader(new InputStreamReader(inputstream));
            StringBuffer sb = new StringBuffer();

            String line ="";
            while ((line=br.readLine()) !=null)
            {
                sb.append(line);
            }

            data= sb.toString();
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (inputstream != null) inputstream.close();
            if (urlConnection != null) urlConnection.disconnect();
        }
        return data;
    }
}
