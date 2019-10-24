§§
package it.cnr.helpdesk.StateMachineManagement;


import it.cnr.helpdesk.StateMachineManagement.ejb.StateMachineManagement;
import it.cnr.helpdesk.StateMachineManagement.ejb.StateMachineManagementHome;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineDAOException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineJBException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineEJBException;
import it.cnr.helpdesk.StateMachineManagement.javabeans.Transition;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.State;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.TransitionKey;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.StateMachineDAO;
import it.cnr.helpdesk.javabeans.HelpDeskJB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/**
 * @author Roberto Puccinelli
 * @author aldo stentella
 */
public class StateMachine extends HelpDeskJB implements Serializable /* implements StateMachineMBean */ {

	//public static StateMachine instance=null;
	public static HashMap<String, StateMachine> map = new HashMap<String, StateMachine>();
	
	private StateMachine()throws StateMachineJBException {
		super();
	}

	public static StateMachine getInstance(String instance) throws StateMachineJBException {
		if (map.get(instance)==null)  {
			StateMachine sm=new StateMachine();
			sm.init(instance);
			map.put(instance, sm);
		}
		return map.get(instance);
		
	}

	public final static int OPEN = 1;
	public final static int WORKING = 2;
	public final static int EXTERNAL = 3;
	public final static int CLOSED = 4;
	public final static int VERIFIED = 5;
	public final static int VALIDATION_REQUIRED = 6;
	public final static int VALIDATING = 7;
	
	private ArrayList states;


	private HashMap transitions;

	/*
	 * <p>Lista dei profili.</p>
	 */
	private ArrayList profiles;

	public ArrayList getProfiles() {
		return profiles;
	}

	public void setProfiles(ArrayList profiles) {
		this.profiles = profiles;
	}


	/**
	 * <p>
	 * Alla prima chiamata di un'istanza della classe, vengono lette le tabelle relative alla StateMachine 
	 * e memorizzate dentro attributi ArrayList della classe stessa.
	 * </p>
	 * 
	 */
	public void init(String instance) throws StateMachineJBException {
		try {
			StateMachineManagementHome smmh = lookupHome();
			StateMachineManagement smm = (StateMachineManagement) PortableRemoteObject.narrow(smmh.create(), it.cnr.helpdesk.StateMachineManagement.ejb.StateMachineManagement.class);
			smm.readConfiguration(this, instance);
			smm.remove();
		} catch (StateMachineEJBException smee) {
			throw new StateMachineJBException(smee.getMessage(), smee.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new StateMachineJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		System.out.println(this.getClass() + "Inizializzata state machine per "+instance);
	}

	/**
	 * <p>Restituisce la lista di stati verso cui un dato 'profilo' pu� effettuare il cambio 
	 *    su di una segnalazione che si trova nello stato 'origine'</p>
	 * @param origine lo stato di partenza della segnalazione
	 * @param profilo il profilo dell'utente che effettua il cambio di stato
	 * @return un ArrayList di oggetti State
	 */
	public ArrayList allowedStates(int origine, int profilo) {
		ArrayList allowedStates=new ArrayList();
		Iterator it= this.states.iterator();
		while (it.hasNext()){
			State state=(State) it.next();
			TransitionKey tk = new TransitionKey(origine, state.getId(), profilo);
			if (this.transitions.containsKey(tk)){
				allowedStates.add(state);
			}
		}
		return allowedStates;
	}

	public String getTemplate(int origine, int destinazione, int profilo, String instance) throws StateMachineJBException{
		StateMachineDAO stateMachineDAO = DAOFactory.getDAOFactoryByProperties().getStateMachineDAO();
		String template = "";
		try {
			stateMachineDAO.createConnection(instance);
			Transition tran = getTransition(new TransitionKey(origine, destinazione, profilo));
			if(tran == null) 
				template=null;
			else
				template = stateMachineDAO.getTemplate(tran.getId(), profilo);
			stateMachineDAO.closeConnection();
		} catch(StateMachineDAOException e){
			e.printStackTrace();
			throw new StateMachineJBException(e.getMessage(), e.getUserMessage());
		}
		return template;
	}
	
	
	public java.util.ArrayList getStates() {
		return states;
	}

	public void setStates(java.util.ArrayList states) {
		this.states = states;
	}

	public java.util.HashMap getTransitions() {
		return transitions;
	}

	public void setTransitions(java.util.HashMap transitions) {
		this.transitions = transitions;
	}

	private StateMachineManagementHome lookupHome() throws StateMachineJBException {
		StateMachineManagementHome smmh = null;
		try {
			Context context = getInitialContext();
        	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
            Object obj = context.lookup(jndiPrefix+"StateMachineManagement!it.cnr.helpdesk.StateMachineManagement.ejb.StateMachineManagementHome");
			smmh = (StateMachineManagementHome) PortableRemoteObject.narrow(obj, it.cnr.helpdesk.StateMachineManagement.ejb.StateMachineManagementHome.class);
		} catch (NamingException namingexception) {
			log(this, "Si \350 verificato un errore durante la ricerca di StateMachineManagement all'interno del registro JNDI. " + namingexception.getMessage());
			namingexception.printStackTrace();
			throw new StateMachineJBException(namingexception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		} catch (StateMachineEJBException problemejbexception) {
			throw new StateMachineJBException(problemejbexception.getMessage(), problemejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new StateMachineJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return smmh;
	}
	 
	 public Transition getTransition(TransitionKey tk) throws StateMachineJBException{
		 return (Transition) this.transitions.get(tk);
	 }
}
