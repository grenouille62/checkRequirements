package checkRequirements.ant.task.generators;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import checkRequirements.ant.task.BusinessRequirement;
import checkRequirements.ant.task.ClassStructure;
import com.thoughtworks.xstream.XStream;

/**
 * XMLGenerator.
 * User: Administrateur
 * Date: 11 nov. 2009
 * Time: 10:44:44
 * To change this template use File | Settings | File Templates.
 */
public class XMLGenerator extends Generator
{
    public XMLGenerator(BusinessRequirement[] requirementSet, String outputFile)
    {
        super(requirementSet, outputFile);
    }


    public List<DetailByClass> generateDetailByClass(Map<ClassStructure,List<String>> mapClass)
    {
        Set<ClassStructure> classes = mapClass.keySet();
        List<DetailByClass> detailByClasses = new ArrayList<DetailByClass>();
        for (ClassStructure keyClass : classes)
        {
            List<String> requirements = mapClass.get(keyClass);
            DetailByClass detailByClass = new DetailByClass(keyClass.getClassName(), requirements, keyClass.getSourceName());
            detailByClasses.add(detailByClass);
        }
        return detailByClasses;
    }

    public List<DetailByClass> generateDetailByClass()
    {
        return generateDetailByClass(mapClass);
    }
    public List<DetailByRequirement> generateDetailByRequirement()
    {
        List<DetailByRequirement> detailByRequirements = new ArrayList<DetailByRequirement>();
        for (BusinessRequirement requirement:requirementSet)
        {
            String name = requirement.getName();
            String description = requirement.getDescription();
            List<ClassStructure> classes = requirement.getClasses();
            DetailByRequirement detailByRequirement = new DetailByRequirement(name, description, classes);
            detailByRequirements.add(detailByRequirement);
        }
        return detailByRequirements;
    }

    public void generate()
    {
        try
        {

            OutputStream outputStream = new FileOutputStream(outputFile);
            int nbNonImplemente = 0;
            int nbImplemente = 0;
            for (BusinessRequirement requirement:requirementSet)
            {
                List<ClassStructure> classNames = requirement.getClasses();
                if (classNames == null || classNames.size() == 0)
                {
                    nbNonImplemente++;
                }
                else
                {
                    nbImplemente++;
                }
            }
            Summary summary = new Summary(requirementSet.length, nbImplemente, nbNonImplemente);
            Report report = new Report(summary, generateDetailByRequirement(), generateDetailByClass());
            XStream xStream = new XStream();
            String result = xStream.toXML(report);
             
            outputStream.write(result.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException("error when creating ouputfile " + outputFile + "\n" +e);
        }
        catch (IOException e)
        {
            throw new RuntimeException("error when writing into ouputfile " + outputFile + "\n" +e);
        }
    }
}
