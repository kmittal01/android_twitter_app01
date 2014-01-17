package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {

	Twitter twitter;
	static final String TAG = "UpdaterServiceCustom";
	static final String DELAY = "30"; // in sec
	boolean running = false;

	@Override
	public void onCreate() {
		super.onCreate();
		twitter = new Twitter("student", "password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api/");
		Log.e(TAG, "OnCreated");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		running = true;
		new Thread() {
			public void run() {
				try {
					while (running) {
						List<Status> timeline = twitter.getPublicTimeline();
						for (Status status : timeline) {
							((YambaApp)getApplication()).statusData.insert(status);
							Log.e(TAG, String.format("%s : %s",
									status.user.name, status.text));
						}
						int delay = Integer.parseInt(((YambaApp)getApplication()).prefs.getString("delay", DELAY));
						Thread.sleep(delay * 1000);
					}
				} catch (TwitterException e) {
					Log.e(TAG, "Failed Because of Network Error");
				} catch (InterruptedException e) {
					Log.d(TAG, "Updater Interrupted", e);
				}
			}
		}.start();

		Log.e(TAG, "OnStarted");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		running = false;
		Log.e(TAG, "OnDestroyed");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
