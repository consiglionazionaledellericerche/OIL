/*
 * Copyright (C) 2019  Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

 

package it.cnr.helpdesk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;

import it.cnr.helpdesk.StatisticsManagement.valueobjects.ProblemsDistributionByStatusDTO;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.charts.dtos.SeriesItemDTO;

public class Converter
{

    public Converter()
    {
    }

    public static String floatToTimeString(float f)
    {
        float f1 = f;
        String s;
        if(Math.floor(f1) < 10D)
            s = "0" + Integer.toString((int)Math.floor(f1));
        else
            s = Integer.toString((int)Math.floor(f1));
        f1 = (f1 - (float)Math.floor(f1)) * 24F;
        if(Math.floor(f1) < 10D)
            s = s + ":" + "0" + Integer.toString((int)Math.floor(f1));
        else
            s = s + ":" + Integer.toString((int)Math.floor(f1));
        f1 = (f1 - (float)Math.floor(f1)) * 60F;
        if(Math.floor(f1) < 10D)
            s = s + ":" + "0" + Integer.toString((int)Math.floor(f1));
        else
            s = s + ":" + Integer.toString((int)Math.floor(f1));
        f1 = (f1 - (float)Math.floor(f1)) * 60F;
        if(Math.floor(f1) < 10D)
            s = s + ":" + "0" + Integer.toString((int)Math.floor(f1));
        else
            s = s + ":" + Integer.toString((int)Math.floor(f1));
        return s;
    }

    public static String doubleToTimeString(double d)
    {
        double d1 = d;
        String s;
        if(Math.floor(d1) < 10D)
            s = "0" + Integer.toString((int)Math.floor(d1));
        else
            s = Integer.toString((int)Math.floor(d1));
        d1 = (d1 - Math.floor(d1)) * 24D;
        if(Math.floor(d1) < 10D)
            s = s + ":" + "0" + Integer.toString((int)Math.floor(d1));
        else
            s = s + ":" + Integer.toString((int)Math.floor(d1));
        d1 = (d1 - Math.floor(d1)) * 60D;
        if(Math.floor(d1) < 10D)
            s = s + ":" + "0" + Integer.toString((int)Math.floor(d1));
        else
            s = s + ":" + Integer.toString((int)Math.floor(d1));
        d1 = (d1 - Math.floor(d1)) * 60D;
        if(Math.floor(d1) < 10D)
            s = s + ":" + "0" + Integer.toString((int)Math.floor(d1));
        else
            s = s + ":" + Integer.toString((int)Math.floor(d1));
        return s;
    }

    public static SeriesItemDTO problemDistributionToSeriesItem(ProblemsDistributionByStatusDTO problemsdistributionbystatusdto)
    {
        SeriesItemDTO seriesitemdto = new SeriesItemDTO();
        seriesitemdto.setLabel(problemsdistributionbystatusdto.getStatus());
        seriesitemdto.setValue(new Float(problemsdistributionbystatusdto.getCount()));
        return seriesitemdto;
    }
    
    public static int xorCheck(long x){
    	StringBuilder sb = new StringBuilder(String.valueOf(x));
    	int test = 0;
    	for (int i = 0; i < sb.length(); i++) {
    		test = test ^ Integer.parseInt(Character.toString(sb.charAt(i)));
    	}
    	return test;
    }
    
    public static String uniqueAttachmentFilename(String name, String[] names){
    	Arrays.sort(names);
    	int j = 1;
    	for(int i = 0; i<names.length; i++){
    		System.out.println("i:"+i);
    		if(name.equalsIgnoreCase(names[i])){
    			if(j==1){
    				name = name.substring(0,name.lastIndexOf(".")).concat("["+(j++)+"]").concat(name.substring(name.lastIndexOf(".")));
    				System.out.println("change:"+name);
    			}else {											//if(name.matches("^.*\\[\\d{1,2}\\]\\.[a-zA-Z0-9]*$")){
    				//String[] temp = name.split("[\\[\\]]");
    				name = name.replaceFirst("\\[\\d{1,2}\\]\\.", "["+(j++)+"].");
    				System.out.println("change:"+name);
    			}
	    		System.out.println("j:"+j);
    		}
    	}
    	return name;
    }
    
    public static Collection<UserValueObject> token2User(String tokens, String instance) throws UserJBException{
		ArrayList<UserValueObject> users = new ArrayList<UserValueObject>();
    	StringTokenizer st = new StringTokenizer(tokens, ";");
		User user = new User();
		UserValueObject uvo;
		while(st.hasMoreTokens()){
			String item = st.nextToken();
			if(item.startsWith(instance+":")){
				user.setLogin(item.substring(item.indexOf(":")+1));
				uvo = user.getDetail(instance);
				if(uvo!=null && uvo.getEnabled().startsWith("y"))
					users.add(uvo);
			}
		}
		return users;
    }
    
    public static String text2HTML (String content){
    	String temp = StringEscapeUtils.escapeHtml(content);
    	StringBuilder builder = new StringBuilder();
    	boolean previousWasASpace = false;
    	for( char c : temp.toCharArray() ) {
    		if( c == ' ' ) {
    			if( previousWasASpace ) {
    				builder.append("&nbsp;");
    				previousWasASpace = false;
    				continue;
    			}
    			previousWasASpace = true;
    		} else {
    			previousWasASpace = false;
    		}
    		if(c == '\n') {
    			builder.append("<br/>");
    		} else
   				builder.append(c);
    	}
    	return builder.toString();
    }
}
