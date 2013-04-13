package checkRequirements.utils;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 10 nov. 2009
 * Time: 15:04:17
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils
{
    public static String rpad(String str, int maxLength, String s)
    {
        if (str == null || maxLength == 0 || str.length() >= maxLength)
        {
            return str;
        }
        StringBuffer buff = new StringBuffer(maxLength);
        buff.append(str);
        for (int i = 0; i < maxLength - str.length(); i++)
        {
            buff.append(s);
        }
        return buff.toString();
    }

    public static String lpad(String str, int maxLength, String s)
    {
        if (str == null || maxLength == 0 || str.length() >= maxLength)
        {
            return str;
        }
        StringBuffer buff = new StringBuffer(maxLength);
        for (int i = 0; i < maxLength - str.length(); i++)
        {
            buff.append(s);
        }
        buff.append(str);
        return buff.toString();
    }

    public static String leftAlign(String str, int length)
    {
           String[] ss = wrap(str,length);
            StringBuffer buff = new StringBuffer();
            ss[ss.length-1] = rpad(ss[ss.length - 1],length," ");
            for (String s:ss)
            {
                buff.append(s);
            }
            return buff.toString();
    }

    public static String[] wrap(String str, int length)
    {
        if (str == null || str.length() == 0)
        {
             return new String[] {""};
        }
        int nbfragment = (str.length() / length) + ((str.length()%length)> 0?1:0);
        String[] ss = new String[nbfragment];
        int beginIndex = 0;
        int endIndex = length;
        for (int i = 0; i<nbfragment - 1; i++)
        {
            ss[i] = str.substring(beginIndex, Math.min(str.length(),endIndex)) + "\n";
            beginIndex += length;
            endIndex += length;
        }
        ss[nbfragment-1] = str.substring(beginIndex, Math.min(str.length(),endIndex));
        return ss;
    }

    public static String rightAlign(String str, int length)
    {
        String[] ss = wrap(str,length);
         StringBuffer buff = new StringBuffer();
         ss[0] = lpad(ss[0],length," ");
         for (String s:ss)
         {
             buff.append(s);
         }
         return buff.toString();
    }

    public static String middleAlign(String str, int length)
    {
        if (str == null || length == 0)
        {
            return str;
        }
        int spaces = (length - str.length()) / 2;
        int spacesBefore = spaces == 0 ? 1 : spaces;
        int spacesAfter = length - (spacesBefore + str.length());
        StringBuffer buff = new StringBuffer(length);
        buff.append(lpad("", spacesBefore, " ")).append(str).append(rpad("", spacesAfter, " "));
        return buff.toString();
    }
}
