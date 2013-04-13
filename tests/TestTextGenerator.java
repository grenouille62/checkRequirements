import checkRequirements.ant.task.generators.Generator;
import checkRequirements.ant.task.generators.TextGenerator;
import checkRequirements.ant.task.BusinessRequirement;
import checkRequirements.ant.task.ClassStructure;
import junit.framework.TestCase;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 10 nov. 2009
 * Time: 09:48:58
 * To change this template use File | Settings | File Templates.
 */
public class TestTextGenerator extends TestCase
{
    public void testGenerateSummary()
    {
        BusinessRequirement businessRequirement = new BusinessRequirement();
        businessRequirement.setName("RG_FOU_WSP_001");
        businessRequirement.setDescription("sites fournisseurs concernés par la validation CHORUS");
        List<ClassStructure> sources = new ArrayList<ClassStructure>();
        sources.add(new ClassStructure("","E:\\siprog\\GestionFournisseur\\src\\main\\java\\core\\fr\\gouv\\defense\\dga\\siprog\\gestionFournisseur\\exceptions\\TechnicalException.java",new ArrayList<Integer>()));
        businessRequirement.setClasses(sources);
        BusinessRequirement[] businessRequirements = new BusinessRequirement[] {businessRequirement};
        TextGenerator generator = new TextGenerator(businessRequirements,null);
        String str = generator.generateSummary(11,5,6);
        assertEquals("-------------------------------------------------\n" +
                "SYNTHESE\n" +
                "-------------------------------------------------\n" +
                "    Nombre de règles/traitements                    : 11\n" +
                "    Nombre de règles/traitements implémnetées       : 5\n" +
                "    Nombre de règles/traitements non implémentées   : 6\n" +
                "\n" +
                "-------------------------------------------------\n" +
                "DETAIL\n" +
                "-------------------------------------------------",str);
    }

    public void testGenerateHeader()
    {
        BusinessRequirement businessRequirement = new BusinessRequirement();
        businessRequirement.setName("RG_FOU_WSP_001");
        businessRequirement.setDescription("sites fournisseurs concernés par la validation CHORUS");
        List<ClassStructure> sources = new ArrayList<ClassStructure>();
        sources.add(new ClassStructure("","E:\\siprog\\GestionFournisseur\\src\\main\\java\\core\\fr\\gouv\\defense\\dga\\siprog\\gestionFournisseur\\exceptions\\TechnicalException.java",new ArrayList<Integer>()));
        businessRequirement.setClasses(sources);
        BusinessRequirement[] businessRequirements = new BusinessRequirement[] {businessRequirement};
        TextGenerator generator = new TextGenerator(businessRequirements,null);
        System.out.println(generator.generateHeaderByRequirement());
    }

    public void testGenerateDetail()
    {
        BusinessRequirement businessRequirement = new BusinessRequirement();
        businessRequirement.setName("RG_FOU_WSP_001");
        businessRequirement.setDescription("sites fournisseurs concernés par la validation CHORUS");
        List<ClassStructure> sources = new ArrayList<ClassStructure>();
        sources.add(new ClassStructure("fr.gouv.Class1","E:\\siprog\\GestionFournisseur\\src\\main\\java\\core\\fr\\gouv\\defense\\dga\\siprog\\gestionFournisseur\\exceptions\\TechnicalException.java",new ArrayList<Integer>()));
        businessRequirement.setClasses(sources);
        businessRequirement.setClasses(sources);
        BusinessRequirement[] businessRequirements = new BusinessRequirement[] {businessRequirement};
        TextGenerator generator = new TextGenerator(businessRequirements,null);
        System.out.println(generator.generateHeaderByRequirement());
        System.out.println(generator.generateDetailByRequirement("RG_FOU_WSP_001", "sites fournisseurs concernés par la validation CHORUS", sources));
    }
}
