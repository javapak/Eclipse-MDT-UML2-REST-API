package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Profile;
import java.util.List;

public class AddStereotypeRequest {
    private Profile profile;
    private String stereotypeName;
    private String baseClass; // "Class", "Property", "Operation", etc.
    private List<PropertySpec> attributes;
    
    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }
    public String getStereotypeName() { return stereotypeName; }
    public void setStereotypeName(String stereotypeName) { this.stereotypeName = stereotypeName; }
    public String getBaseClass() { return baseClass; }
    public void setBaseClass(String baseClass) { this.baseClass = baseClass; }
    public List<PropertySpec> getAttributes() { return attributes; }
    public void setAttributes(List<PropertySpec> attributes) { this.attributes = attributes; }
}