package com.bmsti.product.controller;

import com.bmsti.product.dto.ProductDTO;
import com.bmsti.product.services.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final PagedResourcesAssembler<ProductDTO> resourcesAssembler;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public ProductController(ProductService productService, PagedResourcesAssembler<ProductDTO> resourcesAssembler) {
        this.productService = productService;
        this.resourcesAssembler = resourcesAssembler;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ProductDTO findById(@PathVariable("id") Long id) {
        ProductDTO productDTO = productService.findById(id);
        productDTO.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
        return productDTO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                     @RequestParam(value = "order", defaultValue = "asc") String order) {

        var sort = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sort, "name"));

        Page<ProductDTO> products = productService.findAll(pageable);
        products.stream()
                .forEach(p -> p.add(linkTo(methodOn(ProductController.class).findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<ProductDTO>> pagedModel = resourcesAssembler.toModel(products);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ProductDTO create(@RequestBody ProductDTO productDTO) {
        ProductDTO newProduct = productService.create(productDTO);
        newProduct.add(linkTo(methodOn(ProductController.class).findById(newProduct.getId())).withSelfRel());
        return newProduct;
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ProductDTO update(@RequestBody ProductDTO productDTO) {
        ProductDTO newProduct = productService.update(productDTO);
        newProduct.add(linkTo(methodOn(ProductController.class).findById(productDTO.getId())).withSelfRel());
        return newProduct;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
