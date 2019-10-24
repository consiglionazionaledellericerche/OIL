package it.cnr.helpdesk.rest;

public interface RestNames {
	// authentication
	String AUTENTHICATE = "WWW-Authenticate";
	String AUTHENTICATION_SCHEME = "Basic ";
	String AUTHENTICATION_REALM = "realm=\"helpdesk\"";
	String AUTHORIZATION_PROPERTY = "Authorization";
	
	// istanze
	String INSTANCE_NAMES = "it.cnr.oil.instances.names";
	
	// costanti
	String HD = "hd";
	String ID = "id";
	String UID = "uid";
	String CATG = "catg";
	String USER = "user";
	String UCAT = "ucat";
	String PROB = "prob";
	String PEST = "pest";
	String API = "api";
	
	// percorsi
	String PHD = "{" + HD + "}";
	String PID = "{" + ID + "}";
	String PUID = "{" + UID + "}";
	String PHDID = PHD + "/" + PID;
	String PHDUID = PHD + "/" + PUID;
	String PHDIDUID = PHD + "/" + PID + "/" + PUID;
	String PCATG = "/" + CATG;
	String PUSER = "/" + USER;
	String PUCAT = "/" + UCAT;
	String PPROB = "/" + PROB;
	String PPEST = "/" + PEST;
	String PAPI = "/" + API;
}
