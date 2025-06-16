package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Element;

public class ExportXMIRequest {
    private Element element;

    public ExportXMIRequest() {}

    public ExportXMIRequest(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}