package checkRequirements.ant.task.generators;

import java.util.List;

import checkRequirements.ant.task.ClassStructure;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 11 nov. 2009
 * Time: 12:15:19
 * To change this template use File | Settings | File Templates.
 */
public class DetailByRequirement
{
    private String requirementName;
    private String requirementDescription;
    private List<ClassStructure> classes;

    public String getRequirementName()
    {
        return requirementName;
    }

    public void setRequirementName(String requirementName)
    {
        this.requirementName = requirementName;
    }

    public String getRequirementDescription()
    {
        return requirementDescription;
    }

    public void setRequirementDescription(String requirementDescription)
    {
        this.requirementDescription = requirementDescription;
    }

    public List<ClassStructure> getClasses()
    {
        return classes;
    }

    public void setClasses(List<ClassStructure> classes)
    {
        this.classes = classes;
    }

    public DetailByRequirement(String requirementName, String requirementDescription, List<ClassStructure> classes)
    {
        this.requirementName = requirementName;
        this.requirementDescription = requirementDescription;
        this.classes = classes;
    }
}
