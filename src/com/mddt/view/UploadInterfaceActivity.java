package com.mddt.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mddt.R;

public class UploadInterfaceActivity extends Activity {

	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private static final int CAMERA_REQUEST = 1888;
	private ImageView imageView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploadscreen);

		imageView = (ImageView) findViewById(R.id.imageView1);
		OnClickListener cameraListener = new OnClickListener() {
			public void onClick(View v) {
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
			}
		};

		Button cameraButton = (Button) findViewById(R.id.camerabutton);
		Button manualButton = (Button) findViewById(R.id.manualbutton);

		OnClickListener manualListener = new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(),
						MachineCheckActivity.class);

				Toast theToast = Toast.makeText(getBaseContext(), "Machinez",
						Toast.LENGTH_LONG);
				theToast.show();
				v.getContext().startActivity(i);
			}
		};

		manualButton.setOnClickListener(manualListener);
		cameraButton.setOnClickListener(cameraListener);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			imageView.setImageBitmap(photo);
		}

	}

}
