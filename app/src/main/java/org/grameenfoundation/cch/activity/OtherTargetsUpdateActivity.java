package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.adapters.UpdateTargetsAdapter;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.poc.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

public class OtherTargetsUpdateActivity extends BaseActivity {

	Context mContext;
	private DbHelper db;
	private ExpandableListView listView_other;
	private UpdateTargetsAdapter other_adapter;
	private TextView textView_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_targets);
	    mContext=OtherTargetsUpdateActivity.this;
	    db=new DbHelper(mContext);
        getSupportActionBar().setTitle("Planner");
        getSupportActionBar().setSubtitle("Update Other Targets");
	    listView_other=(ExpandableListView) findViewById(R.id.expandableListView1);
	    textView_name=(TextView) findViewById(R.id.textView_name);
	    textView_name.setText("Other Targets");
	    new GetData().execute();
	    listView_other.setOnChildClickListener(new OnChildClickListener() {
			
			private String[] selected_items;
			private long selected_id;
	

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				selected_items=other_adapter.getChild(groupPosition, childPosition);
				selected_id=Long.parseLong(selected_items[7]);
				String event_name=selected_items[0];
				String event_number=selected_items[1];
				String event_period=selected_items[2];
				String due_date=selected_items[3];
				String start_date=selected_items[5];
				String status=selected_items[6];
				String last_updated=selected_items[8];
				String target_detail=selected_items[11];
				long old_id=Long.parseLong(selected_items[9]);
				//String achieved=selected_items[4];
				ArrayList<String> number_achieved_list=db.getNumberAchieved(selected_id, event_period,MobileLearning.CCH_TARGET_STATUS_NEW);
				Intent intent=new Intent(mContext,UpdateActivity.class);
				intent.putExtra("id",selected_id);
				intent.putExtra("old_id",old_id);
				intent.putExtra("name",event_name);
				intent.putExtra("number",event_number);
				intent.putExtra("period", event_period);
				intent.putExtra("due_date", due_date);
				intent.putExtra("type", "other");
				intent.putExtra("start_date", start_date);
				intent.putExtra("number_achieved", number_achieved_list.get(0));
				intent.putExtra("status", status);
				intent.putExtra("last_updated", last_updated);
				intent.putExtra("event_detail", target_detail);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					return true;
			}
	    
		});
	}

	private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(mContext);
		private ArrayList<EventTargets> DailyEventTargets;
		private ArrayList<EventTargets> WeeklyEventTargets;
		private ArrayList<EventTargets> MonthlyEventTargets;
		private ArrayList<EventTargets> QuarterlyEventTargets;
		private ArrayList<EventTargets> MidyearEventTargets;
		private ArrayList<EventTargets> AnnualEventTargets;
		private int event_number1;
		private int event_number2;
		private int event_number3;
		private int event_number4;
		private int event_number5;
		private int event_number6;
		private String[] groupItems;
	
	

	    @Override
	    protected Object doInBackground(Object... params) {
 		      
	           	DailyEventTargets=db.getListOfDailyTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_OTHER);
	           	WeeklyEventTargets=db.getListOfWeeklyTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_OTHER);
	   			MonthlyEventTargets=db.getListOfMonthlyTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_OTHER);
	   			QuarterlyEventTargets=db.getListOfQuarterlylyTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_OTHER);
	   			MidyearEventTargets=db.getListOfMidYearTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_OTHER);
	   			AnnualEventTargets=db.getListOfAnnualTargetsToUpdate(MobileLearning.CCH_TARGET_TYPE_OTHER);
				return null;
	    }
	    @Override
	    protected void onPostExecute(Object result) {
	    	 event_number1=(int)DailyEventTargets.size();
			  event_number2=(int)WeeklyEventTargets.size();
			  event_number3=(int)MonthlyEventTargets.size();
			  event_number4=(int)QuarterlyEventTargets.size();
			  event_number5=(int)MidyearEventTargets.size();
			  event_number6=(int)AnnualEventTargets.size();
			  groupItems=new String[]{"To update daily ("+String.valueOf(event_number1)+")",
						"To update weekly ("+String.valueOf(event_number2)+")",
						"To update monthly ("+String.valueOf(event_number3)+")",
						"To update quarterly ("+String.valueOf(event_number4)+")",
						"To update mid-yearly ("+String.valueOf(event_number5)+")",
						"To update annually ("+String.valueOf(event_number6)+")"};
			   
	    	other_adapter=new UpdateTargetsAdapter(mContext,DailyEventTargets,
					  WeeklyEventTargets,
					  MonthlyEventTargets,
					  QuarterlyEventTargets,
					  MidyearEventTargets,
					  AnnualEventTargets,
					  groupItems,
					  listView_other);
	    	listView_other.setAdapter(other_adapter);
	    }
	}
	
	
	/*
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		 data=new JSONObject();
		    try {
		    	data.put("page", "Target Updates");
		    	data.put("ver", db.getVersionNumber(mContext));
		    	data.put("battery", db.getBatteryStatus(mContext));
		    	data.put("device", db.getDeviceName());
				data.put("imei", db.getDeviceImei(mContext));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		 db.insertCCHLog("Achievement Center",data.toString(), start_time.toString(), end_time.toString());
		finish();
	}*/
}
