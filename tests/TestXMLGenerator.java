import checkRequirements.ant.task.generators.XMLGenerator;
import checkRequirements.ant.task.generators.Generator;
import checkRequirements.ant.task.BusinessRequirement;
import checkRequirements.ant.task.ClassStructure;
import junit.framework.TestCase;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 10 nov. 2009
 * Time: 09:49:17
 * To change this template use File | Settings | File Templates.
 */
public class TestXMLGenerator extends TestCase
{
    public void testDummy()
    {
        XMLGenerator xmlGenerator = new XMLGenerator(null,null);
        List<String> list = new ArrayList<String>();
        list.add("fr.gouv.Class1");
        list.add("fr.gouv.Class2");
        list.add("fr.gouv.Class3");
        Map<ClassStructure,List<String>> mapClass = new HashMap<ClassStructure, List<String>>();
        List<String> req = new ArrayList<String>();
        req.add("RG_01");
        req.add("RG_02");
        ClassStructure classStructure = new ClassStructure("fr.gouv.Class1","source fr.gouv.Class1",new ArrayList<Integer>());
        mapClass.put(classStructure, req);
        System.out.println(xmlGenerator.generateDetailByClass(mapClass));
    }
}
