package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Type;

public class CreateAssociationRequest {
    private String name;
    private Package owner;
    private Type end1Type;
    private Type end2Type;

    public CreateAssociationRequest() {}

    public CreateAssociationRequest(String name, Package owner, Type end1Type, Type end2Type) {
        this.name = name;
        this.owner = owner;
        this.end1Type = end1Type;
        this.end2Type = end2Type;
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

    public Type getEnd1Type() {
        return end1Type;
    }

    public void setEnd1Type(Type end1Type) {
        this.end1Type = end1Type;
    }

    public Type getEnd2Type() {
        return end2Type;
    }

    public void setEnd2Type(Type end2Type) {
        this.end2Type = end2Type;
    }
}