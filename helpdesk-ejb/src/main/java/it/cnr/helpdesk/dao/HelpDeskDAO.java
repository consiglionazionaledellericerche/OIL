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
package it.cnr.helpdesk.dao;

import java.sql.*;
import java.util.*;
import it.cnr.helpdesk.exceptions.*;
import javax.naming.*;

public class HelpDeskDAO {

	private String urlDB=null;
	private String user=null;
	private String password=null;
	private String driver=null;
	private Connection con=null;
	private String instance=null;

	public HelpDeskDAO(){
	}
	
	/* Roberto Puccinelli: inizio modifiche 06/10/02
		public void createConnection() throws HelpDeskDAOException{
			Properties p=it.cnr.helpdesk.util.ApplicationProperties.getApplicationProperties();
			if (p.getProperty("it.cnr.oil.connectiontype").equals("SERVER_DATASOURCE")) {
				Context ctx = null;
				javax.sql.DataSource ds=null;
				Hashtable ht = null;
				try {
					if (p.getProperty("java.naming.factory.initial")!=null && p.getProperty("java.naming.provider.url")!=null)
						ctx = new InitialContext(ht);
					else
						ctx = new InitialContext();
					log(this,"get connection from connection pool");
					ds=(javax.sql.DataSource) ctx.lookup (p.getProperty("it.cnr.oil.datasource"));
				} catch(NamingException e) {
					log(this, "Errore nel lookup del Data Source dal Connection Pool"+e.getMessage());
					e.printStackTrace();
					throw new HelpDeskDAOException(e.getMessage(),"Si è verificato un errore interno all'applicazione durante il caricamento del driver per la connessione al data base. Segnalare il problema all'assistenza.");
				}
				try {
					con = ds.getConnection();
					if (p.getProperty("it.cnr.oil.applicationserver").equals("WEBLOGIC")) con.setAutoCommit(false);
				} catch(SQLException e2) {
					log(this, "Errore nella creazione della connessione dal Data Source"+e2.getMessage());
	      			e2.printStackTrace();
	      			throw new HelpDeskDAOException(e2.getMessage(),"Si è verificato un errore interno all'applicazione durante il caricamento del driver per la connessione al data base. Segnalare il problema all'assistenza.");
				}
			} else {
				try{
					urlDB=p.getProperty("it.oil.urlDB");
					user=p.getProperty("it.oil.user");
					password=p.getProperty("it.oil.password");
					driver=p.getProperty("it.oil.driver");
					Class.forName(driver);
				} catch(Exception e) {
					log(this, "Errore sul caricamento del Driver. "+e.getMessage());
					e.printStackTrace();
					throw new HelpDeskDAOException(e.getMessage(),"Si è verificato un errore interno all'applicazione durante il caricamento del driver per la connessione al data base. Segnalare il problema all'assistenza.");
				}

				try {
					if (user!=null) 
						con=DriverManager.getConnection(urlDB,user,password);
					else 
						con=DriverManager.getConnection(urlDB,null);
					con.setAutoCommit(false);
				} catch(Exception e) {
					log(this, "Errore sulla creazione della connessione. "+e.getMessage());
					e.printStackTrace();
					throw new HelpDeskDAOException(e.getMessage(),"Si è verificato un errore interno all'applicazione durante la creazione della connessione al data base. Segnalare il problema all'assistenza.");
				}
			}
		}
*/
	public void createConnection(String instance) throws HelpDeskDAOException{
		//Properties p=it.cnr.helpdesk.util.ApplicationProperties.getApplicationProperties();
		if (System.getProperty("it.cnr.oil.connectiontype").equals("SERVER_DATASOURCE")) {
			Context ctx = null;
			javax.sql.DataSource ds=null;
			Hashtable ht = null;
			try {
				if (System.getProperty("java.naming.factory.initial")!=null && System.getProperty("java.naming.provider.url")!=null)
					ctx = new InitialContext(ht);
				else
					ctx = new InitialContext();
				String name = System.getProperty("it.cnr.oil.datasource")+instance;
				log(this,"get connection from connection pool: "+name);
				ds=(javax.sql.DataSource) ctx.lookup (name);
				this.instance = instance;
			} catch(NamingException e) {
				log(this, "Errore nel lookup del Data Source dal Connection Pool"+e.getMessage());
				e.printStackTrace();
				throw new HelpDeskDAOException(e.getMessage(),"Si è verificato un errore interno all'applicazione durante il caricamento del driver per la connessione al data base. Segnalare il problema all'assistenza.");
			}
			try {
				con = ds.getConnection();
				if (System.getProperty("it.cnr.oil.applicationserver").equals("WEBLOGIC")) con.setAutoCommit(false);
			} catch(SQLException e2) {
				log(this, "Errore nella creazione della connessione dal Data Source"+e2.getMessage());
      			e2.printStackTrace();
      			throw new HelpDeskDAOException(e2.getMessage(),"Si è verificato un errore interno all'applicazione durante il caricamento del driver per la connessione al data base. Segnalare il problema all'assistenza.");
			}
		} else {
			try{
				urlDB=System.getProperty("it.oil.urlDB");
				user=System.getProperty("it.oil.user");
				password=System.getProperty("it.oil.password");
				driver=System.getProperty("it.oil.driver");
				Class.forName(driver);
			} catch(Exception e) {
				log(this, "Errore sul caricamento del Driver. "+e.getMessage());
				e.printStackTrace();
				throw new HelpDeskDAOException(e.getMessage(),"Si è verificato un errore interno all'applicazione durante il caricamento del driver per la connessione al data base. Segnalare il problema all'assistenza.");
			}

			try {
				if (user!=null) 
					con=DriverManager.getConnection(urlDB,user,password);
				else 
					con=DriverManager.getConnection(urlDB,null);
				con.setAutoCommit(false);
			} catch(Exception e) {
				log(this, "Errore sulla creazione della connessione. "+e.getMessage());
				e.printStackTrace();
				throw new HelpDeskDAOException(e.getMessage(),"Si è verificato un errore interno all'applicazione durante la creazione della connessione al data base. Segnalare il problema all'assistenza.");
			}
		}
	}



	public Connection getConnection() {
		return con;
	}

// Roberto Puccinelli: inizio modifiche 06/10/02
	public void closeConnection() throws HelpDeskDAOException {
		try{
			if (con!=null) con.close();
		} catch(SQLException e) {
			log(this,"Errore nella chiusura della connessione al data base. "+e.getMessage());
			e.printStackTrace();
			throw new HelpDeskDAOException(e.getMessage(),"Errore nella chiusura della connessione al data base. Contattare l'assistenza.");
		}	
	}
// Roberto Puccinelli: fine modifiche 06/10/02

	public ResultSet execQuery(String sqlQuery)throws SQLException {
		ResultSet rs=null;
		Statement stmt=null;
		stmt=con.createStatement();
		rs=stmt.executeQuery(sqlQuery);
		return rs;
	}

	public ResultSet execSQuery(String sqlQuery) throws SQLException {
		ResultSet rs=null;
		Statement stmt=null;
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		rs=stmt.executeQuery(sqlQuery);
		return rs;
	}

	public ResultSet execUQuery(String sqlQuery) throws SQLException {
		ResultSet rs=null;
		Statement stmt=null;
		stmt=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
		rs=stmt.executeQuery(sqlQuery);
		return rs;
	}

	public void execUpdQuery(String sqlQuery) throws SQLException {
		Statement stmt=null;
		stmt=con.createStatement();
		stmt.executeUpdate(sqlQuery);
	}
	
	public void execPrepStat (String query, byte[] param) throws SQLException {
		PreparedStatement ps = con.prepareStatement(query);
		ps.setBytes(1, param);
		ps.executeUpdate();
	}

	public void log(Object o,String s) {
		java.util.Date d=new java.util.Date();
		java.text.DateFormat df=java.text.DateFormat.getDateTimeInstance();
		String now=df.format(d);
		String className=o.getClass().getName();
		System.out.println();
		System.out.println(now+" - ["+className+"]");
		System.out.println(s);
		System.out.println();
	}

	public void log(String s) {
		java.util.Date d=new java.util.Date();
		java.text.DateFormat df=java.text.DateFormat.getDateTimeInstance();
		String now=df.format(d);
		System.out.println();
		System.out.println(now);
		System.out.println(s);
		System.out.println();
	}


	public String formatString(String s) {
		StringBuffer tmp=null;
		String result=s;
		char c=39;
		if (s!=null){
			if (s.indexOf(c)>=0) {
				tmp=new StringBuffer(s);
				boolean endString=false;
				int index=0;
				int fromIndex=0;
				while (!endString) {
					// ascii(39)='
					index=tmp.toString().indexOf(c,fromIndex);
					if (index>=0) {
						tmp.replace(index, index+1, "''");
						fromIndex=index+2; 
					}
					if (index<0 || fromIndex>tmp.length()) 
						endString=true;
				}
				result=tmp.toString();
			}
		}
		return result;
	}

	public int numberObjectToInt(Object o) {
		int res=0;
		if (o instanceof java.lang.Number) {
			res=((java.lang.Number) o).intValue();
		}
		return res;
	}

	public String getInstance() {
		return instance;
	}


}
