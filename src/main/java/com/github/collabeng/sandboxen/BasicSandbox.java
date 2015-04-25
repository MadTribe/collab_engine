package com.github.collabeng.sandboxen;

import groovy.lang.Closure;
import groovy.lang.Script;
import org.kohsuke.groovy.sandbox.GroovyValueFilter;

import java.util.HashSet;
import java.util.Set;

/**
* Created by paul.smout on 15/04/2015.
*/
public class BasicSandbox extends GroovyValueFilter {
    protected Set<Class> allowedTypes = new HashSet<>();

    public BasicSandbox() {
        allowedTypes.add(Integer.class);
        allowedTypes.add(Boolean.class);
        allowedTypes.add(String.class);
    }

    @Override
    public Object filter(Object o) {
        if (o==null || allowedTypes.contains(o.getClass())){
            return o;
        }
        if (o instanceof Script || o instanceof Closure){
            return o; // access to properties of compiled groovy script
        }
        throw new ScriptSecurityException("Cannot access type: " + o.getClass());
    }

    public Set<Class> getAllowedTypes() {
        return allowedTypes;
    }
}
