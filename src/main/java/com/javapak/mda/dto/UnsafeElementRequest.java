package com.javapak.mda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnsafeElementRequest {
    
    @JsonProperty("elementType")
    private String elementType;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("initialize")
    private boolean initialize = false;
    
    @JsonProperty("validate")
    private boolean validate = true;
    
    @JsonProperty("suppressWarnings")
    private boolean suppressWarnings = false;
    
    public UnsafeElementRequest() {}
    
    public UnsafeElementRequest(String elementType, String name) {
        this.elementType = elementType;
        this.name = name;
    }
    
    // Getters and setters
    public String getElementType() { return elementType; }
    public void setElementType(String elementType) { this.elementType = elementType; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public boolean isInitialize() { return initialize; }
    public void setInitialize(boolean initialize) { this.initialize = initialize; }
    
    public boolean isValidate() { return validate; }
    public void setValidate(boolean validate) { this.validate = validate; }
    
    public boolean isSuppressWarnings() { return suppressWarnings; }
    public void setSuppressWarnings(boolean suppressWarnings) { this.suppressWarnings = suppressWarnings; }
}