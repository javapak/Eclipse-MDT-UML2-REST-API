package com.javapak.mda.controller;

import com.javapak.mda.dto.*;
import com.javapak.mda.service.AdvancedModelingService;
import org.eclipse.uml2.uml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/advanced-modeling")
@CrossOrigin(origins = "*")
public class AdvancedModelingController {

    @Autowired
    private AdvancedModelingService advancedModelingService;

    // ========== GENERIC UNSAFE ELEMENT CREATION ==========
    
    @PostMapping("/unsafe/element")
    public ResponseEntity<?> createUnsafeElement(@RequestBody UnsafeElementRequest request) {
        try {
            Element element = createElementByTypeName(request.getElementType());
            
            if (request.isInitialize() && request.getName() != null) {
                advancedModelingService.makeWellFormed(element, request.getName());
            }
            
            if (request.isValidate()) {
                ValidationResult validationResult = advancedModelingService.getValidator().validate(element);
                if (validationResult.hasErrors()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("element", element);
                    response.put("validation", validationResult);
                    return ResponseEntity.badRequest().body(response);
                }
            }
            
            return ResponseEntity.ok(element);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create element: " + e.getMessage());
        }
    }
    
    @PostMapping("/unsafe/elements/batch")
    public ResponseEntity<?> createUnsafeElementsBatch(@RequestBody BatchElementRequest request) {
        try {
            List<? extends Element> elements = createElementsBatchByTypeName(request.getElementType(), request.getCount());
            
            if (request.isInitialize() && request.getNamePrefix() != null) {
                for (int i = 0; i < elements.size(); i++) {
                    advancedModelingService.makeWellFormed(elements.get(i), request.getNamePrefix() + (i + 1));
                }
            }
            
            return ResponseEntity.ok(elements);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create elements: " + e.getMessage());
        }
    }

    // ========== STRUCTURAL ELEMENTS ==========
    
    @PostMapping("/unsafe/class")
    public ResponseEntity<org.eclipse.uml2.uml.Class> createUnsafeClass(@RequestBody(required = false) UnsafeElementRequest request) {
        org.eclipse.uml2.uml.Class clazz = advancedModelingService.createUnsafeClass();
        if (request != null && request.getName() != null) {
            clazz.setName(request.getName());
        }
        return ResponseEntity.ok(clazz);
    }
    
    @PostMapping("/unsafe/interface")
    public ResponseEntity<Interface> createUnsafeInterface(@RequestBody(required = false) UnsafeElementRequest request) {
        Interface iface = advancedModelingService.createUnsafeInterface();
        if (request != null && request.getName() != null) {
            iface.setName(request.getName());
        }
        return ResponseEntity.ok(iface);
    }
    
    @PostMapping("/unsafe/package")
    public ResponseEntity<org.eclipse.uml2.uml.Package> createUnsafePackage(@RequestBody(required = false) UnsafeElementRequest request) {
        org.eclipse.uml2.uml.Package pkg = advancedModelingService.createUnsafePackage();
        if (request != null && request.getName() != null) {
            pkg.setName(request.getName());
        }
        return ResponseEntity.ok(pkg);
    }
    
    @PostMapping("/unsafe/model")
    public ResponseEntity<Model> createUnsafeModel(@RequestBody(required = false) UnsafeElementRequest request) {
        Model model = advancedModelingService.createUnsafeModel();
        if (request != null && request.getName() != null) {
            model.setName(request.getName());
        }
        return ResponseEntity.ok(model);
    }
    
    @PostMapping("/unsafe/component")
    public ResponseEntity<Component> createUnsafeComponent(@RequestBody(required = false) UnsafeElementRequest request) {
        Component component = advancedModelingService.createUnsafeComponent();
        if (request != null && request.getName() != null) {
            component.setName(request.getName());
        }
        return ResponseEntity.ok(component);
    }
    
    @PostMapping("/unsafe/node")
    public ResponseEntity<Node> createUnsafeNode(@RequestBody(required = false) UnsafeElementRequest request) {
        Node node = advancedModelingService.createUnsafeNode();
        if (request != null && request.getName() != null) {
            node.setName(request.getName());
        }
        return ResponseEntity.ok(node);
    }
    
    @PostMapping("/unsafe/artifact")
    public ResponseEntity<Artifact> createUnsafeArtifact(@RequestBody(required = false) UnsafeElementRequest request) {
        Artifact artifact = advancedModelingService.createUnsafeArtifact();
        if (request != null && request.getName() != null) {
            artifact.setName(request.getName());
        }
        return ResponseEntity.ok(artifact);
    }
    
    @PostMapping("/unsafe/datatype")
    public ResponseEntity<DataType> createUnsafeDataType(@RequestBody(required = false) UnsafeElementRequest request) {
        DataType dataType = advancedModelingService.createUnsafeDataType();
        if (request != null && request.getName() != null) {
            dataType.setName(request.getName());
        }
        return ResponseEntity.ok(dataType);
    }
    
    @PostMapping("/unsafe/primitivetype")
    public ResponseEntity<PrimitiveType> createUnsafePrimitiveType(@RequestBody(required = false) UnsafeElementRequest request) {
        PrimitiveType primitiveType = advancedModelingService.createUnsafePrimitiveType();
        if (request != null && request.getName() != null) {
            primitiveType.setName(request.getName());
        }
        return ResponseEntity.ok(primitiveType);
    }
    
    @PostMapping("/unsafe/enumeration")
    public ResponseEntity<Enumeration> createUnsafeEnumeration(@RequestBody(required = false) UnsafeElementRequest request) {
        Enumeration enumeration = advancedModelingService.createUnsafeEnumeration();
        if (request != null && request.getName() != null) {
            enumeration.setName(request.getName());
        }
        return ResponseEntity.ok(enumeration);
    }
    
    @PostMapping("/unsafe/enumerationliteral")
    public ResponseEntity<EnumerationLiteral> createUnsafeEnumerationLiteral(@RequestBody(required = false) UnsafeElementRequest request) {
        EnumerationLiteral literal = advancedModelingService.createUnsafeEnumerationLiteral();
        if (request != null && request.getName() != null) {
            literal.setName(request.getName());
        }
        return ResponseEntity.ok(literal);
    }
    
    @PostMapping("/unsafe/actor")
    public ResponseEntity<Actor> createUnsafeActor(@RequestBody(required = false) UnsafeElementRequest request) {
        Actor actor = advancedModelingService.createUnsafeActor();
        if (request != null && request.getName() != null) {
            actor.setName(request.getName());
        }
        return ResponseEntity.ok(actor);
    }
    
    @PostMapping("/unsafe/usecase")
    public ResponseEntity<UseCase> createUnsafeUseCase(@RequestBody(required = false) UnsafeElementRequest request) {
        UseCase useCase = advancedModelingService.createUnsafeUseCase();
        if (request != null && request.getName() != null) {
            useCase.setName(request.getName());
        }
        return ResponseEntity.ok(useCase);
    }
    
    @PostMapping("/unsafe/collaboration")
    public ResponseEntity<Collaboration> createUnsafeCollaboration(@RequestBody(required = false) UnsafeElementRequest request) {
        Collaboration collaboration = advancedModelingService.createUnsafeCollaboration();
        if (request != null && request.getName() != null) {
            collaboration.setName(request.getName());
        }
        return ResponseEntity.ok(collaboration);
    }
    
    @PostMapping("/unsafe/instancespecification")
    public ResponseEntity<InstanceSpecification> createUnsafeInstanceSpecification(@RequestBody(required = false) UnsafeElementRequest request) {
        InstanceSpecification instance = advancedModelingService.createUnsafeInstanceSpecification();
        if (request != null && request.getName() != null) {
            instance.setName(request.getName());
        }
        return ResponseEntity.ok(instance);
    }

    // ========== BEHAVIORAL ELEMENTS ==========
    
    @PostMapping("/unsafe/operation")
    public ResponseEntity<Operation> createUnsafeOperation(@RequestBody(required = false) UnsafeElementRequest request) {
        Operation operation = advancedModelingService.createUnsafeOperation();
        if (request != null && request.getName() != null) {
            operation.setName(request.getName());
        }
        return ResponseEntity.ok(operation);
    }
    
    @PostMapping("/unsafe/parameter")
    public ResponseEntity<Parameter> createUnsafeParameter(@RequestBody(required = false) UnsafeElementRequest request) {
        Parameter parameter = advancedModelingService.createUnsafeParameter();
        if (request != null && request.getName() != null) {
            parameter.setName(request.getName());
        }
        return ResponseEntity.ok(parameter);
    }
    
    @PostMapping("/unsafe/property")
    public ResponseEntity<Property> createUnsafeProperty(@RequestBody(required = false) UnsafeElementRequest request) {
        Property property = advancedModelingService.createUnsafeProperty();
        if (request != null && request.getName() != null) {
            property.setName(request.getName());
        }
        return ResponseEntity.ok(property);
    }
    
    @PostMapping("/unsafe/activity")
    public ResponseEntity<Activity> createUnsafeActivity(@RequestBody(required = false) UnsafeElementRequest request) {
        Activity activity = advancedModelingService.createUnsafeActivity();
        if (request != null && request.getName() != null) {
            activity.setName(request.getName());
        }
        return ResponseEntity.ok(activity);
    }
    
    @PostMapping("/unsafe/statemachine")
    public ResponseEntity<StateMachine> createUnsafeStateMachine(@RequestBody(required = false) UnsafeElementRequest request) {
        StateMachine stateMachine = advancedModelingService.createUnsafeStateMachine();
        if (request != null && request.getName() != null) {
            stateMachine.setName(request.getName());
        }
        return ResponseEntity.ok(stateMachine);
    }
    
    @PostMapping("/unsafe/interaction")
    public ResponseEntity<Interaction> createUnsafeInteraction(@RequestBody(required = false) UnsafeElementRequest request) {
        Interaction interaction = advancedModelingService.createUnsafeInteraction();
        if (request != null && request.getName() != null) {
            interaction.setName(request.getName());
        }
        return ResponseEntity.ok(interaction);
    }
    
    @PostMapping("/unsafe/signal")
    public ResponseEntity<Signal> createUnsafeSignal(@RequestBody(required = false) UnsafeElementRequest request) {
        Signal signal = advancedModelingService.createUnsafeSignal();
        if (request != null && request.getName() != null) {
            signal.setName(request.getName());
        }
        return ResponseEntity.ok(signal);
    }
    
    @PostMapping("/unsafe/reception")
    public ResponseEntity<Reception> createUnsafeReception(@RequestBody(required = false) UnsafeElementRequest request) {
        Reception reception = advancedModelingService.createUnsafeReception();
        if (request != null && request.getName() != null) {
            reception.setName(request.getName());
        }
        return ResponseEntity.ok(reception);
    }

    // ========== RELATIONSHIP ELEMENTS ==========
    
    @PostMapping("/unsafe/association")
    public ResponseEntity<Association> createUnsafeAssociation(@RequestBody(required = false) UnsafeElementRequest request) {
        Association association = advancedModelingService.createUnsafeAssociation();
        if (request != null && request.getName() != null) {
            association.setName(request.getName());
        }
        return ResponseEntity.ok(association);
    }
    
    @PostMapping("/unsafe/generalization")
    public ResponseEntity<Generalization> createUnsafeGeneralization(@RequestBody(required = false) UnsafeElementRequest request) {
        Generalization generalization = advancedModelingService.createUnsafeGeneralization();
        return ResponseEntity.ok(generalization);
    }
    
    @PostMapping("/unsafe/interfacerealization")
    public ResponseEntity<InterfaceRealization> createUnsafeInterfaceRealization(@RequestBody(required = false) UnsafeElementRequest request) {
        InterfaceRealization realization = advancedModelingService.createUnsafeInterfaceRealization();
        if (request != null && request.getName() != null) {
            realization.setName(request.getName());
        }
        return ResponseEntity.ok(realization);
    }
    
    @PostMapping("/unsafe/dependency")
    public ResponseEntity<Dependency> createUnsafeDependency(@RequestBody(required = false) UnsafeElementRequest request) {
        Dependency dependency = advancedModelingService.createUnsafeDependency();
        if (request != null && request.getName() != null) {
            dependency.setName(request.getName());
        }
        return ResponseEntity.ok(dependency);
    }
    
    @PostMapping("/unsafe/usage")
    public ResponseEntity<Usage> createUnsafeUsage(@RequestBody(required = false) UnsafeElementRequest request) {
        Usage usage = advancedModelingService.createUnsafeUsage();
        if (request != null && request.getName() != null) {
            usage.setName(request.getName());
        }
        return ResponseEntity.ok(usage);
    }
    
    @PostMapping("/unsafe/abstraction")
    public ResponseEntity<Abstraction> createUnsafeAbstraction(@RequestBody(required = false) UnsafeElementRequest request) {
        Abstraction abstraction = advancedModelingService.createUnsafeAbstraction();
        if (request != null && request.getName() != null) {
            abstraction.setName(request.getName());
        }
        return ResponseEntity.ok(abstraction);
    }
    
    @PostMapping("/unsafe/realization")
    public ResponseEntity<Realization> createUnsafeRealization(@RequestBody(required = false) UnsafeElementRequest request) {
        Realization realization = advancedModelingService.createUnsafeRealization();
        if (request != null && request.getName() != null) {
            realization.setName(request.getName());
        }
        return ResponseEntity.ok(realization);
    }

    // ========== ACTIVITY DIAGRAM ELEMENTS ==========
    
    @PostMapping("/unsafe/initialnode")
    public ResponseEntity<InitialNode> createUnsafeInitialNode(@RequestBody(required = false) UnsafeElementRequest request) {
        InitialNode node = advancedModelingService.createUnsafeInitialNode();
        if (request != null && request.getName() != null) {
            node.setName(request.getName());
        }
        return ResponseEntity.ok(node);
    }
    
    @PostMapping("/unsafe/activityfinalnode")
    public ResponseEntity<ActivityFinalNode> createUnsafeActivityFinalNode(@RequestBody(required = false) UnsafeElementRequest request) {
        ActivityFinalNode node = advancedModelingService.createUnsafeActivityFinalNode();
        if (request != null && request.getName() != null) {
            node.setName(request.getName());
        }
        return ResponseEntity.ok(node);
    }
    
    @PostMapping("/unsafe/opaqueaction")
    public ResponseEntity<OpaqueAction> createUnsafeOpaqueAction(@RequestBody(required = false) UnsafeElementRequest request) {
        OpaqueAction action = advancedModelingService.createUnsafeOpaqueAction();
        if (request != null && request.getName() != null) {
            action.setName(request.getName());
        }
        return ResponseEntity.ok(action);
    }
    
    @PostMapping("/unsafe/callbehavioraction")
    public ResponseEntity<CallBehaviorAction> createUnsafeCallBehaviorAction(@RequestBody(required = false) UnsafeElementRequest request) {
        CallBehaviorAction action = advancedModelingService.createUnsafeCallBehaviorAction();
        if (request != null && request.getName() != null) {
            action.setName(request.getName());
        }
        return ResponseEntity.ok(action);
    }
    
    @PostMapping("/unsafe/calloperationaction")
    public ResponseEntity<CallOperationAction> createUnsafeCallOperationAction(@RequestBody(required = false) UnsafeElementRequest request) {
        CallOperationAction action = advancedModelingService.createUnsafeCallOperationAction();
        if (request != null && request.getName() != null) {
            action.setName(request.getName());
        }
        return ResponseEntity.ok(action);
    }
    
    @PostMapping("/unsafe/decisionnode")
    public ResponseEntity<DecisionNode> createUnsafeDecisionNode(@RequestBody(required = false) UnsafeElementRequest request) {
        DecisionNode node = advancedModelingService.createUnsafeDecisionNode();
        if (request != null && request.getName() != null) {
            node.setName(request.getName());
        }
        return ResponseEntity.ok(node);
    }
    
    @PostMapping("/unsafe/mergenode")
    public ResponseEntity<MergeNode> createUnsafeMergeNode(@RequestBody(required = false) UnsafeElementRequest request) {
        MergeNode node = advancedModelingService.createUnsafeMergeNode();
        if (request != null && request.getName() != null) {
            node.setName(request.getName());
        }
        return ResponseEntity.ok(node);
    }
    
    @PostMapping("/unsafe/forknode")
    public ResponseEntity<ForkNode> createUnsafeForkNode(@RequestBody(required = false) UnsafeElementRequest request) {
        ForkNode node = advancedModelingService.createUnsafeForkNode();
        if (request != null && request.getName() != null) {
            node.setName(request.getName());
        }
        return ResponseEntity.ok(node);
    }
    
    @PostMapping("/unsafe/joinnode")
    public ResponseEntity<JoinNode> createUnsafeJoinNode(@RequestBody(required = false) UnsafeElementRequest request) {
        JoinNode node = advancedModelingService.createUnsafeJoinNode();
        if (request != null && request.getName() != null) {
            node.setName(request.getName());
        }
        return ResponseEntity.ok(node);
    }
    
    @PostMapping("/unsafe/controlflow")
    public ResponseEntity<ControlFlow> createUnsafeControlFlow(@RequestBody(required = false) UnsafeElementRequest request) {
        ControlFlow flow = advancedModelingService.createUnsafeControlFlow();
        if (request != null && request.getName() != null) {
            flow.setName(request.getName());
        }
        return ResponseEntity.ok(flow);
    }
    
    @PostMapping("/unsafe/objectflow")
    public ResponseEntity<ObjectFlow> createUnsafeObjectFlow(@RequestBody(required = false) UnsafeElementRequest request) {
        ObjectFlow flow = advancedModelingService.createUnsafeObjectFlow();
        if (request != null && request.getName() != null) {
            flow.setName(request.getName());
        }
        return ResponseEntity.ok(flow);
    }

    // ========== STATE MACHINE ELEMENTS ==========
    
    @PostMapping("/unsafe/region")
    public ResponseEntity<Region> createUnsafeRegion(@RequestBody(required = false) UnsafeElementRequest request) {
        Region region = advancedModelingService.createUnsafeRegion();
        if (request != null && request.getName() != null) {
            region.setName(request.getName());
        }
        return ResponseEntity.ok(region);
    }
    
    @PostMapping("/unsafe/state")
    public ResponseEntity<State> createUnsafeState(@RequestBody(required = false) UnsafeElementRequest request) {
        State state = advancedModelingService.createUnsafeState();
        if (request != null && request.getName() != null) {
            state.setName(request.getName());
        }
        return ResponseEntity.ok(state);
    }
    
    @PostMapping("/unsafe/pseudostate")
    public ResponseEntity<Pseudostate> createUnsafePseudostate(@RequestBody(required = false) UnsafeElementRequest request) {
        Pseudostate pseudostate = advancedModelingService.createUnsafePseudostate();
        if (request != null && request.getName() != null) {
            pseudostate.setName(request.getName());
        }
        return ResponseEntity.ok(pseudostate);
    }
    
    @PostMapping("/unsafe/finalstate")
    public ResponseEntity<FinalState> createUnsafeFinalState(@RequestBody(required = false) UnsafeElementRequest request) {
        FinalState finalState = advancedModelingService.createUnsafeFinalState();
        if (request != null && request.getName() != null) {
            finalState.setName(request.getName());
        }
        return ResponseEntity.ok(finalState);
    }
    
    @PostMapping("/unsafe/transition")
    public ResponseEntity<Transition> createUnsafeTransition(@RequestBody(required = false) UnsafeElementRequest request) {
        Transition transition = advancedModelingService.createUnsafeTransition();
        if (request != null && request.getName() != null) {
            transition.setName(request.getName());
        }
        return ResponseEntity.ok(transition);
    }

    // ========== INTERACTION ELEMENTS ==========
    
    @PostMapping("/unsafe/lifeline")
    public ResponseEntity<Lifeline> createUnsafeLifeline(@RequestBody(required = false) UnsafeElementRequest request) {
        Lifeline lifeline = advancedModelingService.createUnsafeLifeline();
        if (request != null && request.getName() != null) {
            lifeline.setName(request.getName());
        }
        return ResponseEntity.ok(lifeline);
    }
    
    @PostMapping("/unsafe/message")
    public ResponseEntity<Message> createUnsafeMessage(@RequestBody(required = false) UnsafeElementRequest request) {
        Message message = advancedModelingService.createUnsafeMessage();
        if (request != null && request.getName() != null) {
            message.setName(request.getName());
        }
        return ResponseEntity.ok(message);
    }
    
    @PostMapping("/unsafe/executionspecification")
    public ResponseEntity<ExecutionSpecification> createUnsafeExecutionSpecification(@RequestBody(required = false) UnsafeElementRequest request) {
        ExecutionSpecification exec = advancedModelingService.createUnsafeExecutionSpecification();
        if (request != null && request.getName() != null) {
            exec.setName(request.getName());
        }
        return ResponseEntity.ok(exec);
    }
    
    @PostMapping("/unsafe/combinedfragment")
    public ResponseEntity<CombinedFragment> createUnsafeCombinedFragment(@RequestBody(required = false) UnsafeElementRequest request) {
        CombinedFragment fragment = advancedModelingService.createUnsafeCombinedFragment();
        if (request != null && request.getName() != null) {
            fragment.setName(request.getName());
        }
        return ResponseEntity.ok(fragment);
    }
    
    @PostMapping("/unsafe/interactionoperand")
    public ResponseEntity<InteractionOperand> createUnsafeInteractionOperand(@RequestBody(required = false) UnsafeElementRequest request) {
        InteractionOperand operand = advancedModelingService.createUnsafeInteractionOperand();
        if (request != null && request.getName() != null) {
            operand.setName(request.getName());
        }
        return ResponseEntity.ok(operand);
    }

    // ========== PROFILE ELEMENTS ==========
    
    @PostMapping("/unsafe/profile")
    public ResponseEntity<Profile> createUnsafeProfile(@RequestBody(required = false) UnsafeElementRequest request) {
        Profile profile = advancedModelingService.createUnsafeProfile();
        if (request != null && request.getName() != null) {
            profile.setName(request.getName());
        }
        return ResponseEntity.ok(profile);
    }
    
    @PostMapping("/unsafe/stereotype")
    public ResponseEntity<Stereotype> createUnsafeStereotype(@RequestBody(required = false) UnsafeElementRequest request) {
        Stereotype stereotype = advancedModelingService.createUnsafeStereotype();
        if (request != null && request.getName() != null) {
            stereotype.setName(request.getName());
        }
        return ResponseEntity.ok(stereotype);
    }
    
    @PostMapping("/unsafe/extension")
    public ResponseEntity<Extension> createUnsafeExtension(@RequestBody(required = false) UnsafeElementRequest request) {
        Extension extension = advancedModelingService.createUnsafeExtension();
        if (request != null && request.getName() != null) {
            extension.setName(request.getName());
        }
        return ResponseEntity.ok(extension);
    }

    // ========== CONSTRAINT ELEMENTS ==========
    
    @PostMapping("/unsafe/constraint")
    public ResponseEntity<Constraint> createUnsafeConstraint(@RequestBody(required = false) UnsafeElementRequest request) {
        Constraint constraint = advancedModelingService.createUnsafeConstraint();
        if (request != null && request.getName() != null) {
            constraint.setName(request.getName());
        }
        return ResponseEntity.ok(constraint);
    }
    
    @PostMapping("/unsafe/opaqueexpression")
    public ResponseEntity<OpaqueExpression> createUnsafeOpaqueExpression(@RequestBody(required = false) UnsafeElementRequest request) {
        OpaqueExpression expression = advancedModelingService.createUnsafeOpaqueExpression();
        if (request != null && request.getName() != null) {
            expression.setName(request.getName());
        }
        return ResponseEntity.ok(expression);
    }
    
    @PostMapping("/unsafe/literalboolean")
    public ResponseEntity<LiteralBoolean> createUnsafeLiteralBoolean(@RequestBody(required = false) UnsafeElementRequest request) {
        LiteralBoolean literal = advancedModelingService.createUnsafeLiteralBoolean();
        if (request != null && request.getName() != null) {
            literal.setName(request.getName());
        }
        return ResponseEntity.ok(literal);
    }
    
    @PostMapping("/unsafe/literalinteger")
    public ResponseEntity<LiteralInteger> createUnsafeLiteralInteger(@RequestBody(required = false) UnsafeElementRequest request) {
        LiteralInteger literal = advancedModelingService.createUnsafeLiteralInteger();
        if (request != null && request.getName() != null) {
            literal.setName(request.getName());
        }
        return ResponseEntity.ok(literal);
    }
    
    @PostMapping("/unsafe/literalstring")
    public ResponseEntity<LiteralString> createUnsafeLiteralString(@RequestBody(required = false) UnsafeElementRequest request) {
        LiteralString literal = advancedModelingService.createUnsafeLiteralString();
        if (request != null && request.getName() != null) {
            literal.setName(request.getName());
        }
        return ResponseEntity.ok(literal);
    }

    // ========== COMPOSITE STRUCTURE ELEMENTS ==========
    
    @PostMapping("/unsafe/port")
    public ResponseEntity<Port> createUnsafePort(@RequestBody(required = false) UnsafeElementRequest request) {
        Port port = advancedModelingService.createUnsafePort();
        if (request != null && request.getName() != null) {
            port.setName(request.getName());
        }
        return ResponseEntity.ok(port);
    }
    
    @PostMapping("/unsafe/connector")
    public ResponseEntity<Connector> createUnsafeConnector(@RequestBody(required = false) UnsafeElementRequest request) {
        Connector connector = advancedModelingService.createUnsafeConnector();
        if (request != null && request.getName() != null) {
            connector.setName(request.getName());
        }
        return ResponseEntity.ok(connector);
    }
    
    @PostMapping("/unsafe/connectorend")
    public ResponseEntity<ConnectorEnd> createUnsafeConnectorEnd(@RequestBody(required = false) UnsafeElementRequest request) {
        ConnectorEnd end = advancedModelingService.createUnsafeConnectorEnd();
        return ResponseEntity.ok(end);
    }

    // ========== DEPLOYMENT ELEMENTS ==========
    
    @PostMapping("/unsafe/device")
    public ResponseEntity<Device> createUnsafeDevice(@RequestBody(required = false) UnsafeElementRequest request) {
        Device device = advancedModelingService.createUnsafeDevice();
        if (request != null && request.getName() != null) {
            device.setName(request.getName());
        }
        return ResponseEntity.ok(device);
    }
    
    @PostMapping("/unsafe/executionenvironment")
    public ResponseEntity<ExecutionEnvironment> createUnsafeExecutionEnvironment(@RequestBody(required = false) UnsafeElementRequest request) {
        ExecutionEnvironment env = advancedModelingService.createUnsafeExecutionEnvironment();
        if (request != null && request.getName() != null) {
            env.setName(request.getName());
        }
        return ResponseEntity.ok(env);
    }
    
    @PostMapping("/unsafe/deployment")
    public ResponseEntity<Deployment> createUnsafeDeployment(@RequestBody(required = false) UnsafeElementRequest request) {
        Deployment deployment = advancedModelingService.createUnsafeDeployment();
        if (request != null && request.getName() != null) {
            deployment.setName(request.getName());
        }
        return ResponseEntity.ok(deployment);
    }

    // ========== ACTION ELEMENTS ==========
    
    @PostMapping("/unsafe/createobjectaction")
    public ResponseEntity<CreateObjectAction> createUnsafeCreateObjectAction(@RequestBody(required = false) UnsafeElementRequest request) {
        CreateObjectAction action = advancedModelingService.createUnsafeCreateObjectAction();
        if (request != null && request.getName() != null) {
            action.setName(request.getName());
        }
        return ResponseEntity.ok(action);
    }
    
    @PostMapping("/unsafe/destroyobjectaction")
    public ResponseEntity<DestroyObjectAction> createUnsafeDestroyObjectAction(@RequestBody(required = false) UnsafeElementRequest request) {
        DestroyObjectAction action = advancedModelingService.createUnsafeDestroyObjectAction();
        if (request != null && request.getName() != null) {
            action.setName(request.getName());
        }
        return ResponseEntity.ok(action);
    }
    
    @PostMapping("/unsafe/readstructuralfeatureaction")
    public ResponseEntity<ReadStructuralFeatureAction> createUnsafeReadStructuralFeatureAction(@RequestBody(required = false) UnsafeElementRequest request) {
        ReadStructuralFeatureAction action = advancedModelingService.createUnsafeReadStructuralFeatureAction();
        if (request != null && request.getName() != null) {
            action.setName(request.getName());
        }
        return ResponseEntity.ok(action);
    }
    
    @PostMapping("/unsafe/writestructuralfeatureaction")
    public ResponseEntity<WriteStructuralFeatureAction> createUnsafeWriteStructuralFeatureAction(@RequestBody(required = false) UnsafeElementRequest request) {
        WriteStructuralFeatureAction action = advancedModelingService.createUnsafeWriteStructuralFeatureAction();
        if (request != null && request.getName() != null) {
            action.setName(request.getName());
        }
        return ResponseEntity.ok(action);
    }

    // ========== PIN ELEMENTS ==========
    
    @PostMapping("/unsafe/inputpin")
    public ResponseEntity<InputPin> createUnsafeInputPin(@RequestBody(required = false) UnsafeElementRequest request) {
        InputPin pin = advancedModelingService.createUnsafeInputPin();
        if (request != null && request.getName() != null) {
            pin.setName(request.getName());
        }
        return ResponseEntity.ok(pin);
    }
    
    @PostMapping("/unsafe/outputpin")
    public ResponseEntity<OutputPin> createUnsafeOutputPin(@RequestBody(required = false) UnsafeElementRequest request) {
        OutputPin pin = advancedModelingService.createUnsafeOutputPin();
        if (request != null && request.getName() != null) {
            pin.setName(request.getName());
        }
        return ResponseEntity.ok(pin);
    }
    
    @PostMapping("/unsafe/valuepin")
    public ResponseEntity<ValuePin> createUnsafeValuePin(@RequestBody(required = false) UnsafeElementRequest request) {
        ValuePin pin = advancedModelingService.createUnsafeValuePin();
        if (request != null && request.getName() != null) {
            pin.setName(request.getName());
        }
        return ResponseEntity.ok(pin);
    }

    // ========== COMMENT ELEMENTS ==========
    
    @PostMapping("/unsafe/comment")
    public ResponseEntity<Comment> createUnsafeComment(@RequestBody(required = false) UnsafeElementRequest request) {
        Comment comment = advancedModelingService.createUnsafeComment();
        return ResponseEntity.ok(comment);
    }

    // ========== VALIDATION ENDPOINTS ==========
    
    @PostMapping("/validate/element")
    public ResponseEntity<?> validateElement(@RequestBody ValidationRequest request) {
        try {
            var validationResult = advancedModelingService.getValidator().validate(request.getElement());
            return ResponseEntity.ok(validationResult);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Validation failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/validate/model")
    public ResponseEntity<?> validateModel(@RequestBody Model model) {
        try {
            var validationResult = advancedModelingService.getValidator().validateModel(model);
            return ResponseEntity.ok(validationResult);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Model validation failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/wellformed/check")
    public ResponseEntity<Boolean> checkWellFormed(@RequestBody Element element) {
        try {
            boolean isWellFormed = advancedModelingService.isWellFormed(element);
            return ResponseEntity.ok(isWellFormed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
    
    @PostMapping("/wellformed/make")
    public ResponseEntity<Element> makeWellFormed(@RequestBody Element element, @RequestParam(required = false) String defaultName) {
        try {
            advancedModelingService.makeWellFormed(element, defaultName);
            return ResponseEntity.ok(element);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ========== UTILITY METHODS ==========
    
    private Element createElementByTypeName(String elementType) {
        switch (elementType.toLowerCase()) {
            case "class": return advancedModelingService.createUnsafeClass();
            case "interface": return advancedModelingService.createUnsafeInterface();
            case "package": return advancedModelingService.createUnsafePackage();
            case "model": return advancedModelingService.createUnsafeModel();
            case "component": return advancedModelingService.createUnsafeComponent();
            case "node": return advancedModelingService.createUnsafeNode();
            case "artifact": return advancedModelingService.createUnsafeArtifact();
            case "datatype": return advancedModelingService.createUnsafeDataType();
            case "primitivetype": return advancedModelingService.createUnsafePrimitiveType();
            case "enumeration": return advancedModelingService.createUnsafeEnumeration();
            case "enumerationliteral": return advancedModelingService.createUnsafeEnumerationLiteral();
            case "actor": return advancedModelingService.createUnsafeActor();
            case "usecase": return advancedModelingService.createUnsafeUseCase();
            case "collaboration": return advancedModelingService.createUnsafeCollaboration();
            case "instancespecification": return advancedModelingService.createUnsafeInstanceSpecification();
            case "operation": return advancedModelingService.createUnsafeOperation();
            case "parameter": return advancedModelingService.createUnsafeParameter();
            case "property": return advancedModelingService.createUnsafeProperty();
            case "activity": return advancedModelingService.createUnsafeActivity();
            case "statemachine": return advancedModelingService.createUnsafeStateMachine();
            case "interaction": return advancedModelingService.createUnsafeInteraction();
            case "signal": return advancedModelingService.createUnsafeSignal();
            case "reception": return advancedModelingService.createUnsafeReception();
            case "association": return advancedModelingService.createUnsafeAssociation();
            case "generalization": return advancedModelingService.createUnsafeGeneralization();
            case "interfacerealization": return advancedModelingService.createUnsafeInterfaceRealization();
            case "dependency": return advancedModelingService.createUnsafeDependency();
            case "usage": return advancedModelingService.createUnsafeUsage();
            case "abstraction": return advancedModelingService.createUnsafeAbstraction();
            case "realization": return advancedModelingService.createUnsafeRealization();
            case "initialnode": return advancedModelingService.createUnsafeInitialNode();
            case "activityfinalnode": return advancedModelingService.createUnsafeActivityFinalNode();
            case "opaqueaction": return advancedModelingService.createUnsafeOpaqueAction();
            case "callbehavioraction": return advancedModelingService.createUnsafeCallBehaviorAction();
            case "calloperationaction": return advancedModelingService.createUnsafeCallOperationAction();
            case "decisionnode": return advancedModelingService.createUnsafeDecisionNode();
            case "mergenode": return advancedModelingService.createUnsafeMergeNode();
            case "forknode": return advancedModelingService.createUnsafeForkNode();
            case "joinnode": return advancedModelingService.createUnsafeJoinNode();
            case "controlflow": return advancedModelingService.createUnsafeControlFlow();
            case "objectflow": return advancedModelingService.createUnsafeObjectFlow();
            case "region": return advancedModelingService.createUnsafeRegion();
            case "state": return advancedModelingService.createUnsafeState();
            case "pseudostate": return advancedModelingService.createUnsafePseudostate();
            case "finalstate": return advancedModelingService.createUnsafeFinalState();
            case "transition": return advancedModelingService.createUnsafeTransition();
            case "lifeline": return advancedModelingService.createUnsafeLifeline();
            case "message": return advancedModelingService.createUnsafeMessage();
            case "executionspecification": return advancedModelingService.createUnsafeExecutionSpecification();
            case "combinedfragment": return advancedModelingService.createUnsafeCombinedFragment();
            case "interactionoperand": return advancedModelingService.createUnsafeInteractionOperand();
            case "profile": return advancedModelingService.createUnsafeProfile();
            case "stereotype": return advancedModelingService.createUnsafeStereotype();
            case "extension": return advancedModelingService.createUnsafeExtension();
            case "constraint": return advancedModelingService.createUnsafeConstraint();
            case "opaqueexpression": return advancedModelingService.createUnsafeOpaqueExpression();
            case "literalboolean": return advancedModelingService.createUnsafeLiteralBoolean();
            case "literalinteger": return advancedModelingService.createUnsafeLiteralInteger();
            case "literalstring": return advancedModelingService.createUnsafeLiteralString();
            case "port": return advancedModelingService.createUnsafePort();
            case "connector": return advancedModelingService.createUnsafeConnector();
            case "connectorend": return advancedModelingService.createUnsafeConnectorEnd();
            case "device": return advancedModelingService.createUnsafeDevice();
            case "executionenvironment": return advancedModelingService.createUnsafeExecutionEnvironment();
            case "deployment": return advancedModelingService.createUnsafeDeployment();
            case "createobjectaction": return advancedModelingService.createUnsafeCreateObjectAction();
            case "destroyobjectaction": return advancedModelingService.createUnsafeDestroyObjectAction();
            case "readstructuralfeatureaction": return advancedModelingService.createUnsafeReadStructuralFeatureAction();
            case "writestructuralfeatureaction": return advancedModelingService.createUnsafeWriteStructuralFeatureAction();
            case "inputpin": return advancedModelingService.createUnsafeInputPin();
            case "outputpin": return advancedModelingService.createUnsafeOutputPin();
            case "valuepin": return advancedModelingService.createUnsafeValuePin();
            case "comment": return advancedModelingService.createUnsafeComment();
            default:
                throw new IllegalArgumentException("Unknown element type: " + elementType);
        }
    }
    
    private List<? extends Element> createElementsBatchByTypeName(String elementType, int count) {
        switch (elementType.toLowerCase()) {
            case "class": return advancedModelingService.createUnsafeElements(org.eclipse.uml2.uml.Class.class, count);
            case "interface": return advancedModelingService.createUnsafeElements(Interface.class, count);
            case "package": return advancedModelingService.createUnsafeElements(org.eclipse.uml2.uml.Package.class, count);
            case "model": return advancedModelingService.createUnsafeElements(Model.class, count);
            case "component": return advancedModelingService.createUnsafeElements(Component.class, count);
            case "node": return advancedModelingService.createUnsafeElements(Node.class, count);
            case "artifact": return advancedModelingService.createUnsafeElements(Artifact.class, count);
            case "datatype": return advancedModelingService.createUnsafeElements(DataType.class, count);
            case "primitivetype": return advancedModelingService.createUnsafeElements(PrimitiveType.class, count);
            case "enumeration": return advancedModelingService.createUnsafeElements(Enumeration.class, count);
            case "enumerationliteral": return advancedModelingService.createUnsafeElements(EnumerationLiteral.class, count);
            case "actor": return advancedModelingService.createUnsafeElements(Actor.class, count);
            case "usecase": return advancedModelingService.createUnsafeElements(UseCase.class, count);
            case "collaboration": return advancedModelingService.createUnsafeElements(Collaboration.class, count);
            case "instancespecification": return advancedModelingService.createUnsafeElements(InstanceSpecification.class, count);
            case "operation": return advancedModelingService.createUnsafeElements(Operation.class, count);
            case "parameter": return advancedModelingService.createUnsafeElements(Parameter.class, count);
            case "property": return advancedModelingService.createUnsafeElements(Property.class, count);
            case "activity": return advancedModelingService.createUnsafeElements(Activity.class, count);
            case "statemachine": return advancedModelingService.createUnsafeElements(StateMachine.class, count);
            case "interaction": return advancedModelingService.createUnsafeElements(Interaction.class, count);
            case "signal": return advancedModelingService.createUnsafeElements(Signal.class, count);
            case "reception": return advancedModelingService.createUnsafeElements(Reception.class, count);
            case "association": return advancedModelingService.createUnsafeElements(Association.class, count);
            case "generalization": return advancedModelingService.createUnsafeElements(Generalization.class, count);
            case "interfacerealization": return advancedModelingService.createUnsafeElements(InterfaceRealization.class, count);
            case "dependency": return advancedModelingService.createUnsafeElements(Dependency.class, count);
            case "usage": return advancedModelingService.createUnsafeElements(Usage.class, count);
            case "abstraction": return advancedModelingService.createUnsafeElements(Abstraction.class, count);
            case "realization": return advancedModelingService.createUnsafeElements(Realization.class, count);
            case "initialnode": return advancedModelingService.createUnsafeElements(InitialNode.class, count);
            case "activityfinalnode": return advancedModelingService.createUnsafeElements(ActivityFinalNode.class, count);
            case "opaqueaction": return advancedModelingService.createUnsafeElements(OpaqueAction.class, count);
            case "callbehavioraction": return advancedModelingService.createUnsafeElements(CallBehaviorAction.class, count);
            case "calloperationaction": return advancedModelingService.createUnsafeElements(CallOperationAction.class, count);
            case "decisionnode": return advancedModelingService.createUnsafeElements(DecisionNode.class, count);
            case "mergenode": return advancedModelingService.createUnsafeElements(MergeNode.class, count);
            case "forknode": return advancedModelingService.createUnsafeElements(ForkNode.class, count);
            case "joinnode": return advancedModelingService.createUnsafeElements(JoinNode.class, count);
            case "controlflow": return advancedModelingService.createUnsafeElements(ControlFlow.class, count);
            case "objectflow": return advancedModelingService.createUnsafeElements(ObjectFlow.class, count);
            case "region": return advancedModelingService.createUnsafeElements(Region.class, count);
            case "state": return advancedModelingService.createUnsafeElements(State.class, count);
            case "pseudostate": return advancedModelingService.createUnsafeElements(Pseudostate.class, count);
            case "finalstate": return advancedModelingService.createUnsafeElements(FinalState.class, count);
            case "transition": return advancedModelingService.createUnsafeElements(Transition.class, count);
            case "lifeline": return advancedModelingService.createUnsafeElements(Lifeline.class, count);
            case "message": return advancedModelingService.createUnsafeElements(Message.class, count);
            case "executionspecification": return advancedModelingService.createUnsafeElements(ExecutionSpecification.class, count);
            case "combinedfragment": return advancedModelingService.createUnsafeElements(CombinedFragment.class, count);
            case "interactionoperand": return advancedModelingService.createUnsafeElements(InteractionOperand.class, count);
            case "profile": return advancedModelingService.createUnsafeElements(Profile.class, count);
            case "stereotype": return advancedModelingService.createUnsafeElements(Stereotype.class, count);
            case "extension": return advancedModelingService.createUnsafeElements(Extension.class, count);
            case "constraint": return advancedModelingService.createUnsafeElements(Constraint.class, count);
            case "opaqueexpression": return advancedModelingService.createUnsafeElements(OpaqueExpression.class, count);
            case "literalboolean": return advancedModelingService.createUnsafeElements(LiteralBoolean.class, count);
            case "literalinteger": return advancedModelingService.createUnsafeElements(LiteralInteger.class, count);
            case "literalstring": return advancedModelingService.createUnsafeElements(LiteralString.class, count);
            case "port": return advancedModelingService.createUnsafeElements(Port.class, count);
            case "connector": return advancedModelingService.createUnsafeElements(Connector.class, count);
            case "connectorend": return advancedModelingService.createUnsafeElements(ConnectorEnd.class, count);
            case "device": return advancedModelingService.createUnsafeElements(Device.class, count);
            case "executionenvironment": return advancedModelingService.createUnsafeElements(ExecutionEnvironment.class, count);
            case "deployment": return advancedModelingService.createUnsafeElements(Deployment.class, count);
            case "createobjectaction": return advancedModelingService.createUnsafeElements(CreateObjectAction.class, count);
            case "destroyobjectaction": return advancedModelingService.createUnsafeElements(DestroyObjectAction.class, count);
            case "readstructuralfeatureaction": return advancedModelingService.createUnsafeElements(ReadStructuralFeatureAction.class, count);
            case "writestructuralfeatureaction": return advancedModelingService.createUnsafeElements(WriteStructuralFeatureAction.class, count);
            case "inputpin": return advancedModelingService.createUnsafeElements(InputPin.class, count);
            case "outputpin": return advancedModelingService.createUnsafeElements(OutputPin.class, count);
            case "valuepin": return advancedModelingService.createUnsafeElements(ValuePin.class, count);
            case "comment": return advancedModelingService.createUnsafeElements(Comment.class, count);
            default:
                throw new IllegalArgumentException("Unknown element type: " + elementType);
        }
    }

    // ========== INFORMATION ENDPOINTS ==========
    
    @GetMapping("/unsafe/supported-types")
    public ResponseEntity<List<String>> getSupportedElementTypes() {
        List<String> supportedTypes = List.of(
            "class", "interface", "package", "model", "component", "node", "artifact", 
            "datatype", "primitivetype", "enumeration", "enumerationliteral", "actor", 
            "usecase", "collaboration", "instancespecification", "operation", "parameter", 
            "property", "activity", "statemachine", "interaction", "signal", "reception",
            "association", "generalization", "interfacerealization", "dependency", "usage", 
            "abstraction", "realization", "initialnode", "activityfinalnode", "opaqueaction", 
            "callbehavioraction", "calloperationaction", "decisionnode", "mergenode", 
            "forknode", "joinnode", "controlflow", "objectflow", "region", "state", 
            "pseudostate", "finalstate", "transition", "lifeline", "message", 
            "executionspecification", "combinedfragment", "interactionoperand", "profile", 
            "stereotype", "extension", "constraint", "opaqueexpression", "literalboolean", 
            "literalinteger", "literalstring", "port", "connector", "connectorend", 
            "device", "executionenvironment", "deployment", "createobjectaction", 
            "destroyobjectaction", "readstructuralfeatureaction", "writestructuralfeatureaction", 
            "inputpin", "outputpin", "valuepin", "comment"
        );
        return ResponseEntity.ok(supportedTypes);
    }
    
    @GetMapping("/unsafe/element-info/{elementType}")
    public ResponseEntity<Map<String, Object>> getElementTypeInfo(@PathVariable String elementType) {
        Map<String, Object> info = new HashMap<>();
        
        try {
            Element sampleElement = createElementByTypeName(elementType);
            info.put("elementType", elementType);
            info.put("javaClass", sampleElement.getClass().getSimpleName());
            info.put("isNamedElement", sampleElement instanceof NamedElement);
            info.put("isNamespace", sampleElement instanceof Namespace);
            info.put("isClassifier", sampleElement instanceof Classifier);
            info.put("isRelationship", sampleElement instanceof Relationship);
            info.put("isBehavior", sampleElement instanceof Behavior);
            info.put("isAction", sampleElement instanceof Action);
            info.put("isActivityNode", sampleElement instanceof ActivityNode);
            info.put("isVertex", sampleElement instanceof Vertex);
            info.put("requiresContainer", requiresContainer(sampleElement));
            info.put("canHaveChildren", canHaveChildren(sampleElement));
            
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Unknown element type: " + elementType));
        }
    }
    
    @GetMapping("/unsafe/validation-rules")
    public ResponseEntity<Map<String, List<String>>> getValidationRules() {
        Map<String, List<String>> rules = new HashMap<>();
        
        rules.put("NamedElement", List.of(
            "Must have a non-empty name",
            "Name should be unique within its namespace"
        ));
        
        rules.put("Association", List.of(
            "Must have at least 2 member ends",
            "Member ends must reference valid types"
        ));
        
        rules.put("Generalization", List.of(
            "Must have both general and specific classifiers",
            "Cannot create circular inheritance"
        ));
        
        rules.put("Operation", List.of(
            "Must belong to a classifier",
            "Parameters must have valid types"
        ));
        
        rules.put("Property", List.of(
            "Must belong to a classifier or association",
            "Type should be specified"
        ));
        
        rules.put("Transition", List.of(
            "Must have source and target vertices",
            "Must belong to a region"
        ));
        
        rules.put("Message", List.of(
            "Must have send and receive events",
            "Must belong to an interaction"
        ));
        
        rules.put("ActivityNode", List.of(
            "Must belong to an activity",
            "Control/object flows must connect valid nodes"
        ));
        
        return ResponseEntity.ok(rules);
    }

    // ========== BATCH OPERATIONS ==========
    
    @PostMapping("/unsafe/batch/create-model-structure")
    public ResponseEntity<Map<String, Object>> createModelStructure(@RequestBody Map<String, Object> structure) {
        try {
            Map<String, Object> result = new HashMap<>();
            
            // Create root model
            Model model = advancedModelingService.createUnsafeModel();
            String modelName = (String) structure.getOrDefault("modelName", "UnsafeModel");
            model.setName(modelName);
            result.put("model", model);
            
            // Create packages if specified
            @SuppressWarnings("unchecked")
            List<String> packageNames = (List<String>) structure.get("packages");
            if (packageNames != null) {
                List<org.eclipse.uml2.uml.Package> packages = packageNames.stream()
                    .map(name -> {
                        org.eclipse.uml2.uml.Package pkg = advancedModelingService.createUnsafePackage();
                        pkg.setName(name);
                        return pkg;
                    })
                    .toList();
                result.put("packages", packages);
            }
            
            // Create classes if specified
            @SuppressWarnings("unchecked")
            List<String> classNames = (List<String>) structure.get("classes");
            if (classNames != null) {
                List<org.eclipse.uml2.uml.Class> classes = classNames.stream()
                    .map(name -> {
                        org.eclipse.uml2.uml.Class clazz = advancedModelingService.createUnsafeClass();
                        clazz.setName(name);
                        return clazz;
                    })
                    .toList();
                result.put("classes", classes);
            }
            
            // Create interfaces if specified
            @SuppressWarnings("unchecked")
            List<String> interfaceNames = (List<String>) structure.get("interfaces");
            if (interfaceNames != null) {
                List<Interface> interfaces = interfaceNames.stream()
                    .map(name -> {
                        Interface iface = advancedModelingService.createUnsafeInterface();
                        iface.setName(name);
                        return iface;
                    })
                    .toList();
                result.put("interfaces", interfaces);
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to create model structure: " + e.getMessage()));
        }
    }
    
    @PostMapping("/unsafe/batch/create-activity-diagram")
    public ResponseEntity<Map<String, Object>> createActivityDiagram(@RequestBody Map<String, Object> diagramSpec) {
        try {
            Map<String, Object> result = new HashMap<>();
            
            // Create activity
            Activity activity = advancedModelingService.createUnsafeActivity();
            String activityName = (String) diagramSpec.getOrDefault("activityName", "UnsafeActivity");
            activity.setName(activityName);
            result.put("activity", activity);
            
            // Create nodes
            InitialNode initialNode = advancedModelingService.createUnsafeInitialNode();
            initialNode.setName("Initial");
            
            ActivityFinalNode finalNode = advancedModelingService.createUnsafeActivityFinalNode();
            finalNode.setName("Final");
            
            @SuppressWarnings("unchecked")
            List<String> actionNames = (List<String>) diagramSpec.get("actions");
            List<OpaqueAction> actions = new ArrayList<>();
            if (actionNames != null) {
                actions = actionNames.stream()
                    .map(name -> {
                        OpaqueAction action = advancedModelingService.createUnsafeOpaqueAction();
                        action.setName(name);
                        return action;
                    })
                    .toList();
            }
            
            result.put("initialNode", initialNode);
            result.put("finalNode", finalNode);
            result.put("actions", actions);
            
            // Create control flows
            List<ControlFlow> flows = new ArrayList<>();
            for (int i = 0; i < actions.size() + 1; i++) {
                ControlFlow flow = advancedModelingService.createUnsafeControlFlow();
                flow.setName("Flow" + (i + 1));
                flows.add(flow);
            }
            result.put("controlFlows", flows);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to create activity diagram: " + e.getMessage()));
        }
    }
    
    @PostMapping("/unsafe/batch/create-state-machine")
    public ResponseEntity<Map<String, Object>> createStateMachine(@RequestBody Map<String, Object> smSpec) {
        try {
            Map<String, Object> result = new HashMap<>();
            
            // Create state machine
            StateMachine stateMachine = advancedModelingService.createUnsafeStateMachine();
            String smName = (String) smSpec.getOrDefault("stateMachineName", "UnsafeStateMachine");
            stateMachine.setName(smName);
            result.put("stateMachine", stateMachine);
            
            // Create region
            Region region = advancedModelingService.createUnsafeRegion();
            region.setName("MainRegion");
            result.put("region", region);
            
            // Create states
            @SuppressWarnings("unchecked")
            List<String> stateNames = (List<String>) smSpec.get("states");
            List<State> states = new ArrayList<>();
            if (stateNames != null) {
                states = stateNames.stream()
                    .map(name -> {
                        State state = advancedModelingService.createUnsafeState();
                        state.setName(name);
                        return state;
                    })
                    .toList();
            }
            result.put("states", states);
            
            // Create initial pseudostate
            Pseudostate initial = advancedModelingService.createUnsafePseudostate();
            initial.setName("Initial");
            result.put("initialState", initial);
            
            // Create final state
            FinalState finalState = advancedModelingService.createUnsafeFinalState();
            finalState.setName("Final");
            result.put("finalState", finalState);
            
            // Create transitions
            List<Transition> transitions = new ArrayList<>();
            for (int i = 0; i < states.size() + 1; i++) {
                Transition transition = advancedModelingService.createUnsafeTransition();
                transition.setName("Transition" + (i + 1));
                transitions.add(transition);
            }
            result.put("transitions", transitions);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to create state machine: " + e.getMessage()));
        }
    }

    // ========== HELPER METHODS ==========
    
    private boolean requiresContainer(Element element) {
        // Most UML elements require a container for proper model structure
        return !(element instanceof Model || element instanceof Profile);
    }
    
    private boolean canHaveChildren(Element element) {
        return element instanceof Namespace || 
               element instanceof Activity || 
               element instanceof StateMachine || 
               element instanceof Interaction ||
               element instanceof Region;
    }
    
    // ========== ERROR HANDLING ==========
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e) {
        return ResponseEntity.internalServerError().body(Map.of("error", "Internal server error: " + e.getMessage()));
    }
}