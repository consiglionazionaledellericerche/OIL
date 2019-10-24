package it.cnr.helpdesk.MailParserManagement.schedulers;
import it.cnr.helpdesk.MailParserManagement.javabeans.MailStarter;
import it.cnr.helpdesk.util.Scheduler;

import java.util.HashMap;

/**
 * @author Marco Trischitta
 * @author aldo stentella
 *
 */
public class MailScheduler {

	//private static Scheduler scheduler = new Scheduler();
	private static HashMap<String, Scheduler> schedulers = new HashMap<String, Scheduler>();
	
	public static void start(String la, String instance) throws Throwable {
    	MailStarter ms = new MailStarter();
    	ms.setContextPath(la);
    	ms.setInstance(instance);
    	//if(schedulers.get(instance)==null)
    		schedulers.put(instance, new Scheduler());
    	Scheduler scheduler = schedulers.get(instance);
		String conf = System.getProperty("mail.parser.schedule.config");
		//String conf = Settings.getProperty("mail.parser.schedule.config", instance);
		if(conf == null) conf = "*/5 7-20 * * *";
		scheduler.schedule(conf, ms);
		scheduler.start();
	}

	public static void stop(String instance) throws Throwable {
		Scheduler scheduler = schedulers.get(instance);
		scheduler.stop();
	}
	
	public static boolean isStarted(String instance) throws Throwable {
		Scheduler scheduler = schedulers.get(instance);
		if(scheduler==null)
			return false;
		return scheduler.isStarted();
	}
}