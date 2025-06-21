package com.javapak.mda.service;

import com.javapak.mda.dto.ElementTypeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElementTypeService {
    
    private static final Logger logger = LoggerFactory.getLogger(ElementTypeService.class);
    
    @Autowired
    private PlantUMLService plantUMLService;
    
    // Only standalone UML elements
    private static final List<String> STANDALONE_ELEMENTS = Arrays.asList(
        "class", "interface", "enumeration", "component", "package", "model", 
        "node", "artifact", "device", "executionenvironment", "datatype", 
        "primitivetype", "actor", "usecase", "collaboration", "activity", 
        "statemachine", "interaction", "signal", "profile", "stereotype", 
        "instancespecification", "constraint"
    );
    
    @Cacheable(value = "elementTypes")
    public ElementTypeResponse getStandaloneElementsWithSvg() {
        logger.info("Generating standalone elements with SVG");
        
        List<ElementTypeResponse.ElementType> elementTypes = STANDALONE_ELEMENTS.stream()
            .map(this::createElementTypeWithSvg)
            .filter(elementType -> elementType != null)
            .collect(Collectors.toList());
        
        ElementTypeResponse.Metadata metadata = new ElementTypeResponse.Metadata(
            elementTypes.size(),
            List.of("standalone"),
            LocalDateTime.now(),
            300
        );
        
        return new ElementTypeResponse(elementTypes, metadata);
    }
    
    private ElementTypeResponse.ElementType createElementTypeWithSvg(String typeName) {
        try {
            ElementTypeResponse.ElementType elementType = new ElementTypeResponse.ElementType(
                typeName,
                "standalone",
                getDescription(typeName)
            );
            
            String plantUml = generatePlantUml(typeName);
            elementType.setPlantUmlCode(plantUml);
            
            String svg = plantUMLService.generateSVG(plantUml);
            elementType.setSvgContent(svg);
            
            return elementType;
            
        } catch (Exception e) {
            logger.warn("Failed to generate SVG for {}: {}", typeName, e.getMessage());
            return null;
        }
    }
    
    private String generatePlantUml(String typeName) {
        switch (typeName.toLowerCase()) {
            case "class":
                return "@startuml\n!theme plain\nclass ExampleClass {\n  +method()\n  -field: String\n}\n@enduml";
            case "interface":
                return "@startuml\n!theme plain\ninterface ExampleInterface {\n  +method()\n}\n@enduml";
            case "enumeration":
                return "@startuml\n!theme plain\nenum ExampleEnum {\n  VALUE1\n  VALUE2\n}\n@enduml";
            case "component":
                return "@startuml\n!theme plain\ncomponent ExampleComponent\n@enduml";
            case "package":
                return "@startuml\n!theme plain\npackage ExamplePackage\n@enduml";
            case "model":
                return "@startuml\n!theme plain\npackage ExampleModel <<model>>\n@enduml";
            case "node":
                return "@startuml\n!theme plain\nnode ExampleNode\n@enduml";
            case "artifact":
                return "@startuml\n!theme plain\nartifact ExampleArtifact\n@enduml";
            case "device":
                return "@startuml\n!theme plain\nnode ExampleDevice <<device>>\n@enduml";
            case "executionenvironment":
                return "@startuml\n!theme plain\nnode ExampleEnv <<execution environment>>\n@enduml";
            case "datatype":
                return "@startuml\n!theme plain\nclass ExampleDataType <<datatype>>\n@enduml";
            case "primitivetype":
                return "@startuml\n!theme plain\nclass ExamplePrimitive <<primitive>>\n@enduml";
            case "actor":
                return "@startuml\n!theme plain\nactor ExampleActor\n@enduml";
            case "usecase":
                return "@startuml\n!theme plain\nusecase \"Example Use Case\"\n@enduml";
            case "collaboration":
                return "@startuml\n!theme plain\npackage ExampleCollaboration <<collaboration>>\n@enduml";
            case "activity":
                return "@startuml\n!theme plain\nstart\n:Example Activity;\nstop\n@enduml";
            case "statemachine":
                return "@startuml\n!theme plain\n[*] --> State1\nState1 --> [*]\n@enduml";
            case "interaction":
                return "@startuml\n!theme plain\nparticipant A\nparticipant B\nA -> B : message\n@enduml";
            case "signal":
                return "@startuml\n!theme plain\nclass ExampleSignal <<signal>>\n@enduml";
            case "profile":
                return "@startuml\n!theme plain\npackage ExampleProfile <<profile>>\n@enduml";
            case "stereotype":
                return "@startuml\n!theme plain\nclass Example <<ExampleStereotype>>\n@enduml";
            case "instancespecification":
                return "@startuml\n!theme plain\nobject \"example : Class\" {\n  field = value\n}\n@enduml";
            case "constraint":
                return "@startuml\n!theme plain\nclass Example\nnote right : {constraint}\n@enduml";
            default:
                return "@startuml\n!theme plain\nrectangle " + typeName + "\n@enduml";
        }
    }
    
    private String getDescription(String typeName) {
        switch (typeName.toLowerCase()) {
            case "class": return "A class defines objects with common features";
            case "interface": return "An interface specifies a contract";
            case "enumeration": return "An enumeration defines a set of values";
            case "component": return "A component is a modular system part";
            case "package": return "A package groups related elements";
            case "model": return "A model is the top-level container";
            case "node": return "A node represents a computational resource";
            case "artifact": return "An artifact is a physical piece of information";
            case "device": return "A device is a physical computational resource";
            case "executionenvironment": return "An execution environment hosts components";
            case "datatype": return "A datatype defines values without identity";
            case "primitivetype": return "A primitive type defines basic values";
            case "actor": return "An actor represents an external entity";
            case "usecase": return "A use case describes system functionality";
            case "collaboration": return "A collaboration defines cooperating roles";
            case "activity": return "An activity defines a sequence of actions";
            case "statemachine": return "A state machine models behavior as states";
            case "interaction": return "An interaction defines message exchanges";
            case "signal": return "A signal represents asynchronous communication";
            case "profile": return "A profile defines UML extensions";
            case "stereotype": return "A stereotype extends metaclass properties";
            case "instancespecification": return "An instance represents a specific example";
            case "constraint": return "A constraint specifies conditions";
            default: return "UML element: " + typeName;
        }
    }
}
