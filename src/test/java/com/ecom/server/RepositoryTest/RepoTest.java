package com.ecom.server.RepositoryTest;

import com.ecom.server.Model.ProductModel;
import com.ecom.server.Repository.ProductsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
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

    @Test
    @DisplayName("Save Product Fail")
    public void testSaveProductFail() {
        ProductModel mockProduct = new ProductModel();
        mockProduct.setPrice(12.33);
        mockProduct.setDescription("description1");
        mockProduct.setCategory("category1");
        mockProduct.setImage("image1.jpg");

        try{
            repository.save(mockProduct);
            fail("Saving product with missing title should fail due to validation.");
        }catch(Exception e){
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("Get Product By id")
    public void testGetProductsById() {
        ProductModel mockProduct =new ProductModel();
        mockProduct.setTitle("product1");
        mockProduct.setPrice(12.33);
        mockProduct.setDescription("description1");
        mockProduct.setCategory("category1");
        mockProduct.setImage("image1.jpg");
        repository.save(mockProduct);

        Optional<ProductModel> foundProduct=repository.findById(mockProduct.getId());
        foundProduct.ifPresent(productModel -> assertEquals(mockProduct.getId(), productModel.getId()));

    }

    @Test
    @DisplayName("GetProductBYID_FAIL")
    public void testGetProductById_Fail(){
        Long id=912L;
        Optional<ProductModel> foundProduct=repository.findById(id);
        assertEquals(Optional.empty(),foundProduct);
    }


    @Test
    @DisplayName("Update product by id")
    public void testUpdateProductById() {
        ProductModel mockProduct =new ProductModel();
        mockProduct.setTitle("product1 updated");
        mockProduct.setPrice(12.33);
        mockProduct.setDescription("description1 updated");
        mockProduct.setCategory("category1 updated");
        mockProduct.setImage("image1.jpg updated");
        repository.save(mockProduct);

        Optional<ProductModel> foundProduct=repository.findById(mockProduct.getId());
        if(foundProduct.isPresent()){
            foundProduct.get().setTitle("updated");
            foundProduct.get().setPrice(12.33);
            foundProduct.get().setDescription("description1 updated");
            foundProduct.get().setCategory("category1 updated");
            foundProduct.get().setImage("image1.jpg updated");
            repository.save(foundProduct.get());
            assertEquals(mockProduct.getTitle(), foundProduct.get().getTitle());
        }
    }

    @Test
    @DisplayName("UpdateProductById_Fail")
    public void testUpdateProductById_Fail(){
        Long id=911L;
        ProductModel mockProduct= new ProductModel();
        //if you set an ID manually without first ensuring that the database has an existing record with that exact ID,
        // the database will likely treat it as a new entry. This can be why the repository doesn't find the record by that ID when you
        // attempt an update.
        mockProduct.setId(id);
        mockProduct.setTitle("product1");
        mockProduct.setPrice(12.33);
        mockProduct.setDescription("description1");
        mockProduct.setCategory("category1");
        mockProduct.setImage("image1.jpg");

        try{
            repository.save(mockProduct);
            Optional<ProductModel> foundProduct= repository.findById(id);
            assertEquals(Optional.empty(), foundProduct);
        }catch (Exception e){
            fail("Updating a non-existent product should not throw an exception.");
        }
    }

    @Test
    @DisplayName("Delete product by id")
    public void testDeleteProductById() {
        ProductModel mockProduct =new ProductModel();
        mockProduct.setTitle("product1");
        mockProduct.setPrice(12.33);
        mockProduct.setDescription("description1");
        mockProduct.setCategory("category1");
        mockProduct.setImage("image1.jpg");
        repository.save(mockProduct);

        Optional<ProductModel> foundProduct=repository.findById(mockProduct.getId());
        if(foundProduct.isPresent()){
            repository.deleteById(mockProduct.getId());
            assertEquals(Optional.empty(), repository.findById( mockProduct.getId()));
        }
    }

    @Test
    @DisplayName("DeleteProductById_Fail")
    public void testDeleteProductById_Fail(){
        Long id=911L;
        try{
            repository.deleteById(id);
            Optional<ProductModel> foundProduct=repository.findById(id);
            assertEquals(Optional.empty(), foundProduct);
        }catch (Exception e){
            fail("Deleting a non-existent product should not throw an exception.");
        }
    }
}
