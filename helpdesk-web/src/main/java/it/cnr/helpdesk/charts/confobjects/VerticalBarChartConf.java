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
import java.awt.Color;
import java.awt.Paint;
import java.util.Collection;
import java.util.Iterator;

// Referenced classes of package it.cnr.helpdesk.charts.confobjects:
//            ChartConf

public class VerticalBarChartConf
    implements ChartConf
{

    public VerticalBarChartConf(String s, Paint apaint[], Paint paint, String s1, String s2, CategoryDataset categorydataset, boolean flag, 
            boolean flag1, boolean flag2)
    {
        title = s;
        paints = apaint;
        bgPaint = paint;
        categoryAxisLabel = s1;
        valueAxisLabel = s2;
        categoryDataset = categorydataset;
        legend = flag;
        tooltips = flag1;
        urls = flag2;
    }

    public void setTitle(String s)
    {
        title = s;
    }

    public void setDataset(Dataset dataset)
    {
        categoryDataset = (CategoryDataset)dataset;
    }

    public void setPaints(Paint apaint[])
    {
        paints = apaint;
    }

    public void setBgPaint(Paint paint)
    {
        bgPaint = paint;
    }

    public void setCategoryAxisLabel(String s)
    {
        categoryAxisLabel = s;
    }

    public void setValueAxisLabel(String s)
    {
        valueAxisLabel = s;
    }

    public String getTitle()
    {
        return title;
    }

    public Dataset getDataset()
    {
        return categoryDataset;
    }

    public Paint[] getPaints()
    {
        return paints;
    }

    public Paint getBgPaint()
    {
        return bgPaint;
    }

    public String getCategoryAxisLabel()
    {
        return categoryAxisLabel;
    }

    public String getValueAxisLabel()
    {
        return valueAxisLabel;
    }

    public static Paint[] createPaint(Collection collection)
    {
        Paint apaint[] = new Paint[collection.size()];
        Iterator iterator = collection.iterator();
        for(int i = 0; i < collection.size(); i++)
            apaint[i] = (Color)iterator.next();

        return apaint;
    }

    public static CategoryDataset createCategoryDataset(double ad[][])
    {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset(ad);
        return defaultcategorydataset;
    }

    private String title;
    private Paint paints[];
    private Paint bgPaint;
    private String categoryAxisLabel;
    private String valueAxisLabel;
    private CategoryDataset categoryDataset;
    private boolean legend;
    private boolean tooltips;
    private boolean urls;
}