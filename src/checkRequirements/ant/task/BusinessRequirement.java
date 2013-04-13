package checkRequirements.ant.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 9 nov. 2009
 * Time: 15:05:00
 * To change this template use File | Settings | File Templates.
 */
public class BusinessRequirement
{
    private String name;
    private String description;
    private List<ClassStructure> classes;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }


    public List<ClassStructure> getClasses()
    {
        return classes;
    }

    public void setClasses(List<ClassStructure> classes)
    {
        this.classes = classes;
    }


    public String toString()
    {
        return "BusinessRequirement{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", classes=" + classes +
                '}';
    }

    public List<String> getClassesAsString()
    {
        List<String> l = new ArrayList<String>();
        for (ClassStructure classStructure:classes)
        {
            l.add(classStructure.getClassName());
        }
        return l;
    }
}
