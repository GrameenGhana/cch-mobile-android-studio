package org.grameenfoundation.cch.activity;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.poc.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class UpdateTargetActivity extends BaseActivity{

	private DbHelper db;
	private Context mContext;
	 public ArrayList<EventTargets> DailyTargets;
	 public ArrayList<EventTargets> WeeklyTargets;
	 public ArrayList<EventTargets> MonthlyTargets;
	 public ArrayList<EventTargets> QuarterlyTargets;
	 public ArrayList<EventTargets> MidyearTargets;
	 public ArrayList<EventTargets> AnnualTargets;
	String due_date;
	private ListView expandableListview;
	private TextView textView_label;
	private TextView textView_number;
	private ListAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_achievements_details);
	    expandableListview = (ListView) findViewById(R.id.expandableListView1);
	    mContext=UpdateTargetActivity.this;
	    db=new DbHelper(UpdateTargetActivity.this);
	    String[] groupItems={"Other"};
        getSupportActionBar().setTitle("Planner");
        getSupportActionBar().setSubtitle("Update Targets");
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setVisibility(View.GONE);
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    textView_number.setVisibility(View.GONE);
	    adapter=new ListAdapter(mContext,groupItems);
	    expandableListview.setAdapter(adapter);
	    expandableListview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,OtherTargetsUpdateActivity.class);
					startActivity(intent);
					break;
				}
			}	    	
	    });
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public ListAdapter(Context mContext,String[] listItems){
		this.mContext=mContext;
		this.listItems=listItems;
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
			if( convertView == null ){
				  convertView = minflater.inflate(R.layout.other_listview_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_otherCategory);
			 text.setText(listItems[position]);
			    return convertView;
		}
		
	}
	
}
