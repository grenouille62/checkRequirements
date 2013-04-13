package checkRequirements.ant.task;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 20 nov. 2009
 * Time: 17:47:15
 * To change this template use File | Settings | File Templates.
 */
public class ClassStructure
{
    private String className;
    private String sourceName;
    private List<Integer> lineNumber;

    public ClassStructure(String className, String sourceName, List<Integer> lineNumber)
    {
        this.className = className;
        this.sourceName = sourceName;
        this.lineNumber = lineNumber;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getSourceName()
    {
        return sourceName;
    }

    public void setSourceName(String sourceName)
    {
        this.sourceName = sourceName;
    }


    public String toString()
    {
        return "ClassStructure{" +
                "className='" + className + '\'' +
                ", sourceName='" + sourceName + '\'' +
                '}';
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        ClassStructure that = (ClassStructure) o;

        if (!className.equals(that.className))
        {
            return false;
        }
        if (!sourceName.equals(that.sourceName))
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        int result;
        result = className.hashCode();
        result = 31 * result + sourceName.hashCode();
        return result;
    }

    public List<Integer> getLineNumber()
    {
        return lineNumber;
    }

    public void setLineNumber(List<Integer> lineNumber)
    {
        this.lineNumber = lineNumber;
    }
}
