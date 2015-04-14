package com.github.collabeng.sandboxen;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;
import org.kohsuke.groovy.sandbox.GroovyValueFilter;
import org.kohsuke.groovy.sandbox.SandboxTransformer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by paul.smout on 14/04/2015.
 */
public class GroovySandbox {

    protected Set<Class> allowedTypes = new HashSet<>();

    public GroovySandbox() {
        allowedTypes.add(Integer.class);
        allowedTypes.add(Boolean.class);
        allowedTypes.add(String.class);
    }

    public String scriptRunner(final String script, final String query) {
        final ImportCustomizer imports = new ImportCustomizer();
        imports.addStaticStars("java.lang.Math");


        final CompilerConfiguration config = new CompilerConfiguration();
        config.addCompilationCustomizers(imports, new SandboxTransformer());

        final Binding intBinding = new Binding(); // alow parameters in the script
        intBinding.setVariable("query", query);

        final GroovyShell shell = new GroovyShell(intBinding, config); // create shall

        // code execution
        final Object clos = shell.evaluate(script);

        RobotSandbox sandbox = new RobotSandbox();
        sandbox.register();
        try {
            shell.evaluate(script);
        } finally {
            sandbox.unregister();
        }

        if (clos == null) {
            return "No result avalible!";
        }
        return clos.toString();
    }

    class RobotSandbox extends GroovyValueFilter {
        @Override
        public Object filter(Object o) {
            if (o==null || allowedTypes.contains(o.getClass())){
                return o;
            }
            if (o instanceof Script || o instanceof Closure){
                return o; // access to properties of compiled groovy script
            }
            throw new SecurityException("Oops, unexpected type: " + o.getClass());
        }


    }
}
