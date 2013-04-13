package checkRequirements.ant.task.generators;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 11 nov. 2009
 * Time: 12:15:10
 * To change this template use File | Settings | File Templates.
 */
public class Report
{
    private Summary summary;
    private List<DetailByRequirement> detailByRequirement;
    private List<DetailByClass> detailByClass;

    public Summary getSummary()
    {
        return summary;
    }

    public void setSummary(Summary summary)
    {
        this.summary = summary;
    }

    public List<DetailByRequirement> getDetailByRequirement()
    {
        return detailByRequirement;
    }

    public void setDetailByRequirement(List<DetailByRequirement> detailByRequirement)
    {
        this.detailByRequirement = detailByRequirement;
    }

    public List<DetailByClass> getDetailByClass()
    {
        return detailByClass;
    }

    public void setDetailByClass(List<DetailByClass> detailByClass)
    {
        this.detailByClass = detailByClass;
    }

    public Report(Summary summary, List<DetailByRequirement> detailByRequirement, List<DetailByClass> detailByClass)
    {
        this.summary = summary;
        this.detailByRequirement = detailByRequirement;
        this.detailByClass = detailByClass;
    }
}
