package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;

public class CreateExtensionRequest {
    private String name;
    private Stereotype stereotype;
    private Class metaclass;

    public CreateExtensionRequest() {}

    public CreateExtensionRequest(String name, Stereotype stereotype, Class metaclass) {
        this.name = name;
        this.stereotype = stereotype;
        this.metaclass = metaclass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stereotype getStereotype() {
        return stereotype;
    }

    public void setStereotype(Stereotype stereotype) {
        this.stereotype = stereotype;
    }

    public Class getMetaclass() {
        return metaclass;
    }

    public void setMetaclass(Class metaclass) {
        this.metaclass = metaclass;
    }
}