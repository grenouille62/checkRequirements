package checkRequirements.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * ParseJavaFile.
 * User: Administrateur
 * Date: 10 nov. 2009
 * Time: 09:52:31
 * To change this template use File | Settings | File Templates.
 */
public final class ParseJavaFile
{
    private static final String BEGIN_BLOCK_COMMENT = "/*";
    private static final String END_BLOCK_COMMENT = "*/";
    private static final String BEGIN_LINE_COMMENT = "//";
    private static final String END_LINE_COMMENT = "\n";
    private static final String PACKAGE_KEYWORD = "package";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");


    public static String getPackageName(String content)
    {
        String str = supressComment(content);
        int iPackage  = str.indexOf(PACKAGE_KEYWORD) + PACKAGE_KEYWORD.length();
        int endPackage = str.indexOf(';',iPackage);
        if (endPackage < 0) {
            endPackage = str.length();
        }
        String packageName = str.substring(iPackage, endPackage);
        return packageName.trim();
    }

    public static String supressComment(String content)
    {
        String str = content;
        while (str.contains(BEGIN_BLOCK_COMMENT))
        {
            String blockComment = detectBlockComment(str);
            str = str.substring(0,str.indexOf(blockComment)) + str.substring(str.indexOf(blockComment) + blockComment.length(),str.length());
        }
        while (str.contains(BEGIN_LINE_COMMENT))
        {
            String lineComment = detectLineComment(str);
            str = str.substring(0,str.indexOf(lineComment)) + str.substring(str.indexOf(lineComment) + lineComment.length(),str.length());
        }
        return str;
    }

    public static String detectBlockComment(String str)
    {
        int beginIndex = str.indexOf(BEGIN_BLOCK_COMMENT);
        int endIndex = str.indexOf(END_BLOCK_COMMENT) + END_BLOCK_COMMENT.length();
        return str.substring(beginIndex,endIndex);
    }

    public static String detectLineComment(String str)
    {
        int beginIndex = str.indexOf(BEGIN_LINE_COMMENT);
        int endIndex = str.indexOf(END_LINE_COMMENT,beginIndex) + END_LINE_COMMENT.length();
        return str.substring(beginIndex,endIndex);
    }

    public static String getFullClassName(String content, String fileName)
    {
        String packageName = getPackageName(content);
        return packageName + "." + getClassName(fileName);
    }
    public static String getClassFileName(String fileName)
    {
        String str = fileName;
        while (str.contains(FILE_SEPARATOR))
        {
            str = str.substring(str.indexOf(FILE_SEPARATOR) + FILE_SEPARATOR.length(),str.length());
        }
        return str;
    }
    public static String getClassName(String fileName)
    {
        String str = fileName;
        while (str.contains(FILE_SEPARATOR))
        {
            str = str.substring(str.indexOf(FILE_SEPARATOR) + FILE_SEPARATOR.length(),str.length());
        }
        return str.substring(0,str.indexOf('.'));
    }

    public static List<Integer> detectRequirement(String fileName, String reqName)
    {
        List<Integer> listLineNumbers = new ArrayList<Integer>();
        try
        {
            int lineNumber = 0;
            List<String> stringList = FileUtils.readLines(new File(fileName));
            for (String str:stringList)
            {
                lineNumber++;
                if (str.contains(reqName))
                {
                    listLineNumbers.add(lineNumber);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new RuntimeException("ReadLines error", e);
        }
        return listLineNumbers;  //To change body of created methods use File | Settings | File Templates.
    }
}
