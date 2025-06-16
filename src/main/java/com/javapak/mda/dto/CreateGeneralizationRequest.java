package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Classifier;

public class CreateGeneralizationRequest {
    private Classifier specific;
    private Classifier general;

    public CreateGeneralizationRequest() {}

    public CreateGeneralizationRequest(Classifier specific, Classifier general) {
        this.specific = specific;
        this.general = general;
    }

    public Classifier getSpecific() {
        return specific;
    }

    public void setSpecific(Classifier specific) {
        this.specific = specific;
    }

    public Classifier getGeneral() {
        return general;
    }

    public void setGeneral(Classifier general) {
        this.general = general;
    }
}