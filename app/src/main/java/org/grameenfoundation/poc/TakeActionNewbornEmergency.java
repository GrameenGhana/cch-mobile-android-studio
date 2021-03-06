package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TakeActionNewbornEmergency extends BaseActivity {

	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject json;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Point of Care");
	    mContext=TakeActionNewbornEmergency.this;
	    dbh=new DbHelper(TakeActionNewbornEmergency.this);
	    start_time=System.currentTimeMillis();
	   
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("take_action");
        }
        if(take_action_category.equals("difficulty")){
	    setContentView(R.layout.activity_difficulty_breathing);
            getSupportActionBar().setSubtitle("PNC Diagnostic: Newborn Emergencies > Difficulty Breathing");
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Newborn Emergencies > Difficulty Breathing");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	   TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
	   click_here.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(TakeActionNewbornEmergency.this,KeepingBabyWarmAndMalariaActivity.class);
			intent.putExtra("value", "keeping_baby_warm");
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		}
		   
	   });
        }else if(take_action_category.equals("cyanosis")){
        setContentView(R.layout.activity_cyanosis);
            getSupportActionBar().setSubtitle("PNC Diagnostic: Newborn Emergencies > Cyanosis");
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Newborn Emergencies > Cyanosis");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
 	   click_here.setOnClickListener(new OnClickListener(){

 		@Override
 		public void onClick(View v) {
 			Intent intent=new Intent(TakeActionNewbornEmergency.this,KeepingBabyWarmAndMalariaActivity.class);
 			intent.putExtra("value", "keeping_baby_warm");
 			startActivity(intent);
 			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
 		}
 		   
 	   });
        }else if(take_action_category.equals("convulsion")){
        setContentView(R.layout.activity_convulsion);
            getSupportActionBar().setSubtitle("PNC Diagnostic: Newborn Emergencies > Convulsion");
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Newborn Emergencies > Convulsion");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
  	   click_here.setOnClickListener(new OnClickListener(){

  		@Override
  		public void onClick(View v) {
  			Intent intent=new Intent(TakeActionNewbornEmergency.this,KeepingBabyWarmAndMalariaActivity.class);
  			intent.putExtra("value", "keeping_baby_warm");
  			startActivity(intent);
  			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
  		}
  		   
  	   });
        }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
