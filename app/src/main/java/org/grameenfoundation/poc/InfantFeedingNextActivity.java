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
import android.widget.Button;

public class InfantFeedingNextActivity extends BaseActivity {

	private String take_action_category;
	private Button button_next;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private String data;
	private JSONObject json;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Point of Care");
	  mContext=InfantFeedingNextActivity.this;
	    dbh=new DbHelper(InfantFeedingNextActivity.this);
	    start_time=System.currentTimeMillis();
	   
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if (take_action_category.equals("exclusive_breastfeeding")){
        	setContentView(R.layout.activity_exclusive_breastfeeding_next);
            getSupportActionBar().setSubtitle("PNC Counselling: Exclusive Breastfeeding");
        	  data="PNC Counselling Exclusive Breastfeeding";
        }else if (take_action_category.equals("breast_attachement")){
        	setContentView(R.layout.activity_breast_attachement_next);
            getSupportActionBar().setSubtitle("PNC Counselling: Breast Attachment");
        	  data="PNC Counselling Breast Attachment";
        	button_next=(Button) findViewById(R.id.button_next);
        	button_next.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(InfantFeedingNextActivity.this,GoodAttachementActivity.class);
					intent.putExtra("value", "good_attachement");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
        	});
        }else if(take_action_category.equals("feeding_frequency")){
        	setContentView(R.layout.activity_feeding_frequency_next);
            getSupportActionBar().setSubtitle("PNC Counselling: Feeding Frequency");
        	  data="PNC Counselling Feeding Frequency";
        }else if (take_action_category.equals("low_birth_weight")){
        	setContentView(R.layout.activity_breastfeeding_low_birth_weight);
            getSupportActionBar().setSubtitle("PNC Counselling: Low Birth Weight Baby");
        	  data="PNC Counselling Low Birth Weight Baby";
        	button_next=(Button) findViewById(R.id.button_next);
        	button_next.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(InfantFeedingNextActivity.this,GoodAttachementActivity.class);
					intent.putExtra("value", "low_birth_weight_next");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
        		
        	});
        }else if(take_action_category.equals("separated_from_baby")){
        	setContentView(R.layout.activity_away_from_baby);
            getSupportActionBar().setSubtitle("PNC Counselling: Infant Feeding");
        	  data="PNC Counselling Infant Feeding";
        }else if(take_action_category.equals("feeding_sick_baby")){
        	setContentView(R.layout.activity_feeding_sick_baby);
            getSupportActionBar().setSubtitle("PNC Counselling: Infant Feeding");
        	  data="PNC Counselling Infant Feeding";
        }else if(take_action_category.equals("taking_arv")){
        	setContentView(R.layout.activity_mother_taking_arv);
            getSupportActionBar().setSubtitle("PNC Counselling: Infant Feeding");
        	  data="PNC Counselling Infant Feeding";
        }else if(take_action_category.equals("not_taking_arv")){
        	setContentView(R.layout.activity_mother_not_taking_arv);
            getSupportActionBar().setSubtitle("PNC Counselling: Infant Feeding");
        	  data="PNC Counselling Infant Feeding";
        }else if(take_action_category.equals("mother_remember")){
        	setContentView(R.layout.activity_mother_must_remember);
            getSupportActionBar().setSubtitle("PNC Counselling: Infant Feeding");
        	  data="PNC Counselling Infant Feeding";
        }
        
        
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", data);
			json.put("section", MobileLearning.CCH_COUNSELLING);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
