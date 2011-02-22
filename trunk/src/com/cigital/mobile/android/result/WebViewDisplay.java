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

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


/**
 * @author Sarath Geethakumar
 *
 */
public class WebViewDisplay extends Activity {

	/**
	 * 
	 */
	public WebViewDisplay() {
		// TODO Auto-generated constructor stub
		
	}
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
       // setContentView(R.layout.main);
        
        WebView webview = new WebView(this);
        webview.getSettings().setBuiltInZoomControls(true);
		setContentView(webview);
//		Context ctx = getBaseContext();

		String result = this.getIntent().getExtras().getString("result");
		
		webview.loadData(result, "text/html", "utf-8");
    }

}
