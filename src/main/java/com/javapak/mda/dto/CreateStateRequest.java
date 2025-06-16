package com.javapak.mda.dto;

import org.eclipse.uml2.uml.StateMachine;

public class CreateStateRequest {
    private String name;
    private StateMachine owner;

    public CreateStateRequest() {}

    public CreateStateRequest(String name, StateMachine owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateMachine getOwner() {
        return owner;
    }

    public void setOwner(StateMachine owner) {
        this.owner = owner;
    }
}