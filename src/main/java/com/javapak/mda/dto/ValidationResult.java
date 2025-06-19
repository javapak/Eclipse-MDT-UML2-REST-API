package com.javapak.mda.dto;

public class ValidationResult {
    private java.util.List<String> errors = new java.util.ArrayList<>();
    private java.util.List<String> warnings = new java.util.ArrayList<>();
    
    public void addError(String error) {
        errors.add(error);
    }
    
    public void addWarning(String warning) {
        warnings.add(warning);
    }
    
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
    
    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }
    
    public java.util.List<String> getErrors() {
        return new java.util.ArrayList<>(errors);
    }
    
    public java.util.List<String> getWarnings() {
        return new java.util.ArrayList<>(warnings);
    }
    
    public void merge(ValidationResult other) {
        this.errors.addAll(other.errors);
        this.warnings.addAll(other.warnings);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (hasErrors()) {
            sb.append("Errors:\n");
            errors.forEach(error -> sb.append("  - ").append(error).append("\n"));
        }
        if (hasWarnings()) {
            sb.append("Warnings:\n");
            warnings.forEach(warning -> sb.append("  - ").append(warning).append("\n"));
        }
        if (!hasErrors() && !hasWarnings()) {
            sb.append("Validation passed - no errors or warnings");
        }
        return sb.toString();
    }
}