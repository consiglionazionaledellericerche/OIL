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

package it.cnr.helpdesk.StateMachineManagement.javabeans;

import it.cnr.helpdesk.StateMachineManagement.conditions.Condition;
import it.cnr.helpdesk.StateMachineManagement.exceptions.ConditionException;
import it.cnr.helpdesk.StateMachineManagement.exceptions.StateMachineJBException;
import it.cnr.helpdesk.StateMachineManagement.tasks.Task;
import it.cnr.helpdesk.StateMachineManagement.valueobjects.State;
import it.cnr.helpdesk.javabeans.HelpDeskJB;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Roberto Puccinelli
 */
public class Transition extends HelpDeskJB {

public Transition(int profilo, String nomeProfilo, int id, State origine, State destinazione) {
		this.profilo=profilo;
		this.nomeProfilo=nomeProfilo;
		this.id=id;
		this.origine=origine;
		this.destinazione=destinazione;
	}

	private int id;

    private State origine;


    private State destinazione;


    private int profilo;
    
    private String nomeProfilo;
    
    /**
     * <p>Rappresenta la lista di Task associati alla transizione, che vanno eseguiti dopo 
     * aver effettuato le  scritture su DB del relativo cambio di stato</p>
     */
    public ArrayList normalTasks=new ArrayList();
    
    /**
     * <p>Rappresenta la lista di Task associati alla transizione, che vanno eseguiti in maniera transazionale
     *    con le scritture su DB del relativo cambio di stato </p>
     */
    public ArrayList transTasks=new ArrayList();
    
    public ArrayList preConditions=new ArrayList();
    
    public ArrayList postConditions=new ArrayList();


	public State getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(State destinazione) {
		this.destinazione = destinazione;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeProfilo() {
		return nomeProfilo;
	}

	public void setNomeProfilo(String nomeProfilo) {
		this.nomeProfilo = nomeProfilo;
	}

	public State getOrigine() {
		return origine;
	}

	public void setOrigine(State origine) {
		this.origine = origine;
	}

	public ArrayList getPostConditions() {
		return postConditions;
	}

	public void setPostConditions(ArrayList post_conditions) {
		this.postConditions = post_conditions;
	}

	public ArrayList getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(ArrayList pre_conditions) {
		this.preConditions = pre_conditions;
	}

	public int getProfilo() {
		return profilo;
	}

	public void setProfilo(int profilo) {
		this.profilo = profilo;
	}
	
	public ArrayList getNormalTasks() {
		return normalTasks;
	}

	public void setNormalTasks(ArrayList normalTasks) {
		this.normalTasks = normalTasks;
	}

	public ArrayList getTransTasks() {
		return transTasks;
	}

	public void setTransTasks(ArrayList transTasks) {
		this.transTasks = transTasks;
	}

	public void addNormalTask(Task task) {
		this.normalTasks.add(task);
	}

	public void addTransTask(Task task) {
		this.transTasks.add(task);
	}
		
	public void addPreCondition(Condition condition){
		this.preConditions.add(condition);
	}
	
	public void addPostCondition(Condition condition){
		this.postConditions.add(condition);
	}
	
	public void checkPreConditions (Object token) throws StateMachineJBException, ConditionException {
		Iterator it=this.preConditions.iterator();
		while (it.hasNext()){
			Condition c = (Condition) it.next();
			c.checkCondition(token);
		}
	}
	
	public void checkPostConditions (Object token) throws StateMachineJBException, ConditionException {
		Iterator it=this.postConditions.iterator();
		while (it.hasNext()){
			Condition c = (Condition) it.next();
			c.checkCondition(token);
		}
	}
	
/*	public void doTasks(Problem p) throws StateMachineJBException {
		Iterator it = this.tasks.iterator();
		while (it.hasNext()){
			Task t = (Task) it.next();
			
			t.doAction(p);
		}
	} */
 }
