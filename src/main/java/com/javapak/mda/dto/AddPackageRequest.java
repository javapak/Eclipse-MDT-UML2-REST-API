package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Model;

public class AddPackageRequest {
    private Model model;
    private String packageName;
    private String parentPackageName; // null for root level
    
    public Model getModel() { return model; }
    public void setModel(Model model) { this.model = model; }
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public String getParentPackageName() { return parentPackageName; }
    public void setParentPackageName(String parentPackageName) { this.parentPackageName = parentPackageName; }
}