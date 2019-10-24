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

 

package it.cnr.helpdesk.CategoryManagement.valueobjects;

import it.cnr.helpdesk.valueobjects.ComponentValueObject;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

public class CategoryValueObject extends ComponentValueObject
    implements Serializable
{

    public CategoryValueObject(int id, String nome, String descrizione, int idPadre, int livello)
    {
        this.sottocategorie = null;
        this.nome = nome;
        this.descrizione = descrizione;
        this.id = id;
        this.idPadre = idPadre;
        this.livello = livello;
        this.assigned = false;
    }

    public CategoryValueObject(int id, String nome, String descrizione, int idPadre, String enabled, int livello)
    {
        this.sottocategorie = null;
        this.nome = nome;
        this.descrizione = descrizione;
        this.id = id;
        this.idPadre = idPadre;
        this.livello = livello;
        this.enabled = enabled;
        this.assigned = false;
    }


    public CategoryValueObject()
    {
        nome = "";
        descrizione = "";
        id = 0;
        idPadre = 0;
        livello = 0;
        sottocategorie = null;
    }

    public void setNome(String s)
    {
        nome = s;
    }

    public void setDescrizione(String s)
    {
        descrizione = s;
    }

    public void setIdPadre(int i)
    {
        idPadre = i;
    }

    public void setLivello(int i)
    {
        livello = i;
    }

    public void setAssigned(boolean flag)
    {
        assigned = flag;
    }

    public String getNome()
    {
        return nome;
    }

    public String getDescrizione()
    {
        return descrizione;
    }

    public int getIdPadre()
    {
        return idPadre;
    }

    public int getLivello()
    {
        return livello;
    }

    public int getId()
    {
        return id;
    }

    public boolean getAssigned()
    {
        return assigned;
    }

    public void add(CategoryValueObject categoryvalueobject)
    {
        if(sottocategorie == null)
            sottocategorie = new Vector();
        sottocategorie.addElement(categoryvalueobject);
    }

    public void remove(CategoryValueObject categoryvalueobject)
    {
        if(sottocategorie != null)
            sottocategorie.removeElement(categoryvalueobject);
    }

    public Enumeration elements()
    {
        if(sottocategorie != null){
        	SortedMap<String, CategoryValueObject> map = new TreeMap<String, CategoryValueObject>();
        	for (Enumeration<CategoryValueObject>e = sottocategorie.elements(); e.hasMoreElements();) {
        		CategoryValueObject cvo = e.nextElement() ;
        		map.put(cvo.getNome(), cvo);
			}
        	return (new Vector(map.values())).elements();
        }
        else
            return null;
    }

    private String enabled;
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
    private String nome;
    private String descrizione;
    private int id;
    private int idPadre;
    private int livello;
    private Vector sottocategorie;
    private boolean assigned;
}