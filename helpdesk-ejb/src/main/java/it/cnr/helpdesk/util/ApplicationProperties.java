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
import java.util.Properties;
import java.io.*;
import java.net.URL;
/**
 * <p>Title: Oil - Online Interactive heLpdesk</p>
 * <p>Description: A Web-Based Application for HelpDesk support</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * @author Andrea Bei & Roberto Puccinelli
 * @version 1.0
 */

public class ApplicationProperties {
  public static Properties applicationProperties=null;

  private URL findFile(String fileName) throws IllegalStateException {
  Class confClass = this.getClass();
  ClassLoader loader = confClass.getClassLoader();
  URL fileURL = loader.getResource(fileName);
// Was the resource found?
  if (fileURL == null) {
  System.out.println("Can't find the file: '" + fileName + "'");
  throw new IllegalStateException("Can't locate file: '" + fileName + "'");
  }
  else {
  return fileURL;
  }
}

public static Properties getApplicationProperties()
{
  if (applicationProperties==null)
  {
    applicationProperties=new Properties();
    ApplicationProperties ap=new ApplicationProperties();
    try {
       System.out.println("loading application properties from oil.properties file");
       //URL u=ap.findFile("it/cnr/ediwaycir/conf/ediwaycir.properties");
       URL u=ap.findFile("oil.properties");
       InputStream is=u.openStream();
       applicationProperties.load(is);
       is.close();
    }
    catch (Exception ex) {
      System.out.println("Error during properties loading");
      System.out.println(ex.getMessage());
      ex.printStackTrace();
    }
    return applicationProperties;
  }
  else return applicationProperties;
}

}
