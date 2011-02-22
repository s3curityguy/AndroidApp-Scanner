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
package com.cigital.mobile.android;

import java.util.HashMap;
import java.util.List;

import com.cigital.mobile.android.result.WebViewDisplay;
import com.cigital.mobile.android.scanner.Scanner;
import com.cigital.mobile.android.utils.ComponentManager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RequestDetails extends Activity implements OnClickListener {
	
	private EditText txtPackageName;
	private Button buttonStartScan;
	private Button buttonManualScan;
	private RadioGroup radioGroup;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        txtPackageName = (EditText)findViewById(R.id.txtPackageName);
        
        radioGroup = (RadioGroup)findViewById(R.id.RadioGroup);
        buttonStartScan = (Button)findViewById(R.id.button);
        
        buttonStartScan.setOnClickListener(this);
        buttonManualScan = (Button)findViewById(R.id.Manual);
        buttonManualScan.setOnClickListener(this);
        System.gc();
    }
    
    public void onClick(View v)
    {
    	boolean status = false;
    	StringBuffer appName = null;
    	StringBuffer packageName = null;
    	
    	List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
    	if(radioGroup.getCheckedRadioButtonId() == R.id.radioAppName)
    	{
	    	
	    	for(int i=0;i<packs.size();i++) 
	    	{
	    		appName = new StringBuffer();
	    		PackageInfo p = packs.get(i);
	    		 appName = appName.append(p.applicationInfo.loadLabel(getPackageManager()).toString());
	    		
	    		if(txtPackageName.getText().toString().trim().equalsIgnoreCase(appName.toString().trim()))
	    		{
	    			status = true;
	    			packageName = new StringBuffer(p.packageName);
	    			break;
	    		}
	    		else
	    		{
	    			continue;
	    		}
	    	}
    	}
    	else
    	{
    		for(int i=0;i<packs.size();i++) 
	    	{
    			PackageInfo p = packs.get(i);
    			
    			if(txtPackageName.getText().toString().trim().equalsIgnoreCase(p.packageName))
    			{
    				status = true;
	    			packageName = new StringBuffer(p.packageName);
	    			break;
    			}
	    	}
    	}
    	
		if(status)
		{
			
			if (v.getId()== R.id.button)
	    	{
	    		Scanner scan;
				try 
				{
					scan = new Scanner(packageName.toString(), getPackageManager());
					String result = scan.startScan();
		    		
		    		Intent intent = new Intent(this,WebViewDisplay.class);
		    		intent.putExtra("result", result);
		    		startActivity(intent);
		    		
				} 
				catch (NameNotFoundException e) 
				{
					Toast.makeText(this, "System Error: Could not retrieve "+txtPackageName.getText().toString()+" from the system!!!", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
	    		
	    	}
			else
			{
				try 
				{
					Scanner scan = new Scanner(packageName.toString(), getPackageManager());
					
					HashMap<String,ComponentManager[]> resultMap = scan.manualScan();
					
					if(resultMap==null)
					{
						Toast.makeText(this, "Application "+txtPackageName.getText().toString()+" not found in system!!!", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Intent localIntent = new Intent();
					      localIntent.setClassName("com.cigital.mobile.android", "com.cigital.mobile.android.result.DisplayActivity");
					      localIntent.putExtra("result", resultMap);
					      localIntent.putExtra("packagename", packageName.toString());
					      startActivity(localIntent);
					}
				} 
				catch (NameNotFoundException e) 
				{
					// TODO Auto-generated catch block
					Toast.makeText(this, "System Error: Could not retrieve "+txtPackageName.getText().toString()+" from the system!!!", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
			}
		}
		else
		{
			Toast.makeText(this, "Application "+txtPackageName.getText().toString()+" not found in system!!!", Toast.LENGTH_SHORT).show();
		}
		
    	
    }
}
