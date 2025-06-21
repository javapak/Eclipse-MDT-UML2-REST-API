package com.javapak.mda.controller;

import com.javapak.mda.service.ElementReflectionService;
import com.javapak.mda.service.ElementReflectionService.ElementReflectionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reflection")
@CrossOrigin(origins = "*")
public class ElementReflectionController {

    @Autowired
    private ElementReflectionService reflectionService;

    @GetMapping("/elements")
    public ResponseEntity<List<String>> getAllElementTypes() {
        try {
            List<String> elementTypes = reflectionService.getAllElementTypes();
            return ResponseEntity.ok(elementTypes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/elements/concrete")
    public ResponseEntity<List<String>> getConcreteElementTypes() {
        try {
            List<String> elementTypes = reflectionService.getConcreteElementTypes();
            return ResponseEntity.ok(elementTypes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/elements/{elementType}")
    public ResponseEntity<ElementReflectionInfo> getElementInfo(@PathVariable String elementType) {
        try {
            ElementReflectionInfo info = reflectionService.getElementReflectionInfo(elementType);
            return ResponseEntity.ok(info);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/elements/{elementType}/attributes")
    public ResponseEntity<List<ElementReflectionService.AttributeInfo>> getElementAttributes(@PathVariable String elementType) {
        try {
            ElementReflectionInfo info = reflectionService.getElementReflectionInfo(elementType);
            return ResponseEntity.ok(info.getAttributes());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/elements/{elementType}/references")
    public ResponseEntity<List<ElementReflectionService.ReferenceInfo>> getElementReferences(@PathVariable String elementType) {
        try {
            ElementReflectionInfo info = reflectionService.getElementReflectionInfo(elementType);
            return ResponseEntity.ok(info.getReferences());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/elements/{elementType}/operations")
    public ResponseEntity<List<String>> getAvailableOperations(@PathVariable String elementType) {
        try {
            ElementReflectionInfo info = reflectionService.getElementReflectionInfo(elementType);
            return ResponseEntity.ok(info.getAvailableOperations());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/elements/{elementType}/constraints")
    public ResponseEntity<List<ElementReflectionService.ConstraintInfo>> getElementConstraints(@PathVariable String elementType) {
        try {
            ElementReflectionInfo info = reflectionService.getElementReflectionInfo(elementType);
            return ResponseEntity.ok(info.getConstraints());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/elements/{elementType}/supertypes")
    public ResponseEntity<List<String>> getElementSuperTypes(@PathVariable String elementType) {
        try {
            ElementReflectionInfo info = reflectionService.getElementReflectionInfo(elementType);
            return ResponseEntity.ok(info.getSuperTypes());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/elements/{elementType}/subtypes")
    public ResponseEntity<List<String>> getElementSubTypes(@PathVariable String elementType) {
        try {
            ElementReflectionInfo info = reflectionService.getElementReflectionInfo(elementType);
            return ResponseEntity.ok(info.getSubTypes());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        try {
            int totalElements = reflectionService.getAllElementTypes().size();
            int concreteElements = reflectionService.getConcreteElementTypes().size();
            
            return ResponseEntity.ok(Map.of(
                "status", "healthy",
                "service", "ElementReflectionService",
                "totalElementTypes", totalElements,
                "concreteElementTypes", concreteElements,
                "umlMetamodel", "Eclipse UML2"
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "status", "error",
                "message", e.getMessage()
            ));
        }
    }
}