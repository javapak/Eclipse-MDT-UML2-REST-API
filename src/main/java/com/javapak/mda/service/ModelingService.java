package com.javapak.mda.service;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.javapak.mda.dto.*;

import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ModelingService {

    // ========== STRUCTURAL ELEMENTS ==========
    
    public org.eclipse.uml2.uml.Class createClass(String name, Package owner, boolean isAbstract) {
        org.eclipse.uml2.uml.Class clazz = owner.createOwnedClass(name, isAbstract);
        return clazz;
    }
    
    public Interface createInterface(String name, Package owner) {
        return owner.createOwnedInterface(name);
    }
    
    public Package createPackage(String name, Package owner) {
        return owner.createNestedPackage(name);
    }
    
    public Model createModel(String name, String uri) {
        Model model = UMLFactory.eINSTANCE.createModel();
        model.setName(name);
        if (uri != null) {
            model.setURI(uri);
        }
        return model;
    }
    
    public Component createComponent(String name, Package owner) {
        return (Component) owner.createOwnedType(name, UMLPackage.Literals.COMPONENT);
    }
    
    public Node createNode(String name, Package owner) {
        return (Node) owner.createOwnedType(name, UMLPackage.Literals.NODE);
    }
    
    public Artifact createArtifact(String name, Package owner) {
        return (Artifact) owner.createOwnedType(name, UMLPackage.Literals.ARTIFACT);
    }
    
    public DataType createDataType(String name, Package owner) {
        return (DataType) owner.createOwnedType(name, UMLPackage.Literals.DATA_TYPE);
    }
    
    public PrimitiveType createPrimitiveType(String name, Package owner) {
        return owner.createOwnedPrimitiveType(name);
    }
    
    public Enumeration createEnumeration(String name, Package owner) {
        return owner.createOwnedEnumeration(name);
    }
    
    public EnumerationLiteral createEnumerationLiteral(String name, Enumeration owner) {
        return owner.createOwnedLiteral(name);
    }

    // ========== BEHAVIORAL ELEMENTS ==========
    
    public Operation createOperation(String name, org.eclipse.uml2.uml.Class owner, Type returnType) {
        Operation op = owner.createOwnedOperation(name, null, null, returnType);
        return op;
    }
    
    public Parameter createParameter(String name, Operation owner, Type type, ParameterDirectionKind direction) {
        return owner.createOwnedParameter(name, type);
    }
    
    public Property createProperty(String name, org.eclipse.uml2.uml.Class owner, Type type) {
        return owner.createOwnedAttribute(name, type);
    }
    
    public Activity createActivity(String name, Package owner) {
        return (Activity) owner.createOwnedType(name, UMLPackage.Literals.ACTIVITY);
    }
    
    public StateMachine createStateMachine(String name, org.eclipse.uml2.uml.Class owner) {
        return (StateMachine) owner.createOwnedBehavior(name, UMLPackage.Literals.STATE_MACHINE);
    }
    
    public Interaction createInteraction(String name, Package owner) {
        return (Interaction) owner.createOwnedType(name, UMLPackage.Literals.INTERACTION);
    }

    // ========== RELATIONSHIPS ==========
    
    public Association createAssociation(String name, Package owner, Type end1Type, Type end2Type) {
        return (Association) owner.createOwnedType(name, UMLPackage.Literals.ASSOCIATION);
    }
    
    public Generalization createGeneralization(Classifier specific, Classifier general) {
        return specific.createGeneralization(general);
    }
    
    public InterfaceRealization createInterfaceRealization(String name, BehavioredClassifier implementer, Interface contract) {
        return implementer.createInterfaceRealization(name, contract);
    }
    
    public Dependency createDependency(String name, Package owner, NamedElement client, NamedElement supplier) {
        Dependency dep = (Dependency) owner.createOwnedType(name, UMLPackage.Literals.DEPENDENCY);
        dep.getClients().add(client);
        dep.getSuppliers().add(supplier);
        return dep;
    }
    
    public Usage createUsage(String name, Package owner, NamedElement client, NamedElement supplier) {
        Usage usage = (Usage) owner.createOwnedType(name, UMLPackage.Literals.USAGE);
        usage.getClients().add(client);
        usage.getSuppliers().add(supplier);
        return usage;
    }

    // ========== ACTIVITY DIAGRAM ELEMENTS ==========
    
    public ActivityNode createActivityNode(String name, Activity owner, java.lang.Class nodeType) {
        // Factory method for different activity node types
        if (nodeType == InitialNode.class) {
            return owner.createOwnedNode(name, UMLPackage.Literals.INITIAL_NODE);
        } else if (nodeType == FinalNode.class) {
            return owner.createOwnedNode(name, UMLPackage.Literals.ACTIVITY_FINAL_NODE);
        } else if (nodeType == OpaqueAction.class) {
            return owner.createOwnedNode(name, UMLPackage.Literals.OPAQUE_ACTION);
        }
        // Add more node types as needed
        return null;
    }
    
    public ActivityEdge createActivityEdge(String name, Activity owner, ActivityNode source, ActivityNode target) {
        return owner.createEdge(name, UMLPackage.Literals.CONTROL_FLOW);
    }

    // ========== STATE MACHINE ELEMENTS ==========
    
    public State createState(String name, StateMachine owner) {
        Region region = owner.createRegion("Region1");
        return (State) region.createSubvertex(name, UMLPackage.Literals.STATE);
    }
    
    public Transition createTransition(String name, StateMachine owner, Vertex source, Vertex target) {
        Region region = owner.getRegions().get(0); // Assume first region
        return region.createTransition(name);
    }

    // ========== INTERACTION ELEMENTS ==========
    
    public Lifeline createLifeline(String name, Interaction owner, ConnectableElement represents) {
        Lifeline lifeline = owner.createLifeline(name);
        lifeline.setRepresents(represents);
        return lifeline;
    }    
    public Message createMessage(String name, Interaction owner, MessageSort sort) {
        return owner.createMessage(name);
    }

    // ========== PROFILE ELEMENTS ==========
    
    public Profile createProfile(String name, String uri) {
        Profile profile = UMLFactory.eINSTANCE.createProfile();
        profile.setName(name);
        if (uri != null) {
            profile.setURI(uri);
        }
        return profile;
    }
    
    public Stereotype createStereotype(String name, Profile owner, boolean isAbstract) {
        return owner.createOwnedStereotype(name, isAbstract);
    }
    
    public Extension createExtension(String name, Stereotype stereotype, org.eclipse.uml2.uml.Class metaclass) {
        return stereotype.createExtension(metaclass, false);
    }

    // ========== CONSTRAINT ELEMENTS ==========
    
    public Constraint createConstraint(String name, Namespace owner, String body, String language) {
        Constraint constraint = owner.createOwnedRule(name, UMLPackage.Literals.CONSTRAINT);
        if (body != null) {
            OpaqueExpression expr = UMLFactory.eINSTANCE.createOpaqueExpression();
            expr.getBodies().add(body);
            if (language != null) {
                expr.getLanguages().add(language);
            }
            constraint.setSpecification(expr);
        }
        return constraint;
    }

    // ========== UTILITY METHODS ==========
    
    public void setVisibility(NamedElement element, String visibility) {
        element.setVisibility(VisibilityKind.get(visibility.toLowerCase()));
    }
    
    public void setMultiplicity(MultiplicityElement element, int lower, int upper) {
        element.setLower(lower);
        element.setUpper(upper);
    }
    
    public NamedElement findElementByName(Namespace namespace, String name) {
        return namespace.getOwnedMembers().stream()
            .filter(NamedElement.class::isInstance)
            .map(NamedElement.class::cast)
            .filter(e -> name.equals(e.getName()))
            .findFirst()
            .orElse(null);
    }
    
    public <T extends Element> T findElementByType(Namespace namespace, String name, java.lang.Class<T> type) {
        return namespace.getOwnedMembers().stream()
            .filter(type::isInstance)
            .map(type::cast)
            .filter(e -> e instanceof NamedElement && name.equals(((NamedElement) e).getName()))
            .findFirst()
            .orElse(null);
    }

    // ========== FILE I/O METHODS ==========
    
    public Mono<Model> loadModelFromXMI(FilePart filePart) {
        return Mono.fromCallable(() -> {
            Path tempFile = Files.createTempFile("upload", ".uml");
            
            try {
                filePart.transferTo(tempFile).block();
                
                ResourceSet resourceSet = createConfiguredResourceSet();
                Resource resource = resourceSet.getResource(URI.createFileURI(tempFile.toString()), true);
                
                Model model = (Model) resource.getContents().get(0);
                Files.deleteIfExists(tempFile);
                return model;
                
            } catch (Exception e) {
                try { Files.deleteIfExists(tempFile); } catch (Exception ignored) {}
                throw new RuntimeException("Failed to load XMI file", e);
            }
        });
    }
    
    public String exportToXMI(Element element) {
        try {
            ResourceSet resourceSet = createConfiguredResourceSet();
            Resource resource = resourceSet.createResource(URI.createURI("temp://export.uml"));
            resource.getContents().add(element);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            resource.save(outputStream, null);
            return outputStream.toString("UTF-8");
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to export to XMI", e);
        }
    }

    private ResourceSet createConfiguredResourceSet() {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        resourceSet.getResourceFactoryRegistry()
            .getExtensionToFactoryMap()
            .put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        return resourceSet;
    }
}


