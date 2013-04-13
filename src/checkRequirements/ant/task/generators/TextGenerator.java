package checkRequirements.ant.task.generators;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import checkRequirements.ant.task.BusinessRequirement;
import checkRequirements.ant.task.ClassStructure;
import checkRequirements.utils.ListFiles;
import checkRequirements.utils.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 10 nov. 2009
 * Time: 09:21:39
 * To change this template use File | Settings | File Templates.
 */
public class TextGenerator extends Generator
{
    private static String staticHeaderByClass;
    private static String staticHeaderByRequirement;
    private static String headerClassTemplate;
    private static String lineClassTemplate;
    private static String headerTemplate;
    private static String lineTemplate;
    private static String summaryTemplate;
    private static int implLength;
    private static int nbClassLength;
    private String footer;
    private static final String DESCRIPTION = "<DESCRIPTION>";
    private static final String REGLE = "<REGLE>";
    private static final String CLASSE = "<CLASSE>";
    private static final String IMPLEMENTATION = "<IMPLEMENTATION>";
    private static final String NB_CLASSES = "<NB_CLASSES>";
    private static final String NB_REGLE = "<NB_REGLE>";
    private static final String NB_IMPL = "<NB_IMPL>";
    private static final String NB_NON_IMPL = "<NB_NON_IMPL>";

    static
    {
        try
        {
            staticHeaderByClass = ListFiles.loadFile("/StaticHeaderByClass.txt");
        }
        catch (Exception e)
        {
            throw new RuntimeException("error when accessing file /StaticHeaderByClass.txt" + "\n" + e);
        }
        try
        {
            staticHeaderByRequirement = ListFiles.loadFile("/StaticHeaderByRequirement.txt");
        }
        catch (Exception e)
        {
            throw new RuntimeException("error when accessing file /StaticHeaderByClass.txt" + "\n" + e);
        }
        try
        {
            lineClassTemplate = ListFiles.loadFile("/LineClassTemplate.txt");
        }
        catch (Exception e)
        {
            throw new RuntimeException("error when accessing file /LineClassTemplate.txt" + "\n" + e);
        }
        try
        {
            headerClassTemplate = ListFiles.loadFile("/HeaderClassTemplate.txt");
        }
        catch (Exception e)
        {
            throw new RuntimeException("error when accessing file /HeaderClassTemplate.txt" + "\n" + e);
        }
        try
        {
            lineTemplate = ListFiles.loadFile("/LineRequirementTemplate.txt");
        }
        catch (Exception e)
        {
            throw new RuntimeException("error when accessing file /LineRequirementTemplate.txt" + "\n" + e);
        }
        try
        {
            headerTemplate = ListFiles.loadFile("/HeaderRequirementTemplate.txt");
        }
        catch (Exception e)
        {
            throw new RuntimeException("error when accessing file /HeaderRequirementTemplate.txt" + "\n" + e);
        }
        try
        {
            summaryTemplate = ListFiles.loadFile("/SummaryTemplate.txt");
        }
        catch (Exception e)
        {
            throw new RuntimeException("error when accessing file /SummaryTemplate.txt" + "\n" + e);
        }
        String[] hdr = headerTemplate.split("\\|");
        implLength = hdr[3].length();
        nbClassLength = hdr[4].length();
    }


    public TextGenerator(BusinessRequirement[] requirementSet, String outputFile)
    {
        super(requirementSet, outputFile);
    }

    public String generateDetailByRequirement(String name, String description, List<ClassStructure> classes)
    {
        String template = lineTemplate;
        template = template.replaceAll(REGLE, StringUtils.leftAlign(name, maxNameLength));
        template = template.replaceAll(DESCRIPTION, StringUtils.leftAlign(description, maxDescriptionLength));
        String impl = "";
        int nbClasses = 0;
        if (classes != null && classes.size() > 0)
        {
            impl = "X";
            nbClasses = classes.size();
        }
        template = template.replaceAll(IMPLEMENTATION, StringUtils.middleAlign(impl, implLength));
        template = template.replaceAll(NB_CLASSES, StringUtils.middleAlign(nbClasses == 0 ? " " : String.valueOf(nbClasses), nbClassLength));
        return template;
    }

    public String generateSummary(int nbRegle, int nbImplemente, int nbNonImplemente)
    {
        String template = summaryTemplate;
        template = template.replaceAll(NB_REGLE, String.valueOf(nbRegle));
        template = template.replaceAll(NB_IMPL, String.valueOf(nbImplemente));
        template = template.replaceAll(NB_NON_IMPL, String.valueOf(nbNonImplemente));
        return template;
    }

    private String generateHeader(String template, int[] maxLengths)
    {
        String str = template;
        String[] hdr = template.split("\\|");
        String[] hdrAlign = template.split("\\|");
        hdrAlign[1] = StringUtils.middleAlign(hdr[1], maxLengths[0]);
        hdrAlign[2] = StringUtils.middleAlign(hdr[2], maxLengths[1]);
        str = str.replaceAll(hdr[1], hdrAlign[1]);
        str = str.replaceAll(hdr[2], hdrAlign[2]);

        String s = StringUtils.lpad("", str.length(), "-");
        String s1 = s + "\n" + str + "\n" + s + "\n";
        footer = StringUtils.lpad("", str.length(), "-");
        return s1;
    }

    public String generateHeaderByRequirement()
    {
        return staticHeaderByRequirement + generateHeader(headerTemplate, new int[]{maxNameLength, maxDescriptionLength});
    }

    protected String generateFooter()
    {
        return footer;
    }

    public String generateHeaderByClass()
    {
        return staticHeaderByClass + generateHeader(headerClassTemplate, new int[]{maxClassNameLength, maxNameLength});
    }

    public void generate()
    {
        try
        {
            OutputStream outputStream = new FileOutputStream(outputFile);
            StringBuffer strBuff = new StringBuffer();
            int nbNonImplemente = 0;
            int nbImplemente = 0;
            for (BusinessRequirement requirement:requirementSet)
            {
                String name = requirement.getName();
                String description = requirement.getDescription();
                List<ClassStructure> classNames = requirement.getClasses();
                strBuff.append(generateDetailByRequirement(name, description,classNames));
                if (classNames == null || classNames.size() == 0)
                {
                    nbNonImplemente++;
                }
                else
                {
                    nbImplemente++;
                }
            }
            String summary = generateSummary(requirementSet.length, nbImplemente, nbNonImplemente);
            outputStream.write(summary.getBytes());
            outputStream.write(generateHeaderByRequirement().getBytes());
            outputStream.write(strBuff.toString().getBytes());
            outputStream.write(generateFooter().getBytes());

            outputStream.write(generateHeaderByClass().getBytes());
            outputStream.write(generateDetailByClass(mapClass).getBytes());

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

    public String generateDetailByClass(Map<ClassStructure,List<String>> mapClass)
    {
        Set<ClassStructure> keySet = mapClass.keySet();
        StringBuffer buff = new StringBuffer();
        for (ClassStructure key : keySet)
        {
            List<String> requirements = mapClass.get(key);
            int i = 0;
            int strSeparator = 0;
            for (String requirement : requirements)
            {
                String template = lineClassTemplate;
                String cls = " ";
                if (i == 0)
                {
                    cls = key.getClassName();
                }
                i++;
                template = template.replaceAll(CLASSE, StringUtils.leftAlign(cls, maxClassNameLength));
                template = template.replaceAll(REGLE, StringUtils.leftAlign(requirement, maxNameLength));
                strSeparator = template.length();
                buff.append(template).append('\n');
            }
            buff.append(StringUtils.lpad("",strSeparator,"-")).append('\n');
        }
        return buff.toString();
    }
}
