package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Namespace;

public class CreateConstraintRequest {
    private String name;
    private Namespace owner;
    private String body;
    private String language;

    public CreateConstraintRequest() {}

    public CreateConstraintRequest(String name, Namespace owner, String body, String language) {
        this.name = name;
        this.owner = owner;
        this.body = body;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Namespace getOwner() {
        return owner;
    }

    public void setOwner(Namespace owner) {
        this.owner = owner;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}