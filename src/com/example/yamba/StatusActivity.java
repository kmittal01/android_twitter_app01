package com.example.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity {

	EditText editStatus;
	static final String Tag ="CustomDebugText ";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Debug.startMethodTracing("Yamba.trace");
		setContentView(R.layout.status);
		editStatus = (EditText) findViewById(R.id.edit_status);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// Debug.stopMethodTracing();
	}

	public void OnClickNew(View v) {
		final String statusString = editStatus.getText().toString();
		new PostToTwitter().execute(statusString);
		/*
		 * new Thread() { public void run() { //some method } }.start();
		 */
		Log.d("statusActivity", "OnClicked" + statusString);
	}

	class PostToTwitter extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				Twitter twitter = new Twitter("student", "password");
				twitter.setAPIRootUrl("http://yamba.marakana.com/api");
				twitter.setStatus(params[0]);
				return "Successfully Posted " + params[0];
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				Log.e("StatusActivity", "Died", e);
				e.printStackTrace();
				return "Update Failed " + params[0];
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("StatusActivityonexecute", result);
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG)
					.show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent = new Intent(this, UpdaterService.class);
		Intent intent_refresh = new Intent(this, RefreshService.class);
		
		switch (item.getItemId()) {
		case R.id.item_start_service:
			startService(intent);
			return true;
		case R.id.item_stop_service:
			stopService(intent);
			return true;
		case R.id.item_refresh:
			startService(intent_refresh);
			return true;
		case R.id.item_prefs:
			startActivity(new Intent(StatusActivity.this,PrefsActivity.class));
			return true;
		case R.id.item_timeline:
			startActivity(new Intent(this,TimelineActivity.class));
			return true;
		default:
			return false;
		}
	}

}
