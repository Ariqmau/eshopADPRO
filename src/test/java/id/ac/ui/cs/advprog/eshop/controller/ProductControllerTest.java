package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class) // Ensure only this controller is loaded
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Properly mocks ProductService inside the Spring context
    private ProductService productService;

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");

        mockMvc.perform(post("/product/create")
                        .flashAttr("product", product))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    void testEditProductPage() throws Exception {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");

        when(productService.findById("1")).thenReturn(product);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attributeExists("product"));

        verify(productService, times(1)).findById("1");
    }

    @Test
    void testEditProductPost() throws Exception {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Updated Product");

        mockMvc.perform(post("/product/edit")
                        .flashAttr("product", product))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(productService, times(1)).edit(product);
    }

    @Test
    void testDeleteProductPage() throws Exception {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");

        when(productService.findById("1")).thenReturn(product);

        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("DeleteProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", product));

        verify(productService, times(1)).findById("1");
    }

    @Test
    void testDeleteProductPost() throws Exception {
        Product product = new Product();
        product.setProductId("1");

        mockMvc.perform(post("/product/delete")
                        .flashAttr("product", product))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).delete(product);
    }

    @Test
    void testProductListPage() throws Exception {
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Product 1");

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Product 2");

        List<Product> products = Arrays.asList(product1, product2);
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ProductList"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", products));

        verify(productService, times(1)).findAll();
    }
}
