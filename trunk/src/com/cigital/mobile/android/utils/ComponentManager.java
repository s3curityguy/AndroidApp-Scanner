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

import android.os.Parcel;
import android.os.Parcelable;

public class ComponentManager implements Parcelable {
	
	private String name;
	private int color;
	private String actName;
	
	public ComponentManager(String name, int color, String actName)
	{
		this.name= name;
		this.color= color;
		this.actName = actName;
	}

	public String getName()
	{
		return this.name;
	}
	
	public int getColor()
	{
		return this.color;
	}
	
	public String getActName()
	{
		return this.actName;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(name);
		dest.writeInt(color);
		dest.writeString(actName);
		
	}
	
	public static final Parcelable.Creator<ComponentManager> CREATOR= new Parcelable.Creator<ComponentManager>() 
	{
		public ComponentManager createFromParcel(Parcel in) 
		{
			return new ComponentManager(in);
		}

		public ComponentManager[] newArray(int size) 
		{
			return new ComponentManager[size];
		}
	};
	
	private ComponentManager(Parcel in) 
	{
		name= in.readString();
        color = in.readInt();
        actName = in.readString();
        
    }
}
