package checkRequirements.ant.task;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import checkRequirements.ant.task.generators.IGenerator;
import checkRequirements.ant.task.generators.TextGenerator;
import checkRequirements.ant.task.generators.XMLGenerator;
import checkRequirements.ant.task.loaders.ExcelLoader;
import checkRequirements.ant.task.loaders.ILoader;
import checkRequirements.ant.task.loaders.TextLoader;
import checkRequirements.utils.ListFiles;
import checkRequirements.utils.ParseJavaFile;


/**
 * Ant task to check implemntation of business requirements
 * User: Administrateur
 * Date: 9 nov. 2009
 * Time: 14:43:42
 * To change this template use File | Settings | File Templates.
 */
public class CheckRequirementsTask extends Task
{
    private ArrayList<FileSet> filesets = null;
    private String outputDir;
    private String outputFile;
    private String requirementsSetFile;
    private BusinessRequirement[] requirementSet;
    private Project project;
    private String outputStyle = "TXT";
    private String inputType = "TXT";

    public void init() throws BuildException
    {
        super.init();
    }

    public void addFileSet(FileSet fileset)
    {
        if (fileset == null)
        {
            throw new NullPointerException("fileset");
        }
        if (filesets == null)
        {
            filesets = new ArrayList();
        }

        filesets.add(fileset);
    }


    public void execute() throws BuildException
    {
        System.out.println("[INFO] " + this.getClass().getName() + " - Output directory is " + outputDir);
        System.out.println("[INFO] " + this.getClass().getName() + " - Output directory file is " + outputDir + ListFiles.FILE_SEPARATOR + outputFile);
        System.out.println("[INFO] " + this.getClass().getName() + " - The rulsetFile is " + requirementsSetFile);

        loadRequirementSet();

        if (filesets != null)
        {
            for (FileSet fileSet : filesets)
            {
                DirectoryScanner ds = fileSet.getDirectoryScanner(getProject());
                String baseDir = fileSet.getDir().getAbsolutePath();
                String[] fileNames = ds.getIncludedFiles();
                checkRequirementsInFiles(baseDir, fileNames);
                generateResult();
            }
        }
    }

    public void loadRequirementSet()
    {
        ILoader loader = null;
        if ("EXCEL".equals(inputType.toUpperCase()))
        {
            loader = new ExcelLoader();
        }
        else
        {
            loader = new TextLoader();
        }
        this.requirementSet =loader.loadRequirement(requirementsSetFile);
    }

    private void checkRequirementsInFiles(String baseDir, String[] fileNames)
    {
        System.out.println(baseDir);
        System.out.println(fileNames);
        for (int i = 0; i < fileNames.length; i++)
        {
            checkRequirementsInFile(baseDir + ListFiles.FILE_SEPARATOR + fileNames[i]);
        }
    }

    public void checkRequirementsInFile(String fileName)
    {
        try
        {
            System.out.println("[INFO] Inspecting file " + fileName);
            String content = ListFiles.loadFile(new FileInputStream(fileName));
            for (int i = 0; i < requirementSet.length; i++)
            {
                List<Integer> linesNumber = ParseJavaFile.detectRequirement(fileName, requirementSet[i].getName());
                if (linesNumber.size() > 0)
                {
                    List<ClassStructure> classesList = requirementSet[i].getClasses();
                    if (classesList == null)
                    {
                        classesList = new ArrayList<ClassStructure>();
                        requirementSet[i].setClasses(classesList);
                    }
                    classesList.add(new ClassStructure(ParseJavaFile.getFullClassName(content, fileName),fileName, linesNumber));
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("checkRequirementsInFile error when accessing file : " + fileName + "\n" + e);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("checkRequirementsInFile error : " + e);
        }
    }


    private final void generateResult()
    {
        IGenerator generator = null;
        if ("TXT".equals(outputStyle.toUpperCase()) || "".equals(outputStyle))
        {
            generator = new TextGenerator(requirementSet, outputDir + ListFiles.FILE_SEPARATOR + outputFile);
        }
        else
        {
            generator = new XMLGenerator(requirementSet, outputDir + ListFiles.FILE_SEPARATOR + outputFile);
        }
        generator.generate();
    }

    public String getOutputDir()
    {
        return outputDir;
    }

    public void setOutputDir(String outputDir)
    {
        this.outputDir = outputDir;
    }

    public String getOutputFile()
    {
        return outputFile;
    }

    public void setOutputFile(String outputFile)
    {
        this.outputFile = outputFile;
    }

    public String getRequirementsSetFile()
    {
        return requirementsSetFile;
    }

    public void setRequirementsSetFile(String requirementsSetFile)
    {
        this.requirementsSetFile = requirementsSetFile;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    public String getOutputStyle()
    {
        return outputStyle;
    }

    public void setOutputStyle(String outputStyle)
    {
        this.outputStyle = outputStyle;
    }

    public String getInputType()
    {
        return inputType;
    }

    public void setInputType(String inputType)
    {
        this.inputType = inputType;
    }

}
