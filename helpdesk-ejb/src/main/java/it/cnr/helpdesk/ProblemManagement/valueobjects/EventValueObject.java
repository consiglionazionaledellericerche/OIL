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

 

package it.cnr.helpdesk.ProblemManagement.valueobjects;

import it.cnr.helpdesk.util.Converter;
import it.cnr.helpdesk.valueobjects.ComponentValueObject;
import java.io.Serializable;

public class EventValueObject extends ComponentValueObject
    implements Serializable
{

    public EventValueObject(long l, String s, int i, String s1, int j, String s2, 
            int k, String s3, String s4, String s5, String s6)
    {
        idSegnalazione = 0L;
        eventType = 0;
        eventTypeDescription = "";
        expertLogin = "";
        stateDescription = "";
        categoryDescription = "";
        note = "";
        time = "";
        originatoreEvento = "";
        originatoreEventoDescrizione = "";
        idSegnalazione = l;
        eventType = i;
        eventTypeDescription = s1;
        state = j;
        stateDescription = s2;
        category = k;
        categoryDescription = s3;
        note = s4;
        time = s;
        originatoreEvento = s5;
        originatoreEventoDescrizione = s6;
    }
    
    public EventValueObject(long idSegnalazione, String time, int eventType, String evTypeDesc, int state, String stateDesc, 
            int category, String categoryDesc, String note, String origEvento, String origEventDesc, boolean alterable)
    {
        this.idSegnalazione = idSegnalazione;
        this.eventType = eventType;
        eventTypeDescription = evTypeDesc;
        this.state = state;
        stateDescription = stateDesc;
        this.category = category;
        categoryDescription = categoryDesc;
        this.note = note;
        this.time = time;
        originatoreEvento = origEvento;
        originatoreEventoDescrizione = origEventDesc;
        this.alterable = alterable;        
    }

    public EventValueObject()
    {
        idSegnalazione = 0L;
        eventType = 0;
        eventTypeDescription = "";
        expertLogin = "";
        stateDescription = "";
        categoryDescription = "";
        note = "";
        time = "";
        originatoreEvento = "";
        originatoreEventoDescrizione = "";
    }

    public void setIdSegnalazione(long l)
    {
        idSegnalazione = l;
    }

    public long getIdSegnalazione()
    {
        return idSegnalazione;
    }

    public void setEventType(int i)
    {
        eventType = i;
    }

    public int getEventType()
    {
        return eventType;
    }

    public void setEventTypeDescription(String s)
    {
        eventTypeDescription = s;
    }

    public String getEventTypeDescription()
    {
        return eventTypeDescription;
    }

    public void setExpertLogin(String s)
    {
        expertLogin = s;
    }

    public String getExpertLogin()
    {
        return expertLogin;
    }

    public void setState(int i)
    {
        state = i;
    }

    public int getState()
    {
        return state;
    }

    public void setStateDescription(String s)
    {
        stateDescription = s;
    }

    public String getStateDescription()
    {
        return stateDescription;
    }

    public void setCategory(int i)
    {
        category = i;
    }

    public int getCategory()
    {
        return category;
    }

    public void setCategoryDescription(String s)
    {
        categoryDescription = s;
    }

    public String getCategoryDescription()
    {
        return categoryDescription;
    }

    public void setNote(String s)
    {
        note = s;
    }

    public String getNote()
    {
        return note;
    }
    
    public String getNote2HTML(){
    	return Converter.text2HTML(note);
    }
    
    public String getNote2SQL(){
    	return note.replaceAll("'","''");
    }

    public void setTime(String s)
    {
        time = s;
    }

    public String getTime()
    {
        return time;
    }

    public void setOriginatoreEvento(String s)
    {
        originatoreEvento = s;
    }

    public String getOriginatoreEvento()
    {
        return originatoreEvento;
    }

    public void setOriginatoreEventoDescrizione(String s)
    {
        originatoreEventoDescrizione = s;
    }

    public String getOriginatoreEventoDescrizione()
    {
        return originatoreEventoDescrizione;
    }

    
	public String getDescription() {
		return description;
	}
	public String getDescription2HTML() {
		return Converter.text2HTML(description);
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
    private long idSegnalazione;
    private int eventType;
    private String eventTypeDescription;
    private String expertLogin;
    private String validatorLogin;
    private int state;
    private int oldState = 0;
    private String stateDescription;
    private String oldStateDescription;
    private int category;
    private String categoryDescription;
    private String note;
    private String time;
    private String originatoreEvento;
    private String originatoreEventoDescrizione;
    private String title;
    private String description;
    private boolean alterable;
    private String instance;
    
	/**
	 * @return Returns the validatorLogin.
	 */
	public String getValidatorLogin() {
		return validatorLogin;
	}

	/**
	 * @param validatorLogin The validatorLogin to set.
	 */
	public void setValidatorLogin(String validatorLogin) {
		this.validatorLogin = validatorLogin;
	}

	/**
	 * @return Returns the oldState.
	 */
	public int getOldState() {
		return oldState;
	}

	/**
	 * @param oldState The oldState to set.
	 */
	public void setOldState(int oldState) {
		this.oldState = oldState;
	}
	
	public String getOldStateDescription() {
		return oldStateDescription;
	}
	public void setOldStateDescription(String oldStateDescription) {
		this.oldStateDescription = oldStateDescription;
	}
	public boolean isAlterable() {
		return alterable;
	}
	public void setAlterable(boolean alterable) {
		this.alterable = alterable;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}
}