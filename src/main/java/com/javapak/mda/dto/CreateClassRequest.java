package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Package;

public class CreateClassRequest {
    private String name;
    private Package owner;
    private boolean isAbstract;

    public CreateClassRequest() {}

    public CreateClassRequest(String name, Package owner, boolean isAbstract) {
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

    public Package getOwner() {
        return owner;
    }

    public void setOwner(Package owner) {
        this.owner = owner;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }
}