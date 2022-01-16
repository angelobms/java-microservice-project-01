package com.bmsti.payment.controller;

import com.bmsti.payment.dto.SaleDTO;
import com.bmsti.payment.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;
    private final PagedResourcesAssembler<SaleDTO> resourcesAssembler;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public SaleController(SaleService saleService, PagedResourcesAssembler<SaleDTO> resourcesAssembler) {
        this.saleService = saleService;
        this.resourcesAssembler = resourcesAssembler;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public SaleDTO findById(@PathVariable("id") Long id) {
        SaleDTO saleDTO = saleService.findById(id);
        saleDTO.add(linkTo(methodOn(SaleController.class).findById(id)).withSelfRel());
        return saleDTO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                     @RequestParam(value = "order", defaultValue = "asc") String order) {

        var sort = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort, "date"));

        Page<SaleDTO> sales = saleService.findAll(pageable);
        sales.stream()
                .forEach(s -> s.add(linkTo(methodOn(SaleController.class).findById(s.getId())).withSelfRel()));

        PagedModel<EntityModel<SaleDTO>> pagedModel = resourcesAssembler.toModel(sales);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public SaleDTO create(@RequestBody SaleDTO saleDTO) {
        SaleDTO newSale = saleService.create(saleDTO);
        newSale.add(linkTo(methodOn(SaleController.class).findById(newSale.getId())).withSelfRel());
        return newSale;
    }

}
