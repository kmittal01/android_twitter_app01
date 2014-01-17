package com.example.yamba;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

public class TimelineActivity extends Activity{
	static final String[] FROM={StatusData.C_USER,StatusData.C_TEXT};
	static final int[] TO={android.R.id.text1,android.R.id.text2};
	ListView list;
	Cursor cursor;
	SimpleCursorAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		
		list = (ListView) findViewById(R.id.list);
		
		cursor = ((YambaApp)getApplication()).statusData.query();
		
		adapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item, cursor, FROM, TO);
		list.setAdapter(adapter);
//		while (cursor.moveToNext()) {
//			String user = cursor.getString(cursor.getColumnIndex(StatusData.C_USER));
//			String text= cursor.getString(cursor.getColumnIndex(StatusData.C_TEXT));
//			textOut.append(String.format("\n%s: %s", user,text));
//		}
		
	}

}
