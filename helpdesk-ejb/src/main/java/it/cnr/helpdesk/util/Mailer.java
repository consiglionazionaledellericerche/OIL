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

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.exceptions.SettingsJBException;

import javax.mail.PasswordAuthentication;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;

public class Mailer
{

	public Mailer(String instance) {
		try   {
			this.instance = instance;
			Properties properties = new Properties();
			properties.put("mail.smtp.host", Settings.getProperty("mail.smtp.host",instance));
			properties.put("mail.smtp.port", Settings.getProperty("mail.smtp.port",instance));
			properties.put("mail.smtp.user", Settings.getProperty("mail.smtp.user",instance));
			properties.put("mail.smtp.password", Settings.getProperty("mail.smtp.password",instance));
			if(Settings.getProperty("mail.smtp.authenticated",instance).startsWith("enabled")) {
				properties.put("mail.smtp.auth", true);
				properties.put("mail.smtp.socketFactory.port", Settings.getProperty("mail.smtp.port",instance));
				properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				properties.put("mail.smtp.socketFactory.fallback", "true");
				properties.put("mail.smtp.starttls.enable", "true");
				//properties.put("mail.smtp.from", Settings.getProperty("mail.smtp.from",instance));
			}
			String s1 = Settings.getProperty("mail.smtp.from",instance);
			String s2 = Settings.getProperty("mail.smtp.from.name",instance);

			if(Settings.getProperty("mail.smtp.authenticated",instance).startsWith("enabled"))
				s = Session.getInstance(properties, new SMTPAuthenticator());
			else
				s = Session.getInstance(properties, null);
			m = new MimeMessage(s);
			InternetAddress internetaddress = new InternetAddress(s1, s2);
			InternetAddress[] noreply =  {new InternetAddress("nonRispondereAQuestoIndirizzo@null.null","non rispondere a questo indirizzo")};
			m.setFrom(internetaddress);
			//m.setReplyTo(noreply);
		} catch(Exception exception) {
			System.out.println("Eccezione: " + exception.getMessage());
			exception.printStackTrace();
		}
	}
	
	private class SMTPAuthenticator extends Authenticator  {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = "";
			String password = "";
			try {
				username = Settings.getProperty("mail.smtp.user",instance);
				password = Settings.getProperty("mail.smtp.password",instance);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new PasswordAuthentication(username, password);
		}
	}

    public void setProperties(Properties properties)
    {
        s = Session.getInstance(properties, null);
        m = new MimeMessage(s);
    }

    public MimeMessage getMimeMessage()
    {
        return m;
    }

    public void setFrom(String s1, String s2)
    {
        try
        {
            InternetAddress internetaddress = new InternetAddress(s1, s2);
            m.setFrom(internetaddress);
        }
        catch(Exception exception)
        {
            System.out.println("Eccezione: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void addTo(String s1){
        try {
			//Properties props= ApplicationProperties.getApplicationProperties();
			//String recipientType = (String)props.get("mail.recipient.type");
			String recipientType = Settings.getProperty("mail.recipient.type", instance);
            InternetAddress internetaddress = new InternetAddress(s1);
            if(recipientType.equalsIgnoreCase("to")){
            	m.addRecipient(javax.mail.Message.RecipientType.TO, internetaddress);
            	System.out.println("aggiunto "+internetaddress+" in TO");
            }
            else if(recipientType.equalsIgnoreCase("bcc")){
            	m.addRecipient(javax.mail.Message.RecipientType.BCC, internetaddress);
            	System.out.println("aggiunto "+internetaddress+" in BCC");
            }
        } catch(Exception exception) {
            System.out.println("Eccezione: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void setSubject(String s1)
    {
        try
        {
            m.setSubject(s1);
        }
        catch(Exception exception)
        {
            System.out.println("Eccezione: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void setContent(String s1)
    {
        try
        {
            m.setText(s1);
        }
        catch(Exception exception)
        {
            System.out.println("Eccezione: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void setContent(Multipart multipart){
    	try {
			m.setContent(multipart);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
    
    public void send()
    {
        try
        {
            Transport.send(m);
        }
        catch(Exception exception)
        {
            System.out.println("Eccezione: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void log(Object obj, String s1)
    {
        Date date = new Date();
        DateFormat dateformat = DateFormat.getDateTimeInstance();
        String s2 = dateformat.format(date);
        String s3 = obj.getClass().getName();
        System.out.println();
        System.out.println(s2 + " - [" + s3 + "]");
        System.out.println(s1);
        System.out.println();
    }

    public void log(String s1)
    {
        Date date = new Date();
        DateFormat dateformat = DateFormat.getDateTimeInstance();
        String s2 = dateformat.format(date);
        System.out.println();
        System.out.println(s2);
        System.out.println(s1);
        System.out.println();
    }

    private Session s;
    private MimeMessage m;
    private String instance;
}