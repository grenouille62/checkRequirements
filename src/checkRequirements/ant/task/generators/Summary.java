package checkRequirements.ant.task.generators;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 11 nov. 2009
 * Time: 12:08:24
 * To change this template use File | Settings | File Templates.
 */
public class Summary
{
    private int nbRequirements;
    private int nbImplemented;
    private int nbNonImplemented;

    public int getNbRequirements()
    {
        return nbRequirements;
    }

    public void setNbRequirements(int nbRequirements)
    {
        this.nbRequirements = nbRequirements;
    }

    public int getNbImplemented()
    {
        return nbImplemented;
    }

    public void setNbImplemented(int nbImplemented)
    {
        this.nbImplemented = nbImplemented;
    }

    public int getNbNonImplemented()
    {
        return nbNonImplemented;
    }

    public void setNbNonImplemented(int nbNonImplemented)
    {
        this.nbNonImplemented = nbNonImplemented;
    }

    public Summary(int nbRequirements, int nbImplemented, int nbNonImplemented)
    {
        this.nbRequirements = nbRequirements;
        this.nbImplemented = nbImplemented;
        this.nbNonImplemented = nbNonImplemented;
    }
}
