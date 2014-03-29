package com.rajatkhanna.gridimageview.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.jar.JarException;

/**
 * Created by rajatkhanna on 3/28/14.
 */
public class ImageResult {
    private String fullURL;
    private String thumbURL;

    public ImageResult(JSONObject json){
        try{
            this.fullURL = json.getString("url");
            this.thumbURL = json.getString("tbUrl");

        }catch (JSONException e){
            this.thumbURL = null;
            this.fullURL = null;

        }

    }

    public String getFullURL() {
        return fullURL;
    }
    public String getThumbURL() {
        return thumbURL;
    }
    public String toString() {
        return this.thumbURL;
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();

        for (int i = 0; i < array.length() ; i++) {
            try {
                results.add(new ImageResult(array.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }
}
