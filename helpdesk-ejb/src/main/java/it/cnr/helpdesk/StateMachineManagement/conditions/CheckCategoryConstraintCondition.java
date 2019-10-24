package it.cnr.helpdesk.StateMachineManagement.conditions;

import java.util.StringTokenizer;

import it.cnr.helpdesk.ApplicationSettingsManagement.javabeans.Settings;
import it.cnr.helpdesk.ProblemManagement.valueobjects.EventValueObject;
import it.cnr.helpdesk.StateMachineManagement.exceptions.ConditionException;
import it.cnr.helpdesk.exceptions.SettingsJBException;

/**
 * 
 * @author aldo stentella
 *
 */

public class CheckCategoryConstraintCondition implements Condition {

	/**
	 * @deprecated
	 * @return false
	 */
	public boolean checkCondition() {
		return false;
	}

	@Override
	public void checkCondition(Object token) throws ConditionException {
		EventValueObject evo = (EventValueObject)token;
		String categories;
		try {
			categories = Settings.getProperty("it.cnr.oil.transition.constraint."+evo.getOldState()+"."+evo.getState(), evo.getInstance());
			if(categories==null)
				return;
		} catch (SettingsJBException e) {
			e.printStackTrace();
			return;
		}
		StringTokenizer tcategories = new StringTokenizer(categories, ";");
		while(tcategories.hasMoreTokens()){
			String temp = tcategories.nextToken();
			if(evo.getCategory() == Integer.parseInt(temp))
				return;
		}
		String[] args = {evo.getStateDescription(), evo.getCategoryDescription()};
		throw new ConditionException("exceptions.TransitionNotAllowed", args );
	}

}
