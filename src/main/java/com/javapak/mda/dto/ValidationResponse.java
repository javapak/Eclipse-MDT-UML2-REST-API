package com.javapak.mda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.ArrayList;

public class ValidationResponse {
    
    @JsonProperty("hasErrors")
    private boolean hasErrors;
    
    @JsonProperty("hasWarnings") 
    private boolean hasWarnings;
    
    @JsonProperty("errors")
    private List<String> errors = new ArrayList<>();
    
    @JsonProperty("warnings")
    private List<String> warnings = new ArrayList<>();
    
    @JsonProperty("summary")
    private String summary;
    
    public ValidationResponse() {}
    
    public ValidationResponse(boolean hasErrors, boolean hasWarnings, List<String> errors, List<String> warnings) {
        this.hasErrors = hasErrors;
        this.hasWarnings = hasWarnings;
        this.errors = errors != null ? new ArrayList<>(errors) : new ArrayList<>();
        this.warnings = warnings != null ? new ArrayList<>(warnings) : new ArrayList<>();
        this.summary = generateSummary();
    }
    
    private String generateSummary() {
        if (!hasErrors && !hasWarnings) {
            return "Validation passed - no errors or warnings";
        }
        return String.format("Validation completed with %d error(s) and %d warning(s)", 
                           errors.size(), warnings.size());
    }
    
    // Getters and setters
    public boolean isHasErrors() { return hasErrors; }
    public void setHasErrors(boolean hasErrors) { this.hasErrors = hasErrors; }
    
    public boolean isHasWarnings() { return hasWarnings; }
    public void setHasWarnings(boolean hasWarnings) { this.hasWarnings = hasWarnings; }
    
    public List<String> getErrors() { return new ArrayList<>(errors); }
    public void setErrors(List<String> errors) { this.errors = errors != null ? new ArrayList<>(errors) : new ArrayList<>(); }
    
    public List<String> getWarnings() { return new ArrayList<>(warnings); }
    public void setWarnings(List<String> warnings) { this.warnings = warnings != null ? new ArrayList<>(warnings) : new ArrayList<>(); }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}