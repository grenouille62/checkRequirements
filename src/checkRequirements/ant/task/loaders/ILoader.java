package checkRequirements.ant.task.loaders;

import checkRequirements.ant.task.BusinessRequirement;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 11 nov. 2009
 * Time: 14:49:45
 * To change this template use File | Settings | File Templates.
 */
public interface ILoader
{
    public BusinessRequirement[] loadRequirement(String requirementsSetFile);
}
