package com.javapak.mda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BatchElementRequest {
    
    @JsonProperty("elementType")
    private String elementType;
    
    @JsonProperty("count")
    private int count = 1;
    
    @JsonProperty("namePrefix")
    private String namePrefix;
    
    @JsonProperty("initialize")
    private boolean initialize = false;
    
    public BatchElementRequest() {}
    
    // Getters and setters
    public String getElementType() { return elementType; }
    public void setElementType(String elementType) { this.elementType = elementType; }
    
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    
    public String getNamePrefix() { return namePrefix; }
    public void setNamePrefix(String namePrefix) { this.namePrefix = namePrefix; }
    
    public boolean isInitialize() { return initialize; }
    public void setInitialize(boolean initialize) { this.initialize = initialize; }
}