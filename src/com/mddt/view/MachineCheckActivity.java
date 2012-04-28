package com.mddt.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mddt.R;
import com.mddt.controller.DataUploadManager;
import com.mddt.crop.CropActivity;
import com.mddt.model.Machine;

public class MachineCheckActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual_entry);
		
		Button sendButton = (Button) findViewById(R.id.sendButton);

		OnClickListener sendListener = new OnClickListener() {
			public void onClick(View v) {
				extractAndUploadMachine();
			}
		};

		sendButton.setOnClickListener(sendListener);
		populateListView();
	}
//http://vikaskanani.wordpress.com/2011/07/27/android-focusable-edittext-inside-listview/
	private void populateListView() {
		String[] params = getResources().getStringArray(R.array.parameter_array);
		ListView l = (ListView) findViewById(R.id.paramlist);
		
		l.setAdapter(new ArrayAdapter<String>(this, R.layout.paramrow,R.id.parameter, params));

		for(int i=0; i<params.length; i++){
			
			Log.d("listcount",Integer.toString(l.getCount()));
			
			LinearLayout layout = ((LinearLayout)((LinearLayout)l.getAdapter().getView(i, null, null)).getChildAt(0));
			if(CropActivity.parameterMap.containsKey(i)){
				Log.d("paramlist", Integer.toString(i));
				TextView tv = (TextView)layout.findViewById(R.id.parameter);
				Log.d("paramname", tv.getText().toString());
				EditText e = (EditText) layout.findViewById(R.id.paramval);
				Log.d("newval",CropActivity.parameterMap.get(i));
				tv.setText(CropActivity.parameterMap.get(i));
			}
		}

	}

	public void extractAndUploadMachine() {
		EditText name = (EditText) findViewById(R.id.devicename);
		EditText make = (EditText) findViewById(R.id.make);
		EditText model = (EditText) findViewById(R.id.model);
		EditText tag = (EditText) findViewById(R.id.tag);
		EditText location = (EditText) findViewById(R.id.location);
		HashMap<String, String> props = new HashMap<String, String>();
		props.put("device_name", name.getText().toString());
		props.put("make", make.getText().toString());
		props.put("model", model.getText().toString());
		props.put("tag", tag.getText().toString());
		props.put("location", location.getText().toString());

		DataUploadManager d = new DataUploadManager(this);

		d.uploadMachine(new Machine(props),
				"http://mddt262.appspot.com/storeavalue");
		Toast theToast = Toast.makeText(getBaseContext(), "done uploading",
				Toast.LENGTH_LONG);
		theToast.show();
	}

}
