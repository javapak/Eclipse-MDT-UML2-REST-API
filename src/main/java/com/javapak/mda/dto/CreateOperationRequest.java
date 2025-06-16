package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Type;

public class CreateOperationRequest {
    private String name;
    private Class owner;
    private Type returnType;

    public CreateOperationRequest() {}

    public CreateOperationRequest(String name, Class owner, Type returnType) {
        this.name = name;
        this.owner = owner;
        this.returnType = returnType;
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

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }
}