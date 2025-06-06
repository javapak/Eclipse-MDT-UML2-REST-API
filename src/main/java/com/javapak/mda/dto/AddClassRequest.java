package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Model;
import java.util.List;

public class AddClassRequest {
    private Model model;
    private String className;
    private String packageName; // null for root level
    private String visibility = "public";
    private boolean isAbstract = false;
    private List<PropertySpec> properties;
    
    public Model getModel() { return model; }
    public void setModel(Model model) { this.model = model; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
    public boolean isAbstract() { return isAbstract; }
    public void setAbstract(boolean isAbstract) { this.isAbstract = isAbstract; }
    public List<PropertySpec> getProperties() { return properties; }
    public void setProperties(List<PropertySpec> properties) { this.properties = properties; }
}