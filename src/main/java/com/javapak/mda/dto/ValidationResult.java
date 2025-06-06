package com.javapak.mda.dto;

import java.util.List;

public class ValidationResult {
    private boolean isValid;
    private List<ValidationError> errors;
    private List<ValidationWarning> warnings;
    
    public boolean isValid() { return isValid; }
    public void setValid(boolean isValid) { this.isValid = isValid; }
    public List<ValidationError> getErrors() { return errors; }
    public void setErrors(List<ValidationError> errors) { this.errors = errors; }
    public List<ValidationWarning> getWarnings() { return warnings; }
    public void setWarnings(List<ValidationWarning> warnings) { this.warnings = warnings; }
}