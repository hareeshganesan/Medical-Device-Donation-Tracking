package com.mddt.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;


public abstract class TransitioningActivity extends Activity {

	protected void startActivity(View v, Class activity, String toast) {
		Intent i = new Intent(v.getContext(), activity);

		if (toast != null) {
			Toast theToast = Toast.makeText(getBaseContext(), toast,
					Toast.LENGTH_LONG);
			theToast.show();
		}
		v.getContext().startActivity(i);
	}

}
