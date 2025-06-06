package com.javapak.mda.dto;

import java.util.List;

public class CreateModelWithPackagesRequest {
    private String name;
    private String uri;
    private List<String> packageNames;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public List<String> getPackageNames() { return packageNames; }
    public void setPackageNames(List<String> packageNames) { this.packageNames = packageNames; }
}