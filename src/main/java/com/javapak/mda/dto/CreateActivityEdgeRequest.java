package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityNode;

public class CreateActivityEdgeRequest {
    private String name;
    private Activity owner;
    private ActivityNode source;
    private ActivityNode target;

    public CreateActivityEdgeRequest() {}

    public CreateActivityEdgeRequest(String name, Activity owner, ActivityNode source, ActivityNode target) {
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

    public Activity getOwner() {
        return owner;
    }

    public void setOwner(Activity owner) {
        this.owner = owner;
    }

    public ActivityNode getSource() {
        return source;
    }

    public void setSource(ActivityNode source) {
        this.source = source;
    }

    public ActivityNode getTarget() {
        return target;
    }

    public void setTarget(ActivityNode target) {
        this.target = target;
    }
}