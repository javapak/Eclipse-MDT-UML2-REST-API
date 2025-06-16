package com.javapak.mda.dto;

import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Interaction;

public class CreateLifelineRequest {
    private String name;
    private Interaction owner;
    private ConnectableElement represents;

    public CreateLifelineRequest() {}

    public CreateLifelineRequest(String name, Interaction owner, ConnectableElement represents) {
        this.name = name;
        this.owner = owner;
        this.represents = represents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Interaction getOwner() {
        return owner;
    }

    public void setOwner(Interaction owner) {
        this.owner = owner;
    }

    public ConnectableElement getRepresents() {
        return represents;
    }

    public void setRepresents(ConnectableElement represents) {
        this.represents = represents;
    }
}