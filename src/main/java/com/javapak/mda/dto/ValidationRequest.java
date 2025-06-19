package com.javapak.mda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.uml2.uml.Element;

public class ValidationRequest {
    
    @JsonProperty("element")
    private Element element;
    
    @JsonProperty("validateRecursively")
    private boolean validateRecursively = false;
    
    public ValidationRequest() {}
    
    // Getters and setters
    public Element getElement() { return element; }
    public void setElement(Element element) { this.element = element; }
    
    public boolean isValidateRecursively() { return validateRecursively; }
    public void setValidateRecursively(boolean validateRecursively) { this.validateRecursively = validateRecursively; }
}