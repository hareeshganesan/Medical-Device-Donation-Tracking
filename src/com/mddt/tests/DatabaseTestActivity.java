package com.mddt.tests;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.mddt.R;
import com.mddt.controller.DataUploadManager;
import com.mddt.model.Machine;

public class DatabaseTestActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		DataUploadManager d = new DataUploadManager(this);
		HashMap<String, String> props = new HashMap<String, String>();
		props.put("device_name", "helloworld");
		props.put("make", "duke");
		props.put("model", "2012");
		d.uploadMachine(new Machine("test", props), "http://mddt262.appspot.com/storeavalue");
		Toast theToast = Toast.makeText(getBaseContext(), "done uploading",
				Toast.LENGTH_LONG);
		theToast.show();
	}
}
