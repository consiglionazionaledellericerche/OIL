package it.cnr.helpdesk.MailParserManagement.javabeans;

import javax.mail.AuthenticationFailedException;

import it.cnr.helpdesk.MailParserManagement.exceptions.MailParserJBException;

/**
 * @author Marco Trischitta
 * @author aldo stentella
 *
 */
public class MailStarter implements Runnable{
	
	private String contextPath="";
	private String instance;
	
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public void run() {
		//MailStarter run_mail = new MailStarter();
		//run_mail.executeBean();
		System.out.println("Task di parsing delle Email in esecuzione");
		MailParser mp = new MailParser();
		int i = 0;
		while(i < 3) {
			try {
				int res = mp.parseMail(this.contextPath, instance);
				if(res==0)
					i = 3;
				else if(res==1)
					try {
						i++;
						long d = (long)(Math.random()*10000);
						System.out.println("Task di parsing delle Email ritardato "+d+" ms per problemi di autenticazione (tentativo n."+i+" )");
						Thread.sleep(d);
					} catch (InterruptedException e1) {
						//ignore
					}
			} catch (MailParserJBException e) {
				e.printStackTrace();
				i = 3;
			}
		}
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}

}