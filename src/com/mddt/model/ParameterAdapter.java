package com.mddt.model;

import java.util.ArrayList;

import com.mddt.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ParameterAdapter extends ArrayAdapter<String[]>{
	
	private ArrayList<String[]> items;
	private LayoutInflater li = LayoutInflater.from(this.getContext());

	
	public ParameterAdapter(Context context, int layoutViewID, ArrayList<String[]> objects){
		super(context, layoutViewID, objects);
		items = objects;
	}
	
	@Override
    public View getView(int position, View layoutView, ViewGroup parent) {
		
		
        if(layoutView == null){
            layoutView = li.inflate(R.layout.paramrow, parent, false);
        }

        String[] data = items.get(position);
        ((TextView) layoutView.findViewById(R.id.parameter)).setText(data[0]);
        ((TextView) layoutView.findViewById(R.id.paramval)).setText(data[1]);
        
        return layoutView;
    }

}
