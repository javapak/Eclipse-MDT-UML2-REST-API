package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.MessageSort;

public class CreateMessageRequest {
    private String name;
    private Interaction owner;
    private MessageSort sort;

    public CreateMessageRequest() {}

    public CreateMessageRequest(String name, Interaction owner, MessageSort sort) {
        this.name = name;
        this.owner = owner;
        this.sort = sort;
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

    public MessageSort getSort() {
        return sort;
    }

    public void setSort(MessageSort sort) {
        this.sort = sort;
    }
}