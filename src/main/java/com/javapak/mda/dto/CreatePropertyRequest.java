package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Type;

public class CreatePropertyRequest {
    private String name;
    private Class owner;
    private Type type;

    public CreatePropertyRequest() {}

    public CreatePropertyRequest(String name, Class owner, Type type) {
        this.name = name;
        this.owner = owner;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getOwner() {
        return owner;
    }

    public void setOwner(Class owner) {
        this.owner = owner;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}