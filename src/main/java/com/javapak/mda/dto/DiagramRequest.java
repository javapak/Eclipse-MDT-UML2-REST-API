package com.javapak.mda.dto;

import org.eclipse.uml2.uml.Element;
import java.util.List;
import java.util.Map;

public class DiagramRequest {
    private String type;
    private List<Element> elements;
    private String style;
    private Map<String, Object> styleConfig;
    private Map<String, Object> options;
    
    public DiagramRequest() {}
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public List<Element> getElements() { return elements; }
    public void setElements(List<Element> elements) { this.elements = elements; }
    
    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }
    
    public Map<String, Object> getStyleConfig() { return styleConfig; }
    public void setStyleConfig(Map<String, Object> styleConfig) { this.styleConfig = styleConfig; }
    
    public Map<String, Object> getOptions() { return options; }
    public void setOptions(Map<String, Object> options) { this.options = options; }
}