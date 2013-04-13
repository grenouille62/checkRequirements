import checkRequirements.ant.task.BusinessRequirement;
import checkRequirements.ant.task.loaders.ExcelLoader;
import junit.framework.TestCase;

/**
 * TestExcelLoader.
 * User: Administrateur
 * Date: 11 nov. 2009
 * Time: 15:32:58
 * To change this template use File | Settings | File Templates.
 */
public class TestExcelLoader extends TestCase
{
    public void testLoadRequirement() throws Exception
    {
        System.out.println(System.getProperty("file.encoding"));
        ExcelLoader loader = new ExcelLoader();
        BusinessRequirement[] businessRequirement = loader.loadRequirement("/work/share/myProjects/checkRequirements/tests/ReglesDeGestion.xls");
        for (BusinessRequirement b:businessRequirement) {
            System.out.println(b.getName() + "; \t" + new String(b.getDescription().getBytes()));
        }
    }
}
