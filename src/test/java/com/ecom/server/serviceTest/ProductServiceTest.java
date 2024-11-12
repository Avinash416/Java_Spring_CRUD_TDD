package com.ecom.server.serviceTest;

import com.ecom.server.Model.ProductModel;
import com.ecom.server.Repository.ProductsRepository;
import com.ecom.server.Service.ProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductsService productsService;

    @MockBean
    private ProductsRepository productsRepository;

    @Test
    public void testSetProduct_Success(){
//        Steps:
//        1=> Create a dummyDataModel and expect the dataModel should be the result when the data is saved to the mocked repository.
//        2=>Now pass the same data model to mocked service to get the exact same result
        ProductModel mockProduct= new ProductModel();

        when(productsRepository.save(mockProduct)).thenReturn(mockProduct);

        ProductModel result=productsService.setProducts(mockProduct);

        assertNotNull(result);
        assertEquals(mockProduct,result);
    }

    @Test
    public void testSetProduct_Fail(){
//        Steps:
//        1=>Create the dummy model to save the data to the repository
//        2=>So add the matcher any kind of dummy data model and make it throw a run time error
//        3=>pass the dummy model to the service to assert the exception works or not
//        4=>Verify f the required method from repo is invoked or not and which methods invoking with the passed data
        ProductModel mockProduct= new ProductModel();
        mockProduct.setTitle("title");
        mockProduct.setPrice(13.3);
        mockProduct.setDescription("demo product");
        mockProduct.setCategory("ethnic");
        mockProduct.setImage("pqr.jpg");

        when(productsRepository.save(any(ProductModel.class))).thenThrow(new RuntimeException("Database Error"));

       assertThrows(RuntimeException.class, ()->{
           productsService.setProducts(mockProduct);
       });
       verify(productsRepository,times(1)).save(mockProduct);
    }

    @Test
    public void testGetAllProducts_Success(){
//        Steps:
//        1=>As you know we will get a list of products not a single product so make a dummy model and create multiple data to get
//        2=>Now as the new data is created we need to pass that to the mocked repository and return the same
//        3=>Then run the same method of get from the service to perform the get operation
//        4=> Assert the result we get from service should be not null and verify f the findAll method is invoked from the repository


       List<ProductModel>  mockProduct= new ArrayList<>();
       ProductModel product1= new ProductModel();

       product1.setTitle("first product");
       product1.setPrice(12.2);
       product1.setDescription("good product");
       product1.setCategory("casual");
       product1.setImage("abc.jpg");

        ProductModel product2= new ProductModel();
        product1.setTitle("second product");
        product1.setPrice(14.2);
        product1.setDescription("good product 2");
        product1.setCategory("casual");
        product1.setImage("xyz.jpg");

        when(productsRepository.findAll()).thenReturn(mockProduct);

        List<ProductModel> result=productsService.getAllProducts();

        assertNotNull(result);
        verify(productsRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllProducts_Fail(){
//        Step:
//        1=>Remember here we don't get list of products like List<ProductModel> but we get an ArrayList<>()
//        2=>Add the repo operation in when and return an ArrayList<>
//        3=>Invoke the service method
//        4=>And the result should be and empty arr [] or isEmpty();

        when(productsRepository.findAll()).thenReturn(new ArrayList<>());

        List<ProductModel> result =productsService.getAllProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(productsRepository,times(1)).findAll();
    }

    @Test
    public void testGetProductByID_Success(){
//        Steps:
//        1=>Create a dummy model and assign a id to it
//        2=>Now pass the same model to the mocked repository and return the same
//        3=> Now pass the same id to the service and assert the result should be not null and the id should be the same
        Long id= 1L;

        ProductModel mockProduct= new ProductModel();
        mockProduct.setId(id);
        when(productsRepository.findById(id)).thenReturn(Optional.of(mockProduct));
        Optional<ProductModel> result=productsService.getProductById(id);

        assertTrue(result.isPresent());
        assertEquals(id,result.get().getId());

    }

    @Test
    public void testGetProductById_NotFound(){
//        Steps:
//        1=>Create a dummy id
//        2=>Pass the same id to the mocked repo and return an empty optional
//        3=>Pass the same id to the service and assert the result should be empty or not present
//        4=>Verify the findById method is invoked or not

        Long id=1L;

        when(productsRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ProductModel> result=productsService.getProductById(id);

        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateProductById_Success(){
//        Steps:
//        1=>Create a dummy id and assign it to the dummy model
//        2=>Create a map for the update data
//        3=>Pass the same id to the mocked repo and return the same
//        4=>Now pass the id and map to the service and assert the result should be not null and the title should be the same
//        5=>Verify the findById and save method is invoked or not
        Long id=1L;

        ProductModel mockProduct=new ProductModel();
        mockProduct.setId(id);
        mockProduct.setTitle("Old Title");

        when(productsRepository.findById(id)).thenReturn(Optional.of(mockProduct));

        Map<String,Object> updatedData= Map.of("title","Updated Product title");

        when(productsRepository.save(mockProduct)).thenReturn(mockProduct);

        ProductModel updatedProduct=productsService.updateProductById(id,updatedData);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product title",updatedProduct.getTitle());

        verify(productsRepository).save(mockProduct);

    }

    @Test
    public void testUpdateProductById_Fail(){
//        Steps:
//        1=>Create a dummy id and assign it to the dummy model
//        2=>Pass the id to the mock repository and return an optional empty data
//        3=>Mock the save for the updated data and return null
//        4=>Then invoke the service and assert the exception should be thrown
//        5=>Verify the save method is not invoked or not

        Long id=1L;

        when(productsRepository.findById(id)).thenReturn(Optional.empty());

        when(productsRepository.save(any(ProductModel.class))).thenReturn(null);

        Map<String,Object> updatedProduct=Map.of("title","updated title");
       RuntimeException exception= assertThrows(RuntimeException.class, ()->{
            productsService.updateProductById(id,updatedProduct);
        });

       assertEquals("Product with id " + id + " not found.",exception.getMessage());

        verify(productsRepository,never()).save(any(ProductModel.class));
    }

    @Test
    public void deleteProductById_Success(){
//        Steps:
//        1=>Create a dummy id and assign it to the dummy model
//        2=>Pass the same id to the mocked repo and return the same
//        3=>Now pass the same id to the service and assert the result should be not null and the id should be the same

        Long id= 1L;
        ProductModel mockProduct=new ProductModel();
        mockProduct.setId(id);

        when(productsRepository.findById(id)).thenReturn(Optional.of(mockProduct));

        productsService.deleteById(id);

        verify(productsRepository).deleteById(id);

    }

    @Test
    public void testDeleteProductById_Fail(){
//        Steps:
//        1=>Create a dummy id and assign it to the dummy model
//        2=>Pass the same id to the mocked repo and return an empty optional
//        3=>Then invoke the service and assert the exception should be thrown
//        4=>Verify the findById method is invoked or not
//
        Long id=1L;
        when(productsRepository.findById(id)).thenReturn(Optional.empty());

       RuntimeException exception=assertThrows(RuntimeException.class,()->{
           productsService.deleteById(id);
       });
       assertEquals("product with id:"+id+"is not found",exception.getMessage());
       verify(productsRepository,never()).deleteById(id);

    }
}