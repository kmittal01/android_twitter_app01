package com.example.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApp extends Application implements OnSharedPreferenceChangeListener {
	
	static final String TAG = "YambaApp";
	private Twitter twitter;
	SharedPreferences prefs;
	StatusData statusData;
	@Override
	public void onCreate() {
		super.onCreate();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		
		statusData = new StatusData(this);
		
		Log.d(TAG,"OnCreated");
	}
	
	public Twitter getTwitter() {
		if(twitter==null) {
			// Prefs Stuff
			String user = prefs.getString("username", "");
			String pass = prefs.getString("password", "");
			String server = prefs.getString("apiRoot", "");
			//Twitter Stuff
			twitter = new Twitter(user, pass);
			twitter.setAPIRootUrl(server);
		}
		return twitter;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		twitter=null;
		Log.wtf(TAG, "onSharedPreferenceChanged for key : " + key );
	}

}
