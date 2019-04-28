package com.training.bookmanagement.products;

import com.training.bookmanagement.util.common.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products/v1")
public class ProductsController extends AbstractBaseController {

    @Autowired
    private ProductsService service;

    @GetMapping("/get_all_products")
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        return ok(service.getAllProducts());
    }

    @PostMapping("/save_product")
    public ResponseEntity<Product> saveProducts(@RequestBody Product product) {
        return ok(service.saveProducts(product));
    }
}
