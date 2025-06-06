package com.javapak.mda.dto;

public class ValidationWarning {
    private String message;
    private String elementName;
    private String elementType;
    private String severity = "WARNING";
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getElementName() { return elementName; }
    public void setElementName(String elementName) { this.elementName = elementName; }
    public String getElementType() { return elementType; }
    public void setElementType(String elementType) { this.elementType = elementType; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
}