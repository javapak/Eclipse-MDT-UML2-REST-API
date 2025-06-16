package com.javapak.mda.dto;

import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Interface;

public class CreateInterfaceRealizationRequest {
    private String name;
    private BehavioredClassifier implementer;
    private Interface contract;

    public CreateInterfaceRealizationRequest() {}

    public CreateInterfaceRealizationRequest(String name, BehavioredClassifier implementer, Interface contract) {
        this.name = name;
        this.implementer = implementer;
        this.contract = contract;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BehavioredClassifier getImplementer() {
        return implementer;
    }

    public void setImplementer(BehavioredClassifier implementer) {
        this.implementer = implementer;
    }

    public Interface getContract() {
        return contract;
    }

    public void setContract(Interface contract) {
        this.contract = contract;
    }
}