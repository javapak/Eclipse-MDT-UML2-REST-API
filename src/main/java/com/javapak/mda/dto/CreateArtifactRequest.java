package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Package;

public class CreateArtifactRequest {
    private String name;
    private Package owner;

    public CreateArtifactRequest() {}

    public CreateArtifactRequest(String name, Package owner) {
        this.name = name;
        this.owner = owner;
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
}