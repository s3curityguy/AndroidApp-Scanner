/*
 * Copyright 2010 Cigital Inc.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.cigital.mobile.android.result;

import java.util.HashMap;

import com.cigital.mobile.android.R;
import com.cigital.mobile.android.utils.ComponentManager;
import com.cigital.mobile.android.utils.IntentActionSubActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Sarath Geethakumar
 *
 */
public class DisplayActivity extends ListActivity{

	/**
	 * 
	 */
	private String packageName;
	private static String strActionName;
	private static ListView statParent;
//	private static View statV;
	private static int statPosition;
//	private static long statId;
	TextView[] txtArray;
	
	public DisplayActivity() {
		// TODO Auto-generated constructor stub
	}
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setTheme(R.color.white);
	        
	        Bundle bundle = getIntent().getExtras();
	        @SuppressWarnings("unchecked")
			HashMap<String, ComponentManager[]> result = (HashMap<String, ComponentManager[]>)bundle.get("result");
			packageName =bundle.getString("packagename");
			
			Parcelable[] activities = null;
			Parcelable[] services = null;
			Parcelable[] receivers = null;
	        
	        int arrSize = 0;
	        if(result!=null)
	        {
	        	if(result.containsKey("activities"))
	        	{
	        		activities = result.get("activities");
	        		arrSize = arrSize + activities.length;
	        	}
	        	
	        	if(result.containsKey("services"))
	        	{
	        		services = result.get("services");
	        		arrSize = arrSize + services.length;
	        	}
	        	
	        	if(result.containsKey("receivers"))
	        	{
	        		receivers = result.get("receivers");
	        		arrSize = arrSize + receivers.length;
	        	}
	        	
	        }
	        
	        txtArray = new TextView[arrSize];
	        
	        int i =0;
	        if(result.containsKey("activities"))
	        {
	        	
	        	
	        	for(;i<activities.length;i++)
		        {
	        		TextView view = new TextView(this)
		        	{
		                public String toString() {
		                return  (String) getText();
		                }
		        	};
//		        	view.setBackgroundColor(Color.WHITE);
		        	view.setBackgroundResource(R.color.red);
		        	view.setTextColor(((ComponentManager)activities[i]).getColor());
		        	view.setText(((ComponentManager)activities[i]).getName());
		        	view.setTag(R.id.tag,"activity");

		        	txtArray[i] = view;
		        	
		        }
	        	
	        }
	        
	        if(result.containsKey("services"))
	        {
	        	
	        	
	        	for(int j=0;i<arrSize && j<services.length;i++,j++)
		        {
		        	TextView view = new TextView(this)
		        	{
		                public String toString() {
		                return  (String) getText();
		                }
		        	};
//		        	view.setBackgroundColor(Color.WHITE);
		        	view.setBackgroundResource(R.color.red);
		        	view.setTextColor(((ComponentManager)services[j]).getColor());
		        	view.setText(((ComponentManager)services[j]).getName());
		        	view.setTag(R.id.tag,"service");

		        	txtArray[i] = view;
		        	
		        }
	        	
	        }
	        
	        if(result.containsKey("receivers"))
	        {
	        	
	        	
	        	for(int j=0;i<arrSize && j<receivers.length;i++,j++)
		        {
		        	TextView view = new TextView(this)
		        	{
		                public String toString() {
		                return  (String) getText();
		                }
		        	};
//		        	view.setBackgroundColor(Color.WHITE);
		        	view.setBackgroundResource(R.color.red);
		        	view.setTextColor(((ComponentManager)receivers[j]).getColor());
		        	view.setText(((ComponentManager)receivers[j]).getName());
		        	view.setTag(R.id.tag,"receiver");

		        	txtArray[i] = view;
		        	
		        }
	        	
	        }
	        CustomAdapter adapter = new CustomAdapter(this);
	        setListAdapter(adapter);

	        setTitle("Manual Component Scan");
	        


	        
	    }
	 public void onListItemClick(ListView parent, View v, int position, long id) 
	 {
		 statParent = parent;
//		 statV = v;
		 statPosition = position;
//		 statId = id;
		 strActionName = null;
		 Intent i = new Intent(this, IntentActionSubActivity.class);
		  startActivityForResult(i, 0);
	 }
	 
	 @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	 {
		 if(resultCode==RESULT_OK)
		 {
			 strActionName = data.getExtras().getString("ACTION");
		 }
		 else if(resultCode==RESULT_CANCELED)
		 {
			 strActionName = null;
		 }
		 
		 @SuppressWarnings("unchecked")
			ArrayAdapter<TextView> adapter = (ArrayAdapter<TextView>)statParent.getAdapter();
			 final TextView str = adapter.getItem(statPosition);
			  
			 AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

			    // set the message to display
			 	if(strActionName!=null && !strActionName.equalsIgnoreCase(""))
			 	{
			 		alertbox.setMessage("Are you sure you want to start "+(String)str.getTag(R.id.tag)+" "+str.getText()+" with action "+strActionName);
			 	}
			 	else
			 	{
			 		alertbox.setMessage("Are you sure you want to start "+(String)str.getTag(R.id.tag)+" "+str.getText()+" with default action");
			 	}
			      
			      alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {  
			          public void onClick(DialogInterface dialog, int whichButton){
			          }  
			         });  
			         
			      alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {  
			          public void onClick(DialogInterface dialog, int whichButton){
			        	 Intent intent = new Intent();
			        	 intent.setClassName(packageName, (String) str.getText());
			        	 if(strActionName != null)
			        	 {
			        		 intent.setAction(strActionName.trim());
			        	 }
			        	 try {
							if(((String)str.getTag(R.id.tag)).equalsIgnoreCase("activity"))
							 {
								 startActivity(intent);
							 }
							 else if(((String)str.getTag(R.id.tag)).equalsIgnoreCase("service"))
							 {
								 startService(intent);
							 }
							 else
							 {
								 sendBroadcast(intent);
							 }
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							test(e.getMessage());
							
						
						}
			        	  
			          }  
			         });
			      alertbox.show();
	 }
			
	public void test(String e)
	{
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		 alertbox.setMessage("Start Failed with error : "+e);
		 alertbox.setNegativeButton("OK", new DialogInterface.OnClickListener() {  
	          public void onClick(DialogInterface dialog, int whichButton){
	        	 
	          }  
	         }); 
		 alertbox.show();
		
	}
	
	class CustomAdapter extends ArrayAdapter<TextView>
	{
		Activity context;

		public CustomAdapter(Activity context) {
			super(context, R.layout.row, txtArray);
			this.context=context;
			// TODO Auto-generated constructor stub
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row=inflater.inflate(R.layout.row, null);
			TextView label=(TextView)row.findViewById(R.id.textView);
			label.setText(txtArray[position].getText());
			label.setTextColor(txtArray[position].getTextColors());

			return(row);
			}
		
	}

}


