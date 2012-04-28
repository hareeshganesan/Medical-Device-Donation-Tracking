package com.mddt.crop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.mddt.R;
import com.mddt.view.MachineCheckActivity;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;

import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

/**
 * This activity manages the cropping of the image, starting from the selection
 * and acquisition of an image, and finishing by outputting actual text
 * 
 * @author Hareesh
 * 
 */
public class CropActivity extends Activity {
	private Uri mImageCaptureUri;
	private ImageView mImageView;

	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
	public static HashMap<Integer, String> parameterMap = new HashMap<Integer,String>();
	private String parsedText = new String();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crop);

		final String[] items = new String[] { "Take from camera",
				"Select from gallery" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, items);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Select Image");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) { // pick from
																	// camera
				if (item == 0) {
					takePicture();
				} else { // pick from file
					getPicture();
				}
			}

		});

		final AlertDialog dialog = builder.create();

		Button select_button = (Button) findViewById(R.id.btn_crop);
		mImageView = (ImageView) findViewById(R.id.iv_photo);

		

	    Spinner spinner = (Spinner) findViewById(R.id.paramselect);
	    ArrayAdapter<CharSequence> param_adapter = ArrayAdapter.createFromResource(
	            this, R.array.parameter_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(param_adapter);
		Button completeMachineButton = (Button) findViewById(R.id.completemachine);
		Button saveParamButton = (Button) findViewById(R.id.savetext);

		OnClickListener saveParamListener = new OnClickListener(){
			public void onClick(View v) {
				updateMap();
			}
			
		};
		
		OnClickListener completeMachineListener = new OnClickListener(){
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), MachineCheckActivity.class);
				v.getContext().startActivity(i);
			}
			
		};
		
		saveParamButton.setOnClickListener(saveParamListener);
		completeMachineButton.setOnClickListener(completeMachineListener);
		select_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.show();
			}
		});
	}


	private void updateMap(){
		Spinner spinner = (Spinner) findViewById(R.id.paramselect);
		CropActivity.parameterMap.put(spinner.getSelectedItemPosition(), parsedText);
	}
	private void getPicture() {
		Intent intent = new Intent();

		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);

		startActivityForResult(
				Intent.createChooser(intent, "Complete action using"),
				PICK_FROM_FILE);
	}

	private void takePicture() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), "tmp_avatar_"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
		Log.d("image capture", "beginning");
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);

		try {
			intent.putExtra("return-data", true);

			Log.d("image capture", "about to select from camera");
			startActivityForResult(intent, PICK_FROM_CAMERA);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void toTesseract(Bitmap photo) {

		Log.d("tesseract", "entered");
		File myDir = new File("/mnt/sdcard/tesseract");

		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.init(myDir.toString(), "eng"); // myDir +
												// "/tessdata/eng.traineddata"
												// must be present

		baseApi.setImage(photo);

		String recognizedText = baseApi.getUTF8Text(); // Log or otherwise
														// display this
														// string...
		baseApi.end();

		parsedText = recognizedText;
		TextView t = (TextView)findViewById(R.id.parseText);
		t.setText("Parsed: "+parsedText);
		Toast theToast = Toast.makeText(getBaseContext(), recognizedText,
				Toast.LENGTH_LONG);
		theToast.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			Log.d("activity result", "fail boat");
			return;
		}

		switch (requestCode) {
		case PICK_FROM_CAMERA:
			Log.d("activity result", "pick from cam");
			doCrop();
			break;

		case PICK_FROM_FILE:
			Log.d("activity result", "pick from file");
			mImageCaptureUri = data.getData();
			doCrop();
			break;

		case CROP_FROM_CAMERA:
			Log.d("activity result", "crop from cam");
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				mImageView.setImageBitmap(photo);
				toTesseract(photo);
			}

			File f = new File(mImageCaptureUri.getPath());

			if (f.exists())
				f.delete();

			break;

		}
	}

	private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = getPackageManager().queryIntentActivities(
				intent, 0);

		int size = list.size();

		if (size == 0) {
			Toast.makeText(this, "Can not find image crop app",
					Toast.LENGTH_SHORT).show();
			return;
		} else {
			intent.setData(mImageCaptureUri);
			intent.putExtra("return-data", true);

			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName,
						res.activityInfo.name));

				Log.d("activity result", "start crop from cam");
				startActivityForResult(i, CROP_FROM_CAMERA);
			} else {
				for (ResolveInfo res : list) {
					final CropOption co = new CropOption();

					co.title = getPackageManager().getApplicationLabel(
							res.activityInfo.applicationInfo);
					co.icon = getPackageManager().getApplicationIcon(
							res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);

					co.appIntent
							.setComponent(new ComponentName(
									res.activityInfo.packageName,
									res.activityInfo.name));

					cropOptions.add(co);
				}

				CropOptionAdapter adapter = new CropOptionAdapter(
						getApplicationContext(), cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Choose Crop App");
				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								startActivityForResult(
										cropOptions.get(item).appIntent,
										CROP_FROM_CAMERA);
							}
						});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					public void onCancel(DialogInterface dialog) {

						if (mImageCaptureUri != null) {
							getContentResolver().delete(mImageCaptureUri, null,
									null);
							mImageCaptureUri = null;
						}
					}
				});

				AlertDialog alert = builder.create();

				alert.show();
			}
		}
	}
}