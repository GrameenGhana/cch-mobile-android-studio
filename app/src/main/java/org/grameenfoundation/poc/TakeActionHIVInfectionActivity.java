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

public class TakeActionHIVInfectionActivity extends BaseActivity {

	private String category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject json;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=TakeActionHIVInfectionActivity.this;
	    dbh=new DbHelper(TakeActionHIVInfectionActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras();
        getSupportActionBar().setTitle("Point of Care");
	   
        if (extras != null) {
          category= extras.getString("value");
        }
        if(category.equalsIgnoreCase("negative_no")){
        	setContentView(R.layout.activity_hiv_negative_no);
            getSupportActionBar().setSubtitle("PNC Diagnostic: HIV Infection > Negative");
     	    json=new JSONObject();
     	    try {
     			json.put("page", "PNC Diagnostic: HIV Infection > Negative");
     			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
     			json.put("ver", dbh.getVersionNumber(mContext));
     			json.put("battery", dbh.getBatteryStatus(mContext));
     			json.put("device", dbh.getDeviceName());
     			json.put("imei", dbh.getDeviceImei(mContext));
     		} catch (JSONException e) {
     			e.printStackTrace();
     		}
        }else if(category.equalsIgnoreCase("negative_yes")){
        	setContentView(R.layout.activity_hiv_negative_yes);
            getSupportActionBar().setSubtitle("PNC Diagnostic: HIV Infection > Negative");
      	    json=new JSONObject();
      	    try {
      			json.put("page", "PNC Diagnostic: HIV Infection > Negative");
      			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
      			json.put("ver", dbh.getVersionNumber(mContext));
      			json.put("battery", dbh.getBatteryStatus(mContext));
      			json.put("device", dbh.getDeviceName());
      			json.put("imei", dbh.getDeviceImei(mContext));
      		} catch (JSONException e) {
      			e.printStackTrace();
      		}
        }else if(category.equalsIgnoreCase("positive_no")){
        	setContentView(R.layout.activity_hiv_positive_no);
            getSupportActionBar().setSubtitle("PNC Diagnostic: HIV Infection > Positive");
      	    json=new JSONObject();
      	    try {
      			json.put("page", "PNC Diagnostic: HIV Infection > Positive");
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
  				Intent intent=new Intent(TakeActionHIVInfectionActivity.this,InfantFeedingNextActivity.class);
  				intent.putExtra("value", "not_taking_arv");
  				startActivity(intent);
  				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
  			}
  			   
  		   });
        }else if(category.equalsIgnoreCase("positive_yes")){
        	setContentView(R.layout.activity_hiv_positive_yes);
            getSupportActionBar().setSubtitle("PNC Diagnostic: HIV Infection > Positive");
       	    json=new JSONObject();
       	    try {
       			json.put("page", "PNC Diagnostic: HIV Infection > Positive");
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
 				Intent intent=new Intent(TakeActionHIVInfectionActivity.this,InfantFeedingNextActivity.class);
 				intent.putExtra("value", "taking_arv");
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
