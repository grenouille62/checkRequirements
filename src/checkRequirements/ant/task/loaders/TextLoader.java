package checkRequirements.ant.task.loaders;

import java.io.FileInputStream;
import java.util.ArrayList;

import checkRequirements.ant.task.BusinessRequirement;
import checkRequirements.ant.task.ClassStructure;
import checkRequirements.utils.ListFiles;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 11 nov. 2009
 * Time: 14:51:53
 * To change this template use File | Settings | File Templates.
 */
public class TextLoader implements ILoader
{
    public BusinessRequirement[] loadRequirement(String requirementsSetFile)
    {
        try
        {
            String requirementList = ListFiles.loadFile(new FileInputStream(requirementsSetFile));
            String[] requirementSets = requirementList.split("\n");
            BusinessRequirement[] requirementSet = new BusinessRequirement[requirementSets.length];
            int i = 0;
            for (String s:requirementSets)
            {
                BusinessRequirement businessRequirement = new BusinessRequirement();
                if (s.indexOf(":") >= 0)
                {
                    String[] rs = s.split(":");
                    businessRequirement.setName(rs[0].trim());
                    businessRequirement.setDescription(rs[1].trim());
                }
                else
                {
                    businessRequirement.setName(s);
                    businessRequirement.setDescription("");
                }
                businessRequirement.setClasses(new ArrayList<ClassStructure>());
                System.out.println(businessRequirement);
                requirementSet[i] = businessRequirement;
                i++;
            }
            return requirementSet;
        }
        catch (Exception e)
        {
            throw new RuntimeException("error when accessing file " + requirementsSetFile + "\n" + e);
        }
    }

}
