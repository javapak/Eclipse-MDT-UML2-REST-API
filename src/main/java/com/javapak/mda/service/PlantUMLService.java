package com.javapak.mda.service;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import org.eclipse.uml2.uml.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantUMLService {

    // ========== SVG GENERATION ==========
    
    public String generateSVG(String plantUMLSource) throws IOException {
        SourceStringReader reader = new SourceStringReader(plantUMLSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        reader.outputImage(outputStream, new FileFormatOption(FileFormat.SVG));
        return outputStream.toString("UTF-8");
    }
    
    public String generatePNG(String plantUMLSource) throws IOException {
        SourceStringReader reader = new SourceStringReader(plantUMLSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        reader.outputImage(outputStream, new FileFormatOption(FileFormat.PNG));
        return java.util.Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    // ========== UML TO PLANTUML CONVERSION ==========
    
    public String convertClassDiagram(List<org.eclipse.uml2.uml.Class> classes, List<Interface> interfaces, List<Association> associations) {
        StringBuilder plantUML = new StringBuilder();
        plantUML.append("@startuml\n");
        plantUML.append("!theme plain\n");
        plantUML.append("skinparam classAttributeIconSize 0\n");
        plantUML.append("skinparam classFontSize 12\n\n");
        
        // Add classes
        for (org.eclipse.uml2.uml.Class clazz : classes) {
            plantUML.append(convertClass(clazz));
        }
        
        // Add interfaces
        for (Interface iface : interfaces) {
            plantUML.append(convertInterface(iface));
        }
        
        // Add associations
        for (Association assoc : associations) {
            plantUML.append(convertAssociation(assoc));
        }
        
        plantUML.append("@enduml");
        return plantUML.toString();
    }
    
    public String convertSingleClass(org.eclipse.uml2.uml.Class clazz) {
        StringBuilder plantUML = new StringBuilder();
        plantUML.append("@startuml\n");
        plantUML.append("!theme plain\n");
        plantUML.append("skinparam classAttributeIconSize 0\n");
        plantUML.append("skinparam classFontSize 12\n\n");
        
        plantUML.append(convertClass(clazz));
        
        plantUML.append("@enduml");
        return plantUML.toString();
    }
    
    public String convertSingleInterface(Interface iface) {
        StringBuilder plantUML = new StringBuilder();
        plantUML.append("@startuml\n");
        plantUML.append("!theme plain\n");
        plantUML.append("skinparam interfaceFontSize 12\n\n");
        
        plantUML.append(convertInterface(iface));
        
        plantUML.append("@enduml");
        return plantUML.toString();
    }
    
    public String convertActivityDiagram(Activity activity) {
        StringBuilder plantUML = new StringBuilder();
        plantUML.append("@startuml\n");
        plantUML.append("!theme plain\n");
        plantUML.append("skinparam activityFontSize 12\n\n");
        
        plantUML.append("start\n");
        
        // Convert activity nodes
        for (ActivityNode node : activity.getNodes()) {
            plantUML.append(convertActivityNode(node));
        }
        
        plantUML.append("stop\n");
        plantUML.append("@enduml");
        return plantUML.toString();
    }
    
    public String convertStateMachine(StateMachine stateMachine) {
        StringBuilder plantUML = new StringBuilder();
        plantUML.append("@startuml\n");
        plantUML.append("!theme plain\n");
        plantUML.append("skinparam stateFontSize 12\n\n");
        
        plantUML.append("[*] --> ");
        
        // Find initial state
        for (Region region : stateMachine.getRegions()) {
            for (Vertex vertex : region.getSubvertices()) {
                if (vertex instanceof Pseudostate && 
                    ((Pseudostate) vertex).getKind() == PseudostateKind.INITIAL_LITERAL) {
                    // Find first real state
                    for (Transition transition : vertex.getOutgoings()) {
                        if (transition.getTarget() instanceof State) {
                            plantUML.append(transition.getTarget().getName()).append("\n");
                            break;
                        }
                    }
                    break;
                }
            }
        }
        
        // Convert states and transitions
        for (Region region : stateMachine.getRegions()) {
            for (Vertex vertex : region.getSubvertices()) {
                if (vertex instanceof State) {
                    plantUML.append(convertState((State) vertex));
                }
            }
            
            for (Transition transition : region.getTransitions()) {
                plantUML.append(convertTransition(transition));
            }
        }
        
        plantUML.append("@enduml");
        return plantUML.toString();
    }
    
    public String convertSequenceDiagram(Interaction interaction) {
        StringBuilder plantUML = new StringBuilder();
        plantUML.append("@startuml\n");
        plantUML.append("!theme plain\n");
        plantUML.append("skinparam sequenceFontSize 12\n\n");
        
        // Add lifelines
        for (Lifeline lifeline : interaction.getLifelines()) {
            plantUML.append("participant \"").append(lifeline.getName()).append("\"\n");
        }
        
        plantUML.append("\n");
        
        // Add messages
        for (Message message : interaction.getMessages()) {
            plantUML.append(convertMessage(message));
        }
        
        plantUML.append("@enduml");
        return plantUML.toString();
    }

    // ========== INDIVIDUAL ELEMENT CONVERTERS ==========
    
    private String convertClass(org.eclipse.uml2.uml.Class clazz) {
        StringBuilder result = new StringBuilder();
        
        result.append("class ").append(clazz.getName());
        
        if (clazz.isAbstract()) {
            result.append(" <<abstract>>");
        }
        
        result.append(" {\n");
        
        // Add attributes
        for (Property property : clazz.getOwnedAttributes()) {
            result.append("  ").append(convertVisibility(property.getVisibility()))
                  .append(property.getName());
            if (property.getType() != null) {
                result.append(" : ").append(property.getType().getName());
            }
            result.append("\n");
        }
        
        // Add separator if we have both attributes and operations
        if (!clazz.getOwnedAttributes().isEmpty() && !clazz.getOwnedOperations().isEmpty()) {
            result.append("  --\n");
        }
        
        // Add operations
        for (Operation operation : clazz.getOwnedOperations()) {
            result.append("  ").append(convertVisibility(operation.getVisibility()))
                  .append(operation.getName()).append("(");
            
            // Add parameters
            List<Parameter> params = operation.getOwnedParameters().stream()
                .filter(p -> p.getDirection() != ParameterDirectionKind.RETURN_LITERAL)
                .collect(Collectors.toList());
            
            for (int i = 0; i < params.size(); i++) {
                Parameter param = params.get(i);
                result.append(param.getName());
                if (param.getType() != null) {
                    result.append(" : ").append(param.getType().getName());
                }
                if (i < params.size() - 1) {
                    result.append(", ");
                }
            }
            
            result.append(")");
            
            // Add return type
            Parameter returnParam = operation.getOwnedParameters().stream()
                .filter(p -> p.getDirection() == ParameterDirectionKind.RETURN_LITERAL)
                .findFirst().orElse(null);
            
            if (returnParam != null && returnParam.getType() != null) {
                result.append(" : ").append(returnParam.getType().getName());
            }
            
            result.append("\n");
        }
        
        result.append("}\n\n");
        return result.toString();
    }
    
    private String convertInterface(Interface iface) {
        StringBuilder result = new StringBuilder();
        
        result.append("interface ").append(iface.getName()).append(" {\n");
        
        // Add operations
        for (Operation operation : iface.getOwnedOperations()) {
            result.append("  ").append(operation.getName()).append("(");
            
            // Add parameters
            List<Parameter> params = operation.getOwnedParameters().stream()
                .filter(p -> p.getDirection() != ParameterDirectionKind.RETURN_LITERAL)
                .collect(Collectors.toList());
            
            for (int i = 0; i < params.size(); i++) {
                Parameter param = params.get(i);
                result.append(param.getName());
                if (param.getType() != null) {
                    result.append(" : ").append(param.getType().getName());
                }
                if (i < params.size() - 1) {
                    result.append(", ");
                }
            }
            
            result.append(")");
            
            // Add return type
            Parameter returnParam = operation.getOwnedParameters().stream()
                .filter(p -> p.getDirection() == ParameterDirectionKind.RETURN_LITERAL)
                .findFirst().orElse(null);
            
            if (returnParam != null && returnParam.getType() != null) {
                result.append(" : ").append(returnParam.getType().getName());
            }
            
            result.append("\n");
        }
        
        result.append("}\n\n");
        return result.toString();
    }
    
    private String convertAssociation(Association association) {
        StringBuilder result = new StringBuilder();
        
        if (association.getMemberEnds().size() >= 2) {
            Property end1 = association.getMemberEnds().get(0);
            Property end2 = association.getMemberEnds().get(1);
            
            if (end1.getType() != null && end2.getType() != null) {
                result.append(end1.getType().getName())
                      .append(" -- ")
                      .append(end2.getType().getName());
                
                if (association.getName() != null && !association.getName().isEmpty()) {
                    result.append(" : ").append(association.getName());
                }
                
                result.append("\n");
            }
        }
        
        return result.toString();
    }
    
    private String convertActivityNode(ActivityNode node) {
        StringBuilder result = new StringBuilder();
        
        if (node instanceof InitialNode) {
            result.append("start\n");
        } else if (node instanceof ActivityFinalNode) {
            result.append("stop\n");
        } else if (node instanceof OpaqueAction) {
            result.append(":").append(node.getName() != null ? node.getName() : "Action").append(";\n");
        } else if (node instanceof DecisionNode) {
            result.append("if (").append(node.getName() != null ? node.getName() : "condition").append(") then (yes)\n");
        } else if (node instanceof MergeNode) {
            result.append("endif\n");
        } else if (node instanceof ForkNode) {
            result.append("fork\n");
        } else if (node instanceof JoinNode) {
            result.append("fork again\n");
        }
        
        return result.toString();
    }
    
    private String convertState(State state) {
        StringBuilder result = new StringBuilder();
        result.append("state ").append(state.getName()).append("\n");
        return result.toString();
    }
    
    private String convertTransition(Transition transition) {
        StringBuilder result = new StringBuilder();
        
        if (transition.getSource() != null && transition.getTarget() != null) {
            result.append(transition.getSource().getName())
                  .append(" --> ")
                  .append(transition.getTarget().getName());
            
            if (transition.getName() != null && !transition.getName().isEmpty()) {
                result.append(" : ").append(transition.getName());
            }
            
            result.append("\n");
        }
        
        return result.toString();
    }
    
    private String convertMessage(Message message) {
        StringBuilder result = new StringBuilder();
        
        // This is simplified - you'd need to extract lifeline info from message events
        result.append("A -> B : ").append(message.getName() != null ? message.getName() : "message").append("\n");
        
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

    // ========== UTILITY METHODS ==========
    
    public String generateNodeSVG(Element element) throws IOException {
        String plantUMLSource = convertElementToPlantUML(element);
        return generateSVG(plantUMLSource);
    }
    
    public String convertElementToPlantUML(Element element) {
        if (element instanceof org.eclipse.uml2.uml.Class) {
            return convertSingleClass((org.eclipse.uml2.uml.Class) element);
        } else if (element instanceof Interface) {
            return convertSingleInterface((Interface) element);
        } else if (element instanceof Activity) {
            return convertActivityDiagram((Activity) element);
        } else if (element instanceof StateMachine) {
            return convertStateMachine((StateMachine) element);
        } else if (element instanceof Interaction) {
            return convertSequenceDiagram((Interaction) element);
        } else {
            // Generic element representation
            StringBuilder plantUML = new StringBuilder();
            plantUML.append("@startuml\n");
            plantUML.append("!theme plain\n");
            plantUML.append("skinparam componentFontSize 12\n\n");
            
            String elementName = element instanceof NamedElement ? 
                ((NamedElement) element).getName() : element.getClass().getSimpleName();
            
            plantUML.append("component \"").append(elementName).append("\" as ")
                    .append(element.getClass().getSimpleName()).append("\n");
            
            plantUML.append("@enduml");
            return plantUML.toString();
        }
    }
    
    // ========== REACTFLOW SPECIFIC METHODS ==========
    
    public String generateReactFlowNodeSVG(Element element, String nodeStyle) throws IOException {
        String plantUMLSource = convertElementToPlantUMLWithStyle(element, nodeStyle);
        return generateSVG(plantUMLSource);
    }
    
    private String convertElementToPlantUMLWithStyle(Element element, String style) {
        StringBuilder plantUML = new StringBuilder();
        plantUML.append("@startuml\n");
        plantUML.append("!theme plain\n");
        
        // Apply custom styling for ReactFlow nodes
        switch (style.toLowerCase()) {
            case "compact":
                plantUML.append("skinparam classFontSize 10\n");
                plantUML.append("skinparam classAttributeIconSize 0\n");
                plantUML.append("skinparam padding 2\n");
                break;
            case "detailed":
                plantUML.append("skinparam classFontSize 12\n");
                plantUML.append("skinparam classAttributeIconSize 8\n");
                plantUML.append("skinparam padding 5\n");
                break;
            case "minimal":
                plantUML.append("skinparam classFontSize 8\n");
                plantUML.append("skinparam classAttributeIconSize 0\n");
                plantUML.append("skinparam padding 1\n");
                plantUML.append("hide members\n");
                break;
            default:
                plantUML.append("skinparam classFontSize 11\n");
                plantUML.append("skinparam classAttributeIconSize 0\n");
                plantUML.append("skinparam padding 3\n");
        }
        
        plantUML.append("\n");
        
        if (element instanceof org.eclipse.uml2.uml.Class) {
            plantUML.append(convertClass((org.eclipse.uml2.uml.Class) element));
        } else if (element instanceof Interface) {
            plantUML.append(convertInterface((Interface) element));
        } else {
            String elementName = element instanceof NamedElement ? 
                ((NamedElement) element).getName() : element.getClass().getSimpleName();
            plantUML.append("class \"").append(elementName).append("\"\n");
        }
        
        plantUML.append("@enduml");
        return plantUML.toString();
    }
}