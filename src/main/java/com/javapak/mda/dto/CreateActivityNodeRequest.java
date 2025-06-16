package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Activity;

public class CreateActivityNodeRequest {
    private String name;
    private Activity owner;
    private Class nodeType;

    public CreateActivityNodeRequest() {}

    public CreateActivityNodeRequest(String name, Activity owner, Class nodeType) {
        this.name = name;
        this.owner = owner;
        this.nodeType = nodeType;
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

    public Class getNodeType() {
        return nodeType;
    }

    public void setNodeType(Class nodeType) {
        this.nodeType = nodeType;
    }
}