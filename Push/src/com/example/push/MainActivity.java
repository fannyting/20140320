package com.example.push;

import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText editText; 
	private Spinner spinner;
	private TextView textview;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Parse.initialize(this, "nlIQe4IIlQBDWDgk3k64XyFsQEzI2EYnuOXtsDr1", "f5Wa8GRUoApMlfKVE9KxjrjuCvjYc2CJg6X0MXoP");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		
		PushService.setDefaultPushCallback(this, MainActivity.class);
		PushService.subscribe(this, "all", MainActivity.class);
		PushService.subscribe(this, "device_id_" + getDevcieId(),MainActivity.class);
				
		ParseObject object = new ParseObject("DeviceId");
		object.put("deviceId", getDevcieId());
		object.saveInBackground();
		
		editText = (EditText) findViewById(R.id.editText1);
		spinner = (Spinner) findViewById(R.id.spinner1);
		textview = (TextView) findViewById(R.id.textView1);
		
		
		loadDeviceIds();
		textview.setText(getDevcieId());
	
	}
	
	private String getDevcieId() {
		return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}

	private void loadDeviceIds() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("DeviceId");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {

				String[] ids = new String[objects.size()];
				for (int i = 0; i < objects.size(); i++) {
					ids[i] = objects.get(i).getString("deviceId");
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MainActivity.this,
						android.R.layout.simple_spinner_item, ids);
				spinner.setAdapter(adapter);

			}
		});
	}
	

	public void click(View view) {
		String text = editText.getText().toString();
		String id = (String) spinner.getSelectedItem();
		Log.d("debug", text);
		
		ParsePush push = new ParsePush();
		push.setChannel("device_id_" + id);
		push.setMessage(text);
		push.sendInBackground();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
