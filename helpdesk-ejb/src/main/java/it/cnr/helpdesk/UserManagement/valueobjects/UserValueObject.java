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

 

package it.cnr.helpdesk.UserManagement.valueobjects;

import it.cnr.helpdesk.valueobjects.ComponentValueObject;
import java.io.Serializable;

public class UserValueObject extends ComponentValueObject
    implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	public UserValueObject(String s, String s1, String s2, String s3, int i, String s4, String s5, 
            String s6, String s7)
    {
        firstName = "";
        familyName = "";
        profile = 0;
        login = "";
        email = "";
        password = "";
        telefono = "";
        struttura = "";
        enabled = "";
        if(s2 == null)
            s2 = "";
        else
            firstName = s2;
        if(s3 == null)
            s3 = "";
        else
            familyName = s3;
        profile = i;
        if(s == null)
            s = "";
        else
            login = s;
        if(s4 == null)
            s = "";
        else
            email = s4;
        if(s1 == null)
            s1 = "";
        else
            password = s1;
        if(s5 == null)
            s5 = "";
        else
            telefono = s5;
        if(s6 == null)
            s6 = "";
        else
            struttura = s6;
        if (s7 == null) 
        	s7="";
        else
        	enabled=s7;
    }

    public UserValueObject(String s, String s1, String s2, String s3, int i, String s4, String s5, 
            String s6, String s7, String sd)
    {
        firstName = "";
        familyName = "";
        profile = 0;
        login = "";
        email = "";
        password = "";
        telefono = "";
        struttura = "";
        strutturaDescrizione="";
        enabled = "";
        if(s2 == null)
            s2 = "";
        else
            firstName = s2;
        if(s3 == null)
            s3 = "";
        else
            familyName = s3;
        profile = i;
        if(s == null)
            s = "";
        else
            login = s;
        if(s4 == null)
            s = "";
        else
            email = s4;
        if(s1 == null)
            s1 = "";
        else
            password = s1;
        if(s5 == null)
            s5 = "";
        else
            telefono = s5;
        if(s6 == null)
            s6 = "";
        else
            struttura = s6;
        if (s7 == null) 
        	s7="";
        else
        	enabled=s7;
        if (sd == null) 
        	sd="";
        else
        	strutturaDescrizione=sd;
    }

    public UserValueObject(String userId, String pwd, String nome, String cognome, int profilo, String mail, String tel, 
            String uo, String abilitato, String strutdesc, String mailstop, String oscurato)
    {
        firstName = "";
        familyName = "";
        profile = 0;
        login = "";
        email = "";
        password = "";
        telefono = "";
        struttura = "";
        strutturaDescrizione="";
        enabled = "";
        blurred = "";
        if(nome == null)
            nome = "";
        else
            firstName = nome;
        if(cognome == null)
            cognome = "";
        else
            familyName = cognome;
        profile = profilo;
        if(userId == null)
            userId = "";
        else
            login = userId;
        if(mail == null)
            mail = "";
        else
            email = mail;
        if(pwd == null)
            pwd = "";
        else
            password = pwd;
        if(tel == null)
            tel = "";
        else
            telefono = tel;
        if(uo == null)
            uo = "";
        else
            struttura = uo;
        if (abilitato == null) 
        	abilitato="";
        else
        	enabled=abilitato;
        if (strutdesc == null) 
        	strutdesc="";
        else
        	strutturaDescrizione=strutdesc;
        if (mailstop==null)
        	mailStop="n";
        else
        	mailStop=mailstop;
        if (oscurato==null)
        	blurred="";
        else
        	blurred=oscurato;
    }
    
    public UserValueObject()
    {
        firstName = "";
        familyName = "";
        profile = 0;
        login = "";
        email = "";
        password = "";
        telefono = "";
        struttura = "";
        strutturaDescrizione="";
    }

    public void setFirstName(String s)
    {
        firstName = s;
    }

    public void setFamilyName(String s)
    {
        familyName = s;
    }

    public void setLogin(String s)
    {
        login = s;
    }

    public void setProfile(int i)
    {
        profile = i;
    }

    public void setEmail(String s)
    {
        email = s;
    }

    public void setPassword(String s)
    {
        password = s;
    }

    public void setTelefono(String s)
    {
        telefono = s;
    }

    public void setStruttura(String s)
    {
        struttura = s;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public String getLogin()
    {
        return login;
    }

    public int getProfile()
    {
        return profile;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public String getStruttura()
    {
        return struttura;
    }
    
	/**
	 * @return Returns the enabled.
	 */    
	public String getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled The enabled to set.
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
    /**
     * @return Returns the strutturaDescrizione.
     */
    public String getStrutturaDescrizione() {
        return strutturaDescrizione;
    }
    /**
     * @param strutturaDescrizione The strutturaDescrizione to set.
     */
    public void setStrutturaDescrizione(String strutturaDescrizione) {
        this.strutturaDescrizione = strutturaDescrizione;
    }
    
	/**
	 * @return Returns the mailStop.
	 */
	public String getMailStop() {
		return mailStop;
	}
	/**
	 * @param mailStop The mailStop to set.
	 */
	public void setMailStop(String mailStop) {
		this.mailStop = mailStop;
	}
	
	public String getBlurred() {
		return blurred;
	}
	public void setBlurred(String blurred) {
		this.blurred = blurred;
	}
    private String firstName;
    private String familyName;
    private int profile;
    private String login;
    private String email;
    private String password;
    private String telefono;
    private String struttura;
    private String strutturaDescrizione;
    private String enabled;
    private String mailStop;
    private String blurred;

}