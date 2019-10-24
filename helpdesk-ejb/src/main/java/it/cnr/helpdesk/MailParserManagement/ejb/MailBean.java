package it.cnr.helpdesk.MailParserManagement.ejb;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.AttachmentManagement.exceptions.AttachmentJBException;
import it.cnr.helpdesk.AttachmentManagement.javabeans.Attachment;
import it.cnr.helpdesk.AttachmentManagement.valueobjects.AttachmentValueObject;
import it.cnr.helpdesk.EventManagement.exceptions.EventJBException;
import it.cnr.helpdesk.EventManagement.javabeans.Event;
import it.cnr.helpdesk.MailManagement.javabeans.MailManagement;
import it.cnr.helpdesk.MailManagement.valueobjects.MailItem;
import it.cnr.helpdesk.MailParserManagement.exceptions.MailParserDAOException;
import it.cnr.helpdesk.MailParserManagement.exceptions.MailParserEJBException;
import it.cnr.helpdesk.MailParserManagement.exceptions.MailParserJBException;
import it.cnr.helpdesk.MailParserManagement.javabeans.MailParser;
import it.cnr.helpdesk.MailParserManagement.valueobjects.MailComponent;
import it.cnr.helpdesk.ProblemManagement.exceptions.ProblemJBException;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.StateMachineManagement.StateMachine;
import it.cnr.helpdesk.StateMachineManagement.exceptions.ConditionException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineJBException;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.State;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.TransitionKey;
import it.cnr.helpdesk.UserManagement.exceptions.UserJBException;
import it.cnr.helpdesk.UserManagement.javabeans.User;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.MailParserDAO;
import it.cnr.helpdesk.exceptions.SettingsJBException;
import it.cnr.helpdesk.util.ApplicationProperties;
import it.cnr.helpdesk.util.Converter;
import it.cnr.helpdesk.util.MessageComposer;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.*;


/**
 *
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> *
 <!-- lomboz.beginDefinition -->
 <?xml version="1.0" encoding="UTF-8"?>
 <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz">
 <lomboz:session>
 <lomboz:sessionEjb>
 <j2ee:display-name>MailParserManagement</j2ee:display-name>
 <j2ee:ejb-name>MailParserManagement</j2ee:ejb-name>
 <j2ee:ejb-class>it.cnr.helpdesk.MailParserManagement.ejb.MailBean</j2ee:ejb-class>
 <j2ee:session-type>Stateless</j2ee:session-type>
 <j2ee:transaction-type>Container</j2ee:transaction-type>
 </lomboz:sessionEjb>
 </lomboz:session>
 </lomboz:EJB>
 <!-- lomboz.endDefinition -->
 *
 * <!-- begin-xdoclet-definition --> 
 * @ejb.bean name="MailParserManagement"	
 *           jndi-name="comp/env/ejb/MailParserManagement"
 *           type="Stateless" 
 *           transaction-type="Container"
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="MailParserManagement" 
 *             jndi-name="comp/env/ejb/MailParserManagement"
 * 
 *--
 * <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class MailBean implements javax.ejb.SessionBean {
	
	static final long sup = 999999999;
    File savedir = new File(System.getProperty("java.io.tmpdir"));
    ArrayList savefile = new ArrayList(0);


	//metodo per corretta formattazione body mail in entrata
	private String dumpPart(Part p) {
		String body = null;
		try {
			String ct = p.getContentType();
			String disp = p.getDisposition();			
			System.out.println("### Disposition:"+disp+"\n contentType: "+ct);
			if (p.isMimeType("text/plain") && (disp==null || !(disp.equalsIgnoreCase(Part.ATTACHMENT)||disp.equalsIgnoreCase(Part.INLINE)))) {
				body = (String)p.getContent();
			} else if (p.isMimeType("multipart/*")) {
				Multipart mp = (Multipart)p.getContent();
				int count = mp.getCount();
				for (int i = 0; i < count; i++){
					String pbody = dumpPart(mp.getBodyPart(i));
					if(pbody!=null) body=pbody;
				}
			} else if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT)||disp.equalsIgnoreCase(Part.INLINE))){
				try {
                    this.savefile.add(new File(savedir, decodeName(p.getFileName())));
                    saveFile( (File)savefile.get(savefile.size()-1), p);
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
			else if (p.isMimeType("message/rfc822")) {
				body = dumpPart((Part)p.getContent());
			}
		} 
		catch (Exception e) {
			throw new MailParserEJBException(e.getMessage());
		} 
		return body;
	}
	
	protected int saveFile(File saveFile, Part part) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(saveFile) );

        byte[] buff = new byte[2048];
        InputStream is = part.getInputStream();
        int ret = 0, count = 0;
        while( (ret = is.read(buff)) > 0 ){
                bos.write(buff, 0, ret);
                count += ret;
        }
        bos.close();
        is.close();
        return count;
    }

    protected String decodeName( String name ) throws Exception {
        if(name == null || name.length() == 0){
                return "unknown";
        }
        String ret = java.net.URLDecoder.decode( name, "UTF-8" );

        // also check for a few other things in the string:
        ret = ret.replaceAll("=\\?utf-8\\?q\\?", "");
        ret = ret.replaceAll("\\?=", "");
        ret = ret.replaceAll("=20", " ");

        return ret;
    }
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction type="NotSupported"
	 **/
	public int parseMail(String contextPath, String instance) throws MailParserEJBException {
		final int MAIL_OK = 0;
		final int MAIL_LOCKED = 1;
		final String mbox = "INBOX";
		Store store = null;
		Folder folder = null;
		MailParser mp = new MailParser();
		try{
			Properties props= new Properties(); //ApplicationProperties.getApplicationProperties();
			Session session = Session.getInstance(props, null);      
			/*caricamento dal file di props di dati di config
			String protocol_pop = (String)props.get("mail.config.pop.protocol");
			String host_pop = (String)props.get("mail.config.pop.host");
			String user_pop = (String)props.get("mail.config.pop.user");
			String password_pop = (String)props.get("mail.config.pop.password");
			String tag = (String)props.get("mail.config.tag");
			String div_token = (String)props.getProperty("mail.config.div");
			Session session = Session.getInstance(props, null);   */   
			String protocol_pop = Settings.getProperty("mail.config.pop.protocol", instance);
			String host_pop = Settings.getProperty("mail.config.pop.host", instance);
			String user_pop = Settings.getProperty("mail.config.pop.user", instance);
			String password_pop = Settings.getProperty("mail.config.pop.password", instance);
			String tag = Settings.getProperty("mail.config.tag", instance);
			String div_token = Settings.getProperty("mail.config.div", instance);
			session.setDebug(false);
			
			//connessione alla casella pop mail
			if (protocol_pop != null)		
				store = session.getStore(protocol_pop);
			else
				store = session.getStore();
			if (host_pop != null || user_pop != null || password_pop != null)
				store.connect(host_pop, user_pop, password_pop);
			else
				store.connect();
			folder = store.getDefaultFolder();			//??????????
			if (folder == null) {
				//store.close();
				throw new MailParserEJBException("Mail folder non presente");
			}
			folder = folder.getFolder(mbox);
			if (folder == null) {
				//store.close();
				throw new MailParserEJBException("Mail folder non valido");
			}
			folder.open(Folder.READ_WRITE);
			int totalMessages = folder.getMessageCount();
			if (totalMessages == 0) {
				System.out.println("Mail folder vuoto");
				folder.close(false);
				//store.close();
			} else {
				Message message[] = folder.getMessages();
				System.out.println("Messaggi totali nella casella = "+totalMessages);
				for(int i=0; i<message.length; i++) {
					MailComponent my_mail = new MailComponent();
					String oggetto_temp = message[i].getSubject();
					/*riconosce esistenza di tag iniziale e divide la stringa*/
					if(oggetto_temp.startsWith("[") && oggetto_temp.indexOf("]")>-1) 
					  if (tag.indexOf(oggetto_temp.substring(0,oggetto_temp.indexOf("]")+1))>-1) {
						System.out.println("token riconosciuto: "+oggetto_temp.substring(0,oggetto_temp.indexOf("]")+1));
						Vector campi = new Vector();
						StringTokenizer stoken = new StringTokenizer(oggetto_temp, div_token);
						while (stoken.hasMoreTokens()) {
							campi.addElement(stoken.nextToken());
						}
						//oggetto: [siper]~~categoria~~nome_categoria~~oggetto~~nome~~cognome~~email
						if(campi.size()==7){
							my_mail.setTitolo(campi.elementAt(0)+" "+campi.elementAt(3));
							my_mail.setCategoria(Integer.parseInt(""+campi.elementAt(1)));
							my_mail.setNomeCategoria(""+campi.elementAt(2));
							my_mail.setNome(""+campi.elementAt(4));
							my_mail.setCognome(""+campi.elementAt(5));
							my_mail.setMail(""+campi.elementAt(6));
							my_mail.setData(message[i].getSentDate().toString());
							my_mail.setDescrizione(dumpPart(message[i]));
							my_mail.setContextPath(contextPath);
							long cod1 = (long)(sup*Math.random())+1;
							long cod2 = (long)(sup*Math.random())+1;
							my_mail.setCodice(String.valueOf(cod1)+String.valueOf(cod2));
							try {
								mp.executeInsert(my_mail, savefile, instance);
								message[i].setFlag(Flags.Flag.DELETED, true);
								System.out.println("Eliminata mail: "+message[i].getSubject());
							} catch (MailParserJBException e){
								//
							} catch (MessagingException e1) {
								System.out.println("Impossibile eliminare mail: "+message[i].getSubject());
								e1.printStackTrace();
							}
							savefile.removeAll(savefile);
						} else if(campi.size()==3){
							String field_temp = (String)campi.elementAt(1);
							String sid_temp = (String)campi.elementAt(2);
							if(field_temp.startsWith("sid")){
								System.out.println("Vecchio formato mail: "+message[i].getSubject());
							}else if(field_temp.matches("^[cn]\\d{1,2}$")){
								String body_temp = dumpPart(message[i]);
								int newState = Integer.parseInt(field_temp.substring(1));
								int sid = Integer.parseInt(sid_temp);
								try {
									mp.executeAppend(sid, newState, body_temp, savefile, instance);
									message[i].setFlag(Flags.Flag.DELETED, true);
									System.out.println("Eliminata mail: "+message[i].getSubject());
								} catch (MailParserJBException e){
									//
								} catch (MessagingException e1) {
									System.out.println("Impossibile eliminare mail: "+message[i].getSubject());
									e1.printStackTrace();
								}
								savefile.removeAll(savefile);
							}
						}
					} 
				}
				
				/*codice per cancellare messaggi dalla mailbox
				for(int j=0; j<message.length; j++)
				message[j].setFlag(Flags.Flag.DELETED, true);
				*/
				
			}//fine else
		} catch(MessagingException e){
			if(!e.getMessage().toLowerCase().matches(".*mailbox.*locked.*")){
				e.printStackTrace();
				throw new MailParserEJBException(e);
			}
			else
				return MAIL_LOCKED;
		} catch (SettingsJBException e) {
			e.printStackTrace();
			throw new MailParserEJBException(e);
		} finally {
				try {
					if(folder!=null && folder.isOpen())
						folder.close(true);
					store.close();
				} catch (MessagingException e1) {
					// do nothing
					e1.printStackTrace();
				}
		}
		return MAIL_OK;
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 * @param instance TODO
	 **/
	public int confirmMail(String id, String cod, String instance) throws MailParserEJBException{
		final int CONFIRM_OK = 0;
		final int NUMBER_FORMAT_ERROR = 1;
		final int NON_MATCH = 2;
		final int ALREADY_CONFIRM = 3;
		MailParserDAO mp = DAOFactory.getDAOFactoryByProperties().getMailParserDAO();
		try {
			mp.createConnection(instance);
			MailComponent mail = mp.getRow(Integer.decode(id));
			if(mail==null){
				mp.closeConnection();
				return NUMBER_FORMAT_ERROR;
			}				
			if(mail.isConferma())	{
				mp.closeConnection();
				return ALREADY_CONFIRM;
			}
			if(cod.startsWith(mail.getCodice())) {
				Problem problema = new Problem();
				ProblemValueObject pvo = new ProblemValueObject();
	            pvo.setTitolo(mail.getTitolo());
	            pvo.setDescrizione(mail.getDescrizione());
	            pvo.setStato(1);
	            pvo.setOriginatore(User.MAIL_USER);
	            pvo.setEsperto("");
	            pvo.setFlagFaq(0);
	            pvo.setCategoria(mail.getCategoria());
	            pvo.setCategoriaDescrizione(mail.getNomeCategoria());
	            pvo.setPriorita(1);
	            long id_segn = problema.newProblem(pvo, instance);
	            pvo.setIdSegnalazione(id_segn);
	            mail.setIdSegnalazione((int)id_segn);
	            mail.setConferma(true);
	            mp.executeUpdate(mail);
	            Attachment attachment = new Attachment();
	            Collection atms = attachment.fetchAttachments(Integer.parseInt("-"+id), instance);
	            for (Iterator iter = atms.iterator(); iter.hasNext();) {
					AttachmentValueObject avo = (AttachmentValueObject) iter.next();
	            	avo.setProblem(pvo);
	            	avo.setId_segnalazione(0);
	            	avo.setOriginatore(User.MAIL_USER);
	            	attachment.confirmPreinsert(avo, instance);
				}
			}else {
				mp.closeConnection();
				return NON_MATCH;
			}
			mp.closeConnection();
		} catch(MailParserDAOException e) {
			throw new MailParserEJBException(e.getMessage(), e.getUserMessage());
		} catch(NumberFormatException e) {
			try {
				mp.closeConnection();
			} catch (MailParserDAOException e1) {
				// ignore
			}
			return NUMBER_FORMAT_ERROR;
		} catch (ProblemJBException e) {
			throw new MailParserEJBException(e.getMessage());
		} catch (AttachmentJBException e) {
			e.printStackTrace();
			throw new MailParserEJBException(e.getMessage());
		} finally {
			try {
				mp.closeConnection();
			} catch (MailParserDAOException e1) {
				// ignore
			}
		}
		return CONFIRM_OK; 
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	**/
	public int TicketVerify(String id, String cod, String instance) throws MailParserEJBException{
		final int VERIFY_OK = 0;
		final int NUMBER_FORMAT_ERROR = 1;
		final int NON_MATCH = 2;
		final int ALREADY_VERIFY = 3;
		int result = -1;
		Problem problema = new Problem();
		ProblemValueObject pvo;
		try {
			StateMachine sm = StateMachine.getInstance(instance);
			Vector<String> stateNames = Settings.getId2DescriptionStateMapping(instance);
			long sid = Long.parseLong(id);
			pvo = problema.getProblemDetail(sid, instance);
			List adiacenze = sm.allowedStates(pvo.getStato(), User.UTENTE);
			System.out.println("stato iniziale:"+pvo.getStato());
			for (Iterator iter = adiacenze.iterator(); iter.hasNext();) {
				State element = (State) iter.next();
				System.out.println("stato adiacente:"+element.getId());
				if(element.getId()==StateMachine.VERIFIED){
	    	        result = VERIFY_OK;
					break;
				}
			}
			if(result == -1) result = ALREADY_VERIFY;
			else {
				EventValueObject evo = new EventValueObject();
				evo.setEventType(Event.CAMBIO_STATO);
				evo.setIdSegnalazione(sid);
				evo.setOriginatoreEvento(User.MAIL_USER);
				evo.setCategory(pvo.getCategoria());
				evo.setTitle(pvo.getTitolo());
				evo.setDescription(pvo.getDescrizione());
				evo.setCategoryDescription(pvo.getCategoriaDescrizione());
				evo.setExpertLogin(pvo.getEsperto());
				evo.setNote("Verificata da Email");
				evo.setOldState(pvo.getStato());
				evo.setOldStateDescription(stateNames.elementAt(pvo.getStato()-1));
				evo.setState(StateMachine.VERIFIED);
				evo.setStateDescription(stateNames.elementAt(StateMachine.VERIFIED-1));
				evo.setInstance(instance);
				problema.changeState(evo,new TransitionKey(pvo.getStato(),StateMachine.VERIFIED,1), instance);
			}
		} catch (ProblemJBException e) {
			e.printStackTrace();
			throw new MailParserEJBException(e.getMessage(), e.getUserMessage());
		} catch (ConditionException e) {
			e.printStackTrace();
			throw new MailParserEJBException(e.getMessage(), e.getUserMessage());
		} catch (NumberFormatException e){
			e.printStackTrace();
			result = NUMBER_FORMAT_ERROR;
		} catch (StateMachineJBException e) {
			e.printStackTrace();
			throw new MailParserEJBException(e.getMessage(), e.getUserMessage());
		} catch (SettingsJBException e) {
			e.printStackTrace();
			throw new MailParserEJBException(e.getMessage(), e.getUserMessage());
		}
		return result; 
	}

	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public long problemInsert(MailComponent mail, ProblemValueObject pvo, List<File> attachments, String instance) throws MailParserEJBException { 
		long sid = 0;
		MailParserDAO mp = DAOFactory.getDAOFactoryByProperties().getMailParserDAO();
		Problem problema = new Problem();
		try {
			mp.createConnection(instance);
			int mailId = mp.executeInsert(mail);
			pvo.setOriginatore(User.MAIL_USER);
			pvo.setStato(1);
			sid = problema.newProblem(pvo, instance);
			mail.setId(mailId);
			mail.setIdSegnalazione((int)sid);
			mail.setConferma(true);
			mp.executeUpdate(mail);
			mp.closeConnection();
			if(attachments!=null && attachments.size()>0){
				ArrayList<String> names = new ArrayList<String>();
				for (File file : attachments) {
					String[] anames = new String[names.size()];
					System.arraycopy(names.toArray(),0,anames,0,names.size());
					Attachment attachment=new Attachment();
					AttachmentValueObject avo = new AttachmentValueObject();
					String temp = Converter.uniqueAttachmentFilename(file.getName().replaceAll("'","_").replaceAll("#","_"), anames);
					avo.setNomeFile(temp);
					names.add(temp);
					avo.setDescrizione("Da WebService");
					avo.setAttachment(file);
					avo.setId_segnalazione(sid);
					attachment.insert(avo, instance);
				}
			}

		}catch (MailParserDAOException e){
			throw new MailParserEJBException(e);
		} catch (ProblemJBException e) {
			throw new MailParserEJBException(e);
		} catch (AttachmentJBException e) {
			throw new MailParserEJBException(e);
		}
		return sid;
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public void attachmentInsert(long sid, File file, String instance) throws MailParserEJBException {
		try {
			Problem pjb = new Problem();
			
			Attachment attachment=new Attachment();
			AttachmentValueObject avo = new AttachmentValueObject();
			String temp = Converter.uniqueAttachmentFilename(file.getName().replaceAll("'","_").replaceAll("#","_"), new String[0]);
			avo.setNomeFile(temp);
			avo.setDescrizione("Da WebService");
			avo.setAttachment(file);
			avo.setId_segnalazione(sid);
			avo.setProblem(pjb.getProblemDetail(sid, instance));
			avo.setOriginatore(User.MAIL_USER);
			attachment.insert(avo, instance);
		} catch (AttachmentJBException e) {
			throw new MailParserEJBException(e);
		} catch (ProblemJBException e) {
			throw new MailParserEJBException(e);
		}
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public long executeInsert(MailComponent mail, ArrayList attachments, String instance) throws MailParserEJBException { 
		long id = 0;
		MailParserDAO mp = DAOFactory.getDAOFactoryByProperties().getMailParserDAO();
		try {
			mp.createConnection(instance);
			id = mp.executeInsert(mail);
			mp.closeConnection();
			System.out.println("Presenti " + attachments.size() + " allegati");
			if(attachments.size()>0){
				ArrayList names = new ArrayList();
				for (Iterator iter = attachments.iterator(); iter.hasNext();) {
					String[] anames = new String[names.size()];
					System.arraycopy(names.toArray(),0,anames,0,names.size());
					File file = (File) iter.next();
					Attachment attachment=new Attachment();
					AttachmentValueObject avo = new AttachmentValueObject();
					String temp = Converter.uniqueAttachmentFilename(file.getName().replaceAll("'","_").replaceAll("#","_"), anames);
					avo.setNomeFile(temp);
					names.add(temp);
					avo.setDescrizione("Da MailParser");
					avo.setAttachment(file);
					avo.setId_segnalazione(-id);
					attachment.preInsert(avo, instance);
				}
			}
			String template = "";
			try {
				template = Event.getTemplate(Event.PROBLEMA_DA_MAIL, instance);
			} catch (EventJBException e2) {
				e2.printStackTrace();
				template = "<html><head></head><body>La seguente segnalazione e' stata correttamente ricevuta dal servizio di HelpDesk:<br>\n"+
						"mittente: "+mail.getCognome()+" "+mail.getNome()+"<br>\n"+
						"oggetto: "+mail.getTitolo()+"<br>\n<br>\n"+
						"Per confermarla premere il link seguente:<br>\n"+
						"<a href=\""+mail.getContextPath()+"/pub/MailConfirm?cod="+mail.getCodice()+"&id="+id+"&instance="+instance+"\">"+
						mail.getContextPath()+"/pub/MailConfirm?cod="+mail.getCodice()+"&amp;id="+id+"</a></html>";
			}
			UserValueObject uvo=new UserValueObject("","",mail.getNome(),mail.getCognome(),1,mail.getMail(),"","","y","","n","");
			HashMap map = new HashMap();
			map.put("titolo", mail.getTitolo());
			map.put("descrizione", Converter.text2HTML(mail.getDescrizione()));
			map.put("originatoreProblemaNome", uvo.getFirstName()+" "+uvo.getFamilyName());
			map.put("originatoreProblemaEmail", uvo.getEmail());
			map.put("contextPath",  Settings.getProperty("it.oil.contextPath", instance));
			map.put("codice", mail.getCodice());
			map.put("idSegnalazione", id);
			map.put("categoria", mail.getNomeCategoria());
			map.put("data_odierna", (new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN)).format(new Date()));
			map.put("instance", instance);
			//map.put("url", mail.getContextPath()+"/pub/MailConfirm?cod="+mail.getCodice()+"&id="+id+"&instance="+instance);
			Collection destinatari = new ArrayList();
			destinatari.add(uvo);
			MailItem mailitem;
			mailitem = MessageComposer.compose(map, destinatari, template, instance);
			MailManagement mm = new MailManagement();
			mm.sendMail(mailitem);
		} catch(MailParserDAOException e) {
			e.printStackTrace();
			throw new MailParserEJBException(e);
		} catch (AttachmentJBException e) {
			e.printStackTrace();
			throw new MailParserEJBException(e);
		} catch (SettingsJBException e) {
			e.printStackTrace();
			throw new MailParserEJBException(e);
		}
		return id;
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public void executeAppend(long sid, int newState, String body, ArrayList attachments, String instance) throws MailParserEJBException {
		Problem problema = new Problem();
		try {
			Vector<String> stateNames = Settings.getId2DescriptionStateMapping(instance);
			StateMachine sm = StateMachine.getInstance(instance);
			ProblemValueObject pvo = problema.getProblemDetail(sid, instance);
        	EventValueObject evo = new EventValueObject();
        	evo.setInstance(instance);
	        evo.setEventType(Event.AGGIUNTA_NOTA);
			List adiacenze = sm.allowedStates(pvo.getStato(), User.UTENTE);
			for (Iterator iter = adiacenze.iterator(); iter.hasNext();) {
				State element = (State) iter.next();
				if(element.getId()==newState){
	    	        evo.setEventType(Event.CAMBIO_STATO);
					break;
				}
			}
	        evo.setIdSegnalazione(sid);
	        evo.setOriginatoreEvento(User.MAIL_USER);
			evo.setCategory(pvo.getCategoria());
			evo.setTitle(pvo.getTitolo());
			evo.setDescription(pvo.getDescrizione());
			evo.setCategoryDescription(pvo.getCategoriaDescrizione());
	        evo.setNote(body);
			if(evo.getEventType()==Event.CAMBIO_STATO){
    	        evo.setExpertLogin(pvo.getEsperto());
    	        evo.setOldState(pvo.getStato());
    	        evo.setOldStateDescription(stateNames.elementAt(pvo.getStato()-1));
    	        evo.setState(newState);
    	        evo.setStateDescription(stateNames.elementAt(newState-1));
    	        problema.changeState(evo,new TransitionKey(pvo.getStato(),newState,User.UTENTE), instance);
				
			}else
				problema.addNote(evo);
	        if(attachments!=null && attachments.size()>0){
	        	System.out.println("Presenti " + attachments.size() + " allegati");
	        	ArrayList names = new ArrayList();
				for (Iterator iter = attachments.iterator(); iter.hasNext();) {
					String[] anames = new String[names.size()];
					System.arraycopy(names.toArray(),0,anames,0,names.size());
					File file = (File) iter.next();
					Attachment attachment=new Attachment();
	             	AttachmentValueObject avo = new AttachmentValueObject();
					String temp = Converter.uniqueAttachmentFilename(file.getName().replaceAll("'","_").replaceAll("#","_"), anames);
	             	avo.setProblem(pvo);
	             	avo.setOriginatore(User.MAIL_USER);
					avo.setNomeFile(temp);
					names.add(temp);
	             	avo.setDescrizione("Da MailParser");
	             	avo.setAttachment(file);
					avo.setId_segnalazione(sid);
					attachment.insert(avo, instance);
				}
			}					    	        
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new MailParserEJBException(e);
		}
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public void changeState(long sid, int newState, String body, String originatore, String instance) throws MailParserEJBException {
		Problem problema = new Problem();
		User user = new User();
		try {
			Vector<String> stateNames = Settings.getId2DescriptionStateMapping(instance);
			StateMachine sm = StateMachine.getInstance(instance);
			user.setLogin(originatore);
			UserValueObject uvo = user.getDetail(instance);
			ProblemValueObject pvo = problema.getProblemDetail(sid, instance);
			EventValueObject evo = new EventValueObject();
			evo.setInstance(instance);
			evo.setEventType(Event.AGGIUNTA_NOTA);
			List adiacenze = sm.allowedStates(pvo.getStato(), uvo.getProfile());
			for (Iterator iter = adiacenze.iterator(); iter.hasNext();) {
				State element = (State) iter.next();
				if(element.getId()==newState){
					evo.setEventType(Event.CAMBIO_STATO);
					break;
				}
			}
			evo.setIdSegnalazione(sid);
			evo.setOriginatoreEvento(originatore);
			evo.setOriginatoreEventoDescrizione(uvo.getFamilyName()+" "+uvo.getFirstName());
			evo.setCategory(pvo.getCategoria());
			evo.setTitle(pvo.getTitolo());
			evo.setDescription(pvo.getDescrizione());
			evo.setCategoryDescription(pvo.getCategoriaDescrizione());
			evo.setNote(body);
			if(evo.getEventType()==Event.CAMBIO_STATO){
				evo.setExpertLogin(pvo.getEsperto());
				evo.setOldState(pvo.getStato());
				evo.setOldStateDescription(stateNames.elementAt(pvo.getStato()-1));
				evo.setState(newState);
				evo.setStateDescription(stateNames.elementAt(newState-1));
				problema.changeState(evo,new TransitionKey(pvo.getStato(),newState,uvo.getProfile()), instance);
				
			}else
				problema.addNote(evo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MailParserEJBException(e);
		}
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	 * @ejb.transaction
	 *  type="Required"
	 **/
	public void changeCategory(long sid, int category, String categoryName, String body, String originatore, String instance) throws MailParserEJBException {
		EventValueObject evo = new EventValueObject();
		Problem problema = new Problem();
		User user = new User();
		try {
			user.setLogin(originatore);
			UserValueObject uvo = user.getDetail(instance);
			ProblemValueObject pvo = problema.getProblemDetail(sid, instance);
			evo.setTitle(pvo.getTitolo());
			evo.setDescription(pvo.getDescrizione());
			evo.setCategory(category);
			evo.setCategoryDescription(categoryName);
			evo.setState(pvo.getStato());
			evo.setEventType(Event.CAMBIO_CATEGORIA);
			evo.setIdSegnalazione(sid);
			evo.setInstance(instance);
			evo.setNote(body!=null?body:"");
			evo.setOriginatoreEvento(originatore);
			evo.setOriginatoreEventoDescrizione(uvo.getFamilyName()+" "+uvo.getFirstName());
			if (category!=pvo.getCategoria())	//la categoria è cambiata
				problema.changeCategory(evo);
			else
				problema.addNote(evo);
		} catch (ProblemJBException e) {
			throw new MailParserEJBException(e);
		} catch (UserJBException e) {
			throw new MailParserEJBException(e);
		}
	}
}