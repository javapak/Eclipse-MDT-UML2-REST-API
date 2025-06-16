package com.javapak.mda.controller;

import com.javapak.mda.service.ModelingService;
import com.javapak.mda.dto.*;
import org.eclipse.uml2.uml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/v1/toolkit")
public class ModelingToolkitController {

    @Autowired
    private ModelingService modelingService;

    // ========== MODEL MANAGEMENT ==========

    /**
     * Create model
     */
    @PostMapping("/models/create")
    public Mono<Model> createModel(@RequestBody CreateModelRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createModel(request.getName(), request.getUri());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Load model from XMI file
     */
    @PostMapping("/models/load-xmi")
    public Mono<Model> loadModelFromXMI(@RequestPart("file") FilePart filePart) {
        return modelingService.loadModelFromXMI(filePart);
    }

    /**
     * Export element to XMI
     */
    @PostMapping("/models/export-xmi")
    public Mono<String> exportToXMI(@RequestBody ExportXMIRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.exportToXMI(request.getElement());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // ========== STRUCTURAL ELEMENTS ==========

    /**
     * Create class
     */
    @PostMapping("/classes/create")
    public Mono<org.eclipse.uml2.uml.Class> createClass(@RequestBody CreateClassRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createClass(
                request.getName(), 
                request.getOwner(), 
                request.isAbstract()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create interface
     */
    @PostMapping("/interfaces/create")
    public Mono<Interface> createInterface(@RequestBody CreateInterfaceRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createInterface(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create package
     */
    @PostMapping("/packages/create")
    public Mono<org.eclipse.uml2.uml.Package> createPackage(@RequestBody CreatePackageRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createPackage(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create component
     */
    @PostMapping("/components/create")
    public Mono<Component> createComponent(@RequestBody CreateComponentRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createComponent(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create node
     */
    @PostMapping("/nodes/create")
    public Mono<Node> createNode(@RequestBody CreateNodeRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createNode(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create artifact
     */
    @PostMapping("/artifacts/create")
    public Mono<Artifact> createArtifact(@RequestBody CreateArtifactRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createArtifact(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create data type
     */
    @PostMapping("/datatypes/create")
    public Mono<DataType> createDataType(@RequestBody CreateDataTypeRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createDataType(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create primitive type
     */
    @PostMapping("/datatypes/create-primitive")
    public Mono<PrimitiveType> createPrimitiveType(@RequestBody CreatePrimitiveTypeRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createPrimitiveType(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create enumeration
     */
    @PostMapping("/enumerations/create")
    public Mono<Enumeration> createEnumeration(@RequestBody CreateEnumerationRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createEnumeration(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create enumeration literal
     */
    @PostMapping("/enumerations/create-literal")
    public Mono<EnumerationLiteral> createEnumerationLiteral(@RequestBody CreateEnumerationLiteralRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createEnumerationLiteral(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // ========== BEHAVIORAL ELEMENTS ==========

    /**
     * Create operation
     */
    @PostMapping("/operations/create")
    public Mono<Operation> createOperation(@RequestBody CreateOperationRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createOperation(
                request.getName(), 
                request.getOwner(), 
                request.getReturnType()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create parameter
     */
    @PostMapping("/parameters/create")
    public Mono<Parameter> createParameter(@RequestBody CreateParameterRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createParameter(
                request.getName(), 
                request.getOwner(), 
                request.getType(), 
                request.getDirection()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create property
     */
    @PostMapping("/properties/create")
    public Mono<Property> createProperty(@RequestBody CreatePropertyRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createProperty(
                request.getName(), 
                request.getOwner(), 
                request.getType()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create activity
     */
    @PostMapping("/activities/create")
    public Mono<Activity> createActivity(@RequestBody CreateActivityRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createActivity(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create state machine
     */
    @PostMapping("/statemachines/create")
    public Mono<StateMachine> createStateMachine(@RequestBody CreateStateMachineRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createStateMachine(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create interaction
     */
    @PostMapping("/interactions/create")
    public Mono<Interaction> createInteraction(@RequestBody CreateInteractionRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createInteraction(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // ========== RELATIONSHIPS ==========

    /**
     * Create association
     */
    @PostMapping("/associations/create")
    public Mono<Association> createAssociation(@RequestBody CreateAssociationRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createAssociation(
                request.getName(), 
                request.getOwner(), 
                request.getEnd1Type(), 
                request.getEnd2Type()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create generalization
     */
    @PostMapping("/generalizations/create")
    public Mono<Generalization> createGeneralization(@RequestBody CreateGeneralizationRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createGeneralization(request.getSpecific(), request.getGeneral());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create interface realization
     */
    @PostMapping("/realizations/create")
    public Mono<InterfaceRealization> createInterfaceRealization(@RequestBody CreateInterfaceRealizationRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createInterfaceRealization(
                request.getName(), 
                request.getImplementer(), 
                request.getContract()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create dependency
     */
    @PostMapping("/dependencies/create")
    public Mono<Dependency> createDependency(@RequestBody CreateDependencyRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createDependency(
                request.getName(), 
                request.getOwner(), 
                request.getClient(), 
                request.getSupplier()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create usage
     */
    @PostMapping("/usages/create")
    public Mono<Usage> createUsage(@RequestBody CreateUsageRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createUsage(
                request.getName(), 
                request.getOwner(), 
                request.getClient(), 
                request.getSupplier()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // ========== ACTIVITY DIAGRAM ELEMENTS ==========

    /**
     * Create activity node
     */
    @PostMapping("/activities/create-node")
    public Mono<ActivityNode> createActivityNode(@RequestBody CreateActivityNodeRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createActivityNode(
                request.getName(), 
                request.getOwner(), 
                request.getNodeType()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create activity edge
     */
    @PostMapping("/activities/create-edge")
    public Mono<ActivityEdge> createActivityEdge(@RequestBody CreateActivityEdgeRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createActivityEdge(
                request.getName(), 
                request.getOwner(), 
                request.getSource(), 
                request.getTarget()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // ========== STATE MACHINE ELEMENTS ==========

    /**
     * Create state
     */
    @PostMapping("/statemachines/create-state")
    public Mono<State> createState(@RequestBody CreateStateRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createState(request.getName(), request.getOwner());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create transition
     */
    @PostMapping("/statemachines/create-transition")
    public Mono<Transition> createTransition(@RequestBody CreateTransitionRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createTransition(
                request.getName(), 
                request.getOwner(), 
                request.getSource(), 
                request.getTarget()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // ========== INTERACTION ELEMENTS ==========

    /**
     * Create lifeline
     */
    @PostMapping("/interactions/create-lifeline")
    public Mono<Lifeline> createLifeline(@RequestBody CreateLifelineRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createLifeline(
                request.getName(), 
                request.getOwner(), 
                request.getRepresents()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create message
     */
    @PostMapping("/interactions/create-message")
    public Mono<Message> createMessage(@RequestBody CreateMessageRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createMessage(
                request.getName(), 
                request.getOwner(), 
                request.getSort()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // ========== PROFILE ELEMENTS ==========

    /**
     * Create profile
     */
    @PostMapping("/profiles/create")
    public Mono<Profile> createProfile(@RequestBody CreateProfileRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createProfile(request.getName(), request.getUri());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create stereotype
     */
    @PostMapping("/stereotypes/create")
    public Mono<Stereotype> createStereotype(@RequestBody CreateStereotypeRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createStereotype(
                request.getName(), 
                request.getOwner(), 
                request.isAbstract()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create extension
     */
    @PostMapping("/extensions/create")
    public Mono<Extension> createExtension(@RequestBody CreateExtensionRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createExtension(
                request.getName(), 
                request.getStereotype(), 
                request.getMetaclass()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // ========== CONSTRAINT ELEMENTS ==========

    /**
     * Create constraint
     */
    @PostMapping("/constraints/create")
    public Mono<Constraint> createConstraint(@RequestBody CreateConstraintRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createConstraint(
                request.getName(), 
                request.getOwner(), 
                request.getBody(), 
                request.getLanguage()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }
}

    // ========== UTILITY