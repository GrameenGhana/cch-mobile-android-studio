package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AskMalariaFeverPNCMotherActivity extends BaseActivity {

	private Button button_no;
	private Button button_yes;
	private Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext = AskMalariaFeverPNCMotherActivity.this;
	    	    setContentView(R.layout.activity_malaria_fever_ask);
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
        getSupportActionBar().setTitle("Point of Care");
        getSupportActionBar().setSubtitle("PNC Mother Diagnostic: Malaria");
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Mother Diagnostic: Malaria");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    button_yes=(Button) findViewById(R.id.button_yes);
	    button_yes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,AskMalariaFeverMotherPNCYesActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,AnaemiaPNCMotherAskActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic Malaria", start_time.toString(), end_time.toString());
	    dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
