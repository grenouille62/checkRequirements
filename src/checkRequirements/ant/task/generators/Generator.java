package checkRequirements.ant.task.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import checkRequirements.ant.task.BusinessRequirement;
import checkRequirements.ant.task.ClassStructure;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 10 nov. 2009
 * Time: 09:35:30
 * To change this template use File | Settings | File Templates.
 */
public abstract class Generator implements IGenerator
{
    protected BusinessRequirement[] requirementSet;
    protected String outputFile;
    protected int maxNameLength = 0;
    protected int maxDescriptionLength = 0;
    protected Map<ClassStructure,List<String>> mapClass = new HashMap();
    protected int maxClassNameLength = 0;

    public Generator(BusinessRequirement[] requirementSet, String outputFile)
    {
        this.requirementSet = requirementSet;
        this.outputFile = outputFile;
        if (requirementSet == null)
        {
            return;
        }
        for (BusinessRequirement requirement:requirementSet)
        {
            maxNameLength = Math.max(maxNameLength,requirement.getName().length());
            maxDescriptionLength = Math.max(maxDescriptionLength,requirement.getDescription().length());
            if (requirement.getClasses() == null)
            {
                break;
            }
            for (ClassStructure classStructure:requirement.getClasses())
            {
                maxClassNameLength = Math.max(maxClassNameLength,classStructure.getClassName().length());
                List<String> s = mapClass.get(classStructure);
                if (s == null)
                {
                    s=new ArrayList<String>();
                    mapClass.put(classStructure,s);
                }
                s.add(requirement.getName());
            }
        }

    }

    public abstract void generate();
}
