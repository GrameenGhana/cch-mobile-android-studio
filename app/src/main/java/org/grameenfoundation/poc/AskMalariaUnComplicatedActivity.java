package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.poc.AskMalariaComplicatedActivity.ListAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AskMalariaUnComplicatedActivity extends BaseActivity {
//	private Context mContext;
	private ListView listView_malaria;
	private Button button_next;
	 private DbHelper dbh;
		private Long start_time;
		private Long end_time;
		private JSONObject json;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_uncomplicated_malaria_ask);
	    mContext=AskMalariaUnComplicatedActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
        getSupportActionBar().setTitle("Point of Care");
        getSupportActionBar().setSubtitle("ANC Diagnostic: Malaria");
	    json=new JSONObject();
	    try {
			json.put("page", "ANC Diagnostic: Malaria");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    listView_malaria=(ListView) findViewById(R.id.listView_malaria);
	    String[] items={"Fever or history of fever present within the last 2-3 days ",
	    				"Chills","Rigor (shivering) ",
    					"Headache ","Body and joint pain ","Nausea with or without vomiting ",
    					"Loss of appetite ","Sweating ","Bitterness in the mouth "};
	    ListAdapter adapter=new ListAdapter(mContext,items);
	    listView_malaria.setAdapter(adapter);
	    button_next=(Button) findViewById(R.id.button_next);
	    button_next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,AskMalariaTrimesterActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	}
	
	
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		 public LayoutInflater minflater;
		public ListAdapter(Context mContext,String[] items){
			this.mContext=mContext;
			this.items=items;
			 minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 LinearLayout layout=(LinearLayout) convertView.findViewById(R.id.Linearlayout_listView);
			 layout.setPadding(0, 5, 0, 5);
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bullet, 0, 0, 0);
			 text.setText(items[position]);
			 
			    return convertView;
		}
		
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "ANC Diagnostic Malaria", start_time.toString(), end_time.toString());
	    dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
