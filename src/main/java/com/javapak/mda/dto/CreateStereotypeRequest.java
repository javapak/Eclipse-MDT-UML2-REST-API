package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Profile;

public class CreateStereotypeRequest {
    private String name;
    private Profile owner;
    private boolean isAbstract;

    public CreateStereotypeRequest() {}

    public CreateStereotypeRequest(String name, Profile owner, boolean isAbstract) {
        this.name = name;
        this.owner = owner;
        this.isAbstract = isAbstract;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profile getOwner() {
        return owner;
    }

    public void setOwner(Profile owner) {
        this.owner = owner;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }
}