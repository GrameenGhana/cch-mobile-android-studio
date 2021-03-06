package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;

public class PostpartumExercisesNextFourActivity extends BaseActivity {

	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext= PostpartumExercisesNextFourActivity.this;
	    setContentView(R.layout.activity_postpartum_exercises_next_four);
        getSupportActionBar().setTitle("Point of Care");
        getSupportActionBar().setSubtitle("PNC Counselling: Postpartum Exercises");
	    dbh=new DbHelper(PostpartumExercisesNextFourActivity.this);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "PNC Counselling: Postpartum Exercises");
			json.put("section", MobileLearning.CCH_COUNSELLING);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
