package com.example.push;

import com.parse.Parse;
import com.parse.ParsePush;
import com.parse.PushService;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText editText; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Parse.initialize(this, "nlIQe4IIlQBDWDgk3k64XyFsQEzI2EYnuOXtsDr1", "f5Wa8GRUoApMlfKVE9KxjrjuCvjYc2CJg6X0MXoP");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		
		PushService.setDefaultPushCallback(this, MainActivity.class);
		PushService.subscribe(this, "all", MainActivity.class);
		
		setContentView(R.layout.activity_main);
		
		editText = (EditText) findViewById(R.id.editText1);
	
	}

	public void click(View view) {
		String text = editText.getText().toString();
		Log.d("debug", text);
		
		ParsePush push = new ParsePush();
		push.setChannel("all");
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
