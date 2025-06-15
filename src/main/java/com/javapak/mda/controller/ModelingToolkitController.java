package com.javapak.mda.controller;

import com.javapak.mda.service.ModelingService;
import com.javapak.mda.dto.*;
import org.eclipse.uml2.uml.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/v1/toolkit")
@CrossOrigin(origins = "http://localhost:5173") // temp local testing only... remove later!
public class ModelingToolkitController {

    @Autowired
    private ModelingService modelingService;

    // === CREATE FROM SCRATCH ===

    /**
     * Create completely empty model
     */
    @PostMapping("/models/create-empty")
    public Mono<Model> createEmptyModel(@RequestBody CreateModelRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createEmptyModel(request.getName(), request.getUri());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create model with basic package structure
     */
    @PostMapping("/models/create-with-packages")
    public Mono<Model> createModelWithPackages(@RequestBody CreateModelWithPackagesRequest request) {
        return Mono.fromCallable(() -> {
            Model model = modelingService.createEmptyModel(request.getName(), request.getUri());
            
            // Add requested packages
            for (String packageName : request.getPackageNames()) {
                model.createNestedPackage(packageName);
            }
            
            return model;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create model from template (Domain Model, Component Model, etc.)
     */
    @PostMapping("/models/create-from-template")
    public Mono<Model> createFromTemplate(@RequestBody CreateFromTemplateRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createModelFromTemplate(
                request.getName(), 
                request.getUri(), 
                request.getTemplateType()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // === BUILD MODEL INCREMENTALLY ===

    /**
     * Add package to existing model
     */
    @PostMapping("/models/add-package")
    public Mono<Model> addPackage(@RequestBody AddPackageRequest request) {
        return Mono.fromCallable(() -> {
            Model model = request.getModel();
            
            if (request.getParentPackageName() != null) {
                org.eclipse.uml2.uml.Package parentPkg = modelingService.findPackage(model, request.getParentPackageName());
                parentPkg.createNestedPackage(request.getPackageName());
            } else {
                model.createNestedPackage(request.getPackageName());
            }
            
            return model;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Add class to model
     */
    @PostMapping("/models/add-class")
    public Mono<Model> addClass(@RequestBody AddClassRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.addClassToModel(request);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Add interface to model
     */
    @PostMapping("/models/add-interface")
    public Mono<Model> addInterface(@RequestBody AddInterfaceRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.addInterfaceToModel(request);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // === CREATE PROFILES FROM SCRATCH ===

    /**
     * Create empty profile
     */
    @PostMapping("/profiles/create-empty")
    public Mono<Profile> createEmptyProfile(@RequestBody CreateProfileRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createEmptyProfile(request.getName(), request.getUri());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Create profile from template (Web Profile, Persistence Profile, etc.)
     */
    @PostMapping("/profiles/create-from-template")
    public Mono<Profile> createProfileFromTemplate(@RequestBody CreateProfileFromTemplateRequest request) {
        return Mono.fromCallable(() -> {
            return modelingService.createProfileFromTemplate(
                request.getName(),
                request.getUri(),
                request.getTemplateType()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }
}