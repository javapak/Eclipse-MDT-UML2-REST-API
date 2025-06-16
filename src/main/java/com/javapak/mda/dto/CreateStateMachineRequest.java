package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Class;

public class CreateStateMachineRequest {
    private String name;
    private Class owner;

    public CreateStateMachineRequest() {}

    public CreateStateMachineRequest(String name, Class owner) {
        this.name = name;
        this.owner = owner;
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
}