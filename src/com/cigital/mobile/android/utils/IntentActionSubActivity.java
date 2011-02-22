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
package com.cigital.mobile.android.utils;

import com.cigital.mobile.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class IntentActionSubActivity extends Activity implements OnClickListener {

	private EditText txtActionName;
	private Button buttonConfirm;
	private Button buttonCancel; 
	
	public IntentActionSubActivity() {
		// TODO Auto-generated constructor stub
	}
	
	 public void onCreate(Bundle savedInstanceState) 
	 {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.actionsubactivity);
	        
	        txtActionName = (EditText)findViewById(R.id.intentaction);
	        buttonConfirm = (Button)findViewById(R.id.confirm);
	        buttonCancel = (Button)findViewById(R.id.cancel);
	        
	        buttonConfirm.setOnClickListener(this);
	        buttonCancel.setOnClickListener(this);
	 }

	@Override
	public void onClick(View v) 
	{
		Intent intent = new Intent();
		String strActionName = txtActionName.getText().toString();
		if(v.getId()==R.id.confirm)
		{
			if(strActionName!=null)
			{
				intent.putExtra("ACTION", txtActionName.getText().toString().trim());
				setResult(RESULT_OK,intent);
			}
			else
			{
				intent.putExtra("ACTION", txtActionName.getText().toString());
				setResult(RESULT_CANCELED,intent);
			}
			
		}
		else if(v.getId()==R.id.cancel)
		{
			intent.putExtra("ACTION", txtActionName.getText().toString());
			setResult(RESULT_CANCELED,intent);
		}
		
		finish();
	}

}
