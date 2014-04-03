package com.rajatkhanna.gridimageview.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.OnClickListener;
import static android.widget.AdapterView.*;


public class FilterActivity extends ActionBarActivity implements  OnClickListener, OnItemSelectedListener {
    Spinner spnSize;
    Spinner spnType;
    Spinner spnColor;
    EditText etSite;
    Button btnSubmit;
    String size,type,color,site;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Intent i = getIntent();

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        spnSize = (Spinner) findViewById(R.id.spnSize);
        ArrayAdapter aSpnSize = ArrayAdapter.createFromResource(this,R.array.img_size,android.R.layout.simple_spinner_item);
        spnSize.setAdapter(aSpnSize);
        spnSize.setOnItemSelectedListener(this);

        spnColor = (Spinner) findViewById(R.id.spnColor);
        ArrayAdapter aSpnColor = ArrayAdapter.createFromResource(this,R.array.img_color,android.R.layout.simple_spinner_item);
        spnColor.setAdapter(aSpnColor);
        spnColor.setOnItemSelectedListener(this);

        spnType = (Spinner) findViewById(R.id.spnType);
        ArrayAdapter aSpnType = ArrayAdapter.createFromResource(this,R.array.img_type,android.R.layout.simple_spinner_item);
        spnType.setAdapter(aSpnType);
        spnType.setOnItemSelectedListener(this);

        etSite = (EditText) findViewById(R.id.etSite);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter, menu);
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
    public void onClick(View v) {
        site = etSite.getText().toString();

        Intent i = new Intent();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
     //   Toast.makeText(getBaseContext()," Size :"+size+" Color :"+color+" Type :"+type,Toast.LENGTH_LONG).show();


       i.putExtra("size",size);
        i.putExtra("color",color);
       i.putExtra("type",type);
       i.putExtra("site",site);
        setResult(RESULT_OK,i);
        finish();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView itemSelected = (TextView) view;

        switch (parent.getId()){
            case R.id.spnSize:
             //  Toast.makeText(this,"R.id.spnSize : "+ parent.getId(),Toast.LENGTH_SHORT).show();
                size =  itemSelected.getText().toString();

                 break;
            case R.id.spnColor:
            //    Toast.makeText(this,"R.id.spnColor : "+ parent.getId(),Toast.LENGTH_SHORT).show();

                color =  itemSelected.getText().toString();
                break;
            case R.id.spnType:
             //   Toast.makeText(this,"R.id.spnType : "+ parent.getId(),Toast.LENGTH_SHORT).show();

                type = itemSelected.getText().toString();
                break;


        }


    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
