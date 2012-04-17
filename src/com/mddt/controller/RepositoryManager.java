package com.mddt.controller;


import com.mddt.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class RepositoryManager extends ListActivity { 
    /** Called when the activity is first created. */ 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        String[] departments = getResources().getStringArray(R.array.dept_array);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.dept_list, departments)); 
        ListView lv = getListView(); 
        lv.setTextFilterEnabled(true); 
        lv.setOnItemClickListener(new OnItemClickListener() { 
          public void onItemClick(AdapterView<?> parent, View view, 
              int position, long id) { 
            // When clicked, show a toast with the TextView text 
            Toast.makeText(getApplicationContext(), ((TextView) view).getText(), 
                Toast.LENGTH_SHORT).show(); 
          } 
        }); 
    } 
   
} 