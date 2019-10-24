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
/*
 * Created on 9-apr-2005
 *


 */
package it.cnr.helpdesk.util;

/**
 * @author Aldo Stentella Liberati
 *


 */
public class MimeTypeImg {
	
	public static String decodeType(String fileName) {
		String ext = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		//String path = "images/icon/";
			if ("gif|jpg|jpeg|png|bmp|tiff".indexOf(ext)!=-1)
				return ("image.gif");
			if ("txt|asc|log".indexOf(ext)!=-1) 
				return ("text.gif");
			if ("doc|dot|rtf|wri|docx".indexOf(ext)!=-1) 
				return ("word.gif");
			if ("pdf".indexOf(ext)!=-1)
				return ("pdf.gif");
			if ("xls|xlt|csv|xlsx".indexOf(ext)!=-1) 
				return ("excel.gif");
			if ("htm|html|jsp".indexOf(ext)!=-1) 
				return ("html.gif");
			if ("ppt|pps|pptx|ppsx".indexOf(ext)!=-1) 
				return ("ppoint.gif");
			if ("zip|arj|lzh|cab|tar|tgz".indexOf(ext)!=-1) 
				return ("zip.gif");
			if ("exe|com|dll|scr|".indexOf(ext)!=-1) 
				return ("exe.gif");
			return ("unknown.gif");
	}

}
