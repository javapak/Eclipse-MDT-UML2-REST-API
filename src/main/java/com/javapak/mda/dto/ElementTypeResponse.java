package com.javapak.mda.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.List;

public class ElementTypeResponse {
    
    @JsonProperty("elementTypes")
    private List<ElementType> elementTypes;
    
    @JsonProperty("metadata")
    private Metadata metadata;
    
    // Constructors
    public ElementTypeResponse() {}
    
    public ElementTypeResponse(List<ElementType> elementTypes, Metadata metadata) {
        this.elementTypes = elementTypes;
        this.metadata = metadata;
    }
    
    // Getters and Setters
    public List<ElementType> getElementTypes() {
        return elementTypes;
    }
    
    public void setElementTypes(List<ElementType> elementTypes) {
        this.elementTypes = elementTypes;
    }
    
    public Metadata getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
    
    public static class ElementType {
        @JsonProperty("id")
        private String id;
        
        @JsonProperty("type")
        private String type;
        
        @JsonProperty("category")
        private String category;
        
        @JsonProperty("description")
        private String description;
        
        @JsonProperty("plantUmlCode")
        private String plantUmlCode;
        
        @JsonProperty("svgContent")
        private String svgContent;
        
        // Constructors
        public ElementType() {}
        
        public ElementType(String type, String category, String description) {
            this.id = type + "-" + category;
            this.type = type;
            this.category = category;
            this.description = description;
        }
        
        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getPlantUmlCode() { return plantUmlCode; }
        public void setPlantUmlCode(String plantUmlCode) { this.plantUmlCode = plantUmlCode; }
        
        public String getSvgContent() { return svgContent; }
        public void setSvgContent(String svgContent) { this.svgContent = svgContent; }
    }
    
    public static class Metadata {
        @JsonProperty("totalCount")
        private int totalCount;
        
        @JsonProperty("categories")
        private List<String> categories;
        
        @JsonProperty("generatedAt")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime generatedAt;
        
        @JsonProperty("cacheExpiry")
        private Integer cacheExpiry;
        
        // Constructors
        public Metadata() {}
        
        public Metadata(int totalCount, List<String> categories, LocalDateTime generatedAt, Integer cacheExpiry) {
            this.totalCount = totalCount;
            this.categories = categories;
            this.generatedAt = generatedAt;
            this.cacheExpiry = cacheExpiry;
        }
        
        // Getters and Setters
        public int getTotalCount() { return totalCount; }
        public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
        
        public List<String> getCategories() { return categories; }
        public void setCategories(List<String> categories) { this.categories = categories; }
        
        public LocalDateTime getGeneratedAt() { return generatedAt; }
        public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
        
        public Integer getCacheExpiry() { return cacheExpiry; }
        public void setCacheExpiry(Integer cacheExpiry) { this.cacheExpiry = cacheExpiry; }
    }
}