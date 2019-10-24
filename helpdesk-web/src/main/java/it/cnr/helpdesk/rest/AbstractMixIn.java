package it.cnr.helpdesk.rest;

import it.cnr.helpdesk.util.SearchingCollection;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * classe MixIn per l'eliminazione dall'output della proprietà searchingCollection
 * 
 * @author Aurelio D'Amico
 * @version 1.0 [Feb 5, 2015]
 */
abstract class AbstractMixIn {
	@JsonIgnore abstract SearchingCollection getSearchingCollection();
}
