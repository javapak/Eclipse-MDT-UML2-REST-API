package com.javapak.mda.dto;

import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;

public class CreateUsageRequest {
    private String name;
    private Package owner;
    private NamedElement client;
    private NamedElement supplier;

    public CreateUsageRequest() {}

    public CreateUsageRequest(String name, Package owner, NamedElement client, NamedElement supplier) {
        this.name = name;
        this.owner = owner;
        this.client = client;
        this.supplier = supplier;
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

    public NamedElement getClient() {
        return client;
    }

    public void setClient(NamedElement client) {
        this.client = client;
    }

    public NamedElement getSupplier() {
        return supplier;
    }

    public void setSupplier(NamedElement supplier) {
        this.supplier = supplier;
    }
}