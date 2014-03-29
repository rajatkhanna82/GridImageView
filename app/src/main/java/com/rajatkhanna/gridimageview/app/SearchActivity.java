package com.rajatkhanna.gridimageview.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity implements SearchView.OnCloseListener, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    SearchView svQuery;
    GridView gvResults;
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultArrayAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupView();
        imageAdapter = new ImageResultArrayAdapter(this,imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(this);

    }

    private void setupView() {
        svQuery = (SearchView) findViewById(R.id.svQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        svQuery.setIconifiedByDefault(false);
        svQuery.setOnQueryTextListener(this);
        svQuery.setOnCloseListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onClose() {
        onImageSearch("");

        return false;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        onImageSearch(query);


        return false;
    }

    private void onImageSearch(String query) {
        //Toast.makeText(this,"Searching for "+ query,Toast.LENGTH_SHORT).show();

        AsyncHttpClient client = new AsyncHttpClient();
        // https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=Android

        client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" +
                    "start=" + 0 +"&v=1.0&q=" + Uri.encode(query),
                new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(JSONObject response){
                    JSONArray imageJsonResults = null;
                    try {
//                      JSONObject response for HTTP Image Search query
//                      {
//                            "responseData": {
//                            "results": [
//                            {
//                                "GsearchResultClass": "GimageSearch",
//                                    "width": "1600",
//                                    "height": "1600",
//                                    "imageId": "ANd9GcRd03HuS6a_SxKnRnp1VaNUsXXz5kw4BH1MEh3kES3SDheC-Wjnvg-Bj1nYRw",
//                                    "tbWidth": "150",
//                                    "tbHeight": "150",
//                                    "unescapedUrl": "http://blog.appliedis.com/wp-content/uploads/2013/11/android1.png",
//                                    "url": "http://blog.appliedis.com/wp-content/uploads/2013/11/android1.png",
//                                    "visibleUrl": "blog.appliedis.com",
//                                    "title": "<b>Android</b> Development for .NET Developers: Getting Started | Applied <b>...</b>",
//                                    "titleNoFormatting": "Android Development for .NET Developers: Getting Started | Applied ...",
//                                    "originalContextUrl": "http://blog.appliedis.com/2013/11/14/android-development-for-net-developers-getting-started/",
//                                    "content": "of the <b>Android</b> operating",
//                                    "contentNoFormatting": "of the Android operating",
//                                    "tbUrl": "http://t0.gstatic.com/images?q=tbn:ANd9GcRd03HuS6a_SxKnRnp1VaNUsXXz5kw4BH1MEh3kES3SDheC-Wjnvg-Bj1nYRw"
//                            },
                       imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                       imageAdapter.clear();
                       imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
                        Log.d("DEBUG",imageResults.toString());
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
        });


    }

    @Override
    public boolean onQueryTextChange(String newText) {

       //onImageSearch(newText +"*");
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getApplicationContext(),ImageDisplayActivity.class);
        ImageResult imageResult = imageResults.get(position);
        i.putExtra("url",imageResult.getFullURL());
        startActivity(i);

    }
}
