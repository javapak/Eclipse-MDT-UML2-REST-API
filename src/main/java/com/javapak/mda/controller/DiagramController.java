package com.javapak.mda.controller;

import com.javapak.mda.service.PlantUMLService;
import com.javapak.mda.service.ModelingService;
import org.eclipse.uml2.uml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/diagrams")
@CrossOrigin(origins = "*")
public class DiagramController {

    @Autowired
    private PlantUMLService plantUMLService;

    

    // ========== SVG GENERATION FOR REACTFLOW NODES ==========
    
    @PostMapping("/element/svg")
    public ResponseEntity<String> generateElementSVG(@RequestBody Element element) {
        try {
            String svg = plantUMLService.generateNodeSVG(element);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/svg+xml"));
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(svg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate SVG: " + e.getMessage());
        }
    }
    
    @PostMapping("/element/svg/{style}")
    public ResponseEntity<String> generateStyledElementSVG(
            @RequestBody Element element, 
            @PathVariable String style) {
        try {
            String svg = plantUMLService.generateReactFlowNodeSVG(element, style);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/svg+xml"));
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(svg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate styled SVG: " + e.getMessage());
        }
    }
    
    @PostMapping("/class/svg")
    public ResponseEntity<String> generateClassSVG(@RequestBody org.eclipse.uml2.uml.Class clazz) {
        try {
            String plantUML = plantUMLService.convertSingleClass(clazz);
            String svg = plantUMLService.generateSVG(plantUML);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/svg+xml"));
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(svg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate class SVG: " + e.getMessage());
        }
    }
    
    @PostMapping("/interface/svg")
    public ResponseEntity<String> generateInterfaceSVG(@RequestBody Interface iface) {
        try {
            String plantUML = plantUMLService.convertSingleInterface(iface);
            String svg = plantUMLService.generateSVG(plantUML);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/svg+xml"));
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(svg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate interface SVG: " + e.getMessage());
        }
    }

    // ========== FULL DIAGRAM GENERATION ==========
    
    @PostMapping("/class-diagram/svg")
    public ResponseEntity<String> generateClassDiagramSVG(@RequestBody Map<String, Object> diagramData) {
        try {
            @SuppressWarnings("unchecked")
            List<org.eclipse.uml2.uml.Class> classes = (List<org.eclipse.uml2.uml.Class>) diagramData.get("classes");
            @SuppressWarnings("unchecked")
            List<Interface> interfaces = (List<Interface>) diagramData.get("interfaces");
            @SuppressWarnings("unchecked")
            List<Association> associations = (List<Association>) diagramData.get("associations");
            
            String plantUML = plantUMLService.convertClassDiagram(
                classes != null ? classes : List.of(),
                interfaces != null ? interfaces : List.of(),
                associations != null ? associations : List.of()
            );
            
            String svg = plantUMLService.generateSVG(plantUML);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/svg+xml"));
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(svg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate class diagram SVG: " + e.getMessage());
        }
    }
    
    @PostMapping("/activity-diagram/svg")
    public ResponseEntity<String> generateActivityDiagramSVG(@RequestBody Activity activity) {
        try {
            String plantUML = plantUMLService.convertActivityDiagram(activity);
            String svg = plantUMLService.generateSVG(plantUML);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/svg+xml"));
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(svg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate activity diagram SVG: " + e.getMessage());
        }
    }
    
    @PostMapping("/state-machine/svg")
    public ResponseEntity<String> generateStateMachineSVG(@RequestBody StateMachine stateMachine) {
        try {
            String plantUML = plantUMLService.convertStateMachine(stateMachine);
            String svg = plantUMLService.generateSVG(plantUML);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/svg+xml"));
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(svg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate state machine SVG: " + e.getMessage());
        }
    }
    
    @PostMapping("/sequence-diagram/svg")
    public ResponseEntity<String> generateSequenceDiagramSVG(@RequestBody Interaction interaction) {
        try {
            String plantUML = plantUMLService.convertSequenceDiagram(interaction);
            String svg = plantUMLService.generateSVG(plantUML);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/svg+xml"));
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(svg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate sequence diagram SVG: " + e.getMessage());
        }
    }

    // ========== PLANTUML SOURCE GENERATION ==========
    
    @PostMapping("/class/plantuml")
    public ResponseEntity<Map<String, String>> generateClassPlantUML(@RequestBody org.eclipse.uml2.uml.Class clazz) {
        try {
            String plantUML = plantUMLService.convertSingleClass(clazz);
            return ResponseEntity.ok(Map.of("plantUML", plantUML));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to generate PlantUML: " + e.getMessage()));
        }
    }
    
    @PostMapping("/interface/plantuml")
    public ResponseEntity<Map<String, String>> generateInterfacePlantUML(@RequestBody Interface iface) {
        try {
            String plantUML = plantUMLService.convertSingleInterface(iface);
            return ResponseEntity.ok(Map.of("plantUML", plantUML));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to generate PlantUML: " + e.getMessage()));
        }
    }
    
    @PostMapping("/activity/plantuml")
    public ResponseEntity<Map<String, String>> generateActivityPlantUML(@RequestBody Activity activity) {
        try {
            String plantUML = plantUMLService.convertActivityDiagram(activity);
            return ResponseEntity.ok(Map.of("plantUML", plantUML));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to generate PlantUML: " + e.getMessage()));
        }
    }

    // ========== PNG GENERATION (BASE64 ENCODED) ==========
    
    @PostMapping("/element/png")
    public ResponseEntity<Map<String, String>> generateElementPNG(@RequestBody Element element) {
        try {
            String plantUML = plantUMLService.convertElementToPlantUML(element);
            String base64PNG = plantUMLService.generatePNG(plantUML);
            
            return ResponseEntity.ok(Map.of(
                "png", base64PNG,
                "mimeType", "image/png"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to generate PNG: " + e.getMessage()));
        }
    }

    // ========== REACTFLOW INTEGRATION HELPERS ==========
    
    @PostMapping("/reactflow/node-data")
    public ResponseEntity<Map<String, Object>> generateReactFlowNodeData(@RequestBody Map<String, Object> request) {
        try {
            Element element = (Element) request.get("element");
            String style = (String) request.getOrDefault("style", "default");
            
            String svg = plantUMLService.generateReactFlowNodeSVG(element, style);
            String plantUML = plantUMLService.convertElementToPlantUML(element);
            
            Map<String, Object> nodeData = new HashMap<>();
            nodeData.put("svg", svg);
            nodeData.put("plantUML", plantUML);
            nodeData.put("elementType", element.getClass().getSimpleName());
            
            if (element instanceof NamedElement) {
                nodeData.put("name", ((NamedElement) element).getName());
            }
            
            // Add dimensions estimate based on content
            Map<String, Integer> dimensions = estimateSVGDimensions(element, style);
            nodeData.put("width", dimensions.get("width"));
            nodeData.put("height", dimensions.get("height"));
            
            return ResponseEntity.ok(nodeData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to generate ReactFlow node data: " + e.getMessage()));
        }
    }
    
    @PostMapping("/reactflow/batch-nodes")
    public ResponseEntity<List<Map<String, Object>>> generateBatchReactFlowNodes(@RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<Element> elements = (List<Element>) request.get("elements");
            String style = (String) request.getOrDefault("style", "default");
            
            List<Map<String, Object>> nodeDataList = elements.stream()
                .map(element -> {
                    try {
                        String svg = plantUMLService.generateReactFlowNodeSVG(element, style);
                        Map<String, Object> nodeData = new HashMap<>();
                        nodeData.put("svg", svg);
                        nodeData.put("elementType", element.getClass().getSimpleName());
                        
                        if (element instanceof NamedElement) {
                            nodeData.put("name", ((NamedElement) element).getName());
                        }
                        
                        Map<String, Integer> dimensions = estimateSVGDimensions(element, style);
                        nodeData.put("width", dimensions.get("width"));
                        nodeData.put("height", dimensions.get("height"));
                        
                        return nodeData;
                    } catch (Exception e) {
                        Map<String, Object> errorData = new HashMap<>();
                        errorData.put("error", "Failed to process element: " + e.getMessage());
                        errorData.put("elementType", element.getClass().getSimpleName());
                        return errorData;
                    }
                })
                .toList();
            
            return ResponseEntity.ok(nodeDataList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of(Map.of("error", "Failed to generate batch nodes: " + e.getMessage())));
        }
    }

    // ========== UTILITY METHODS ==========
    
    private Map<String, Integer> estimateSVGDimensions(Element element, String style) {
        Map<String, Integer> dimensions = new HashMap<>();
        
        // Base dimensions
        int baseWidth = 120;
        int baseHeight = 60;
        
        // Adjust based on style
        switch (style.toLowerCase()) {
            case "compact":
                baseWidth = 100;
                baseHeight = 50;
                break;
            case "detailed":
                baseWidth = 200;
                baseHeight = 120;
                break;
            case "minimal":
                baseWidth = 80;
                baseHeight = 40;
                break;
        }
        
        // Adjust based on element type and content
        if (element instanceof org.eclipse.uml2.uml.Class) {
            org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) element;
            int attributeCount = clazz.getOwnedAttributes().size();
            int operationCount = clazz.getOwnedOperations().size();
            
            // Estimate height based on content
            baseHeight += (attributeCount + operationCount) * 15;
            
            // Estimate width based on longest name
            int maxNameLength = Math.max(
                clazz.getName() != null ? clazz.getName().length() : 0,
                clazz.getOwnedAttributes().stream()
                    .mapToInt(attr -> attr.getName() != null ? attr.getName().length() : 0)
                    .max().orElse(0)
            );
            
            baseWidth = Math.max(baseWidth, maxNameLength * 8 + 20);
            
        } else if (element instanceof Interface) {
            Interface iface = (Interface) element;
            int operationCount = iface.getOwnedOperations().size();
            baseHeight += operationCount * 15;
            
            int maxNameLength = Math.max(
                iface.getName() != null ? iface.getName().length() : 0,
                iface.getOwnedOperations().stream()
                    .mapToInt(op -> op.getName() != null ? op.getName().length() : 0)
                    .max().orElse(0)
            );
            
            baseWidth = Math.max(baseWidth, maxNameLength * 8 + 20);
        }
        
        dimensions.put("width", baseWidth);
        dimensions.put("height", baseHeight);
        return dimensions;
    }

    // ========== STYLE MANAGEMENT ==========
    
    @GetMapping("/styles/available")
    public ResponseEntity<List<Map<String, Object>>> getAvailableStyles() {
        List<Map<String, Object>> styles = List.of(
            Map.of(
                "name", "default",
                "description", "Standard UML styling",
                "fontSize", 11,
                "padding", 3
            ),
            Map.of(
                "name", "compact",
                "description", "Smaller nodes for dense diagrams",
                "fontSize", 10,
                "padding", 2
            ),
            Map.of(
                "name", "detailed",
                "description", "Larger nodes with more detail",
                "fontSize", 12,
                "padding", 5
            ),
            Map.of(
                "name", "minimal",
                "description", "Minimal styling, names only",
                "fontSize", 8,
                "padding", 1
            )
        );
        
        return ResponseEntity.ok(styles);
    }
    
    @PostMapping("/styles/custom")
    public ResponseEntity<String> generateCustomStyledSVG(@RequestBody Map<String, Object> request) {
        try {
            Element element = (Element) request.get("element");
            @SuppressWarnings("unchecked")
            Map<String, Object> styleConfig = (Map<String, Object>) request.get("style");
            
            String plantUML = generateCustomPlantUML(element, styleConfig);
            String svg = plantUMLService.generateSVG(plantUML);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/svg+xml"));
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(svg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate custom styled SVG: " + e.getMessage());
        }
    }
    
    private String generateCustomPlantUML(Element element, Map<String, Object> styleConfig) {
        StringBuilder plantUML = new StringBuilder();
        plantUML.append("@startuml\n");
        plantUML.append("!theme plain\n");
        
        // Apply custom styling
        if (styleConfig.containsKey("fontSize")) {
            plantUML.append("skinparam classFontSize ").append(styleConfig.get("fontSize")).append("\n");
        }
        
        if (styleConfig.containsKey("backgroundColor")) {
            plantUML.append("skinparam classBackgroundColor ").append(styleConfig.get("backgroundColor")).append("\n");
        }
        
        if (styleConfig.containsKey("borderColor")) {
            plantUML.append("skinparam classBorderColor ").append(styleConfig.get("borderColor")).append("\n");
        }
        
        if (styleConfig.containsKey("fontColor")) {
            plantUML.append("skinparam classFontColor ").append(styleConfig.get("fontColor")).append("\n");
        }
        
        if (styleConfig.containsKey("hideMembers") && (Boolean) styleConfig.get("hideMembers")) {
            plantUML.append("hide members\n");
        }
        
        if (styleConfig.containsKey("hideAttributes") && (Boolean) styleConfig.get("hideAttributes")) {
            plantUML.append("hide attributes\n");
        }
        
        if (styleConfig.containsKey("hideMethods") && (Boolean) styleConfig.get("hideMethods")) {
            plantUML.append("hide methods\n");
        }
        
        plantUML.append("\n");
        
        // Convert element based on type
        if (element instanceof org.eclipse.uml2.uml.Class) {
            plantUML.append(convertClassWithCustomStyle((org.eclipse.uml2.uml.Class) element, styleConfig));
        } else if (element instanceof Interface) {
            plantUML.append(convertInterfaceWithCustomStyle((Interface) element, styleConfig));
        } else {
            String elementName = element instanceof NamedElement ? 
                ((NamedElement) element).getName() : element.getClass().getSimpleName();
            plantUML.append("class \"").append(elementName).append("\"\n");
        }
        
        plantUML.append("@enduml");
        return plantUML.toString();
    }
    
    private String convertClassWithCustomStyle(org.eclipse.uml2.uml.Class clazz, Map<String, Object> styleConfig) {
        StringBuilder result = new StringBuilder();
        
        result.append("class ").append(clazz.getName());
        
        if (clazz.isAbstract()) {
            result.append(" <<abstract>>");
        }
        
        // Apply custom colors if specified
        if (styleConfig.containsKey("backgroundColor") || styleConfig.containsKey("borderColor")) {
            result.append(" #");
            if (styleConfig.containsKey("backgroundColor")) {
                result.append(styleConfig.get("backgroundColor"));
            }
            if (styleConfig.containsKey("borderColor")) {
                result.append("/").append(styleConfig.get("borderColor"));
            }
        }
        
        result.append(" {\n");
        
        // Add attributes (unless hidden)
        if (!Boolean.TRUE.equals(styleConfig.get("hideAttributes")) && 
            !Boolean.TRUE.equals(styleConfig.get("hideMembers"))) {
            for (Property property : clazz.getOwnedAttributes()) {
                result.append("  ").append(convertVisibility(property.getVisibility()))
                      .append(property.getName());
                if (property.getType() != null) {
                    result.append(" : ").append(property.getType().getName());
                }
                result.append("\n");
            }
        }
        
        // Add separator if we have both attributes and operations
        if (!clazz.getOwnedAttributes().isEmpty() && !clazz.getOwnedOperations().isEmpty() &&
            !Boolean.TRUE.equals(styleConfig.get("hideMembers"))) {
            result.append("  --\n");
        }
        
        // Add operations (unless hidden)
        if (!Boolean.TRUE.equals(styleConfig.get("hideMethods")) && 
            !Boolean.TRUE.equals(styleConfig.get("hideMembers"))) {
            for (Operation operation : clazz.getOwnedOperations()) {
                result.append("  ").append(convertVisibility(operation.getVisibility()))
                      .append(operation.getName()).append("()\n");
            }
        }
        
        result.append("}\n\n");
        return result.toString();
    }
    
    private String convertInterfaceWithCustomStyle(Interface iface, Map<String, Object> styleConfig) {
        StringBuilder result = new StringBuilder();
        
        result.append("interface ").append(iface.getName());
        
        // Apply custom colors if specified
        if (styleConfig.containsKey("backgroundColor") || styleConfig.containsKey("borderColor")) {
            result.append(" #");
            if (styleConfig.containsKey("backgroundColor")) {
                result.append(styleConfig.get("backgroundColor"));
            }
            if (styleConfig.containsKey("borderColor")) {
                result.append("/").append(styleConfig.get("borderColor"));
            }
        }
        
        result.append(" {\n");
        
        // Add operations (unless hidden)
        if (!Boolean.TRUE.equals(styleConfig.get("hideMethods")) && 
            !Boolean.TRUE.equals(styleConfig.get("hideMembers"))) {
            for (Operation operation : iface.getOwnedOperations()) {
                result.append("  ").append(operation.getName()).append("()\n");
            }
        }
        
        result.append("}\n\n");
        return result.toString();
    }
    
    private String convertVisibility(VisibilityKind visibility) {
        if (visibility == null) return "";
        
        switch (visibility) {
            case PUBLIC_LITERAL: return "+";
            case PRIVATE_LITERAL: return "-";
            case PROTECTED_LITERAL: return "#";
            case PACKAGE_LITERAL: return "~";
            default: return "";
        }
    }

    // ========== DIAGRAM EXPORT ==========
    
    @PostMapping("/export/svg")
    public ResponseEntity<String> exportDiagramAsSVG(@RequestBody Map<String, Object> diagramData) {
        try {
            String diagramType = (String) diagramData.get("type");
            String svg;
            
            switch (diagramType.toLowerCase()) {
                case "class":
                    svg = generateClassDiagramSVG(diagramData).getBody();
                    break;
                case "activity":
                    Activity activity = (Activity) diagramData.get("activity");
                    svg = generateActivityDiagramSVG(activity).getBody();
                    break;
                case "statemachine":
                    StateMachine stateMachine = (StateMachine) diagramData.get("stateMachine");
                    svg = generateStateMachineSVG(stateMachine).getBody();
                    break;
                case "sequence":
                    Interaction interaction = (Interaction) diagramData.get("interaction");
                    svg = generateSequenceDiagramSVG(interaction).getBody();
                    break;
                default:
                    return ResponseEntity.badRequest().body("Unsupported diagram type: " + diagramType);
            }
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("image/svg+xml"));
            headers.add("Content-Disposition", "attachment; filename=diagram.svg");
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(svg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to export diagram: " + e.getMessage());
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "healthy",
            "service", "DiagramController",
            "plantuml", "available"
        ));
    }
}