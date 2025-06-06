package com.javapak.mda.config;

import org.emfjson.jackson.module.EMFModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // Register EMF module for UML2 serialization
        EMFModule module = new EMFModule();
        module.configure(EMFModule.Feature.OPTION_USE_ID, true);
        module.configure(EMFModule.Feature.OPTION_SERIALIZE_TYPE, true);
        mapper.registerModule(module);
        
        return mapper;
    }

    @Bean
    public Jackson2JsonEncoder jackson2JsonEncoder(ObjectMapper mapper) {
        return new Jackson2JsonEncoder(mapper);
    }

    @Bean
    public Jackson2JsonDecoder jackson2JsonDecoder(ObjectMapper mapper) {
        return new Jackson2JsonDecoder(mapper);
    }
}