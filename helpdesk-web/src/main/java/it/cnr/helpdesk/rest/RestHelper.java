package it.cnr.helpdesk.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.util.Base64;

import it.cnr.helpdesk.CategoryManagement.valueobjects.CategoryValueObject;
import it.cnr.helpdesk.LdapManagemet.javabeans.Ldap;
import it.cnr.helpdesk.MailParserManagement.javabeans.MailParser;
import it.cnr.helpdesk.MailParserManagement.valueobjects.MailComponent;
import it.cnr.helpdesk.ProblemManagement.javabeans.Problem;
import it.cnr.helpdesk.ProblemManagement.valueobjects.ProblemValueObject;
import it.cnr.helpdesk.UserManagement.valueobjects.Profile;
import it.cnr.helpdesk.UserManagement.valueobjects.UserValueObject;
import it.cnr.helpdesk.dao.CategoryDAO;
import it.cnr.helpdesk.dao.DAOFactory;
import it.cnr.helpdesk.dao.ProblemDAO;
import it.cnr.helpdesk.dao.StateMachineDAO;
import it.cnr.helpdesk.dao.UserDAO;
import it.cnr.helpdesk.rest.model.ExternalProblem;
import it.cnr.helpdesk.util.Converter;
import it.cnr.helpdesk.util.PageByPageIterator;
import it.cnr.helpdesk.util.PageByPageIteratorImpl;
import it.cnr.helpdesk.valueobjects.ComponentValueObject;


@SuppressWarnings("unchecked")
public class RestHelper {
	private static RestHelper instance;

	private RestHelper() {
		// private constructor
	}
	public static synchronized  RestHelper getInstance() {
		if (instance==null) instance = new RestHelper();
		return instance;
	}
	public String getAuthorization(final String encoded) throws Exception {
		if (encoded == null || encoded.isEmpty()) return null;
		String encodedUserPassword = encoded.replaceFirst(RestNames.AUTHENTICATION_SCHEME, "");
		return new String(Base64.decode(encodedUserPassword));
	}
	//===============================================================
	// Gestione Categorie
	//===============================================================
	public List<CategoryValueObject> getCategorie(String instance, String enabled) throws Exception {
        Collection<CategoryValueObject> items = null;
        CategoryDAO dao = DAOFactory.getDAOFactoryByProperties().getCategoryDAO();
        try {
	        dao.createConnection(instance);
	        if ("n".equals(enabled)) {
	        	items = dao.allCategoriesList();
	        } else {
	        	items = dao.allEnabledCategoriesList();
	        }
        } finally {
        	dao.closeConnection();
        }
        return (List<CategoryValueObject>) getList(items);		
	}
	public List<CategoryValueObject> getSubCategorie(String instance, Integer id) throws Exception {
        Collection<CategoryValueObject> items = null;
        CategoryDAO dao = DAOFactory.getDAOFactoryByProperties().getCategoryDAO();
        try {
	        dao.createConnection(instance);
	        if(dao.getCategoryDetail(id)==null) return null;
	        items = dao.allCategoriesList(id);
        } finally {
        	dao.closeConnection();
        }
        return (List<CategoryValueObject>) getList(items);		
	}
	public boolean deleteCategoria(String instance, Integer id) throws Exception {
        CategoryDAO dao = DAOFactory.getDAOFactoryByProperties().getCategoryDAO();
        try {
	        dao.createConnection(instance);
	        if(dao.getCategoryDetail(id)==null) return false;
	        dao.setIdCategoria(id);
	        dao.disable();
	        return true;
        } finally {
        	dao.closeConnection();
        }
	}
	public int saveCategoria(String instance, CategoryValueObject x) throws Exception {
        boolean isNewItem = false;
        int id = x.getId();
        CategoryDAO dao = DAOFactory.getDAOFactoryByProperties().getCategoryDAO();
        try {
	        dao.createConnection(instance);
	        isNewItem = (dao.getCategoryDetail(x.getId())==null);
	        dao.setIdCategoria(x.getId());
	        dao.setIdPadre(x.getIdPadre());
	        dao.setNome(x.getNome());
	        dao.setDescrizione(x.getDescrizione());
	        if (isNewItem)
	        	id = dao.executeInsert();
	        else
	        	dao.executeUpdate();
        } finally {
        	dao.closeConnection();
        }
        return id;
	}
	//===============================================================
	// Gestione Utenti (esperti)
	//===============================================================
	public List<UserValueObject> getUtenti(String instance, int role) throws Exception {
        Collection<UserValueObject> items = null;
        UserDAO dao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        try {
	        dao.createConnection(instance);
	        UserValueObject user = new UserValueObject();
	        user.setProfile(role);
	        PageByPageIterator pbpi = dao.allUsers(new PageByPageIteratorImpl(0, 0, null, 0), user);
	        items = pbpi.getCollection();
        } finally {
        	dao.closeConnection();
        }
        return (List<UserValueObject>) getList(items);		
	}
	public UserValueObject getUtente(String instance, String username) throws Exception {
		UserValueObject user = null;
        UserDAO dao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        try {
	        dao.createConnection(instance);
	        dao.setLogin(username);
	        user = dao.getUserDetail();
        } finally {
        	dao.closeConnection();
        }
        return (UserValueObject) clearObject(user);
	}
	public boolean saveUtente(String instance, UserValueObject x) throws Exception {
        boolean isNewItem = false;
        UserDAO dao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        try {
	        dao.createConnection(instance);
	        dao.setLogin(x.getLogin());
	        isNewItem = (dao.getUserDetail()==null);
	        dao.setEmail(x.getEmail());
	        dao.setFamilyName(x.getFamilyName());
	        dao.setFirstName(x.getFirstName());
	        dao.setStruttura(x.getStruttura());
	        dao.setTelefono(x.getTelefono());
	        dao.setProfile(x.getProfile());
	        dao.setPassword(x.getPassword());
	        dao.setProfile(x.getProfile());
	        dao.setMailStop(x.getMailStop()==null ? "y" : x.getMailStop());
	        if (isNewItem)
	        	dao.executeInsert();
	        else
	        	dao.executeUpdate();
        } finally {
        	dao.closeConnection();
        }
		Ldap ldap = new Ldap();
		if (isNewItem) {
			ldap.allowUser(x.getLogin(), x.getLogin(), instance);
		}
        return isNewItem;
	}
	public int deleteUtente(String instance, String uid) throws Exception {
        UserDAO dao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        try {
	        dao.createConnection(instance);
	        dao.setLogin(uid);
	        if (dao.getUserDetail()==null) return -1;
			if (!isUtenteDeletable(instance, uid)) return 1;
	        dao.disable();
	        return 0;
        } finally {
        	dao.closeConnection();
        }
	}
	private boolean isUtenteDeletable(String instance, String uid) throws Exception {
		Collection<?> list = null;		
        ProblemDAO dao = DAOFactory.getDAOFactoryByProperties().getProblemDAO();
        try {
	        dao.createConnection(instance);
	        dao.setEsperto(uid);
	        list = dao.getReassignableExpertProblems();
        } finally {
        	dao.closeConnection();
        }
        return list==null || list.isEmpty() ? true : false;	       
	}
	//===============================================================
	// Associazione Utenti & Categorie
	//===============================================================
	public List<UserValueObject> getUsersByCategory(String instance, Integer id) throws Exception {
        Collection<UserValueObject> items = null;
        UserDAO dao = DAOFactory.getDAOFactoryByProperties().getUserDAO();
        try {
	        dao.createConnection(instance);
	        items = dao.allCategoryExperts(id);
        } finally {
        	dao.closeConnection();
        }
        return (List<UserValueObject>) getList(items);		
	}
	public List<CategoryValueObject> getCategoriesByUser(String instance, String uid) throws Exception {
        Collection<CategoryValueObject> items = null;
        CategoryDAO dao = DAOFactory.getDAOFactoryByProperties().getCategoryDAO();
        try {
	        dao.createConnection(instance);
	        items = dao.allExpertCategoriesList(uid);
        } finally {
        	dao.closeConnection();
        }
        return (List<CategoryValueObject>) getList(items);		
	}
	public boolean deleteExpert(String instance, Integer id, String uid) throws Exception {
        CategoryDAO dao = DAOFactory.getDAOFactoryByProperties().getCategoryDAO();
        try {
        	List<UserValueObject> list = getUsersByCategory(instance, id);
        	if (list!=null) {
	        	for (UserValueObject x : list) {
					if (x.getLogin().equals(uid)) {
				        dao.createConnection(instance);
				        dao.setIdCategoria(id);
				        dao.removeFromExpert(uid);
				        return true;					
					}
				}
        	}
        	return false;
        } finally {
        	dao.closeConnection();
        }
	}
	public boolean saveExpert(String instance, Integer id, String uid) throws Exception {
		// l'utente deve esistere
		if(getUtente(instance, uid)==null) return false;
        CategoryDAO dao = DAOFactory.getDAOFactoryByProperties().getCategoryDAO();
        try {        	
	        dao.createConnection(instance);
			// la categoria deve esistere
	        if(dao.getCategoryDetail(id)==null) return false;
	        dao.getCategoryDetail(id);
	        // l'associazione deve essere assente
	        Collection<CategoryValueObject> items = dao.allExpertCategoriesList(uid);
	        if (! (items==null || items.isEmpty()) ) {
	        	for (CategoryValueObject x : items) {
	        		if (x.getId()==id.intValue()) return true;
	        	}
	        }
	        // tutto ok: crea associazione 
	        dao.setIdCategoria(id);
	        dao.assignToExpert(uid);
	        return true;
        } finally {
        	dao.closeConnection();
        }
	}
	//===============================================================
	// Tools
	//===============================================================	
	private List<?> getList(Collection<?> items) {
		if (items==null || items.isEmpty()) return null;
		List<Object> list = new ArrayList<Object>(items);
		for (Object x : list) {
			clearObject(x);
		}
		return list;
	}
	private Object clearObject(Object x) {
		if (x instanceof ComponentValueObject)
			((ComponentValueObject)x).setSearchingCollection(null);
		if (x instanceof UserValueObject)
			((UserValueObject)x).setPassword(null);
		return x;
	}
	public List<Profile> getRoles(String instance) throws Exception {
		StateMachineDAO dao = DAOFactory.getDAOFactoryByProperties().getStateMachineDAO();
        try {
	        dao.createConnection(instance);
	        return dao.readProfiles();
        } finally {
        	dao.closeConnection();
        }		
	}
	public int getRoleId(String instance, String name) throws Exception {
        List<Profile> roles = getRoles(instance);
        if (roles!=null) {
	        for (Profile x : roles) {
				if (x.getNome().equals(name)) 
					return x.getId();
			}
        }
		return -1;
	}
	public String getRoleName(String instance, int id) throws Exception {
        List<Profile> roles = getRoles(instance);
        if (roles!=null) {
	        for (Profile x : roles) {
				if (x.getId()==id) 
					return x.getNome();
			}
        }
		return null;
	}
	public long saveExternalProblem(String hd, ExternalProblem x, boolean isNew) throws Exception {
		return isNew ? newExternalProblem(hd, x) : changeState(hd, x);			
	}
	private long newExternalProblem(String hd, ExternalProblem x) throws Exception {
		verificaExternalProblem(ExternalProblem.NEW, x);
		MailParser parser = new MailParser();
		if(x.getConfirmRequested().startsWith("y")) {
			MailComponent mail = new MailComponent();
			mail.setTitolo(x.getTitolo());
			mail.setCategoria(x.getCategoria());
			mail.setNomeCategoria(x.getCategoriaDescrizione());
			mail.setNome(x.getFirstName());
			mail.setCognome(x.getFamilyName());
			mail.setMail(x.getEmail());
			mail.setData((new Date()).toString());
			mail.setDescrizione(x.getDescrizione());
			mail.setContextPath("");				//deprecato, valore preso dalla tabella main_settings
			long sup = 999999999;
			long cod1 = (long)(sup*Math.random())+1;
			long cod2 = (long)(sup*Math.random())+1;
			mail.setCodice(String.valueOf(cod1)+String.valueOf(cod2));
			return -parser.executeInsert(mail, new ArrayList(0), hd);
		}else {
			UserValueObject uvo = new UserValueObject();
			uvo.setFirstName(x.getFirstName());
			uvo.setFamilyName(x.getFamilyName());
			uvo.setEmail(x.getEmail());
			ProblemValueObject pvo = new ProblemValueObject();
			pvo.setTitolo(x.getTitolo());
			pvo.setDescrizione(x.getDescrizione());
			pvo.setCategoria(x.getCategoria());
			pvo.setCategoriaDescrizione(x.getCategoriaDescrizione());
			pvo.setPriorita(ExternalProblem.PRIORITY);
			return parser.problemInsert(uvo, pvo, null, hd);
		}
	}
	private long changeState(String hd, ExternalProblem x) throws Exception {
		if (StringUtils.isNotBlank(x.getAllegato())) return addAllegato(hd, x);
		if (x.getCategoria() != null) return changeCategoria(hd, x);
		verificaExternalProblem(ExternalProblem.STT, x);
		Problem problem = new Problem();
		ProblemValueObject pvo = problem.getProblemDetail(x.getIdSegnalazione(), hd);
		if (pvo==null) return 0;
		MailParser parser = new MailParser();
		parser.changeState(
				x.getIdSegnalazione(), 
				x.getStato(), 
				x.getNota(), 
				x.getLogin(), 
				hd);
		return Long.MAX_VALUE;
	}
	private long changeCategoria(String hd, ExternalProblem x) throws Exception {
		verificaExternalProblem(ExternalProblem.CTG, x);
		Problem problem = new Problem();
		ProblemValueObject pvo = problem.getProblemDetail(x.getIdSegnalazione(), hd);
		if (pvo==null) return -1;
		MailParser parser = new MailParser();
		parser.changeCategory(
				x.getIdSegnalazione(), 
				x.getCategoria(), 
				x.getCategoriaDescrizione(), 
				x.getNota(), 
				x.getLogin(),
				hd);
		return 0;
	}
	private void verificaExternalProblem(String[] fields, ExternalProblem x) throws Exception {
		for (int i = 0; i < fields.length; i++) {
			if (isBlankOrNull(BeanUtils.getProperty(x, fields[i])))
				throw new BadRequestException(getBadRequestMessage(fields[i]));
		}		
	}
	private long addAllegato(String hd, ExternalProblem x) throws Exception {
		verificaExternalProblem(ExternalProblem.ATT, x);
		byte[] bytes = Base64.decode(x.getAllegato());
		File file = writeFile(bytes,getFileTemp(x.getDescrizione()));
		MailParser parser = new MailParser();
		parser.attachmentInsert(x.getIdSegnalazione(), file, hd);
//		try {
//			file.delete();
//		} catch (Exception e) {
//			file.deleteOnExit();
//		}
		return 0;
	}
	public boolean addAllegato(String hd, MultipartFormDataInput x, Integer sid) throws Exception {
		String filename = "";
		if(sid>0) {
			Problem problem = new Problem();
			ProblemValueObject pvo = problem.getProblemDetail(sid, hd);
			if (pvo==null) return false;
			Map<String, List<InputPart>> uploadForm = x.getFormDataMap();
			List<InputPart> inputParts = uploadForm.get(ExternalProblem.ALLEGATO);
			for (InputPart inputPart : inputParts) {
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				filename = getFileName(header);
				InputStream inputStream = inputPart.getBody(InputStream.class,null);
				byte [] bytes = IOUtils.toByteArray(inputStream);
				File file = writeFile(bytes,getFileTemp(filename));
				MailParser parser = new MailParser();
				parser.attachmentInsert(sid, file, hd);
//			try {
//				file.delete();
//			} catch (Exception e) {
//				file.deleteOnExit();
//			}
			}
		} else {
			Map<String, List<InputPart>> uploadForm = x.getFormDataMap();
			List<InputPart> inputParts = uploadForm.get(ExternalProblem.ALLEGATO);
			ArrayList<String> names = new ArrayList<String>();
			for (InputPart inputPart : inputParts) {
				String[] anames = new String[names.size()];
				System.arraycopy(names.toArray(),0,anames,0,names.size());
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				filename = getFileName(header);
				String temp = Converter.uniqueAttachmentFilename(filename.replaceAll("'","_").replaceAll("#","_"), anames);
				names.add(temp);
				InputStream inputStream = inputPart.getBody(InputStream.class,null);
				byte [] bytes = IOUtils.toByteArray(inputStream);
				File file = writeFile(bytes,getFileTemp(temp));
				MailParser parser = new MailParser();
				parser.attachmentPreinsert(sid, file, hd);
			}
		}
		return true;
    }
 
	// Utility methods
	
    private File writeFile(byte[] content, String filename) throws Exception {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
        return file;
    }

    private String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

    private String getFileTemp(String filename) throws Exception {
    	String path = System.getProperty("jboss.server.temp.dir");
    	if (StringUtils.isBlank(path)) path = System.getProperty("java.io.tmpdir");
    	String base = FilenameUtils.getBaseName(filename);
    	String ext = FilenameUtils.getExtension(filename);
    	String name = base + "." + ext;
        File f = new File(path,name);
        int i = 0;
        while (f.exists()) {
            name = base + "[" + Integer.toString(++i) + "]." + ext;
            f = new File(path,name);
        }
    	return f.getAbsolutePath();
	}

    private String getBadRequestMessage(String attributo) {		
		return "Attributo " + attributo + " assente.";
	}
	private boolean isBlankOrNull(String s) {
		return StringUtils.isBlank(s);
	}
}
