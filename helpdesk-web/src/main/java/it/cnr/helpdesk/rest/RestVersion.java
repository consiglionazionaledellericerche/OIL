package it.cnr.helpdesk.rest;

import java.io.Serializable;

/**
 * Log del versionamento del servizio REST
 * @author Aurelio D'Amico
 * [2015-01-14] gestione categoria (15,19,20,21)
 * [2015-02-04] gestione sicurezza (05,06,09)
 * [2015-02-16] gestione esperti (17,18,19,20)
 * [2015-02-26] gestione autorizzazioni (27)
 * [2015-03-02] collaudo
 * [2015-06-25] gestione utenze multiple
 * [2015-06-25] gestione utenze multiple
 * [2015-07-01] ritorno categoria id dopo creazione e aggiornamento
 * [2015-07-06] limite delle categorie da elencare
 * [2016-04-15] gestione segnalazioni degli utenti esterni (18,19,20,21,22,26,28)
 * [2016-04-15] gestione segnalazioni degli utenti esterni (18,19,20,21,22,26,28)
 * [2016-05-04] gestione modifica categoria (05)
*/
public class RestVersion implements Serializable {
	private static final long serialVersionUID = 1L;
	public static String versione = "$Revision: 104 $";
	public static String getVersione() {
		if (versione == null || versione.trim().length() == 0)
			return null;
		String temp = versione.replace('$', ' ');
		int pos = temp.indexOf(':');
		if (pos < 0)
			return null;
		return temp.substring(pos + 1).trim();
	}
}
