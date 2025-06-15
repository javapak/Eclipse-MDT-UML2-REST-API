package com.javapak.mda.config;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.uml2.uml.UMLPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class EmfJacksonConfig implements WebFluxConfigurer {

    @Bean
    @Primary
    public ObjectMapper emfObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // Create and configure ResourceSet for UML2
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        resourceSet.getResourceFactoryRegistry()
            .getExtensionToFactoryMap()
            .put("uml", new XMIResourceFactoryImpl());
        
        // Configure EMF module
        EMFModule emfModule = new EMFModule();
        emfModule.configure(EMFModule.Feature.OPTION_USE_ID, true);
        emfModule.configure(EMFModule.Feature.OPTION_SERIALIZE_TYPE, true);
        
        mapper.registerModule(emfModule);
        
        return mapper;
    }    
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {        ObjectMapper emfMapper = emfObjectMapper();
        
        configurer.defaultCodecs().jackson2JsonEncoder(
            new Jackson2JsonEncoder(emfMapper)
        );
        configurer.defaultCodecs().jackson2JsonDecoder(
            new Jackson2JsonDecoder(emfMapper)
        );
    }
}