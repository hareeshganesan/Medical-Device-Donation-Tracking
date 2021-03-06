package com.mddt.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.mddt.R;
import com.mddt.crop.CropActivity;

public class UploadInterfaceActivity extends TransitioningActivity {

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploadscreen);

		Button cameraButton = (Button) findViewById(R.id.camerabutton);
		Button manualButton = (Button) findViewById(R.id.manualbutton);

		OnClickListener cameraListener = new OnClickListener() {
			public void onClick(View v) {
				startActivity(v, CropActivity.class, "crop image");
			}
		};

		OnClickListener manualListener = new OnClickListener() {
			public void onClick(View v) {
				startActivity(v, MachineCheckActivity.class,
						"move to manual entry");
			}
		};

		manualButton.setOnClickListener(manualListener);
		cameraButton.setOnClickListener(cameraListener);
	}

}
