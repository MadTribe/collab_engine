package com.github.collabeng.sandboxen;

import com.github.collabeng.api.error.ScriptException;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
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

    public String scriptRunner(final String script, final Object api, GroovyValueFilter valueFilter) {
        final ImportCustomizer imports = new ImportCustomizer();
        imports.addStaticStars("java.lang.Math");


        final CompilerConfiguration config = new CompilerConfiguration();
        config.addCompilationCustomizers(imports, new SandboxTransformer());

        final Binding intBinding = new Binding(); // alow parameters in the script
        intBinding.setVariable("api", api);

        final GroovyShell shell = new GroovyShell(intBinding, config); // create shall

        // code execution
        Object clos = null;


        valueFilter.register();
        try {
            clos = shell.evaluate(script);
        } catch (RuntimeException e){
            throw new ScriptException("Error Running Script", e);
        } finally {
            valueFilter.unregister();
        }

        if (clos == null) {
            return "No result avalible!";
        }
        return clos.toString();
    }

    public Set<Class> getAllowedTypes() {
        return allowedTypes;
    }
}
