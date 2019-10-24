package it.cnr.helpdesk.MailParserManagement.valueobjects;

/**
 * @author Marco Trischitta
 * @author aldo stentella
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MailComponent {
	private int id;
	private String mail;
	private String titolo;
	private int categoria;
	private String nomeCategoria;
	private String nome;
	private String cognome;
	private String descrizione;
	private boolean conferma;
	private String data;
	private String codice;
	private int idSegnalazione;
	private String contextPath;
	
	
	public int getId() {
		return this.id;
	}
	public String getMail() {
		return this.mail;
	}
	
	public boolean isConferma() {
		return conferma;
	}
	public void setConferma(boolean conferma) {
		this.conferma = conferma;
	}
	public int getIdSegnalazione() {
		return idSegnalazione;
	}
	public void setIdSegnalazione(int idSegnalazione) {
		this.idSegnalazione = idSegnalazione;
	}
	public String getTitolo() {
		return this.titolo;
	}
	public String getTitolo2SQL() {
		return clean2SQL(titolo);
	}
	public int getCategoria() {
		return this.categoria;
	}
	
	public String getNome() {
		return this.nome;
	}
	public String getNome2SQL()
	{
		return clean2SQL(nome);
	}
	public String getCognome() {
		return this.cognome;
	}
	public String getCognome2SQL()
	{
		return clean2SQL(cognome);
	}
	public String getDescrizione() {
		return this.descrizione;
	}
	public String getDescrizione2SQL()
	{
		return clean2SQL(descrizione);
	}  
	public String getData() {
		return this.data;
	}
	
	public String getCodice() {
		return this.codice;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	private String clean2SQL(String s){
		return s.replaceAll("\\\\","").replaceAll("'","''");
	}
}