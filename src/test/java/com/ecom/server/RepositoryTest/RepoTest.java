package com.ecom.server.RepositoryTest;

import com.ecom.server.Model.ProductModel;
import com.ecom.server.Repository.ProductsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class RepoTest {

    @Autowired
    private ProductsRepository repository;

    @Test
    @DisplayName("Save Products test")
    public void testSaveProduct() {

        ProductModel mockProduct=new ProductModel();
        mockProduct.setTitle("product1");
        mockProduct.setPrice(12.33);
        mockProduct.setDescription("description1");
        mockProduct.setCategory("category1");
        mockProduct.setImage("image1.jpg");

        ProductModel savedProduct=repository.save(mockProduct);
        assertNotNull(savedProduct.getId());
    }
}
