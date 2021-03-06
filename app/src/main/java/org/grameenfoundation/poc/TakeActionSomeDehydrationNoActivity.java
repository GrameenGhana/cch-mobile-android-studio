package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.poc.NoInjuriesActivity.NoInjuriesListAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TakeActionSomeDehydrationNoActivity extends BaseActivity {

	private String take_action_category;
	private ListView listView_someDehydrationNo;
	private DbHelper dbh;
	private Long start_time;
	private JSONObject json;
	private Long end_time;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = TakeActionSomeDehydrationNoActivity.this;
		dbh=new DbHelper(TakeActionSomeDehydrationNoActivity.this);
		start_time=System.currentTimeMillis();
        getSupportActionBar().setTitle("Point of Care");
	   
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			take_action_category = extras.getString("category");
		}
		if (take_action_category.equals("yes")) {
			setContentView(R.layout.activity_some_dehydration_yes);
            getSupportActionBar().setSubtitle("PNC Diagnostic: Diarrhoea > Some Dehydration");
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
			TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
			   click_here.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(TakeActionSomeDehydrationNoActivity.this,KeepingBabyWarmAndMalariaActivity.class);
					intent.putExtra("value", "keeping_baby_warm");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
				   
			   });
			   
			   TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
			   click_here_too.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(TakeActionSomeDehydrationNoActivity.this,TreatingDiarrhoeaActivity.class);
					intent.putExtra("value", "keeping_baby_warm");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
				   
			   });
		} else if (take_action_category.equals("no")) {
			setContentView(R.layout.activity_some_dehydration_no);
            getSupportActionBar().setSubtitle("PNC Diagnostic: Diarrhoea > Some Dehydration");
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
			listView_someDehydrationNo = (ListView) findViewById(R.id.listView_someDehydrationNo);
			String[] items = {
					"Home visit or Outreach Clinic",
					"CHPS + mother & baby is able to stay for > 4 hours at facility ",
					"CHPS or Health Center + clients NOT able to stay for 4 hours at facility",
					"Diarrhoea with No Dehydration  " };
			SomeDehydrationListAdapter adapter = new SomeDehydrationListAdapter(
					TakeActionSomeDehydrationNoActivity.this, items);
			listView_someDehydrationNo.setAdapter(adapter);
			listView_someDehydrationNo.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent;
					switch(position){
					case 0:
						intent=new Intent(mContext,TakeActionSomeDehydrationEncounterActivity.class);
						intent.putExtra("category", "home_visit");
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
						break;
					case 1:
						intent=new Intent(mContext,TakeActionSomeDehydrationEncounterActivity.class);
						intent.putExtra("category", "chps_one");
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
						break;
					case 2:
						intent=new Intent(mContext,TakeActionSomeDehydrationEncounterActivity.class);
						intent.putExtra("category", "chps_two");
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
						break;
					case 3:
						intent=new Intent(mContext,TakeActionDiarrhoeaNoDehydrationActivity.class);
						intent.putExtra("category", "chps_two");
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
						break;
					}
					
				}
				
			});
		}
	}

	class SomeDehydrationListAdapter extends BaseAdapter {
		Context mContext;
		String[] listItems;
		public LayoutInflater minflater;

		public SomeDehydrationListAdapter(Context mContext, String[] listItems) {
			this.mContext = mContext;
			this.listItems = listItems;
			minflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			return listItems.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = minflater.inflate(R.layout.listview_text_single,
						parent, false);
			}
			TextView text = (TextView) convertView
					.findViewById(R.id.textView_listViewText);
			text.setText(listItems[position]);
			return convertView;
		}

	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", json.toString() , start_time.toString(), end_time.toString());
		finish();
	}
}
