package com.javapak.mda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReactFlowNodeData {
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("svg")
    private String svg;
    
    @JsonProperty("plantUML")
    private String plantUML;
    
    @JsonProperty("elementType")
    private String elementType;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("width")
    private Integer width;
    
    @JsonProperty("height")
    private Integer height;
    
    @JsonProperty("style")
    private String style;
    
    public ReactFlowNodeData() {}
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getSvg() { return svg; }
    public void setSvg(String svg) { this.svg = svg; }
    
    public String getPlantUML() { return plantUML; }
    public void setPlantUML(String plantUML) { this.plantUML = plantUML; }
    
    public String getElementType() { return elementType; }
    public void setElementType(String elementType) { this.elementType = elementType; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }
    
    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }
    
    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }
}