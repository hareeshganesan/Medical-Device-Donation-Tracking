package com.mddt.view;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mddt.R;
import com.mddt.controller.DataUploadManager;
import com.mddt.crop.CropActivity;
import com.mddt.model.Machine;

public class MachineCheckActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textentry);

		Button sendButton = (Button) findViewById(R.id.sendButton);

		OnClickListener sendListener = new OnClickListener() {
			public void onClick(View v) {
				extractAndUploadMachine();
			}
		};

		sendButton.setOnClickListener(sendListener);

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
