package com.dhs.kddevice.kddevice.util;

import com.dhs.kddevice.kddevice.vo.LocationVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chinnaraj on 3/11/2018.
 */

public class DataParser {

    private List<LocationVO> getPlace(JSONObject googlePlaceJson) {
        List<LocationVO> googlePlacesList = new ArrayList<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJson.getString("reference");

            LocationVO locationVO = new LocationVO();
            locationVO.setName(placeName);
            locationVO.setLattitude(Double.parseDouble(latitude));
            locationVO.setLongitude(Double.parseDouble(longitude));
            locationVO.setMapReference(reference);
            locationVO.setVicinty(vicinity);
            googlePlacesList.add(locationVO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlacesList;
    }
        private List<LocationVO> getPlaces(JSONArray jsonArray)
        {
            int count = jsonArray.length();
            List<LocationVO> placesList = new ArrayList<>();
            List<LocationVO> placeMap= null;
            if (count > 5) count = 5;
            for(int i = 0;i<count;i++)
            {
                try {
                    placeMap = getPlace((JSONObject) jsonArray.get(i));
                    placesList.addAll(placeMap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return placesList;
        }

        public List<LocationVO> parse(String jsonData)
        {
            JSONArray jsonArray = null;
            JSONObject jsonObject;

            try {
                jsonObject=new JSONObject(jsonData);
                jsonArray = jsonObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return getPlaces(jsonArray);
        }
    }
