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

public class TakeActionSomeDehydration extends BaseActivity {

	private Button button_yes;
	private Button button_no;
	private Context mContext;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject json;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_some_dehydration);
		mContext=TakeActionSomeDehydration.this;
        getSupportActionBar().setTitle("Point of Care");
        getSupportActionBar().setSubtitle("PNC Diagnostic: Diarrhoea > Some Dehydration");
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Diagnostic: Diarrhoea > Some Dehydration");
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
			Intent intent=new Intent(mContext, TakeActionSomeDehydrationNoActivity.class);
			intent.putExtra("category","yes");	
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			
		});
		button_no=(Button) findViewById(R.id.button_no);
		button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			Intent intent=new Intent(mContext, TakeActionSomeDehydrationNoActivity.class);
			intent.putExtra("category","no");	
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			
		});
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", json.toString() , start_time.toString(), end_time.toString());
		finish();
	}
}
