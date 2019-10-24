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

 

package it.cnr.helpdesk.charts.confobjects;

import com.jrefinery.data.Dataset;
import java.awt.Paint;

public interface ChartConf
{

    public abstract void setTitle(String s);

    public abstract void setDataset(Dataset dataset);

    public abstract void setPaints(Paint apaint[]);

    public abstract void setBgPaint(Paint paint);

    public abstract String getTitle();

    public abstract Dataset getDataset();

    public abstract Paint[] getPaints();

    public abstract Paint getBgPaint();
}