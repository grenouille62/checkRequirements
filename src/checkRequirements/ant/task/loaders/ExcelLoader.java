package checkRequirements.ant.task.loaders;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Locale;

import checkRequirements.ant.task.BusinessRequirement;
import checkRequirements.ant.task.ClassStructure;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 11 nov. 2009
 * Time: 15:00:09
 * To change this template use File | Settings | File Templates.
 */
public class ExcelLoader implements ILoader
{
    public BusinessRequirement[] loadRequirement(String requirementsSetFile)
    {
        Workbook workbook = null;
        try
        {
            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setCellValidationDisabled(true);
            workbookSettings.setEncoding("iso-8859-1");
            workbookSettings.setLocale(Locale.FRENCH);
            workbook = Workbook.getWorkbook(new FileInputStream(requirementsSetFile), workbookSettings);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("error when accessing excel file " + requirementsSetFile + "\n", e);
        }
        Sheet sheet = workbook.getSheet(0);
        BusinessRequirement[] businessRequirements = new BusinessRequirement[sheet.getRows() - 1];
        for (int row = 1; row < sheet.getRows(); row++)
        {
            businessRequirements[row-1] = readLine(sheet, row);
        }
        return businessRequirements;
    }
    private static BusinessRequirement readLine(Sheet sheet, int line)
    {
        BusinessRequirement businessRequirement = new BusinessRequirement();
        businessRequirement.setName(sheet.getCell(0,line).getContents().trim());
        if (CellType.EMPTY.equals(sheet.getCell(1,line).getCellFormat()))
        {
            businessRequirement.setDescription("No description");
        }
        else
        {
            businessRequirement.setDescription(sheet.getCell(1,line).getContents().trim());
        }
        businessRequirement.setClasses(new ArrayList<ClassStructure>());
        return businessRequirement;
    }

}

