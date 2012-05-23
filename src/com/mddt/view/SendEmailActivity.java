package com.mddt.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mddt.R;

public class SendEmailActivity extends Activity {
	Button send;
	EditText address, subject, emailbody;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_request);

		address = (EditText) findViewById(R.id.address);
		subject = (EditText) findViewById(R.id.subject);
		emailbody = (EditText) findViewById(R.id.body);
		send = (Button) findViewById(R.id.send);

		send.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sendEmail();
			}
		});
	}

	public void sendEmail() {

		if (!address.getText().toString().trim().equalsIgnoreCase("")) {
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { address.getText().toString() });
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					subject.getText());
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					emailbody.getText());
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
		} else {
			Toast.makeText(getApplicationContext(),
					"Please enter an email address..", Toast.LENGTH_LONG)
					.show();
		}
	}
}
