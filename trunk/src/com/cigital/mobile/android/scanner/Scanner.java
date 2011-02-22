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
package com.cigital.mobile.android.scanner;

import java.util.HashMap;

import com.cigital.mobile.android.table.TableCreator;
import com.cigital.mobile.android.utils.ComponentManager;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.graphics.Color;

public class Scanner extends Activity {
	
	PackageInfo pkgInfo;

	
	public Scanner(String packageName, PackageManager pm) throws NameNotFoundException
	{
		pkgInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES|PackageManager.GET_CONFIGURATIONS
				|PackageManager.GET_INTENT_FILTERS|PackageManager.GET_PERMISSIONS|PackageManager.GET_PROVIDERS
				|PackageManager.GET_RECEIVERS|PackageManager.GET_SERVICES|PackageManager.GET_SHARED_LIBRARY_FILES|PackageManager.GET_SIGNATURES
				|PackageManager.GET_URI_PERMISSION_PATTERNS|PackageManager.GET_UNINSTALLED_PACKAGES);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String startScan()
	{
		StringBuffer buff = new StringBuffer("<html><body>");
		buff.append("**********************************************************************<br>");
		buff.append("App Name: "+pkgInfo.packageName+"<br>");
		PermissionInfo[] appPermission = pkgInfo.permissions;
		boolean permFlag =  false;
		HashMap permMap = new HashMap();
		if(appPermission!=null)
		{
			String[] array = new String[appPermission.length];
			
			for(int i=0;i<appPermission.length;i++)
			{
//				StringBuffer tmp = new StringBuffer();
				PermissionInfo info = appPermission[i];
				if(info.protectionLevel == 0)
				{
					array[i] = ">"+info.name+"@ bgcolor=red>Normal";
					permMap.put(info.name,"red");
				}	
				else if(info.protectionLevel == 1)
				{
					array[i] = ">"+info.name+"@ bgcolor=yellow>Dangerous";
					if(!permFlag)
						permFlag = true;
					permMap.put(info.name,"yellow");
				}
				else if(info.protectionLevel == 2)
				{
					array[i] = ">"+info.name+"@ bgcolor=green>Signature";
					if(!permFlag)
						permFlag = true;
					permMap.put(info.name,"green");
				}
				else if(info.protectionLevel > 1)
				{
					array[i] = ">"+info.name+"@ bgcolor=green>Signature/System";
					if(!permFlag)
						permFlag = true;
					permMap.put(info.name,"green");
				}
			}
			
			buff.append(TableCreator.makeMultiDimeTable(array, "Permission Name@Protection Level"));
			
		}
		
		buff.append("Application PERMISSION : "+pkgInfo.permissions+"<br>");
		buff.append("Data Dir : "+pkgInfo.applicationInfo.dataDir+"<br>");
		buff.append("Source Dir : "+pkgInfo.applicationInfo.publicSourceDir+"<br>");
		String[] usePermission = pkgInfo.requestedPermissions;
		
		if(usePermission!=null)
		{
			buff.append(TableCreator.makeUniDimeTable(usePermission,"Requested Permissions"));
		}
		Signature[] signatures = pkgInfo.signatures;
		
		if(signatures!=null)
		{
			for(int i=0;i<signatures.length;i++)
			{
				Signature sign = signatures[i];
				buff.append("Signature "+(i+1)+" :"+sign.toCharsString()+"<br>");
			}
		}
		
		ActivityInfo[] actInfo  = pkgInfo.activities;
		ServiceInfo[] servInfo = pkgInfo.services;
		ProviderInfo[] provInfo = pkgInfo.providers;
		ActivityInfo[] recvInfo = pkgInfo.receivers;
		
		
		if (actInfo!=null) 
		{
			String[] array = new String[actInfo.length];
			
			for (int i = 0; i < actInfo.length; i++) 
			{
				ActivityInfo act = actInfo[i];
				
				if(!act.exported)
				{
					array[i] = ">"+act.name+"@ bgcolor=green>"+((act.permission==null)? "&nbsp;&nbsp;&nbsp;&nbsp;" :act.permission);
				}
				else if(act.permission!=null)
				{
					if(permMap.get(act.permission)!=null)
					{
						array[i] = ">"+act.name+"@ bgcolor="+permMap.get(act.permission)+">"+act.permission;
					}
					else
					{
						array[i] = ">"+act.name+"@ bgcolor=white>"+act.permission;
					}
					
				}
				else
				{
					array[i] = ">"+act.name+"@ bgcolor=red>&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			}
			
			buff.append(TableCreator.makeMultiDimeTable(array, "Activity Name@Permission"));
		}
		
		if (servInfo!=null) {
			
			String[] array = new String[servInfo.length];
			
			for (int i = 0; i < servInfo.length; i++) 
			{
				ServiceInfo serv = servInfo[i];
				
				if(!serv.exported)
				{
					array[i] = ">"+serv.name+"@ bgcolor=green>"+((serv.permission==null)? "&nbsp;&nbsp;&nbsp;&nbsp;" :serv.permission);
				}
				else if(serv.permission!=null)
				{
					if(permMap.get(serv.permission)!=null)
					{
						array[i] = ">"+serv.name+"@ bgcolor="+permMap.get(serv.permission)+">"+serv.permission;
					}
					else
					{
						array[i] = ">"+serv.name+"@ bgcolor=white>"+serv.permission;
					}
				}
				else
				{
					array[i] = ">"+serv.name+"@ bgcolor=red>&nbsp;&nbsp;&nbsp;&nbsp;";
				}

			}
			buff.append(TableCreator.makeMultiDimeTable(array, "Service Name@Permission"));
		}
		
		if (recvInfo!=null) {
			
			String[] array = new String[recvInfo.length];
			
			for (int i = 0; i < recvInfo.length; i++) 
			{
				ActivityInfo recv = recvInfo[i];
				
				if(!recv.exported)
				{
					array[i] = ">"+recv.name+"@ bgcolor=green>"+((recv.permission==null)? "&nbsp;&nbsp;&nbsp;&nbsp;" :recv.permission);
				}
				else if(recv.permission!=null)
				{
					if(permMap.get(recv.permission)!=null)
					{
						array[i] = ">"+recv.name+"@ bgcolor="+permMap.get(recv.permission)+">"+recv.permission;
					}
					else
					{
						array[i] = ">"+recv.name+"@ bgcolor=white>"+recv.permission;
					}
				}
				else
				{
					array[i] = ">"+recv.name+"@ bgcolor=red>&nbsp;&nbsp;&nbsp;&nbsp;";
				}

			}
			
			buff.append(TableCreator.makeMultiDimeTable(array, "Receiver Name@Permission"));
		}

		if (provInfo!=null) {
			
			String[] array = new String[provInfo.length];
			
			for (int i = 0; i < provInfo.length; i++) 
			{
				ProviderInfo prov = provInfo[i];
				
				StringBuffer strProv = new StringBuffer();
				if(!prov.exported)
				{
					strProv = strProv.append(">"+prov.name+"@ bgcolor=green>"+prov.readPermission+"@ bgcolor=green>"+prov.writePermission);
				}
				else if((prov.readPermission==null && prov.writePermission==null) && !permFlag)
				{
					strProv = strProv.append(">"+prov.name+"@ bgcolor=red>&nbsp;&nbsp;&nbsp;&nbsp;@ bgcolor=red>&nbsp;&nbsp;&nbsp;&nbsp;");
				}
				else
				{
					
					//read permission
					if(prov.readPermission != null && permMap.get(prov.readPermission)!=null)
					{
						strProv = strProv.append(">"+prov.name+"@ bgcolor="+permMap.get(prov.readPermission)+">"+prov.readPermission);
					}
					else
					{
						strProv = strProv.append(">"+prov.name+"@ bgcolor=white>"+((prov.readPermission==null) ?"&nbsp;&nbsp;&nbsp;&nbsp;":prov.readPermission));
					}
					
					// write permission
					if(prov.writePermission!=null && permMap.get(prov.writePermission)!=null)
					{
						strProv = strProv.append(">"+prov.name+"@ bgcolor="+permMap.get(prov.writePermission)+">"+prov.writePermission);
					}
					else
					{
						strProv = strProv.append(">"+prov.name+"@ bgcolor=white>"+((prov.writePermission==null) ?"&nbsp;&nbsp;&nbsp;&nbsp;":prov.writePermission));
					}
					
				}
				array[i]= strProv.toString();
				
			}
			
			buff.append(TableCreator.makeMultiDimeTable(array, "Content Provider Name@Read Permission@Write Permission"));
		}
		
		buff.append("**********************************************************************");
		buff.append("</body></html>");
		
		return buff.toString();
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap<String,ComponentManager[]> manualScan()
	{
		PermissionInfo[] appPermission = pkgInfo.permissions;
//		boolean permFlag =  false;
		HashMap permMap = new HashMap();
		if(appPermission!=null)
		{
//			ComponentManager[] array = new ComponentManager[appPermission.length];
			
			for(int i=0;i<appPermission.length;i++)
			{
				
				PermissionInfo info = appPermission[i];
				if(info.protectionLevel == 0)
				{
					
					permMap.put(info.name,new Integer(Color.RED));
				}	
				else if(info.protectionLevel == 1)
				{
					
					permMap.put(info.name,new Integer(Color.YELLOW));
				}
				else if(info.protectionLevel == 2)
				{
					
					permMap.put(info.name,new Integer(Color.GREEN));
				}
				else if(info.protectionLevel > 1)
				{
					
					permMap.put(info.name,new Integer(Color.GREEN));
				}
			}
		}
			
		ActivityInfo[] actInfo  = pkgInfo.activities;
		ServiceInfo[] servInfo = pkgInfo.services;
//		ProviderInfo[] provInfo = pkgInfo.providers;
		ActivityInfo[] recvInfo = pkgInfo.receivers;
		
		HashMap<String, ComponentManager[]> resultMap = new HashMap<String, ComponentManager[]>();
		
		/*
		 * Retrieving activity info
		 */
		if(actInfo!=null)
		{
			ComponentManager[] actArray = new ComponentManager[actInfo.length];
			
			for (int i = 0; i < actInfo.length; i++) 
			{
				
//					actArray[i]= actInfo[i].name;
				ActivityInfo act = actInfo[i];
				ComponentManager manager = null;
				if(!act.exported)
				{
					 manager = new ComponentManager(act.name, Color.GREEN, "activity");
				}
				else if(act.permission!=null)
				{
					if(permMap.get(act.permission)!=null)
					{
						 manager = new ComponentManager(act.name, ((Integer)permMap.get(act.permission)).intValue(), "activity");
						
					}
					else
					{
						 manager = new ComponentManager(act.name, Color.WHITE, "activity");
					}
					
				}
				else
				{
					 manager = new ComponentManager(act.name, Color.RED, "activity");
				}
				
				actArray[i] = manager;
			}	
			resultMap.put("activities", actArray);
		}
		
		/*
		 * Retrieving Service Info
		 */
		if(servInfo!=null)
		{
			ComponentManager[] servArray = new ComponentManager[servInfo.length];
			
			for (int i = 0; i < servInfo.length; i++) 
			{
				
//					actArray[i]= actInfo[i].name;
				ServiceInfo serv = servInfo[i];
				ComponentManager manager = null;
				if(!serv.exported)
				{
					 manager = new ComponentManager(serv.name, Color.GREEN, "service");
				}
				else if(serv.permission!=null)
				{
					if(permMap.get(serv.permission)!=null)
					{
						 manager = new ComponentManager(serv.name, ((Integer)permMap.get(serv.permission)).intValue(), "service");
						
					}
					else
					{
						 manager = new ComponentManager(serv.name, Color.WHITE, "service");
					}
					
				}
				else
				{
					 manager = new ComponentManager(serv.name, Color.RED, "service");
				}
				
				servArray[i] = manager;
			}	
			resultMap.put("services", servArray);
			
		}
		
		/*
		 * Retrieving receiver info
		 */
		if(recvInfo!=null)
		{
			ComponentManager[] recvArray = new ComponentManager[recvInfo.length];
			
			for (int i = 0; i < recvInfo.length; i++) 
			{
				ActivityInfo recv = recvInfo[i];
				ComponentManager manager = null;
				
				if(!recv.exported)
				{
					 manager = new ComponentManager(recv.name, Color.GREEN, "receiver");
				}
				else if(recv.permission!=null)
				{
					if(permMap.get(recv.permission)!=null)
					{
						 manager = new ComponentManager(recv.name, ((Integer)permMap.get(recv.permission)).intValue(), "receiver");
						
					}
					else
					{
						 manager = new ComponentManager(recv.name, Color.WHITE, "receiver");
					}
					
				}
				else
				{
					 manager = new ComponentManager(recv.name, Color.RED, "receiver");
				}
				
				recvArray[i] = manager;
			}	
			resultMap.put("receivers", recvArray);
			
		}
		
		return resultMap;
		
	}

}
