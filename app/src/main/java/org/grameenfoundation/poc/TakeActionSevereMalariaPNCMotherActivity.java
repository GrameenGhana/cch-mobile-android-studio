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
import android.widget.TextView;

public class TakeActionSevereMalariaPNCMotherActivity extends BaseActivity {
	private String take_action_category;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private String data;
	private Context mContext;
	private JSONObject json;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras();
        getSupportActionBar().setTitle("Point of Care");
	    mContext=TakeActionSevereMalariaPNCMotherActivity.this;
	    dbh=new DbHelper(TakeActionSevereMalariaPNCMotherActivity.this);
	    start_time=System.currentTimeMillis();
        if (extras != null) {
          take_action_category= extras.getString("category");
        }
        if(take_action_category.equals("severe_malaria")){
        	setContentView(R.layout.activity_severe_malaria);
            getSupportActionBar().setSubtitle("PNC Mother Diagnostic: Malaria > Severe Malaria");
            data="PNC Mother Diagnostic: Malaria > Severe Malaria";
        }else if(take_action_category.equals("Ist Trimester")){
        setContentView(R.layout.activity_first_trimester_malaria);
            getSupportActionBar().setSubtitle("PNC Mother Diagnostic: Malaria > 1st Trimester");
        data="PNC Mother Diagnostic: Malaria > 1st Trimester";
        }else if(take_action_category.equals("2nd Trimester")){
            setContentView(R.layout.activity_second_trimester_malaria);
            getSupportActionBar().setSubtitle("PNC Mother Diagnostic: Malaria > 2nd Trimester");
            data="PNC Mother Diagnostic: Malaria > 2nd Trimester";
            }
        else if(take_action_category.equals("3rd Trimester")){
            setContentView(R.layout.activity_third_trimester_malaria);
            getSupportActionBar().setSubtitle("PNC Mother Diagnostic: Malaria > 3rd Trimester");
            data="PNC Mother Diagnostic: Malaria > 3rd Trimester";
            }
	
        else if(take_action_category.equals("negative")){
            setContentView(R.layout.activity_malaria_test_negative_pnc_mother);
            getSupportActionBar().setSubtitle("PNC Mother Diagnostic: Malaria > Test Negative");
            data="PNC Mother Diagnostic:  Malaria > Test Negative";
            TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
            click_here.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "malaria");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
            	
            });
            }
        else if(take_action_category.equals("not done")){
            setContentView(R.layout.activity_malaria_test_not_done_pnc_mother);
            getSupportActionBar().setSubtitle("PNC Mother Diagnostic: Malaria > Test not done");
            data="PNC Mother Diagnostic: Malaria > Test not done";
            TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
            click_here.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "malaria");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
            	
            });
            }
        else if(take_action_category.equals("severe anaemia")){
            setContentView(R.layout.activity_severe_anaemia_take_action);
            getSupportActionBar().setSubtitle("PNC Mother Diagnostic: Anaemia > Severe Anaemia");
            data="PNC Mother Diagnostic: Anaemia > Severe Anaemia";
            }
        else if(take_action_category.equals("moderate_anaemia")){
        setContentView(R.layout.activity_moderate_anaemia);
            getSupportActionBar().setSubtitle("PNC Mother Diagnostic: Anaemia > Moderate Anaemia");
        data="PNC Mother Diagnostic:  Anaemia > Moderate Anaemia";
        TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
        click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,NutritionCounsellingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
        	
        });
        }
        else if(take_action_category.equals("no anaemia")){
            setContentView(R.layout.activity_no_anaemia_take_action);
            getSupportActionBar().setSubtitle("PNC Mother Diagnostic: Anaemia > No Anaemia");
            data="PNC Mother Diagnostic: Anaemia > No Anaemia";
            TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
            click_here.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(mContext,NutritionCounsellingActivity.class);
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
            	
            });
            TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
            click_here_too.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(mContext,SoftUterusPNCMotherActivity.class);
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
            	
            });
            TextView click_here_three=(TextView) findViewById(R.id.textView_clickHereThree);
            click_here_three.setOnClickListener(new OnClickListener(){

    			@Override
    			public void onClick(View v) {
    				Intent intent=new Intent(mContext,BreastProblemsCounsellingActivity.class);
    				startActivity(intent);
    				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
    			}
            	
            });
            }else if(take_action_category.equals("no_anaemia_anc")){
                setContentView(R.layout.activity_anc_no_anaemia);
            getSupportActionBar().setSubtitle("ANC Diagnostic: Anaemia > No Anaemia");
                data="ANC Diagnostic:  Anaemia > No Anaemia";
                TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
                click_here.setOnClickListener(new OnClickListener(){

        			@Override
        			public void onClick(View v) {
        				Intent intent=new Intent(mContext,NutritionCounsellingActivity.class);
        				startActivity(intent);
        				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        			}
                	
                });
                TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
                click_here_too.setOnClickListener(new OnClickListener(){

        			@Override
        			public void onClick(View v) {
        				Intent intent=new Intent(mContext,ANCCounsellingTopicsMenuActivity.class);
        				startActivity(intent);
        				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        			}
                	
                });
                
                }
	
	   
	}
	public void onBackPressed()
	{
		json=new JSONObject();
	    try {
			json.put("page", data);
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", json.toString() , start_time.toString(), end_time.toString());
		finish();
	}
}
