package com.javapak.mda.dto;

import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Vertex;

public class CreateTransitionRequest {
    private String name;
    private StateMachine owner;
    private Vertex source;
    private Vertex target;

    public CreateTransitionRequest() {}

    public CreateTransitionRequest(String name, StateMachine owner, Vertex source, Vertex target) {
        this.name = name;
        this.owner = owner;
        this.source = source;
        this.target = target;
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

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getTarget() {
        return target;
    }

    public void setTarget(Vertex target) {
        this.target = target;
    }
}