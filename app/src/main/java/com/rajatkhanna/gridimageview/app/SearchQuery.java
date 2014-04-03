package com.rajatkhanna.gridimageview.app;

import android.net.Uri;

/**
 * Created by rajatkhanna on 4/1/14.
 */
public class SearchQuery {
    private String query;
    private int page;
    private String size;
    private String color;
    private String type;
    private String site;

    public  SearchQuery(){
        this.query = "";
        this.page = 0;
        this.color = "any";
        this.size = "any";
        this.site ="";
        this.type= "any";


    }


    public void setQuery(String query) {
        this.query = query;
    }


    public void setPage(int page) {
        this.page = page;
    }


    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public String getURL(){
        String baseURL ="https://ajax.googleapis.com/ajax/services/search/images?&rsz=8";
        String finalURL = "https://ajax.googleapis.com/ajax/services/search/images?&rsz=8&start="+page+"&v=1.0&q="+ Uri.encode(this.query);

        if(!this.size.equals("any")){
            finalURL = finalURL+ "&imgsz="+this.size;
        }
        if(!this.color.equals("any")){
            finalURL= finalURL+"&imgcolor="+this.color;

        }
        if(!this.type.equals("any")){
            finalURL = finalURL+ "&imgtype="+this.type;

        }
        // baseURL + "&start="+this.page+ ((this.size != "none")? "&imgsz="+this.size:"") ;

     return finalURL;

    }


}
