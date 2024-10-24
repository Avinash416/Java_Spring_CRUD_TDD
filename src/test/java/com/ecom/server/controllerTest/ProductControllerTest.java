package com.ecom.server.controllerTest;

import com.ecom.server.Model.ProductModel;
import com.ecom.server.Service.ProductsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//The big difference in service testing and controller testing is in service test we mock the repository but in controller
// test we mock the service.

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsService productsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Disabled
    public void testGetData_Success() throws Exception{
        mockMvc.perform(get("/products/getProducts"))
                       .andExpect(status().isOk());
    }


    @Test
    public void testGetProductById_Success () throws Exception{

//        Step:
//        1=>Create a mock product model and set its id
//        2=>Mock the service layer response
//        3=>Mock the controller layer response
//        4=>Assert the controller response by checking the json path

        Long productId= 4L;
        ProductModel mockModel=new ProductModel();
        mockModel.setId(productId);

        when(productsService.getProductById(productId)).thenReturn(Optional.of(mockModel));

        mockMvc.perform(get("/products/getProductById/{id}",productId))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.id").value(productId));
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
//        Step:
//        1=>Create a mock product model and set its id
//        2=>Mock the service layer response
//        3=>Mock the controller layer response
//        4=>Assert the controller response by checking the status code
        Long id =5L;
        when(productsService.getProductById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/getProductById/{id}",id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateProductById_Success() throws Exception{
//        Steps:
//        1=>Create a mock product model and set its id
//        2=>Create a map for the fields
//        3=>Mock Service layer response
//        4=>Mock the controller layer response
//        5=>Assert the controller response by checking the json path

        Long productId=4L;

        //Create a map for the fields
        Map<String,Object> updates=new HashMap<>();
        updates.put("title","Mens Casual");
        updates.put("price",33.3);

        //Mock Service layer response
        ProductModel updatedProduct=new ProductModel();
        updatedProduct.setId(productId);
        updatedProduct.setTitle("Mens Casual");
        updatedProduct.setPrice(33.3);
        updatedProduct.setDescription("The color could be slightly different between on the screen and in practice. / Please note that body builds vary by person, therefore, detailed size information should be reviewed below on the product description.");
        updatedProduct.setCategory("men's clothing");
        updatedProduct.setImage("https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg");

        when(productsService.updateProductById(eq(productId), any(Map.class))).thenReturn(updatedProduct);
        mockMvc.perform(patch("/products/updateProductById/{id}",productId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Mens Casual"))
                .andExpect(jsonPath("$.price").value(33.3));
    }

    @Test
    public void testUpdateProductById_NotFound() throws Exception{
//        Steps:
//        1=>Create a mock product model and set its id
//        2=>Create a map for the fields
//        3=>Mock Service layer response
//        4=>Mock the controller layer response
//        5=>Assert the controller response by checking the status code

        Long productId=4L;

        Map<String,Object> updateData=new HashMap<>();
        updateData.put("title","Mens Casual");

        when(productsService.updateProductById(eq(productId), any(Map.class))).thenReturn(null);

        mockMvc.perform(patch("/products/{id}",productId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteByID_Success() throws Exception {
//        Steps:
//        1=>Create a dummy id
//        2=>Mock the service layer response
//        3=>Mock the controller layer response
//        4=>Assert the controller response by checking the status code and response body
//        5=>Verify the service layer method is called once

        Long id=4L;

        doNothing().when(productsService).deleteById(id);

        mockMvc.perform(delete("/products/deleteProductById/{id}",id))
                .andExpect(status().isOk())
                .andExpect(content().string("Product with id"+id+"successfully deleted"));

        verify(productsService).deleteById(id);
    }

    @Test
    public void testDeleteById_Fail() throws Exception {
//        Steps:
//        1=>Create a dummy id
//        2=>Mock the service layer response
//        3=>Mock the controller layer response
//        4=>Assert the controller response by checking the status code and response body
//        5=>Verify the service layer method is called once

        Long id=5L;

        doThrow(new RuntimeException("product with id:"+id+"is not found"))

                .when(productsService).deleteById(id);

        mockMvc.perform(delete("/products/deleteProductById/{id}",id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("product with id:"+id+"is not found"));

        verify(productsService).deleteById(id);
    }
}
