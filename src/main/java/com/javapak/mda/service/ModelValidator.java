package com.javapak.mda.service;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;

import com.javapak.mda.dto.ValidationResult;

public class ModelValidator {
    
    public ValidationResult validate(Element element) {
        ValidationResult result = new ValidationResult();
        
        // Basic validation rules
        if (element instanceof NamedElement) {
            NamedElement named = (NamedElement) element;
            if (named.getName() == null || named.getName().trim().isEmpty()) {
                result.addError("NamedElement must have a non-empty name");
            }
        }
        
        if (element instanceof Association) {
            Association assoc = (Association) element;
            if (assoc.getMemberEnds().size() < 2) {
                result.addError("Association must have at least 2 member ends");
            }
        }
        
        if (element instanceof Generalization) {
            Generalization gen = (Generalization) element;
            if (gen.getGeneral() == null || gen.getSpecific() == null) {
                result.addError("Generalization must have both general and specific classifiers");
            }
        }
        
        // Add more validation rules as needed
        
        return result;
    }
    
    public ValidationResult validateModel(Model model) {
        ValidationResult result = new ValidationResult();
        
        // Validate the model and all its contents recursively
        result.merge(validate(model));
        
        for (Element element : model.allOwnedElements()) {
            result.merge(validate(element));
        }
        
        return result;
    }
}

