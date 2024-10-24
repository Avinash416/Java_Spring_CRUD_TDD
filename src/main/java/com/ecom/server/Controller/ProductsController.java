package com.ecom.server.Controller;

import com.ecom.server.Model.ProductModel;
import com.ecom.server.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping("/getAllProducts")
    public Flux<ProductModel> getProducts(@RequestParam int limit){
        return productsService.callExternalAPI(limit);
    }

    @PostMapping("/setProduct")
    public ProductModel setProducts(@RequestBody ProductModel data){
        return productsService.setProducts(data);
    }

    @GetMapping("/getProducts")
    public List<ProductModel> getAllProducts(){
        return productsService.getAllProducts();
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<?> getProductById(@PathVariable  Long id){
        Optional<ProductModel> exists= productsService.getProductById(id);
        if(exists.isPresent()){
            return ResponseEntity.status(200).body(exists);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/updateProductById/{id}")
    public ProductModel updateProductById(@PathVariable Long id,@RequestBody Map<String, Object> updateData){
        return productsService.updateProductById(id, updateData);
    }

    @DeleteMapping("/deleteProductById/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable  Long id){
       try{
           productsService.deleteById(id);
           return ResponseEntity.ok("Product with id"+id+ "successfully deleted");
       }catch (RuntimeException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
    }
}
