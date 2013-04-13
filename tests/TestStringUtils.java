import junit.framework.TestCase;
import checkRequirements.utils.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 10 nov. 2009
 * Time: 15:35:32
 * To change this template use File | Settings | File Templates.
 */
public class TestStringUtils extends TestCase
{
    public void testRpad()
    {
        String str = "12345";
        assertEquals("1234500000", StringUtils.rpad(str,10,"0"));
    }
    public void testLpad()
    {
        String str = "12345";
        assertEquals("0000012345", StringUtils.lpad(str,10,"0"));
    }

    public void testLeftAlign()
    {
        String str = "12345";
        assertEquals("12345     ",StringUtils.leftAlign(str,10));
    }

    public void testRightAlign()
    {
        String str = "12345";
        assertEquals("     12345",StringUtils.rightAlign(str,10));
    }
    public void testMiddleAlign()
    {
        String str = "12345";
        assertEquals("  12345   ",StringUtils.middleAlign(str,10));
    }
}
