package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;

public class ApplyProfileRequest {
    private Model model;
    private Profile profile;
    
    public Model getModel() { return model; }
    public void setModel(Model model) { this.model = model; }
    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }
}