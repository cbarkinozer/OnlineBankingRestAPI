package com.cbarkinozer.onlinebankingrestapi.app.cus.controller;

import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerUpdateDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.service.CusCustomerService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customers")
public class CusCustomerController {

    private final CusCustomerService cusCustomerService;

    @Operation(
            tags = "Customer Controller",
            summary = "All Customers",
            description = "Gets all customers."
    )
    @GetMapping
    public ResponseEntity<RestResponse<List<CusCustomerDto>>> findAllCustomers(){

        List<CusCustomerDto> cusCustomerDtoList = cusCustomerService.findAllCustomers();

        return ResponseEntity.ok(RestResponse.of(cusCustomerDtoList));
    }

    @Operation(
            tags = "Customer Controller",
            summary = "Get a Customer",
            description = "Gets a customer by id."
    )
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<CusCustomerDto>> findCustomerById(@PathVariable Long id){

        CusCustomerDto cusCustomerDto = cusCustomerService.findCustomerById(id);

        return ResponseEntity.ok(RestResponse.of(cusCustomerDto));
    }

    @Operation(
            tags="Customer Controller",
            summary = "Save a customer",
            description = "Saves a new customer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customers",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CusCustomerSaveDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    name = "new customer",
                                                    summary = "New Customer Example",
                                                    description = "Complete request with all available fields for customer.",
                                                    value = "{\n" +
                                                            "  \"name\": \"string1\",\n" +
                                                            "  \"surname\": \"string1\",\n" +
                                                            "  \"identityNo\": 1,\n" +
                                                            "  \"password\": \"string1\"\n" +
                                                            "}"
                                            )
                                    }
                            ),
                    }
            )
    )
    @PostMapping("/save-customer")
    public ResponseEntity<RestResponse<MappingJacksonValue>> saveCustomer(@RequestBody CusCustomerSaveDto cusCustomerSaveDto){

        CusCustomerDto cusCustomerDto = cusCustomerService.saveCustomer(cusCustomerSaveDto);

        WebMvcLinkBuilder linkGet = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).findCustomerById(cusCustomerDto.getId()));

        WebMvcLinkBuilder linkDelete = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).deleteCustomer(cusCustomerDto.getId()));

        EntityModel<CusCustomerDto> entityModel = EntityModel.of(cusCustomerDto);

        entityModel.add(linkGet.withRel("find-by-id"));
        entityModel.add(linkDelete.withRel("delete"));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(entityModel);

        return ResponseEntity.ok(RestResponse.of(mappingJacksonValue));
    }

    @Operation(
            tags="Customer Controller",
            summary = "Update a customer",
            description = "Updates customers all fields."
    )
    @PutMapping("/update-customer")
    public ResponseEntity<RestResponse<CusCustomerDto>> updateCustomer(@RequestBody CusCustomerUpdateDto cusCustomerUpdateDto){

        CusCustomerDto cusCustomerDto = cusCustomerService.updateCustomer(cusCustomerUpdateDto);

        return ResponseEntity.ok(RestResponse.of(cusCustomerDto));
    }

    @Operation(
            tags="Customer Controller",
            summary = "Delete a customer",
            description = "Deletes a customer by id."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<?>> deleteCustomer(@PathVariable Long id){

        cusCustomerService.deleteCustomer(id);

        return ResponseEntity.ok(RestResponse.empty());
    }
}
