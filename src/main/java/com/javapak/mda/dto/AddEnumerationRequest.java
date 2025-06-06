package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Model;
import java.util.List;

public class AddEnumerationRequest {
    private Model model;
    private String enumerationName;
    private String packageName;
    private String visibility = "public";
    private List<String> literals;
    
    public Model getModel() { return model; }
    public void setModel(Model model) { this.model = model; }
    public String getEnumerationName() { return enumerationName; }
    public void setEnumerationName(String enumerationName) { this.enumerationName = enumerationName; }
    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
    public List<String> getLiterals() { return literals; }
    public void setLiterals(List<String> literals) { this.literals = literals; }
}