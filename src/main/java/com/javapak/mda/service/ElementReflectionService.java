package com.javapak.mda.service;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.uml.UMLPackage;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ElementReflectionService {
    
    private final UMLPackage umlPackage = UMLPackage.eINSTANCE;
    
    public ElementReflectionInfo getElementReflectionInfo(String elementTypeName) {
        EClass elementClass = findElementClass(elementTypeName);
        if (elementClass == null) {
            throw new IllegalArgumentException("Unknown element type: " + elementTypeName);
        }
        
        return analyzeElementClass(elementClass);
    }
    
    public List<String> getAllElementTypes() {
        return umlPackage.getEClassifiers().stream()
            .filter(EClass.class::isInstance)
            .map(EClass.class::cast)
            .filter(eClass -> !eClass.isAbstract())
            .map(ENamedElement::getName)
            .sorted()
            .collect(Collectors.toList());
    }
    
    public List<String> getConcreteElementTypes() {
        return umlPackage.getEClassifiers().stream()
            .filter(EClass.class::isInstance)
            .map(EClass.class::cast)
            .filter(eClass -> !eClass.isAbstract() && !eClass.isInterface())
            .filter(this::isInstantiable)
            .map(ENamedElement::getName)
            .sorted()
            .collect(Collectors.toList());
    }
    
    private EClass findElementClass(String elementTypeName) {
        return umlPackage.getEClassifiers().stream()
            .filter(EClass.class::isInstance)
            .map(EClass.class::cast)
            .filter(eClass -> eClass.getName().equalsIgnoreCase(elementTypeName))
            .findFirst()
            .orElse(null);
    }
    
    private ElementReflectionInfo analyzeElementClass(EClass elementClass) {
        ElementReflectionInfo info = new ElementReflectionInfo();
        info.setElementType(elementClass.getName());
        info.setIsAbstract(elementClass.isAbstract());
        info.setIsInterface(elementClass.isInterface());
        info.setPackageName(elementClass.getEPackage().getName());
        
        // Get all attributes (properties)
        info.setAttributes(analyzeAttributes(elementClass));
        
        // Get all references (relationships)
        info.setReferences(analyzeReferences(elementClass));
        
        // Get operations (methods)
        info.setOperations(analyzeOperations(elementClass));
        
        // Get supertypes
        info.setSuperTypes(elementClass.getESuperTypes().stream()
            .map(ENamedElement::getName)
            .collect(Collectors.toList()));
        
        // Get subtypes
        info.setSubTypes(findSubTypes(elementClass));
        
        // Determine what operations can be performed
        info.setAvailableOperations(determineAvailableOperations(elementClass));
        
        // Get constraints
        info.setConstraints(analyzeConstraints(elementClass));
        
        return info;
    }
    
    private List<AttributeInfo> analyzeAttributes(EClass elementClass) {
        return elementClass.getEAllAttributes().stream()
            .map(this::analyzeAttribute)
            .collect(Collectors.toList());
    }
    
    private AttributeInfo analyzeAttribute(EAttribute attribute) {
        AttributeInfo info = new AttributeInfo();
        info.setName(attribute.getName());
        info.setType(getAttributeType(attribute));
        info.setIsRequired(!attribute.isUnsettable() && attribute.getLowerBound() > 0);
        info.setIsMany(attribute.isMany());
        info.setIsReadOnly(!attribute.isChangeable());
        info.setDefaultValue(attribute.getDefaultValue());
        info.setLowerBound(attribute.getLowerBound());
        info.setUpperBound(attribute.getUpperBound());
        info.setDescription(getDocumentation(attribute));
        
        // Determine if it's an enumeration
        if (attribute.getEType() instanceof EEnum) {
            EEnum enumType = (EEnum) attribute.getEType();
            info.setEnumValues(enumType.getELiterals().stream()
                .map(EEnumLiteral::getName)
                .collect(Collectors.toList()));
        }
        
        return info;
    }
    
    private List<ReferenceInfo> analyzeReferences(EClass elementClass) {
        return elementClass.getEAllReferences().stream()
            .map(this::analyzeReference)
            .collect(Collectors.toList());
    }
    
    private ReferenceInfo analyzeReference(EReference reference) {
        ReferenceInfo info = new ReferenceInfo();
        info.setName(reference.getName());
        info.setTargetType(reference.getEReferenceType().getName());
        info.setIsContainment(reference.isContainment());
        info.setIsRequired(reference.getLowerBound() > 0);
        info.setIsMany(reference.isMany());
        info.setIsReadOnly(!reference.isChangeable());
        info.setLowerBound(reference.getLowerBound());
        info.setUpperBound(reference.getUpperBound());
        info.setDescription(getDocumentation(reference));
        
        // Check if it has an opposite
        if (reference.getEOpposite() != null) {
            info.setOpposite(reference.getEOpposite().getName());
            info.setIsBidirectional(true);
        }
        
        return info;
    }
    
    private List<OperationInfo> analyzeOperations(EClass elementClass) {
        return elementClass.getEAllOperations().stream()
            .map(this::analyzeOperation)
            .collect(Collectors.toList());
    }
    
    private OperationInfo analyzeOperation(EOperation operation) {
        OperationInfo info = new OperationInfo();
        info.setName(operation.getName());
        info.setReturnType(operation.getEType() != null ? operation.getEType().getName() : "void");
        info.setDescription(getDocumentation(operation));
        
        // Analyze parameters
        info.setParameters(operation.getEParameters().stream()
            .map(this::analyzeParameter)
            .collect(Collectors.toList()));
        
        // Analyze exceptions
        info.setExceptions(operation.getEExceptions().stream()
            .map(ENamedElement::getName)
            .collect(Collectors.toList()));
        
        return info;
    }
    
    private ParameterInfo analyzeParameter(EParameter parameter) {
        ParameterInfo info = new ParameterInfo();
        info.setName(parameter.getName());
        info.setType(parameter.getEType().getName());
        info.setIsRequired(parameter.getLowerBound() > 0);
        info.setIsMany(parameter.isMany());
        info.setDescription(getDocumentation(parameter));
        return info;
    }
    
    private List<String> findSubTypes(EClass elementClass) {
        return umlPackage.getEClassifiers().stream()
            .filter(EClass.class::isInstance)
            .map(EClass.class::cast)
            .filter(eClass -> eClass.getESuperTypes().contains(elementClass))
            .map(ENamedElement::getName)
            .collect(Collectors.toList());
    }
    
    private List<String> determineAvailableOperations(EClass elementClass) {
        List<String> operations = new ArrayList<>();
        
        // Basic CRUD operations
        operations.add("create");
        operations.add("read");
        operations.add("update");
        operations.add("delete");
        
        // Property-based operations
        elementClass.getEAllAttributes().forEach(attr -> {
            operations.add("get" + capitalize(attr.getName()));
            if (attr.isChangeable()) {
                operations.add("set" + capitalize(attr.getName()));
            }
            if (attr.isMany()) {
                operations.add("add" + capitalize(attr.getName()));
                operations.add("remove" + capitalize(attr.getName()));
            }
        });
        
        // Reference-based operations
        elementClass.getEAllReferences().forEach(ref -> {
            operations.add("get" + capitalize(ref.getName()));
            if (ref.isChangeable()) {
                if (ref.isMany()) {
                    operations.add("add" + capitalize(ref.getName()));
                    operations.add("remove" + capitalize(ref.getName()));
                } else {
                    operations.add("set" + capitalize(ref.getName()));
                }
            }
        });
        
        // Element-specific operations
        operations.addAll(getElementSpecificOperations(elementClass.getName()));
        
        return operations.stream().distinct().sorted().collect(Collectors.toList());
    }
    
    private List<String> getElementSpecificOperations(String elementType) {
        List<String> operations = new ArrayList<>();
        
        switch (elementType.toLowerCase()) {
            case "class":
                operations.addAll(List.of("addAttribute", "addOperation", "addGeneralization", 
                    "removeAttribute", "removeOperation", "setAbstract"));
                break;
            case "package":
                operations.addAll(List.of("addElement", "removeElement", "importPackage", 
                    "createNestedPackage"));
                break;
            case "activity":
                operations.addAll(List.of("addNode", "addEdge", "removeNode", "removeEdge", 
                    "validate", "execute"));
                break;
            case "statemachine":
                operations.addAll(List.of("addState", "addTransition", "removeState", 
                    "removeTransition", "validate", "simulate"));
                break;
            // Add more element-specific operations as needed
        }
        
        return operations;
    }
    
    private List<ConstraintInfo> analyzeConstraints(EClass elementClass) {
        List<ConstraintInfo> constraints = new ArrayList<>();
        
        // Analyze structural constraints from the metamodel
        elementClass.getEAllAttributes().forEach(attr -> {
            if (attr.getLowerBound() > 0) {
                constraints.add(new ConstraintInfo(
                    attr.getName() + "_required",
                    "required",
                    attr.getName() + " is required",
                    "structural"
                ));
            }
            
            if (attr.getUpperBound() != -1 && attr.getUpperBound() > 1) {
                constraints.add(new ConstraintInfo(
                    attr.getName() + "_multiplicity",
                    "multiplicity",
                    attr.getName() + " has maximum " + attr.getUpperBound() + " values",
                    "structural"
                ));
            }
        });
        
        elementClass.getEAllReferences().forEach(ref -> {
            if (ref.getLowerBound() > 0) {
                constraints.add(new ConstraintInfo(
                    ref.getName() + "_required",
                    "required",
                    ref.getName() + " is required",
                    "structural"
                ));
            }
        });
        
        return constraints;
    }
    
    private boolean isInstantiable(EClass eClass) {
        // Check if the class can be instantiated (not abstract, has required features, etc.)
        return !eClass.isAbstract() && 
               !eClass.isInterface() && 
               eClass.getEPackage() == umlPackage;
    }
    
    private String getAttributeType(EAttribute attribute) {
        EClassifier type = attribute.getEType();
        if (type instanceof EEnum) {
            return "enum:" + type.getName();
        } else if (type instanceof EDataType) {
            return mapEDataTypeToString((EDataType) type);
        }
        return type.getName();
    }
    
    private String mapEDataTypeToString(EDataType dataType) {
        String name = dataType.getName();
        switch (name) {
            case "EString": return "string";
            case "EInt": return "integer";
            case "EBoolean": return "boolean";
            case "EDouble": return "double";
            case "EFloat": return "float";
            case "EDate": return "date";
            default: return name.toLowerCase();
        }
    }
    
    private String getDocumentation(ENamedElement element) {
        // Try to get documentation from annotations
        return element.getEAnnotations().stream()
            .filter(ann -> "documentation".equals(ann.getSource()))
            .findFirst()
            .map(ann -> ann.getDetails().get("documentation"))
            .orElse("");
    }
    
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    // ========== DATA CLASSES ==========
    
    public static class ElementReflectionInfo {
        private String elementType;
        private boolean isAbstract;
        private boolean isInterface;
        private String packageName;
        private List<AttributeInfo> attributes = new ArrayList<>();
        private List<ReferenceInfo> references = new ArrayList<>();
        private List<OperationInfo> operations = new ArrayList<>();
        private List<String> superTypes = new ArrayList<>();
        private List<String> subTypes = new ArrayList<>();
        private List<String> availableOperations = new ArrayList<>();
        private List<ConstraintInfo> constraints = new ArrayList<>();
        
        // Getters and setters
        public String getElementType() { return elementType; }
        public void setElementType(String elementType) { this.elementType = elementType; }
        
        public boolean isAbstract() { return isAbstract; }
        public void setIsAbstract(boolean isAbstract) { this.isAbstract = isAbstract; }
        
        public boolean isInterface() { return isInterface; }
        public void setIsInterface(boolean isInterface) { this.isInterface = isInterface; }
        
        public String getPackageName() { return packageName; }
        public void setPackageName(String packageName) { this.packageName = packageName; }
        
        public List<AttributeInfo> getAttributes() { return attributes; }
        public void setAttributes(List<AttributeInfo> attributes) { this.attributes = attributes; }
        
        public List<ReferenceInfo> getReferences() { return references; }
        public void setReferences(List<ReferenceInfo> references) { this.references = references; }
        
        public List<OperationInfo> getOperations() { return operations; }
        public void setOperations(List<OperationInfo> operations) { this.operations = operations; }
        
        public List<String> getSuperTypes() { return superTypes; }
        public void setSuperTypes(List<String> superTypes) { this.superTypes = superTypes; }
        
        public List<String> getSubTypes() { return subTypes; }
        public void setSubTypes(List<String> subTypes) { this.subTypes = subTypes; }
        
        public List<String> getAvailableOperations() { return availableOperations; }
        public void setAvailableOperations(List<String> availableOperations) { this.availableOperations = availableOperations; }
        
        public List<ConstraintInfo> getConstraints() { return constraints; }
        public void setConstraints(List<ConstraintInfo> constraints) { this.constraints = constraints; }
    }
    
    public static class AttributeInfo {
        private String name;
        private String type;
        private boolean isRequired;
        private boolean isMany;
        private boolean isReadOnly;
        private Object defaultValue;
        private int lowerBound;
        private int upperBound;
        private String description;
        private List<String> enumValues = new ArrayList<>();
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public boolean isRequired() { return isRequired; }
        public void setIsRequired(boolean isRequired) { this.isRequired = isRequired; }
        
        public boolean isMany() { return isMany; }
        public void setIsMany(boolean isMany) { this.isMany = isMany; }
        
        public boolean isReadOnly() { return isReadOnly; }
        public void setIsReadOnly(boolean isReadOnly) { this.isReadOnly = isReadOnly; }
        
        public Object getDefaultValue() { return defaultValue; }
        public void setDefaultValue(Object defaultValue) { this.defaultValue = defaultValue; }
        
        public int getLowerBound() { return lowerBound; }
        public void setLowerBound(int lowerBound) { this.lowerBound = lowerBound; }
        
        public int getUpperBound() { return upperBound; }
        public void setUpperBound(int upperBound) { this.upperBound = upperBound; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public List<String> getEnumValues() { return enumValues; }
        public void setEnumValues(List<String> enumValues) { this.enumValues = enumValues; }
    }
    
    public static class ReferenceInfo {
        private String name;
        private String targetType;
        private boolean isContainment;
        private boolean isRequired;
        private boolean isMany;
        private boolean isReadOnly;
        private boolean isBidirectional;
        private String opposite;
        private int lowerBound;
        private int upperBound;
        private String description;
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getTargetType() { return targetType; }
        public void setTargetType(String targetType) { this.targetType = targetType; }
        
        public boolean isContainment() { return isContainment; }
        public void setIsContainment(boolean isContainment) { this.isContainment = isContainment; }
        
        public boolean isRequired() { return isRequired; }
        public void setIsRequired(boolean isRequired) { this.isRequired = isRequired; }
        
        public boolean isMany() { return isMany; }
        public void setIsMany(boolean isMany) { this.isMany = isMany; }
        
        public boolean isReadOnly() { return isReadOnly; }
        public void setIsReadOnly(boolean isReadOnly) { this.isReadOnly = isReadOnly; }
        
        public boolean isBidirectional() { return isBidirectional; }
        public void setIsBidirectional(boolean isBidirectional) { this.isBidirectional = isBidirectional; }
        
        public String getOpposite() { return opposite; }
        public void setOpposite(String opposite) { this.opposite = opposite; }
        
        public int getLowerBound() { return lowerBound; }
        public void setLowerBound(int lowerBound) { this.lowerBound = lowerBound; }
        
        public int getUpperBound() { return upperBound; }
        public void setUpperBound(int upperBound) { this.upperBound = upperBound; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    public static class OperationInfo {
        private String name;
        private String returnType;
        private String description;
        private List<ParameterInfo> parameters = new ArrayList<>();
        private List<String> exceptions = new ArrayList<>();
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getReturnType() { return returnType; }
        public void setReturnType(String returnType) { this.returnType = returnType; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public List<ParameterInfo> getParameters() { return parameters; }
        public void setParameters(List<ParameterInfo> parameters) { this.parameters = parameters; }
        
        public List<String> getExceptions() { return exceptions; }
        public void setExceptions(List<String> exceptions) { this.exceptions = exceptions; }
    }
    
    public static class ParameterInfo {
        private String name;
        private String type;
        private boolean isRequired;
        private boolean isMany;
        private String description;
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public boolean isRequired() { return isRequired; }
        public void setIsRequired(boolean isRequired) { this.isRequired = isRequired; }
        
        public boolean isMany() { return isMany; }
        public void setIsMany(boolean isMany) { this.isMany = isMany; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    public static class ConstraintInfo {
        private String name;
        private String type;
        private String description;
        private String category;
        
        public ConstraintInfo(String name, String type, String description, String category) {
            this.name = name;
            this.type = type;
            this.description = description;
            this.category = category;
        }
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }
}