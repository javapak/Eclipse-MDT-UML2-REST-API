package com.javapak.mda.dto;

public class ParameterSpec {
    private String name;
    private String typeName;
    private String direction = "in"; // "in", "out", "inout", "return"
    private String defaultValue;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
    public String getDefaultValue() { return defaultValue; }
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }
}