package org.grameenfoundation.cch.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.model.Course;
import org.grameenfoundation.cch.model.CourseAchievments;
import org.grameenfoundation.adapters.CourseAchievementsAdapter;
import org.grameenfoundation.cch.utils.TextProgressBar;
import org.grameenfoundation.poc.BaseActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CourseAchievementActivity extends BaseActivity {

	private ListView Listview;
	private Context mContext;
	private DbHelper db;
	private ArrayList<CourseAchievments> courses;
	private long modid;
	private CourseAchievementsAdapter adapter;
	private TextView textView_label;
	private String course_name;
	private TextProgressBar progress;
	private boolean loadingMore;
	private Long start_time;
	private Long end_time;
	private JSONObject data;
	private int month;
	private int year;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_course_achievements);
	    try{
	    Listview = (ListView) findViewById(R.id.listView1);
	    mContext=CourseAchievementActivity.this;
	    db=new DbHelper(CourseAchievementActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          modid= extras.getLong("modid");
          course_name=extras.getString("course_name");
          month= extras.getInt("month");
          year=extras.getInt("year");
        }
        progress=(TextProgressBar) findViewById(R.id.progressBar1);
        progress.setProgress((int)db.getCourseProgress((int)modid));
	    courses = db.getQuizzesForAchievements(course_name,month+1,year);
            getSupportActionBar().setTitle("Achievement Center");
            getSupportActionBar().setSubtitle("Achievement Details");
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setText(course_name);
	    adapter=new CourseAchievementsAdapter(mContext,courses);
	    Listview.setAdapter(adapter);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		 data=new JSONObject();
		    try {
		    	data.put("page", "Course Achievements");
		    	data.put("ver", db.getVersionNumber(CourseAchievementActivity.this));
		    	data.put("battery", db.getBatteryStatus(CourseAchievementActivity.this));
		    	data.put("device", db.getDeviceName());
				data.put("imei", db.getDeviceImei(CourseAchievementActivity.this));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		db.insertCCHLog("Achievement Center", data.toString(), start_time.toString(), end_time.toString());
		finish();
	}

	
}
