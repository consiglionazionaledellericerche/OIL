§§

package it.cnr.helpdesk.StateMachineManagement.tasks;

import it.cnr.helpdesk.StateMachineManagement.exceptions.TaskException;

import java.util.HashMap;
import java.io.Serializable;

/**
 * 
 * @author Roberto Puccinelli
 */
public abstract class Task implements Serializable {
	
	private int id;
	private String descrizione;
	
public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


    //public abstract void doAction() throws TaskException;
    
    public abstract void doAction(Object token) throws TaskException;
}


