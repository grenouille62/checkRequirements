import java.io.FileInputStream;

import checkRequirements.ant.task.CheckRequirementsTask;
import checkRequirements.utils.ListFiles;
import checkRequirements.utils.ParseJavaFile;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 10 nov. 2009
 * Time: 10:04:35
 * To change this template use File | Settings | File Templates.
 */
public class TestParseJavaFile extends TestCase
{
    public void testDetectBlockComment ()
    {
        String str = "package pluginHudson;\n" +
                "import hudson.Launcher;\n" +
                "import hudson.Extension;\n" +
                "import hudson.util.FormValidation;\n" +
                "import hudson.model.AbstractBuild;\n" +
                "import hudson.model.BuildListener;\n" +
                "import hudson.model.AbstractProject;\n" +
                "import hudson.tasks.Builder;\n" +
                "import hudson.tasks.BuildStepDescriptor;\n" +
                "import net.sf.json.JSONObject;\n" +
                "import org.kohsuke.stapler.DataBoundConstructor;\n" +
                "import org.kohsuke.stapler.StaplerRequest;\n" +
                "import org.kohsuke.stapler.QueryParameter;\n" +
                "\n" +
                "import javax.servlet.ServletException;\n" +
                "import java.io.IOException;\n" +
                "\n" +
                "/**\n" +
                " * Sample {@link Builder}.\n" +
                " *\n" +
                " * <p>\n" +
                " * When the user configures the project and enables this builder,\n" +
                " * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked\n" +
                " * and a new {@link HelloWorldBuilder} is created. The created\n" +
                " * instance is persisted to the project configuration XML by using\n" +
                " * XStream, so this allows you to use instance fields (like {@link #name})\n" +
                " * to remember the configuration.\n" +
                " *\n" +
                " * <p>\n" +
                " * When a build is performed, the {@link #perform(Build, Launcher, BuildListener)} method\n" +
                " * will be invoked. \n" +
                " *\n" +
                " * @author Kohsuke Kawaguchi\n" +
                " */\n" +
                "public class HelloWorldBuilder extends Builder {\n" +
                "\n" +
                "    private final String name;\n" +
                "";
        assertEquals("/**\n" +
                " * Sample {@link Builder}.\n" +
                " *\n" +
                " * <p>\n" +
                " * When the user configures the project and enables this builder,\n" +
                " * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked\n" +
                " * and a new {@link HelloWorldBuilder} is created. The created\n" +
                " * instance is persisted to the project configuration XML by using\n" +
                " * XStream, so this allows you to use instance fields (like {@link #name})\n" +
                " * to remember the configuration.\n" +
                " *\n" +
                " * <p>\n" +
                " * When a build is performed, the {@link #perform(Build, Launcher, BuildListener)} method\n" +
                " * will be invoked. \n" +
                " *\n" +
                " * @author Kohsuke Kawaguchi\n" +
                " */",ParseJavaFile.detectBlockComment(str));

    }

    public void testDetectLineComment()
    {
        String str = "    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {\n" +
                "        // this is where you 'build' the project\n" +
                "        if(getDescriptor().useFrench())\n" +
                "            listener.getLogger().println(\"Bonjour, \"+name+\"!\");\n" +
                "        else\n" +
                "            listener.getLogger().println(\"Hello, \"+name+\"!\");\n" +
                "        return true;\n" +
                "    }";
        assertEquals("// this is where you 'build' the project\n",ParseJavaFile.detectLineComment(str));
    }

    public void testSuppressComment()
    {
        String str = "    /**\n" +
                "     * We'll use this from the <tt>config.jelly</tt>.\n" +
                "     */\n" +
                "    public String getName() {\n" +
                "        return name;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {\n" +
                "        // this is where you 'build' the project\n" +
                "        // since this is a dummy, we just say 'hello world' and call that a build\n" +
                "\n" +
                "        // this also shows how you can consult the global configuration of the builder\n" +
                "        if(getDescriptor().useFrench())\n" +
                "            listener.getLogger().println(\"Bonjour, \"+name+\"!\");\n" +
                "        else\n" +
                "            listener.getLogger().println(\"Hello, \"+name+\"!\");\n" +
                "        return true;\n" +
                "    }";
        String resultat = ParseJavaFile.supressComment(str);
        assertTrue (resultat.indexOf("/*") < 0);
        assertTrue (resultat.indexOf("*/") < 0);
        assertTrue (resultat.indexOf("//") < 0);
        System.out.print(resultat);
    }

    public void testGetPackageName()
    {
        String str ="package pluginHudson;\n" +
                "import hudson.Launcher;\n" +
                "import hudson.Extension;\n" +
                "import hudson.util.FormValidation;\n" +
                "import hudson.model.AbstractBuild;\n" +
                "import hudson.model.BuildListener;\n" +
                "import hudson.model.AbstractProject;\n" +
                "import hudson.tasks.Builder;\n" +
                "import hudson.tasks.BuildStepDescriptor;\n" +
                "import net.sf.json.JSONObject;\n" +
                "import org.kohsuke.stapler.DataBoundConstructor;\n" +
                "import org.kohsuke.stapler.StaplerRequest;\n" +
                "import org.kohsuke.stapler.QueryParameter;\n" +
                "\n" +
                "import javax.servlet.ServletException;\n" +
                "import java.io.IOException;\n" +
                "\n" +
                "/**\n" +
                " * Sample {@link Builder}.\n" +
                " *\n" +
                " * <p>\n" +
                " * When the user configures the project and enables this builder,\n" +
                " * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked\n" +
                " * and a new {@link HelloWorldBuilder} is created. The created\n" +
                " * instance is persisted to the project configuration XML by using\n" +
                " * XStream, so this allows you to use instance fields (like {@link #name})\n" +
                " * to remember the configuration.\n" +
                " *\n" +
                " * <p>\n" +
                " * When a build is performed, the {@link #perform(Build, Launcher, BuildListener)} method\n" +
                " * will be invoked. \n" +
                " *\n" +
                " * @author Kohsuke Kawaguchi\n" +
                " */\n" +
                "public class HelloWorldBuilder extends Builder {\n" +
                "\n" +
                "    private final String name;\n" +
                "\n" +
                "    @DataBoundConstructor\n" +
                "    public HelloWorldBuilder(String name) {\n" +
                "        this.name = name;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * We'll use this from the <tt>config.jelly</tt>.\n" +
                "     */\n" +
                "    public String getName() {\n" +
                "        return name;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {\n" +
                "        // this is where you 'build' the project\n" +
                "        // since this is a dummy, we just say 'hello world' and call that a build\n" +
                "\n" +
                "        // this also shows how you can consult the global configuration of the builder\n" +
                "        if(getDescriptor().useFrench())\n" +
                "            listener.getLogger().println(\"Bonjour, \"+name+\"!\");\n" +
                "        else\n" +
                "            listener.getLogger().println(\"Hello, \"+name+\"!\");\n" +
                "        return true;\n" +
                "    }\n" +
                "\n" +
                "    // overrided for better type safety.\n" +
                "    // if your plugin doesn't really define any property on Descriptor,\n" +
                "    // you don't have to do this.\n" +
                "    @Override\n" +
                "    public DescriptorImpl getDescriptor() {\n" +
                "        return (DescriptorImpl)super.getDescriptor();\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Descriptor for {@link HelloWorldBuilder}. Used as a singleton.\n" +
                "     * The class is marked as public so that it can be accessed from views.\n" +
                "     *\n" +
                "     * <p>\n" +
                "     * See <tt>views/hudson/plugins/hello_world/HelloWorldBuilder/*.jelly</tt>\n" +
                "     * for the actual HTML fragment for the configuration screen.\n" +
                "     */\n" +
                "    @Extension // this marker indicates Hudson that this is an implementation of an extension point.\n" +
                "    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {\n" +
                "        /**\n" +
                "         * To persist global configuration information,\n" +
                "         * simply store it in a field and call save().\n" +
                "         *\n" +
                "         * <p>\n" +
                "         * If you don't want fields to be persisted, use <tt>transient</tt>.\n" +
                "         */\n" +
                "        private boolean useFrench;\n" +
                "\n" +
                "        /**\n" +
                "         * Performs on-the-fly validation of the form field 'name'.\n" +
                "         *\n" +
                "         * @param value\n" +
                "         *      This parameter receives the value that the user has typed.\n" +
                "         * @return\n" +
                "         *      Indicates the outcome of the validation. This is sent to the browser.\n" +
                "         */\n" +
                "        public FormValidation doCheckName(@QueryParameter String value) throws IOException, ServletException {\n" +
                "            if(value.length()==0)\n" +
                "                return FormValidation.error(\"Please set a name\");\n" +
                "            if(value.length()<4)\n" +
                "                return FormValidation.warning(\"Isn't the name too short?\");\n" +
                "            return FormValidation.ok();\n" +
                "        }\n" +
                "\n" +
                "        public boolean isApplicable(Class<? extends AbstractProject> aClass) {\n" +
                "            // indicates that this builder can be used with all kinds of project types \n" +
                "            return true;\n" +
                "        }\n" +
                "\n" +
                "        /**\n" +
                "         * This human readable name is used in the configuration screen.\n" +
                "         */\n" +
                "        public String getDisplayName() {\n" +
                "            return \"Say hello world\";\n" +
                "        }\n" +
                "\n" +
                "        @Override\n" +
                "        public boolean configure(StaplerRequest req, JSONObject o) throws FormException {\n" +
                "            // to persist global configuration information,\n" +
                "            // set that to properties and call save().\n" +
                "            useFrench = o.getBoolean(\"useFrench\");\n" +
                "            save();\n" +
                "            return super.configure(req,o);\n" +
                "        }\n" +
                "\n" +
                "        /**\n" +
                "         * This method returns true if the global configuration says we should speak French.\n" +
                "         */\n" +
                "        public boolean useFrench() {\n" +
                "            return useFrench;\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "";
        assertEquals("pluginHudson",ParseJavaFile.getPackageName(str));
    }

    public void testGetFullClassName()
    {
        String content = "package pluginHudson;\n" +
                "import hudson.Launcher;\n" +
                "import hudson.Extension;\n" +
                "import hudson.util.FormValidation;\n" +
                "import hudson.model.AbstractBuild;\n" +
                "import hudson.model.BuildListener;\n" +
                "import hudson.model.AbstractProject;\n" +
                "import hudson.tasks.Builder;\n" +
                "import hudson.tasks.BuildStepDescriptor;\n" +
                "import net.sf.json.JSONObject;\n" +
                "import org.kohsuke.stapler.DataBoundConstructor;\n" +
                "import org.kohsuke.stapler.StaplerRequest;\n" +
                "import org.kohsuke.stapler.QueryParameter;\n" +
                "\n" +
                "import javax.servlet.ServletException;\n" +
                "import java.io.IOException;\n" +
                "\n" +
                "/**\n" +
                " * Sample {@link Builder}.\n" +
                " *\n" +
                " * <p>\n" +
                " * When the user configures the project and enables this builder,\n" +
                " * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked\n" +
                " * and a new {@link HelloWorldBuilder} is created. The created\n" +
                " * instance is persisted to the project configuration XML by using\n" +
                " * XStream, so this allows you to use instance fields (like {@link #name})\n" +
                " * to remember the configuration.\n" +
                " *\n" +
                " * <p>\n" +
                " * When a build is performed, the {@link #perform(Build, Launcher, BuildListener)} method\n" +
                " * will be invoked. \n" +
                " *\n" +
                " * @author Kohsuke Kawaguchi\n" +
                " */\n" +
                "public class HelloWorldBuilder extends Builder {\n" +
                "\n" +
                "    private final String name;\n" +
                "\n" +
                "    @DataBoundConstructor\n" +
                "    public HelloWorldBuilder(String name) {\n" +
                "        this.name = name;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * We'll use this from the <tt>config.jelly</tt>.\n" +
                "     */\n" +
                "    public String getName() {\n" +
                "        return name;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {\n" +
                "        // this is where you 'build' the project\n" +
                "        // since this is a dummy, we just say 'hello world' and call that a build\n" +
                "\n" +
                "        // this also shows how you can consult the global configuration of the builder\n" +
                "        if(getDescriptor().useFrench())\n" +
                "            listener.getLogger().println(\"Bonjour, \"+name+\"!\");\n" +
                "        else\n" +
                "            listener.getLogger().println(\"Hello, \"+name+\"!\");\n" +
                "        return true;\n" +
                "    }\n" +
                "\n" +
                "    // overrided for better type safety.\n" +
                "    // if your plugin doesn't really define any property on Descriptor,\n" +
                "    // you don't have to do this.\n" +
                "    @Override\n" +
                "    public DescriptorImpl getDescriptor() {\n" +
                "        return (DescriptorImpl)super.getDescriptor();\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Descriptor for {@link HelloWorldBuilder}. Used as a singleton.\n" +
                "     * The class is marked as public so that it can be accessed from views.\n" +
                "     *\n" +
                "     * <p>\n" +
                "     * See <tt>views/hudson/plugins/hello_world/HelloWorldBuilder/*.jelly</tt>\n" +
                "     * for the actual HTML fragment for the configuration screen.\n" +
                "     */\n" +
                "    @Extension // this marker indicates Hudson that this is an implementation of an extension point.\n" +
                "    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {\n" +
                "        /**\n" +
                "         * To persist global configuration information,\n" +
                "         * simply store it in a field and call save().\n" +
                "         *\n" +
                "         * <p>\n" +
                "         * If you don't want fields to be persisted, use <tt>transient</tt>.\n" +
                "         */\n" +
                "        private boolean useFrench;\n" +
                "\n" +
                "        /**\n" +
                "         * Performs on-the-fly validation of the form field 'name'.\n" +
                "         *\n" +
                "         * @param value\n" +
                "         *      This parameter receives the value that the user has typed.\n" +
                "         * @return\n" +
                "         *      Indicates the outcome of the validation. This is sent to the browser.\n" +
                "         */\n" +
                "        public FormValidation doCheckName(@QueryParameter String value) throws IOException, ServletException {\n" +
                "            if(value.length()==0)\n" +
                "                return FormValidation.error(\"Please set a name\");\n" +
                "            if(value.length()<4)\n" +
                "                return FormValidation.warning(\"Isn't the name too short?\");\n" +
                "            return FormValidation.ok();\n" +
                "        }\n" +
                "\n" +
                "        public boolean isApplicable(Class<? extends AbstractProject> aClass) {\n" +
                "            // indicates that this builder can be used with all kinds of project types \n" +
                "            return true;\n" +
                "        }\n" +
                "\n" +
                "        /**\n" +
                "         * This human readable name is used in the configuration screen.\n" +
                "         */\n" +
                "        public String getDisplayName() {\n" +
                "            return \"Say hello world\";\n" +
                "        }\n" +
                "\n" +
                "        @Override\n" +
                "        public boolean configure(StaplerRequest req, JSONObject o) throws FormException {\n" +
                "            // to persist global configuration information,\n" +
                "            // set that to properties and call save().\n" +
                "            useFrench = o.getBoolean(\"useFrench\");\n" +
                "            save();\n" +
                "            return super.configure(req,o);\n" +
                "        }\n" +
                "\n" +
                "        /**\n" +
                "         * This method returns true if the global configuration says we should speak French.\n" +
                "         */\n" +
                "        public boolean useFrench() {\n" +
                "            return useFrench;\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "";
        String fileName = "E:\\divers\\pluginHudson\\src\\main\\java\\pluginHudson\\HelloWorldBuilder.java";
        assertEquals("pluginHudson.HelloWorldBuilder",ParseJavaFile.getFullClassName(content,fileName));
    }

    public void testGetClassFileName()
    {
        String fileName = "E:\\divers\\pluginHudson\\src\\main\\java\\pluginHudson\\HelloWorldBuilder.java";
        assertEquals("HelloWorldBuilder.java",ParseJavaFile.getClassFileName(fileName));
    }

    public void testGetFullClassName1 () throws Exception
    {
        String str = ListFiles.loadFile(new FileInputStream("E:\\siprog\\GestionFournisseur\\src\\main\\java\\core\\fr\\gouv\\defense\\dga\\siprog\\gestionFournisseur\\services\\business\\fournisseur\\ValidationChorusServiceImpl.java"));
        ParseJavaFile.getFullClassName(str,"E:\\siprog\\GestionFournisseur\\src\\main\\java\\core\\fr\\gouv\\defense\\dga\\siprog\\gestionFournisseur\\services\\business\\fournisseur\\ValidationChorusServiceImpl.java");
    }

    public void testDetectRequirement() {
        ParseJavaFile.detectRequirement("/work/share/CG92-CRIF/OGIL/ogil/ogil-grails/ogil-infos-generales/grails-app/controllers/ogil/infos/generales/EmployeController.groovy", "RG_FOU_WSP_002");
    }

    public void testCheckRequirementsInFile() {
        CheckRequirementsTask checkRequirementsTask = new CheckRequirementsTask();
        checkRequirementsTask.setInputType("excel");
        checkRequirementsTask.setOutputDir("/work/share/temp");
        checkRequirementsTask.setOutputStyle("xml");
        checkRequirementsTask.setRequirementsSetFile("/work/share/myProjects/checkRequirements/tests/ReglesDeGestion.xls");
        checkRequirementsTask.loadRequirementSet();
        checkRequirementsTask.checkRequirementsInFile("/work/share/CG92-CRIF/OGIL/ogil/ogil-grails/ogil-infos-generales/grails-app/controllers/ogil/infos/generales/EmployeController.groovy");

    }
}
