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
package com.cigital.mobile.android.table;

public class TableCreator {
	
	public static String makeUniDimeTable(String[] array, String header)
	{
		StringBuffer buff = new StringBuffer("<table border=1>");
		
		buff.append("<tr><th bgcolor=#00FF00>"+header+"</th></tr>&nbsp;");
		
		for (int i=0; i<array.length;i++)
		{
			buff.append("<tr><td>"+array[i]+"</td></tr>&nbsp;");
		}
		buff.append("</table><br> <br>");
		return buff.toString();
	}
	
	public static String makeMultiDimeTable(String[] array, String header)
	{
		StringBuffer buff = new StringBuffer("<table border=1>");
		String[] hdr = header.trim().split("@");
		
		buff.append("<tr>");//+header+"</th></tr>&nbsp;");
		for (int i=0;i<hdr.length; i++)
		{
			System.out.println(hdr[i]);
			buff.append("<th bgcolor=#306EFF>"+hdr[i]+"</th>&nbsp;");
		}
		buff.append("</tr>&nbsp;");
		
		for (int i=0; i<array.length;i++)
		{
			System.out.println("Length of array is :"+array.length);
			System.out.println(array[i]);
			String[] bdy = array[i].trim().split("@");
			buff.append("<tr>");
			for (int j=0; j<bdy.length;j++)
			{
				buff.append("<td"+bdy[j]+"</td>&nbsp;");
			}
			buff.append("</tr>&nbsp;");
		}
		buff.append("</table>");
		return buff.toString();
	}

}
