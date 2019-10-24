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

 

package it.cnr.helpdesk.CategoryManagement.javabeans;

import it.cnr.helpdesk.dao.CategoryDAO;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagement;
import it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagementHome;
import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryDAOException;
import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryEJBException;
import it.cnr.helpdesk.CategoryManagement.exceptions.CategoryJBException;
import it.cnr.helpdesk.CategoryManagement.valueobjects.CategoryValueObject;
import it.cnr.helpdesk.javabeans.HelpDeskJB;

import java.util.*;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

// Referenced classes of package it.cnr.helpdesk.javabeans:
//            HelpDeskJB

public class Category extends HelpDeskJB
{

    public Category()
    {
        idCategoria = 0;
        nome = null;
        descrizione = null;
        idPadre = 0;
        outputString = new StringBuffer();
    }

    public void setIdCategoria(int i)
    {
        idCategoria = i;
    }

    public int getIdCategoria()
    {
        return idCategoria;
    }

    public void setNome(String s)
    {
        nome = s;
    }

    public String getNome()
    {
        return nome;
    }

    public void setDescrizione(String s)
    {
        descrizione = s;
    }

    public String getDescrizione()
    {
        return descrizione;
    }

    public void setIdPadre(int i)
    {
        idPadre = i;
    }

    public int getIdPadre()
    {
        return idPadre;
    }

    public String printTree(String instance)
        throws CategoryJBException
    {
        CategoryValueObject categoryvalueobject = null;
        try
        {
            CategoryManagementHome categorymanagementhome = lookupHome();
            CategoryManagement categorymanagement = (CategoryManagement)PortableRemoteObject.narrow(categorymanagementhome.create(), it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagement.class);
            categoryvalueobject = categorymanagement.allCategories(instance);
            categorymanagement.remove();
        }
        catch(CategoryEJBException categoryejbexception)
        {
            throw new CategoryJBException(categoryejbexception.getMessage(), categoryejbexception.getUserMessage());
        }
        catch(Exception exception)
        {
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new CategoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
        }
        if(categoryvalueobject != null)
            recursivePrintTree(categoryvalueobject,true);
        return outputString.toString();
    }

    public Collection getAssignedCategories(String s, String instance)
        throws CategoryJBException
    {
        Collection collection = null;
        Object obj = null;
        try
        {
            CategoryManagementHome categorymanagementhome = lookupHome();
            CategoryManagement categorymanagement = (CategoryManagement)PortableRemoteObject.narrow(categorymanagementhome.create(), it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagement.class);
            collection = categorymanagement.allCategoriesList(instance);
            Collection collection1 = categorymanagement.allExpertCategoriesList(s, instance);
            CategoryValueObject categoryvalueobject;
            boolean flag;
            for(Iterator iterator = collection.iterator(); iterator.hasNext(); categoryvalueobject.setAssigned(flag))
            {
                categoryvalueobject = (CategoryValueObject)iterator.next();
                flag = false;
                for(Iterator iterator1 = collection1.iterator(); iterator1.hasNext() && !flag;)
                {
                    CategoryValueObject categoryvalueobject1 = (CategoryValueObject)iterator1.next();
                    if(categoryvalueobject.getId() == categoryvalueobject1.getId())
                        flag = true;
                }

            }

            categorymanagement.remove();
        }
        catch(CategoryEJBException categoryejbexception)
        {
            throw new CategoryJBException(categoryejbexception.getMessage(), categoryejbexception.getUserMessage());
        }
        catch(Exception exception)
        {
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new CategoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
        }
        return collection;
    }

    public Collection getAllCategories(String instance)
        throws CategoryJBException
    {
        Collection collection = null;
        try
        {
            CategoryManagementHome categorymanagementhome = lookupHome();
            CategoryManagement categorymanagement = (CategoryManagement)PortableRemoteObject.narrow(categorymanagementhome.create(), it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagement.class);
            collection = categorymanagement.allCategoriesList(instance);
        }
        catch(CategoryEJBException categoryejbexception)
        {
            throw new CategoryJBException(categoryejbexception.getMessage(), categoryejbexception.getUserMessage());
        }
        catch(Exception exception)
        {
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new CategoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
        }
        return collection;
    }

    public Collection getAllEnabledCategory(String instance) throws CategoryJBException {
        Collection collection = null;
	        CategoryDAO catdao = DAOFactory.getDAOFactoryByProperties().getCategoryDAO();
    try
    {
        catdao.createConnection(instance);
        collection = catdao.allEnabledCategoriesList();
        catdao.closeConnection();
    }
    catch(CategoryDAOException catdaoexception)
    {
        throw new CategoryJBException(catdaoexception.getMessage(), catdaoexception.getUserMessage());
    }
    catch(Exception exception)
    {
        log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
        exception.printStackTrace();
        throw new CategoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile recuperare la lista degli utenti. Contattare l'assistenza.");
    }
    return collection;
}
    public void assignCategoryToExpert(int i, String s, String instance)
        throws CategoryJBException
    {
        try
        {
            CategoryManagementHome categorymanagementhome = lookupHome();
            CategoryManagement categorymanagement = (CategoryManagement)PortableRemoteObject.narrow(categorymanagementhome.create(), it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagement.class);
            System.out.println("prima di assign category to expert");
            categorymanagement.assignCategoryToExpert(i, s, instance);
            System.out.println("dopo assign category to expert");
            categorymanagement.remove();
        }
        catch(CategoryEJBException categoryejbexception)
        {
            throw new CategoryJBException(categoryejbexception.getMessage(), categoryejbexception.getUserMessage());
        }
        catch(Exception exception)
        {
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new CategoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
        }
    }

    public void removeCategoryFromExpert(int i, String s, String instance)
        throws CategoryJBException
    {
        try
        {
            CategoryManagementHome categorymanagementhome = lookupHome();
            CategoryManagement categorymanagement = (CategoryManagement)PortableRemoteObject.narrow(categorymanagementhome.create(), it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagement.class);
            categorymanagement.removeCategoryFromExpert(i, s, instance);
            categorymanagement.remove();
        }
        catch(CategoryEJBException categoryejbexception)
        {
            throw new CategoryJBException(categoryejbexception.getMessage(), categoryejbexception.getUserMessage());
        }
        catch(Exception exception)
        {
            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
            exception.printStackTrace();
            throw new CategoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
        }
    }

    private void recursivePrintTree(CategoryValueObject categoryvalueobject)
    {
        Enumeration enumeration = categoryvalueobject.elements();
        String s = null;
        if(categoryvalueobject.getId() == getIdCategoria())
            s = " checked ";
        else
            s = "";
        if(enumeration != null)
        {
            toOut("<li id=\"foldheader\">  " + categoryvalueobject.getNome() + "<input type=\"radio\" value=\"" + categoryvalueobject.getId() + ":" + categoryvalueobject.getNome() + "\"" + s + " name=\"R1\"></li>\n");
            toOut("<ul id=\"foldinglist\" style=\"display:none\" style=&{head};>\n");
            for(; enumeration.hasMoreElements(); recursivePrintTree((CategoryValueObject)enumeration.nextElement()));
            toOut("</ul>\n");
        } else
        {
            toOut("<li>  " + categoryvalueobject.getNome() + "<input type=\"radio\" value=\"" + categoryvalueobject.getId() + ":" + categoryvalueobject.getNome() + "\"" + s + " name=\"R1\"></li>");
        }
    }

    private void recursivePrintTree(CategoryValueObject categoryvalueobject, boolean filter)
    {
        if(categoryvalueobject.getEnabled().startsWith("y")||!filter){			//categoria aggiunta se non è disabilitata o se il filtro non è attivo
	    	Enumeration enumeration = categoryvalueobject.elements();
	        String s = null;
	        if(categoryvalueobject.getId() == getIdCategoria())
	            s = " checked ";
	        else
	            s = "";
	        if(enumeration != null)
	        {
	            toOut("<li id=\"foldheader\">  " + categoryvalueobject.getNome() + "<input type=\"radio\" value=\"" + categoryvalueobject.getId() + ":" + categoryvalueobject.getNome() + "\"" + s + " name=\"R1\"></li>\n");
	            toOut("<ul id=\"foldinglist\" style=\"display:none\" style=&{head};>\n");
	            for(; enumeration.hasMoreElements(); recursivePrintTree((CategoryValueObject)enumeration.nextElement(),filter));
	            toOut("</ul>\n");
	        } else
	        {
	            toOut("<li>  " + categoryvalueobject.getNome() + "<input type=\"radio\" value=\"" + categoryvalueobject.getId() + ":" + categoryvalueobject.getNome() + "\"" + s + " name=\"R1\"></li>");
	        }
        } else 
        	toOut("<!-- disabled element: "+categoryvalueobject.getNome()+". -->\n");
    }
    private CategoryManagementHome lookupHome()
        throws CategoryJBException
    {
        CategoryManagementHome categorymanagementhome = null;
        try
        {
            Context context = getInitialContext();
        	String jndiPrefix = System.getProperty("it.cnr.oil.ejb.jndiPrefix");
            Object obj = context.lookup(jndiPrefix+"CategoryManagement!it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagementHome");
            categorymanagementhome = (CategoryManagementHome)PortableRemoteObject.narrow(obj, it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagementHome.class);
        }
        catch(CategoryEJBException categoryejbexception)
        {
            throw new CategoryJBException(categoryejbexception.getMessage(), categoryejbexception.getUserMessage());
        }
        catch(Exception exception)
        {
            log(this, "Si \350 verificato un errore durante la ricerca nel registro JNDI. " + exception.getMessage());
            exception.printStackTrace();
            throw new CategoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
        }
        return categorymanagementhome;
    }

    private void toOut(String s)
    {
        outputString.append(s);
    }

	public int insert(String instance) throws CategoryJBException {
		int i = 0;
		try {
			CategoryManagementHome categorymanagementhome = lookupHome();
			CategoryManagement categorymanagement = (CategoryManagement) PortableRemoteObject.narrow(categorymanagementhome.create(), it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagement.class);
			CategoryValueObject categoryvalueobject = new CategoryValueObject();
			categoryvalueobject.setNome(getNome());
			categoryvalueobject.setDescrizione(getDescrizione());
			categoryvalueobject.setIdPadre(getIdPadre());
			i = categorymanagement.insertCategory(categoryvalueobject, instance);
			categorymanagement.remove();
		} catch (CategoryEJBException categoryejbexception) {
			throw new CategoryJBException(categoryejbexception.getMessage(), categoryejbexception.getUserMessage());
		} catch (Exception exception) {
			log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
			exception.printStackTrace();
			throw new CategoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
		}
		return i;
	}

    public void update(String instance)
    	throws CategoryJBException
	    {
	        try
	        {
	            CategoryManagementHome categorymanagementhome = lookupHome();
	            CategoryManagement categorymanagement = (CategoryManagement)PortableRemoteObject.narrow(categorymanagementhome.create(), it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagement.class);
	            CategoryValueObject categoryvalueobject = new CategoryValueObject(getIdCategoria(),getNome(),getDescrizione(),0,0);
	            categorymanagement.updateCategory(categoryvalueobject, instance);
	            categorymanagement.remove();
	        }
	        catch(CategoryEJBException categoryejbexception)
	        {
	            throw new CategoryJBException(categoryejbexception.getMessage(), categoryejbexception.getUserMessage());
	        }
	        catch(Exception exception)
	        {
	            log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
	            exception.printStackTrace();
	            throw new CategoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Contattare l'assistenza.");
	        }
	    }

    public boolean hasChildren(int id, String instance) throws CategoryJBException {
    	boolean res = false;
    	try
	    {
	        CategoryDAO categorydao = DAOFactory.getDAOFactoryByProperties().getCategoryDAO();
	        categorydao.createConnection(instance);
	        res = categorydao.hasChildren(id);
	        categorydao.closeConnection();
	    }
	    catch(CategoryDAOException categorydaoexception)
	    {
	        throw new CategoryJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
	    }
	    return res;
    }

    public boolean hasDisabledParent(int id, String instance) throws CategoryJBException {
    	boolean res = false;
	    try
	    {
	    	CategoryDAO categorydao = DAOFactory.getDAOFactoryByProperties().getCategoryDAO();
	        categorydao.createConnection(instance);
	        res = categorydao.hasDisabledParent(id);
	        categorydao.closeConnection();
	    }
	    catch(CategoryDAOException categorydaoexception)
	    {
	        throw new CategoryJBException(categorydaoexception.getMessage(), categorydaoexception.getUserMessage());
	    }
	    return res;
    }
    
    public void disable(int id, String instance) throws CategoryJBException {
    	try {
    		CategoryManagementHome categorymanagementhome = lookupHome();
    		CategoryManagement categorymanagement = (CategoryManagement)PortableRemoteObject.narrow(categorymanagementhome.create(), it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagement.class);
    		categorymanagement.disable(id, instance);
    		categorymanagement.remove();
    	} catch(CategoryEJBException categoryejbexception){
    		throw new CategoryJBException(categoryejbexception.getMessage(), categoryejbexception.getUserMessage());
    	} catch(Exception exception) {
    		log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
    		exception.printStackTrace();
    		throw new CategoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile verificare la password dell'utente. Contattare l'assistenza.");
    	}
    }    
 
    public void enable(int id, String instance) throws CategoryJBException {
    	try {
    		CategoryManagementHome categorymanagementhome = lookupHome();
    		CategoryManagement categorymanagement = (CategoryManagement)PortableRemoteObject.narrow(categorymanagementhome.create(), it.cnr.helpdesk.CategoryManagement.ejb.CategoryManagement.class);
    		categorymanagement.enable(id, instance);
    		categorymanagement.remove();
    	} catch(CategoryEJBException categoryejbexception){
    		throw new CategoryJBException(categoryejbexception.getMessage(), categoryejbexception.getUserMessage());
    	} catch(Exception exception) {
    		log(this, "Si \350 verificato un errore interno all'applicazione. " + exception.getMessage());
    		exception.printStackTrace();
    		throw new CategoryJBException(exception.getMessage(), "Si \350 verificato un errore interno all'applicazione. Impossibile verificare la password dell'utente. Contattare l'assistenza.");
    	}
    }    


    private int idCategoria;
    private String nome;
    private String descrizione;
    private int idPadre;
    private StringBuffer outputString;
}