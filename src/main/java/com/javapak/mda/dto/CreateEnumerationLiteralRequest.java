package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Enumeration;

public class CreateEnumerationLiteralRequest {
    private String name;
    private Enumeration owner;

    public CreateEnumerationLiteralRequest() {}

    public CreateEnumerationLiteralRequest(String name, Enumeration owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enumeration getOwner() {
        return owner;
    }

    public void setOwner(Enumeration owner) {
        this.owner = owner;
    }
}