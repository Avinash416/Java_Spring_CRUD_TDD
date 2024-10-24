package com.ecom.server.Service;

import com.ecom.server.Model.ProductModel;
import com.ecom.server.Repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    private final WebClient webClient;

    public ProductsService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://fakestoreapi.com").build();
    }

    public Flux<ProductModel> callExternalAPI(int limit) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products")
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .bodyToFlux(ProductModel.class);
    }


    public ProductModel setProducts(ProductModel data) {
        return productsRepository.save(data);
    }

    public List<ProductModel> getAllProducts() {
        return productsRepository.findAll();
    }

    public Optional<ProductModel> getProductById(Long id) {
        return productsRepository.findById(id);
    }


    public ProductModel updateProductById(Long id, Map<String, Object> updateData) {
        Optional<ProductModel> optionalProduct = productsRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product with id " + id + " not found.");
        }
            ProductModel existing=optionalProduct.get();
            updateData.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(ProductModel.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, existing, value);
                }
            });
            return productsRepository.save(existing);

    }

    public void deleteById(Long id) {
        Optional<ProductModel> exist =productsRepository.findById(id);
        if(exist.isPresent()){
            productsRepository.deleteById(id);
        }else {
            throw  new RuntimeException("product with id:"+id+"is not found");
        }
    }
}

