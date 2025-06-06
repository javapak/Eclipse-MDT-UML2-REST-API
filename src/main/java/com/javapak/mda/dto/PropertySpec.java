package com.javapak.mda.dto;

public class PropertySpec {
    private String name;
    private String typeName;
    private String visibility = "private";
    private boolean isStatic = false;
    private boolean isFinal = false;
    private String defaultValue;
    private int multiplicity = 1;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
    public boolean isStatic() { return isStatic; }
    public void setStatic(boolean isStatic) { this.isStatic = isStatic; }
    public boolean isFinal() { return isFinal; }
    public void setFinal(boolean isFinal) { this.isFinal = isFinal; }
    public String getDefaultValue() { return defaultValue; }
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }
    public int getMultiplicity() { return multiplicity; }
    public void setMultiplicity(int multiplicity) { this.multiplicity = multiplicity; }
}