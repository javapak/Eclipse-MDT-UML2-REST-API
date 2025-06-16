package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;

public class CreateParameterRequest {
    private String name;
    private Operation owner;
    private Type type;
    private ParameterDirectionKind direction;

    public CreateParameterRequest() {}

    public CreateParameterRequest(String name, Operation owner, Type type, ParameterDirectionKind direction) {
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Operation getOwner() {
        return owner;
    }

    public void setOwner(Operation owner) {
        this.owner = owner;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ParameterDirectionKind getDirection() {
        return direction;
    }

    public void setDirection(ParameterDirectionKind direction) {
        this.direction = direction;
    }
}