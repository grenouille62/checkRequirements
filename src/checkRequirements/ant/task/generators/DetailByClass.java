package checkRequirements.ant.task.generators;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 11 nov. 2009
 * Time: 12:16:18
 * To change this template use File | Settings | File Templates.
 */
public class DetailByClass
{
    private String className;
    private List<String> requirements;
    private String sourceName;

    public DetailByClass(String className, List<String> requirements)
    {
        this.className = className;
        this.requirements = requirements;
    }

    public DetailByClass(String className, List<String> requirements, String sourceName)
    {
        this.className = className;
        this.requirements = requirements;
        this.sourceName = sourceName;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public List<String> getRequirements()
    {
        return requirements;
    }

    public void setRequirements(List<String> requirements)
    {
        this.requirements = requirements;
    }

    public String getSourceName()
    {
        return sourceName;
    }

    public void setSourceName(String sourceName)
    {
        this.sourceName = sourceName;
    }
}
