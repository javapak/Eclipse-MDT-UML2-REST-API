package com.javapak.mda.dto;

import java.util.List;

public class OperationSpec {
    private String name;
    private String returnType;
    private String visibility = "public";
    private boolean isAbstract = false;
    private boolean isStatic = false;
    private List<ParameterSpec> parameters;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getReturnType() { return returnType; }
    public void setReturnType(String returnType) { this.returnType = returnType; }
    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
    public boolean isAbstract() { return isAbstract; }
    public void setAbstract(boolean isAbstract) { this.isAbstract = isAbstract; }
    public boolean isStatic() { return isStatic; }
    public void setStatic(boolean isStatic) { this.isStatic = isStatic; }
    public List<ParameterSpec> getParameters() { return parameters; }
    public void setParameters(List<ParameterSpec> parameters) { this.parameters = parameters; }
}