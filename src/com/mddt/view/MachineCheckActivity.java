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
import com.mddt.controller.MachineDataManager;
import com.mddt.controller.RemoteUploadManager;
import com.mddt.controller.UploadManagerProxy;
import com.mddt.crop.CropActivity;
import com.mddt.model.Machine;
import com.mddt.model.ParameterAdapter;

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

	protected void onStart() {
		super.onStart();
		populateListView();
	}

	private void populateListView() {
		String[] params = getResources()
				.getStringArray(R.array.parameter_array);
		
		ArrayList<String[]> paramList = new ArrayList<String[]>();
		for (int i = 0; i < params.length; i++) {
			String[] data = new String[2];
			data[0] = params[i];
			if (CropActivity.parameterMap.containsKey(i)) {
				data[1] = CropActivity.parameterMap.get(i);
			} else {
				data[1] = "";
			}
			paramList.add(data);
		}
		CropActivity.parameterMap.clear();
		ListView l = (ListView) findViewById(R.id.paramlist);

		l.setAdapter(new ParameterAdapter(this, R.layout.paramrow, paramList));

	}

	private void extractAndUploadMachine() {
		String[] params = getResources()
				.getStringArray(R.array.parameter_array);
		ListView l = (ListView) findViewById(R.id.paramlist);
		HashMap<String, String> props = new HashMap<String, String>();

		for (int i = 0; i < params.length; i++) {
			String text = ((EditText) ((LinearLayout) l.getChildAt(i))
					.findViewById(R.id.paramval)).getText().toString();
			props.put(params[i], text);
		}
		RemoteUploadManager p = new UploadManagerProxy(this);
		Machine m = new Machine(props);
		p.storeMachineLocally(m);
		p.uploadMachine(m,
		 "http://mddt262.appspot.com/storeavalue");

		Toast theToast = Toast.makeText(getBaseContext(), "done uploading",
				Toast.LENGTH_LONG);
		theToast.show();

	}

}
