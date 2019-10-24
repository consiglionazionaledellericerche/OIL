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

import com.jrefinery.data.*;
import it.cnr.helpdesk.charts.dtos.SeriesItemDTO;
import java.awt.Color;
import java.awt.Paint;
import java.util.Collection;
import java.util.Iterator;

// Referenced classes of package it.cnr.helpdesk.charts.confobjects:
//            ChartConf

public class PieChartConf
    implements ChartConf
{

    public PieChartConf(String s, PieDataset piedataset, Paint apaint[], Paint paint)
    {
        title = s;
        pieDataset = piedataset;
        paints = apaint;
        bgPaint = paint;
    }

    public void setTitle(String s)
    {
        title = s;
    }

    public void setDataset(Dataset dataset)
    {
        pieDataset = (PieDataset)dataset;
    }

    public void setPaints(Paint apaint[])
    {
        paints = apaint;
    }

    public void setBgPaint(Paint paint)
    {
        bgPaint = paint;
    }

    public String getTitle()
    {
        return title;
    }

    public Dataset getDataset()
    {
        return pieDataset;
    }

    public Paint[] getPaints()
    {
        return paints;
    }

    public Paint getBgPaint()
    {
        return bgPaint;
    }

    public static Paint[] createPaint(Collection collection)
    {
        Paint apaint[] = new Paint[collection.size()];
        Iterator iterator = collection.iterator();
        for(int i = 0; i < collection.size(); i++)
            apaint[i] = (Color)iterator.next();

        return apaint;
    }

    public static PieDataset createPieDataset(Collection collection)
    {
        Iterator iterator = collection.iterator();
        DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
        SeriesItemDTO seriesitemdto;
        for(; iterator.hasNext(); defaultpiedataset.setValue(seriesitemdto.getLabel(), seriesitemdto.getValue()))
            seriesitemdto = (SeriesItemDTO)iterator.next();

        return defaultpiedataset;
    }

    private String title;
    private PieDataset pieDataset;
    private Paint paints[];
    private Paint bgPaint;
}