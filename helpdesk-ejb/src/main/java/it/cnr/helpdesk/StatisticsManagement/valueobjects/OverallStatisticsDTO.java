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

 
// Source File Name:   OverallStatisticsDTO.java

package it.cnr.helpdesk.StatisticsManagement.valueobjects;

import java.io.*;

public class OverallStatisticsDTO
    implements Externalizable
{

    public OverallStatisticsDTO()
    {
    }

    public OverallStatisticsDTO(int problemCount, double minResponseTime, double maxResponseTime, double avgResponseTime)
    {
        this.problemCount = problemCount;
        this.minResponseTime = minResponseTime;
        this.maxResponseTime = maxResponseTime;
        this.avgResponseTime = avgResponseTime;
    }

    public void setProblemCount(int problemCount)
    {
        this.problemCount = problemCount;
    }

    public void setMinResponseTime(double minResponseTime)
    {
        this.minResponseTime = minResponseTime;
    }

    public void setMaxResponseTime(double maxResponseTime)
    {
        this.maxResponseTime = maxResponseTime;
    }

    public void setAvgResponseTime(double avgResponseTime)
    {
        this.avgResponseTime = avgResponseTime;
    }

    public int getProblemCount()
    {
        return problemCount;
    }

    public double getMinResponseTime()
    {
        return minResponseTime;
    }

    public double getMaxResponseTime()
    {
        return maxResponseTime;
    }

    public double getAvgResponseTime()
    {
        return avgResponseTime;
    }

    public void writeExternal(ObjectOutput objectoutput)
        throws IOException
    {
    }

    public void readExternal(ObjectInput objectinput)
        throws IOException, ClassNotFoundException
    {
    }

    private int problemCount;
    private double minResponseTime;
    private double maxResponseTime;
    private double avgResponseTime;
}