package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals(product.getProductId(), createdProduct.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testEdit() {
        when(productRepository.edit(product)).thenReturn(product);

        Product editedProduct = productService.edit(product);

        assertNotNull(editedProduct);
        assertEquals(product.getProductId(), editedProduct.getProductId());
        verify(productRepository, times(1)).edit(product);
    }

    @Test
    void testDelete() {
        when(productRepository.delete(product)).thenReturn(product);

        Product deletedProduct = productService.delete(product);

        assertNotNull(deletedProduct);
        assertEquals(product.getProductId(), deletedProduct.getProductId());
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testFindById() {
        when(productRepository.findById("test-id")).thenReturn(product);

        Product foundProduct = productService.findById("test-id");

        assertNotNull(foundProduct);
        assertEquals(product.getProductId(), foundProduct.getProductId());
        verify(productRepository, times(1)).findById("test-id");
    }

    @Test
    void testFindAll() {
        Product product2 = new Product();
        product2.setProductId("test-id-2");
        product2.setProductName("Test Product 2");
        product2.setProductQuantity(5);

        List<Product> productList = Arrays.asList(product, product2);
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("test-id", result.get(0).getProductId());
        assertEquals("test-id-2", result.get(1).getProductId());
        verify(productRepository, times(1)).findAll();
    }
}
