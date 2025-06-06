package com.javapak.mda.dto;

public class CreateModelRequest {
    private String name;
    private String uri;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
}