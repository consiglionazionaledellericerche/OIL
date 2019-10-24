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

 

package it.cnr.helpdesk.StatisticsManagement.valueobjects;

import it.cnr.helpdesk.valueobjects.ComponentValueObject;
import java.io.Serializable;

public class StatisticsDTO extends ComponentValueObject
    implements Serializable
{

    public StatisticsDTO()
    {
        endDate = "";
        helpdeskUserID = "";
        startDate = "";
    }

    public void setHelpdeskUserID(String s)
    {
        helpdeskUserID = s;
    }

    public String getHelpdeskUserID()
    {
        return helpdeskUserID;
    }

    public void setHelpdeskUserProfile(int i)
    {
        helpdeskUserProfile = i;
    }

    public int getHelpdeskUserProfile()
    {
        return helpdeskUserProfile;
    }

    public void setStartDate(String s)
    {
        startDate = s;
    }

    public void setStatisticsType(int i)
    {
        statisticsType = i;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setEndDate(String s)
    {
        endDate = s;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setSamplingInterval(int i)
    {
        samplingInterval = i;
    }

    public int getSamplingInterval()
    {
        return samplingInterval;
    }

    public int getStatisticsType()
    {
        return statisticsType;
    }

    private String endDate;
    private String helpdeskUserID;
    private int samplingInterval;
    private String startDate;
    private int helpdeskUserProfile;
    private int statisticsType;
}