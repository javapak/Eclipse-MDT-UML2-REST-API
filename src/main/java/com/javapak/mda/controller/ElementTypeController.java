package com.javapak.mda.controller;

import com.javapak.mda.dto.ElementTypeResponse;
import com.javapak.mda.service.ElementTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/element-types")
@CrossOrigin(origins = "*")
public class ElementTypeController {

    @Autowired
    private ElementTypeService elementTypeService;

    @GetMapping("/standalone")
    public ResponseEntity<ElementTypeResponse> getStandaloneElements() {
        try {
            ElementTypeResponse response = elementTypeService.getStandaloneElementsWithSvg();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
