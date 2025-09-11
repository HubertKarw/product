package com.hubertkarw.product.controller;

import com.hubertkarw.product.model.CustomizationCreateDTO;
import com.hubertkarw.product.model.CustomizationDTO;
import com.hubertkarw.product.service.CustomizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Customization", description = "Customization operations")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customs")
public class CustomizationController {

    private final CustomizationService service;

    @Operation(summary = "Get customizations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found customizations", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomizationDTO.class))})
    })
    @GetMapping
    public List<CustomizationDTO> getCustomizations(@Parameter(description = "Filtering by type") @RequestParam(required = false) String type) {
        log.info(type==null?"GET /customs requested":"GET /customs?type={} requested",type);
        return service.getCustomizations(type);
    }

    @Operation(summary = "Get Customization by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found customization", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomizationDTO.class))})
    })
    @GetMapping("/{id}")
    public CustomizationDTO getCustomization(@PathVariable long id) {
        log.info("GET /customs/{} requested", id);
        return service.getCustomization(id);
    }

    @Operation(summary = "Create Customization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created customization", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomizationDTO.class))})
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomizationDTO addCustomization(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Customization to create",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomizationCreateDTO.class),
                    examples = @ExampleObject(value =
                            """
                            {
                            "name": "Red",
                            "type": "color",
                            "price": 0
                            }
                            """)))
            @RequestBody CustomizationCreateDTO customizationCreateDTO) {
        log.info("POST /customs requested body={}", customizationCreateDTO);
        return service.addCustomization(customizationCreateDTO);
    }

    @Operation(summary = "Update customization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated customization", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomizationDTO.class))})
    })
    @PutMapping("/{id}")
    public CustomizationDTO updateCustomization(@PathVariable long id,
                                                @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                        description = "Customization changed",
                                                        required = true,
                                                        content = @Content(mediaType = "application/json",
                                                                schema = @Schema(implementation = CustomizationCreateDTO.class),
                                                                examples = @ExampleObject(value =
                                                                        """
                                                                        {
                                                                        "name": "Red",
                                                                        "type": "color",
                                                                        "price": 0
                                                                        }
                                                                        """)))
                                                @RequestBody CustomizationCreateDTO customizationCreateDTO) {
        log.info("PUT /customs/{} requested with body={}", id, customizationCreateDTO);
        return service.updateCustomization(id, customizationCreateDTO);
    }

    @Operation(summary = "Delete customization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed customization")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomization(@PathVariable long id) {
        log.info("DELETE /customs/{} requested", id);
        service.deleteCustomization(id);
    }
}
