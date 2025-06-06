package com.javapak.mda.service;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.javapak.mda.dto.AddClassRequest;
import com.javapak.mda.dto.AddInterfaceRequest;
import com.javapak.mda.dto.PropertySpec;

import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ModelingService {

    /**
     * Create completely empty model
     */
    public Model createEmptyModel(String name, String uri) {
        Model model = UMLFactory.eINSTANCE.createModel();
        model.setName(name);
        if (uri != null) {
            model.setURI(uri);
        }
        return model;
    }

    /**
     * Create model from predefined templates
     */
    public Model createModelFromTemplate(String name, String uri, String templateType) {
        Model model = createEmptyModel(name, uri);
        
        switch (templateType.toLowerCase()) {
            case "domain":
                return createDomainModelTemplate(model);
            case "component":
                return createComponentModelTemplate(model);
            case "layered":
                return createLayeredArchitectureTemplate(model);
            case "microservice":
                return createMicroserviceTemplate(model);
            default:
                return model; // Return empty if unknown template
        }
    }

    /**
     * Domain Model Template - typical business domain structure
     */
    private Model createDomainModelTemplate(Model model) {
        // Create typical domain packages
        Package domainPkg = model.createNestedPackage("domain");
        Package entitiesPkg = domainPkg.createNestedPackage("entities");
        Package valueObjectsPkg = domainPkg.createNestedPackage("valueobjects");
        Package servicesPkg = domainPkg.createNestedPackage("services");
        Package repositoriesPkg = domainPkg.createNestedPackage("repositories");
        
        // Add some basic interfaces
        Interface entityInterface = entitiesPkg.createOwnedInterface("Entity");
        Interface repositoryInterface = repositoriesPkg.createOwnedInterface("Repository");
        Interface domainServiceInterface = servicesPkg.createOwnedInterface("DomainService");
        
        return model;
    }

    /**
     * Component Model Template - component-based architecture
     */
    private Model createComponentModelTemplate(Model model) {
        Package componentsPkg = model.createNestedPackage("components");
        Package interfacesPkg = model.createNestedPackage("interfaces");
        Package connectorsPkg = model.createNestedPackage("connectors");
        
        return model;
    }

    /**
     * Layered Architecture Template
     */
    private Model createLayeredArchitectureTemplate(Model model) {
        Package presentationPkg = model.createNestedPackage("presentation");
        Package businessPkg = model.createNestedPackage("business");
        Package persistencePkg = model.createNestedPackage("persistence");
        Package infrastructurePkg = model.createNestedPackage("infrastructure");
        
        return model;
    }

    /**
     * Microservice Template
     */
    private Model createMicroserviceTemplate(Model model) {
        Package apiPkg = model.createNestedPackage("api");
        Package domainPkg = model.createNestedPackage("domain");
        Package infrastructurePkg = model.createNestedPackage("infrastructure");
        Package configPkg = model.createNestedPackage("configuration");
        
        return model;
    }

    /**
     * Add class to model
     */
    public Model addClassToModel(AddClassRequest request) {
        Model model = request.getModel();
        
        Package targetPackage = request.getPackageName() != null 
            ? findPackage(model, request.getPackageName())
            : model;
        
        org.eclipse.uml2.uml.Class newClass = targetPackage.createOwnedClass(request.getClassName(), request.isAbstract());
        newClass.setVisibility(VisibilityKind.get(request.getVisibility()));
        
        // Add properties if specified
        if (request.getProperties() != null) {
            for (PropertySpec propSpec : request.getProperties()) {
                Property prop = newClass.createOwnedAttribute(propSpec.getName(), null);
                prop.setVisibility(VisibilityKind.get(propSpec.getVisibility()));
                // TODO: Set type based on propSpec.getTypeName()
            }
        }
        
        return model;
    }

    /**
     * Add interface to model
     */
    public Model addInterfaceToModel(AddInterfaceRequest request) {
        Model model = request.getModel();
        
        Package targetPackage = request.getPackageName() != null 
            ? findPackage(model, request.getPackageName())
            : model;
        
        Interface newInterface = targetPackage.createOwnedInterface(request.getInterfaceName());
        newInterface.setVisibility(VisibilityKind.get(request.getVisibility()));
        
        return model;
    }

    /**
     * Create empty profile
     */
    public Profile createEmptyProfile(String name, String uri) {
        Profile profile = UMLFactory.eINSTANCE.createProfile();
        profile.setName(name);
        if (uri != null) {
            profile.setURI(uri);
        }
        return profile;
    }

    /**
     * Create profile from template
     */
    public Profile createProfileFromTemplate(String name, String uri, String templateType) {
        Profile profile = createEmptyProfile(name, uri);
        
        switch (templateType.toLowerCase()) {
            case "web":
                return createWebProfileTemplate(profile);
            case "persistence":
                return createPersistenceProfileTemplate(profile);
            case "security":
                return createSecurityProfileTemplate(profile);
            default:
                return profile;
        }
    }

    private Profile createWebProfileTemplate(Profile profile) {
        // Create common web stereotypes
        Stereotype controller = profile.createOwnedStereotype("Controller", false);
        Stereotype service = profile.createOwnedStereotype("Service", false);
        Stereotype repository = profile.createOwnedStereotype("Repository", false);
        
        return profile;
    }

    private Profile createPersistenceProfileTemplate(Profile profile) {
        Stereotype entity = profile.createOwnedStereotype("Entity", false);
        Stereotype table = profile.createOwnedStereotype("Table", false);
        Stereotype column = profile.createOwnedStereotype("Column", false);
        
        return profile;
    }

    private Profile createSecurityProfileTemplate(Profile profile) {
        Stereotype secured = profile.createOwnedStereotype("Secured", false);
        Stereotype authenticated = profile.createOwnedStereotype("Authenticated", false);
        Stereotype authorized = profile.createOwnedStereotype("Authorized", false);
        
        return profile;
    }

    public Package findPackage(Model model, String packageName) {
        return model.getNestedPackages().stream()
            .filter(pkg -> packageName.equals(pkg.getName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Package not found: " + packageName));
    }

    /**
     * Convert uploaded XMI file to UML2 Model (returns as EMF JSON via Jackson)
     */
    public Mono<Model> convertXMIFileToModel(FilePart filePart) {
        return Mono.fromCallable(() -> {
            // Save uploaded file temporarily
            Path tempFile = Files.createTempFile("upload", ".uml");
            
            try {
                // Save uploaded content
                filePart.transferTo(tempFile).block();
                
                // Load as UML2 model
                ResourceSet resourceSet = createConfiguredResourceSet();
                Resource resource = resourceSet.getResource(URI.createFileURI(tempFile.toString()), true);
                
                Model model = (Model) resource.getContents().get(0);
                
                // Clean up
                Files.deleteIfExists(tempFile);
                
                return model; // Jackson EMF will serialize this to JSON
                
            } catch (Exception e) {
                try { Files.deleteIfExists(tempFile); } catch (Exception ignored) {}
                throw new RuntimeException("Failed to process XMI file", e);
            }
        });
    }

    /**
     * Convert uploaded XMI file to UML2 Profile
     */
    public Mono<Profile> convertXMIFileToProfile(FilePart filePart) {
        return Mono.fromCallable(() -> {
            Path tempFile = Files.createTempFile("profile", ".uml");
            
            try {
                filePart.transferTo(tempFile).block();
                
                ResourceSet resourceSet = createConfiguredResourceSet();
                Resource resource = resourceSet.getResource(URI.createFileURI(tempFile.toString()), true);
                
                Profile profile = (Profile) resource.getContents().get(0);
                
                Files.deleteIfExists(tempFile);
                return profile;
                
            } catch (Exception e) {
                try { Files.deleteIfExists(tempFile); } catch (Exception ignored) {}
                throw new RuntimeException("Failed to process profile file", e);
            }
        });
    }

    /**
     * Apply profile to model
     */
    public Model applyProfileToModel(Model model, Profile profile) {
        // Apply the profile
        model.applyProfile(profile);
        return model;
    }

    /**
     * Add stereotype to profile
     */
    public Profile addStereotypeToProfile(Profile profile, String stereotypeName, String baseClass) {
        Stereotype stereotype = profile.createOwnedStereotype(stereotypeName, false);
        
        // Find the UML metaclass to extend
        org.eclipse.uml2.uml.Class metaclass = (org.eclipse.uml2.uml.Class) profile.eClass();
        if (metaclass != null) {
            stereotype.createExtension(metaclass, false);
        }
        
        return profile;
    }

    /**
     * Process/validate model
     */
    public Model processModel(Model model) {
        // Perform validation, cleanup, etc.
        // For now, just return the model
        return model;
    }

    /**
     * Export model to XMI string
     */
    public String exportModelToXMI(Model model) {
        try {
            ResourceSet resourceSet = createConfiguredResourceSet();
            Resource resource = resourceSet.createResource(URI.createURI("temp://export.uml"));
            resource.getContents().add(model);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            resource.save(outputStream, null);
            return outputStream.toString("UTF-8");
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to export model to XMI", e);
        }
    }

    /**
     * Export profile to XMI string
     */
    public String exportProfileToXMI(Profile profile) {
        try {
            ResourceSet resourceSet = createConfiguredResourceSet();
            Resource resource = resourceSet.createResource(URI.createURI("temp://profile.uml"));
            resource.getContents().add(profile);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            resource.save(outputStream, null);
            return outputStream.toString("UTF-8");
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to export profile to XMI", e);
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

    private Class findMetaclass(String baseClassName) {
        // Simplified - in reality you'd look up UML metaclasses
        // This would find Class, Property, Operation, etc. from UML metamodel
        return null; // TODO: Implement proper metaclass lookup
    }
}