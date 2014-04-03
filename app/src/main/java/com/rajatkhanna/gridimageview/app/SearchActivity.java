package com.rajatkhanna.gridimageview.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.widget.AbsListView.OnScrollListener;
import static android.widget.AdapterView.OnItemClickListener;
import static android.widget.SearchView.*;


public class SearchActivity extends ActionBarActivity implements OnCloseListener, OnQueryTextListener, OnItemClickListener {
    private static final int REQUEST_CODE_FILTER = 100;

    SearchView svQuery;
    GridView gvResults;
    SearchQuery searchQuery = new SearchQuery();
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultArrayAdapter imageAdapter;

    AsyncHttpClient client = new AsyncHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        searchQuery.setDefault();
        setupView();
        imageAdapter = new ImageResultArrayAdapter(this,imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customOnLoadMore(page,totalItemsCount);
            }
        });
        gvResults.setOnItemClickListener(this);

    }

    private void customOnLoadMore(int page, int totalItemsCount) {
    // Toast.makeText(getBaseContext(),"totalItemsCount :"+totalItemsCount,Toast.LENGTH_SHORT).show();
      searchQuery.setPage(page*4);
       onImageSearch();

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
        if (id == R.id.filter_settings) {
          //  Toast.makeText(this,"Open Filter Setting",Toast.LENGTH_SHORT).show();
            onFilterSetting();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onFilterSetting() {
        Intent i = new Intent(getBaseContext(),FilterActivity.class);
        startActivityForResult(i, REQUEST_CODE_FILTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String color,size,type,site;
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_FILTER && resultCode == RESULT_OK){
            color = data.getStringExtra("color");
            searchQuery.setColor(color);
            size =data.getStringExtra("size");
            searchQuery.setSize(size);
            type = data.getStringExtra("type");
            searchQuery.setType(type);
            site = data.getStringExtra("site");
            searchQuery.setSite(site);
          //  Toast.makeText(this,"color :"+color+" Size : "+size ,Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public boolean onClose() {

        searchQuery.setQuery("");
        searchQuery.setPage(0);
        imageAdapter.clear();
      //  Toast.makeText(getBaseContext(),"CLose the search",Toast.LENGTH_SHORT).show();
        onImageSearch();

        return false;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        searchQuery.setQuery(query);
        searchQuery.setPage(0);
        imageAdapter.clear();
        onImageSearch();


        return false;
    }

    private void onImageSearch() {
        //Toast.makeText(this,"Searching for "+ query,Toast.LENGTH_SHORT).show();

        // https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=Android
       // if(page== 0) imageAdapter.clear();
        Log.d("DEBUG","in onImageSearch :"+searchQuery.getURL().toString());

        client.get(searchQuery.getURL(),
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject response) {
                        JSONArray imageJsonResults = null;
                        try {
                            imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                            //imageAdapter.clear();
                            imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
                            Log.d("DEBUG", imageResults.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );


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
