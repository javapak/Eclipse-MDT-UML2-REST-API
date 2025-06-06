package com.javapak.mda.dto;

public class CreateFromTemplateRequest {
    private String name;
    private String uri;
    private String templateType; // "domain", "component", "layered", "microservice"
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public String getTemplateType() { return templateType; }
    public void setTemplateType(String templateType) { this.templateType = templateType; }
}