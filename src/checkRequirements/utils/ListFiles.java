package checkRequirements.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 9 nov. 2009
 * Time: 14:39:05
 * To change this template use File | Settings | File Templates.
 */
public final class ListFiles
{
    public static final int MAX_BUFFER_LENGTH = 65656;
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * Liste complète des fichiers à partir d'un répertoire.
     * Méthode récursive
     * @param directory : répertoire
     * @param listAllArchive .
     * @return la liste des fichiers constitués
     */
    List listFiles(File directory, List listAllArchive)
    {
        File[] arrayDirs = directory.listFiles();
        for (int i = 0; i < arrayDirs.length; i++)
        {
            File f = arrayDirs[i];
            if (f.isDirectory())
            {
                listFiles(f, listAllArchive);
            }
        }

        listAllArchive.addAll(listFileInside(directory));
        return listAllArchive;
    }

    Collection listFileInside(File directory)
    {
        Collection col = new ArrayList();
        File[] arrayDirs = directory.listFiles(new FileFilter());
        for (int i = 0; i < arrayDirs.length; i++)
        {
            col.add(arrayDirs[i].getAbsolutePath());
        }
        return col;
    }
    public static String loadFile(final String fileName) throws Exception
    {
        return loadFile(getResourceAsStream(fileName));
    }

    public static String loadFile(final InputStream in) throws Exception
    {

        int i;
        final byte[] b = new byte[MAX_BUFFER_LENGTH];
        final StringBuffer sb = new StringBuffer();
        while ((i = in.read(b)) != -1)
        {
            final String str = new String(b, 0, i);
            sb.append(str);
        }
        in.close();
        return sb.toString();
    }

    /**
     * Filtre des fichiers à prendre en compte
     */
    private class FileFilter implements FilenameFilter
    {
        public boolean accept(File dir, String name)
        {
            return (name.endsWith(".jar")) || (name.endsWith(".war")) || (name.endsWith(".ear"));
        }
    }

    public static InputStream getResourceAsStream(String name)
    {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
        if (in == null)
        {
            in = ListFiles.class.getResourceAsStream(name);
            if (in == null)
            {
                in = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
            }
        }
        return in;
    }

}
