package org.grameenfoundation.cch.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.grameenfoundation.cch.utils.TextProgressBar;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class CoverageTargetsDetailActivity extends ActionBarActivity {

	private TextView textView_dueDate;
	private TextView textView_period;
	private TextView textView_name;
	private ImageView imageView_status;
	private static String due_date;
	private String status;
	private long coverage_id;
	private TextView textView_number;
	private DbHelper db;
	private RadioGroup category_options;
	private String[] items3;
	private String coverage_name;
	private String coverage_number;
	private String coverage_period;
	private String due_date_extra;
	private String last_updated;
	private Button button_edit;
	private Button button_delete;
	private Button button_update;
	private TextView textView_startDate;
	private String start_date_extra;
	private TextView textView_achieved;
	private String achieved;
	private TextView textView_percentageAchieved;
	private Integer due_date_day;
	private Integer due_date_month;
	private Integer due_date_year;
	private int date_difference;
	private int today_month;
	private int today_day;
	private int today_year;
	private TextView textView_progressCheck;
	private static TextView dueDateValue;
	private static TextView startDateValue;
	private static HashMap<String, String> updateItems;
	private static ArrayList<String> number_entered;
	private static ArrayList<String> number_remaining;
	private static ArrayList<String> number_achieved;
	private static ArrayList<String> update_id;
	private String coverage_detail;
	private String coverage_detail_extra;
	private TextProgressBar progress_bar;
	private int progress_status;
	static long due_date_to_compare;
	private static String start_date;
	private long end_time;
	private long start_time;
	private TextView day_difference;
	public long coverage_old_id;
	private String coverage_category;
	private String coverage_category_extra;
	private RadioButton category_people;
	private MaterialSpinner spinner_coverageName;
	private MaterialSpinner spinner_coverageDetails;
	private MaterialSpinner spinner_coveragePeriod;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_target_detail);
	    db=new DbHelper(CoverageTargetsDetailActivity.this);
        getSupportActionBar().setTitle("Event Planner");
        getSupportActionBar().setSubtitle("Coverage Target Details");
	    Calendar c = Calendar.getInstance();
        today_month=c.get(Calendar.MONTH)+1;
        today_day=c.get(Calendar.DAY_OF_WEEK);
        today_year=c.get(Calendar.YEAR);
        start_time=System.currentTimeMillis();
	    textView_name=(TextView) findViewById(R.id.textView_name);
	    textView_period=(TextView) findViewById(R.id.textView_period);
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    textView_dueDate=(TextView) findViewById(R.id.textView_dueDate);
	    textView_startDate=(TextView) findViewById(R.id.textView_startDate);
	    textView_achieved=(TextView) findViewById(R.id.textView_achieved);
	    textView_percentageAchieved=(TextView) findViewById(R.id.textView_percentageAchieved);
	    textView_progressCheck=(TextView) findViewById(R.id.textView_progressCheck);
	    day_difference=(TextView) findViewById(R.id.textView_dayDifference);
	    progress_bar=(TextProgressBar) findViewById(R.id.progressBar1);
	    imageView_status=(ImageView) findViewById(R.id.imageView_status);
	    button_edit=(Button) findViewById(R.id.button_edit);
	    button_delete=(Button) findViewById(R.id.button_delete);
	    button_update=(Button) findViewById(R.id.button_update);
	    new GetData().execute();
        button_update.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				  number_achieved=db.getNumberAchieved(coverage_id,coverage_period,MobileLearning.CCH_TARGET_STATUS_NEW);
				Intent intent3 = new Intent(Intent.ACTION_MAIN);
				intent3.setClass(CoverageTargetsDetailActivity.this,UpdateActivity.class);
				intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	intent3.putExtra("id", coverage_id);
	        	intent3.putExtra("old_id",coverage_old_id);
				intent3.putExtra("number",coverage_number);
				intent3.putExtra("name", coverage_name);
				intent3.putExtra("type", "coverage");
				intent3.putExtra("start_date", start_date_extra);
				intent3.putExtra("due_date", due_date_extra);
				intent3.putExtra("period", coverage_period);
				intent3.putExtra("last_updated", last_updated);
				intent3.putExtra("number_achieved", number_achieved.get(0));//0
	        	startActivity(intent3);
	        	finish();
	        	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
        });
        button_delete.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						CoverageTargetsDetailActivity.this);
					alertDialogBuilder.setTitle("Delete Confirmation");
					alertDialogBuilder
						.setMessage("You are about to delete this target. Proceed?")
						.setCancelable(false)
						.setIcon(R.drawable.ic_error)
						.setPositiveButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								dialog.cancel();
							}
						  })
						.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								if(db.deleteTarget(coverage_id)==true){
									  number_achieved=db.getNumberAchieved(coverage_id,coverage_period,MobileLearning.CCH_TARGET_STATUS_NEW);
									JSONObject json = new JSONObject();
									 try {
										 if(coverage_old_id!=0){
											 json.put("id", coverage_old_id);
										 }else{
											 json.put("id", coverage_id);
										 }
										json.put("category", "coverage");
										json.put("target_type", coverage_name);
										 json.put("target_number", coverage_number);
										 json.put("start_date", start_date_extra);
										 json.put("due_date", due_date_extra);
										 json.put("achieved_number", achieved);
										 json.put("last_updated", last_updated);
										json.put("deleted", 1);
										json.put("changed", 0);
										json.put("ver", db.getVersionNumber(CoverageTargetsDetailActivity.this));
										json.put("battery", db.getBatteryStatus(CoverageTargetsDetailActivity.this));
								    	json.put("device", db.getDeviceName());
								    	json.put("imei", db.getDeviceImei(CoverageTargetsDetailActivity.this));
									} catch (JSONException e) {
										e.printStackTrace();
									}
									 end_time=System.currentTimeMillis();
									 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
					        		Intent intent2 = new Intent(Intent.ACTION_MAIN);
						 	          intent2.setClass(CoverageTargetsDetailActivity.this, NewEventPlannerActivity.class);
						 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						 	          startActivity(intent2);
						 	          finish();	
						 	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
						 	         Toast.makeText(CoverageTargetsDetailActivity.this, "Deleted successfully!",
									         Toast.LENGTH_LONG).show();
					        	}
							}
						});
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
			}	
        });
        button_edit.setOnClickListener(new OnClickListener(){
			private ArrayAdapter<String> coverage_name_adapter;
			private ArrayAdapter<String> details_adapter;

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(CoverageTargetsDetailActivity.this);
				dialog.setContentView(R.layout.coverage_add_dialog);
				spinner_coverageName=(MaterialSpinner) dialog.findViewById(R.id.spinner_dialogCoverageName);
				spinner_coverageDetails=(MaterialSpinner) dialog.findViewById(R.id.spinner_dialogCoverageDetail);
				final Button button_show=(Button) dialog.findViewById(R.id.button_show);
				button_show.setVisibility(View.GONE);
				dialog.setTitle("Edit Coverage Target");
				 category_options=(RadioGroup) dialog.findViewById(R.id.radioGroup_category);
				 
				  items3=new String[]{};
				  items3=getResources().getStringArray(R.array.CoverageIndicatorsName);
				  coverage_name_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items3);
					spinner_coverageName.setAdapter(coverage_name_adapter);
					
					String[] detail_items1=getSpinnerData(spinner_coverageName.getSelectedItem().toString());
					details_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items1);
					spinner_coverageDetails.setAdapter(details_adapter);
					
					if(coverage_category_extra!=null&&coverage_category_extra.equalsIgnoreCase("Indicators")){
						category_options.check(R.id.radio_people);
						String[] items=new String[]{};
						items=getResources().getStringArray(R.array.CoverageIndicatorsName);
						 coverage_name_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
						 spinner_coverageName.setAdapter(coverage_name_adapter);
						 spinner_coverageName.setSelection(getIndex(spinner_coverageName,coverage_detail_extra));
						 
						 items3=getSpinnerData(coverage_detail_extra);
						 details_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items3);
						 spinner_coverageDetails.setAdapter(details_adapter);
						 spinner_coverageDetails.setSelection(getIndex(spinner_coverageDetails, coverage_name));
					}else if(coverage_category_extra!=null&&coverage_category_extra.equalsIgnoreCase("Immunizations")){
						category_options.check(R.id.radio_immunization);
						String[] items=new String[]{};
						items=getResources().getStringArray(R.array.CoverageImmunizationTypes);
						coverage_name_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
						 spinner_coverageName.setAdapter(coverage_name_adapter);
						 spinner_coverageName.setSelection(getIndex(spinner_coverageName,coverage_detail_extra));
						 
							items3=getSpinnerData(coverage_detail_extra);
							details_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items3);
							 spinner_coverageDetails.setAdapter(details_adapter);
							 spinner_coverageDetails.setSelection(getIndex(spinner_coverageDetails, coverage_name));
					}
				  spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

						@Override
						public void onItemSelected(
								AdapterView<?> parent, View view,
								int position, long id) {
						if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Family Planning")){
								String[] detail_items1=getResources().getStringArray(R.array.CoverageIndicatorsDetailFamilyPlanning);
								ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items1);
								spinner_coverageDetails.setAdapter(details_adapter);
								coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
						}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Age Groups")){
								String[] detail_items2=getResources().getStringArray(R.array.CoverageIndicatorsDetailAgeGroups);
								ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items2);
								spinner_coverageDetails.setAdapter(details_adapter2);
								coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
						}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("School Health")){	
								String[] detail_items3=getResources().getStringArray(R.array.CoverageIndicatorsSchoolHealth);
								ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items3);
								spinner_coverageDetails.setAdapter(details_adapter3);
								coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
						}
							
						}

						@Override
						public void onNothingSelected(
								AdapterView<?> parent) {
						}
						
					});
					
				    category_options.setOnCheckedChangeListener(new OnCheckedChangeListener(){
						

						public void onCheckedChanged(
								RadioGroup buttonView,
								int isChecked) {
							if (isChecked == R.id.radio_people) {
								coverage_category="Indicators";
								items3=new String[]{};
								  items3=getResources().getStringArray(R.array.CoverageIndicatorsName);
								ArrayAdapter<String> adapter3=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter3);
								spinner_coverageName.setSelection(getIndex(spinner_coverageName,coverage_detail_extra));
								String[] detail_items1=getSpinnerData(spinner_coverageName.getSelectedItem().toString());
								ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items1);
								spinner_coverageDetails.setAdapter(details_adapter);
								 spinner_coverageDetails.setSelection(getIndex(spinner_coverageDetails, coverage_name));
								spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){
																										
									@Override
									public void onItemSelected(
											AdapterView<?> parent, View view,
											int position, long id) {
										
										if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Family Planning")){
											String[] detail_items1=getResources().getStringArray(R.array.CoverageIndicatorsDetailFamilyPlanning);
											ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items1);
											spinner_coverageDetails.setAdapter(details_adapter);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
									}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Age Groups")){
											String[] detail_items2=getResources().getStringArray(R.array.CoverageIndicatorsDetailAgeGroups);
											ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items2);
											spinner_coverageDetails.setAdapter(details_adapter2);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
									}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("School Health")){	
											String[] detail_items3=getResources().getStringArray(R.array.CoverageIndicatorsSchoolHealth);
											ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items3);
											spinner_coverageDetails.setAdapter(details_adapter3);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
									}
										
									}

									@Override
									public void onNothingSelected(
											AdapterView<?> parent) {
									}
									
								});
								
							} else if (isChecked == R.id.radio_immunization) {
								coverage_category="Immunizations";
								items3=new String[]{};
								items3=getResources().getStringArray(R.array.CoverageImmunizationTypes);
								ArrayAdapter<String> adapter4=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter4);
								spinner_coverageName.setSelection(getIndex(spinner_coverageName,coverage_detail_extra));
								String[] detail_items1=getResources().getStringArray(R.array.CoverageImmunizationTypeBCG);
								ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items1);
								spinner_coverageDetails.setAdapter(details_adapter);
								 spinner_coverageDetails.setSelection(getIndex(spinner_coverageDetails, coverage_name));
								spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

									@Override
									public void onItemSelected(
											AdapterView<?> parent, View view,
											int position, long id) {
										if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("BCG")){
											String[] detail_items1=getResources().getStringArray(R.array.CoverageImmunizationTypeBCG);
											ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items1);
											spinner_coverageDetails.setAdapter(details_adapter);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}
										else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Penta")){
											String[] detail_items2=getResources().getStringArray(R.array.CoverageImmunizationPenta);
											ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items2);
											spinner_coverageDetails.setAdapter(details_adapter2);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("OPV")){
											String[] detail_items3=getResources().getStringArray(R.array.CoverageImmunizationOPV);
											ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items3);
											spinner_coverageDetails.setAdapter(details_adapter3);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("ROTA")){
											String[] detail_items4=getResources().getStringArray(R.array.CoverageImmunizationROTA);
											ArrayAdapter<String> details_adapter4=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items4);
											spinner_coverageDetails.setAdapter(details_adapter4);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("PCV")){
											String[] detail_itemsPCV=getResources().getStringArray(R.array.CoverageImmunizationPCV);
											ArrayAdapter<String> details_adapterPCV=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_itemsPCV);
											spinner_coverageDetails.setAdapter(details_adapterPCV);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Measles Rubella")){
											String[] detail_items5=getResources().getStringArray(R.array.CoverageImmunizationMeaslesRubella);
											ArrayAdapter<String> details_adapter5=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items5);
											spinner_coverageDetails.setAdapter(details_adapter5);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Vitamin A")){
											String[] detail_items6=getResources().getStringArray(R.array.CoverageImmunizationVitaminA);
											ArrayAdapter<String> details_adapter6=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items6);
											spinner_coverageDetails.setAdapter(details_adapter6);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("TT pregnant")){
											String[] detail_items7=getResources().getStringArray(R.array.CoverageImmunizationTT);
											ArrayAdapter<String> details_adapter7=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items7);
											spinner_coverageDetails.setAdapter(details_adapter7);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("TT non-pregnant")){
											String[] detail_items8=getResources().getStringArray(R.array.CoverageImmunizationTT);
											ArrayAdapter<String> details_adapter8=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items8);
											spinner_coverageDetails.setAdapter(details_adapter8);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}else if(spinner_coverageName.getSelectedItem().toString().equalsIgnoreCase("Yellow Fever")){
											String[] detail_items9=getResources().getStringArray(R.array.CoverageImmunizationYellowFever);
											ArrayAdapter<String> details_adapter9=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, detail_items9);
											spinner_coverageDetails.setAdapter(details_adapter9);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										}
										
									}

									@Override
									public void onNothingSelected(AdapterView<?> parent) {
										
									}
									
								});
								
							}
						}
				    });
				    Button cancel=(Button) dialog.findViewById(R.id.button_cancel);
					cancel.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
						dialog.dismiss();
							
						}
						
					});
				String[] items2=getResources().getStringArray(R.array.ReminderFrequency);
				spinner_coveragePeriod=(MaterialSpinner) dialog.findViewById(R.id.spinner_coveragePeriod);
				ArrayAdapter<String> spinner_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items2);
				spinner_coveragePeriod.setAdapter(spinner_adapter);
				spinner_coveragePeriod.setSelection(getIndex(spinner_coveragePeriod,coverage_period));
				final EditText editText_coverageNumber=(EditText) dialog.findViewById(R.id.editText_dialogCoverageNumber);
				if(coverage_number!=null){
					editText_coverageNumber.setText(coverage_number);
				}else {
					editText_coverageNumber.setText(0);
				}
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddCoverage);
				dialogButton.setText("Save");
				dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
				if(due_date_extra!=null){
					dueDateValue.setText(due_date_extra);
				}else{
					dueDateValue.setText("null");
				}
				startDateValue=(TextView) dialog.findViewById(R.id.textView_startDate);
				if(start_date_extra!=null){
					startDateValue.setText(start_date_extra);
				}else{
					startDateValue.setText("null");
				}
				ImageButton datepickerDialog=(ImageButton) dialog.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", today_month, today_year);
						dialogCaldroidFragment.show(getSupportFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	dueDateValue.setText(formatter.format(date));
						    	due_date=formatter.format(date);
						        Toast.makeText(CoverageTargetsDetailActivity.this, formatter.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};
						dialogCaldroidFragment.setCaldroidListener(listener);
					}
				});
				ImageButton datepickerDialog2=(ImageButton) dialog.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", today_month, today_year);
						dialogCaldroidFragment.show(getSupportFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	startDateValue.setText(formatter.format(date));
						    	start_date=formatter.format(date);
						        Toast.makeText(CoverageTargetsDetailActivity.this, formatter.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};
						dialogCaldroidFragment.setCaldroidListener(listener);
					}
				});
				dialogButton.setOnClickListener(new OnClickListener() {
					

					@Override
					public void onClick(View v) {
						//dialog.dismiss();
						String duration = " ";
						String coverage_name=spinner_coverageName.getSelectedItem().toString();
						String coverage_period=spinner_coveragePeriod.getSelectedItem().toString();
						String coverage_number=editText_coverageNumber.getText().toString();
						coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
						if(!checkValidation()){
				      		Toast.makeText(CoverageTargetsDetailActivity.this, "Provide data for required fields!", Toast.LENGTH_LONG).show();
				      	}else{
				      		if(db.editTarget(coverage_name, coverage_detail,coverage_category, coverage_number,startDateValue.getText().toString(),dueDateValue.getText().toString(),coverage_period, coverage_id) ==true){
					    	  number_achieved=db.getNumberAchieved(coverage_id,coverage_period,MobileLearning.CCH_TARGET_STATUS_NEW);
					    	JSONObject json = new JSONObject();
							 try {
								 if(coverage_old_id!=0){
									 json.put("id", coverage_old_id);
								 }else{
									 json.put("id", coverage_id);
								 }
								json.put("category", "coverage");
								json.put("target_type", coverage_name);
								 json.put("target_number", coverage_number);
								 json.put("start_date", start_date_extra);
								 json.put("due_date", due_date_extra);
								 json.put("achieved_number", achieved);
								 json.put("last_updated", last_updated);
								 json.put("changed", 1);
								 json.put("deleted", 0);
								 json.put("ver", db.getVersionNumber(CoverageTargetsDetailActivity.this));
									json.put("battery", db.getBatteryStatus(CoverageTargetsDetailActivity.this));
							    	json.put("device", db.getDeviceName());
							    	json.put("imei", db.getDeviceImei(CoverageTargetsDetailActivity.this));
							
							} catch (JSONException e) {
								e.printStackTrace();
							}
							 end_time=System.currentTimeMillis();
							 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
							 Intent intent2 = new Intent(Intent.ACTION_MAIN);
				 	          intent2.setClass(CoverageTargetsDetailActivity.this, NewEventPlannerActivity.class);
				 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 	          startActivity(intent2);
				 	          finish();	
				 	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
					    	 Toast.makeText(CoverageTargetsDetailActivity.this, "Coverage target edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(CoverageTargetsDetailActivity.this, "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
					}
				});
	 				dialog.show();
				
			}
        	
        });
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.custom_action_bar, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Intent goHome = new Intent(Intent.ACTION_MAIN);
	 	          goHome.setClass(CoverageTargetsDetailActivity.this, MainScreenActivity.class);
	 	          goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(goHome);
	 	          finish();
	 	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	 public static long differenceIndays(String startDate,String endDate,String today)
	 {
			// String toDateAsString = "05/11/2010";
			 Date start_date = null;
			 Date end_date = null;
			 Date today_date=null;
			 long starDateAsTimestamp=0;
			long endDateTimestamp=0;
			try {
				today_date= new SimpleDateFormat("dd-MM-yyyy").parse(today);
				if(startDate!=null){
					start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
					 starDateAsTimestamp = start_date.getTime();
				}else{
					System.out.println("Enter a valid date!");
				}
				if(endDate!=null){
					end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
					  endDateTimestamp = end_date.getTime();
				}else {
					System.out.println("Enter a valid date!");	
				}
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 long todayDateTimestamp = today_date.getTime();
			 long diff = endDateTimestamp - todayDateTimestamp;
			  
			  long diffDays = diff / (24 * 60 * 60 * 1000);
			  if(todayDateTimestamp>endDateTimestamp){
			  return -1;
			  }else{
				return diffDays;
			  }
	    }
	 private String getDateTime() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "dd-MM-yyyy", Locale.getDefault());
	        Date date = new Date();
	        return dateFormat.format(date);
	}
	 public static boolean isDateAfter(String startDate,String endDate)
	    {
		// String toDateAsString = "05/11/2010";
		 Date start_date = null;
		 Date end_date = null;
		long starDateAsTimestamp = 0;
		long endDateTimestamp = 0;
		try {
			if(startDate!=null){
				start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
				starDateAsTimestamp = start_date.getTime();
			}else{
				System.out.println("Enter a valid date!");
			}
			if(endDate!=null){
				end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
				endDateTimestamp = end_date.getTime();
			}else{
				System.out.println("Enter a valid date!");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 long getRidOfTime = 1000 * 60 * 60 * 24;
		 long startDateAsTimestampWithoutTime = starDateAsTimestamp / getRidOfTime;
		 long endDateTimestampWithoutTime = endDateTimestamp / getRidOfTime;

		 if (startDateAsTimestampWithoutTime > endDateTimestampWithoutTime) {
		    return true;
		 } else {
		    return false;
		 }
	    }
	 private class GetData extends AsyncTask<Object, Void, Object> {

	    @Override
	    protected Object doInBackground(Object... params) {
	    	 Bundle extras = getIntent().getExtras(); 
	         if (extras != null) {
	         	coverage_name=extras.getString("coverage_name");
	 			coverage_number=extras.getString("coverage_number");
	 			coverage_period=extras.getString("coverage_period");
	 			due_date_extra=extras.getString("due_date");
	 			start_date_extra=extras.getString("start_date");
	 			achieved=extras.getString("achieved");
	 			status=extras.getString("status");
	 			coverage_id=extras.getLong("coverage_id");
	 			coverage_old_id=extras.getLong("old_id");
	 			last_updated=extras.getString("last_updated");
	 			coverage_category_extra=extras.getString("coverage_category");
	 			coverage_detail_extra=extras.getString("coverage_detail");
	         }
	        
	         int number_achieved_current=Integer.valueOf(achieved);
	         int number_entered_current=Integer.valueOf(coverage_number);
	         Double percentage=((double)number_achieved_current/number_entered_current)*100;
	         String percentage_achieved=String.format("%.0f", percentage);
	         textView_percentageAchieved.setText(percentage_achieved+"%");
	         progress_status=(int) percentage.doubleValue();
	         progress_bar.setProgress(progress_status);
	         progress_bar.setPrefixText(percentage_achieved+"%");
	         progress_bar.setPrefixText(" ");
	         
	        
				return null;
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	    	 long difference_in_days=differenceIndays(start_date_extra,due_date_extra,getDateTime());
	         if (difference_in_days==1){
	         	day_difference.setTextColor(Color.rgb(225,170,7));
	         	day_difference.setText("Due in: "+String.valueOf(difference_in_days)+ " day");
	         	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
	         }else if(difference_in_days==0) {
	         	day_difference.setTextColor(Color.RED);
	         	day_difference.setText("Due today!!!!");
	         	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
	         }else if(difference_in_days==-1) {
	         	day_difference.setTextColor(Color.RED);
	         	day_difference.setText("Due date is past");
	         	imageView_status.setImageResource(R.drawable.sad);
	         }else {
	         	day_difference.setText("Due in: "+String.valueOf(difference_in_days)+ " days");
	         	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
	         }
	        textView_name.setText(coverage_name);
	        textView_period.setText(coverage_period);
	        textView_dueDate.setText(due_date_extra);
	        textView_number.setText(coverage_number);
	        textView_startDate.setText(start_date_extra);
	        textView_achieved.setText(achieved);
	        
	    }
	}
	 private int getIndex(MaterialSpinner spinner, String myString)
	 {
		 int index = 0;

	  for (int i=0;i<spinner.getCount();i++){
		  	if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
		  			index = i;
		  			break;
		  }
	  }
	  return index;
	 } 
	 
	 public String[] getSpinnerData(String selection){
		String[] values=new String[]{};
		selection=spinner_coverageName.getSelectedItem().toString();
		switch(selection){
		case "Family Planning":
			values=getResources().getStringArray(R.array.CoverageIndicatorsDetailFamilyPlanning);
			break;
		case "Age Groups":
			values=getResources().getStringArray(R.array.CoverageIndicatorsDetailAgeGroups);
			break;
		case "School Health":
			values=getResources().getStringArray(R.array.CoverageIndicatorsSchoolHealth);
			break;
		case "BCG":
			values=getResources().getStringArray(R.array.CoverageImmunizationTypeBCG);
			break;
		case "Penta":
			values=getResources().getStringArray(R.array.CoverageImmunizationPenta);
			break;
		case "OPV":
			values=getResources().getStringArray(R.array.CoverageImmunizationOPV);
			break;
		case "ROTA":
			values=getResources().getStringArray(R.array.CoverageImmunizationROTA);
			break;
		case "PCV":
			values=getResources().getStringArray(R.array.CoverageImmunizationPCV);
			break;
		case "Measles Rubella":
			values=getResources().getStringArray(R.array.CoverageImmunizationMeaslesRubella);
			break;
		case "Vitamin A":
			values=getResources().getStringArray(R.array.CoverageImmunizationVitaminA);
			break;
		case "TT pregnant":
			values=getResources().getStringArray(R.array.CoverageImmunizationTT);
			break;
		case "TT non-pregnant":
			values=getResources().getStringArray(R.array.CoverageImmunizationTT);
			break;
		case "Yellow Fever":
			values=getResources().getStringArray(R.array.CoverageImmunizationYellowFever);
			break;
		}
		
		
		return values;
		
		 
		 
	 }
	 private boolean checkValidation() {
	        boolean ret = true;
	 
	        if (!Validation.hasTextTextView(startDateValue)) ret = false;
	        if (!Validation.hasTextTextView(dueDateValue)) ret = false;
	        if (!Validation.hasSelection(spinner_coverageDetails)) ret = false;
	        if (!Validation.hasSelection(spinner_coverageName)) ret = false;
	        if (!Validation.hasSelection(spinner_coveragePeriod)) ret = false;
	        if (Validation.isDateAfter(startDateValue.getText().toString(),dueDateValue.getText().toString(),startDateValue)) ret = false;
	        return ret;
	    }	
}
