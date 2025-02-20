package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
        // empty
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testCreateAndFindNegative() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(-1);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(0, savedProduct.getProductQuantity());
    }

    @Test
    void testEditProduct() {
        // Create initial product
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Edit product
        Product editedProduct = new Product();
        editedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editedProduct.setProductName("Sampo Cap Bambang Edit");
        editedProduct.setProductQuantity(200);
        productRepository.edit(editedProduct);

        // Verify edit
        Product foundProduct = productRepository.findById(product.getProductId());
        assertEquals(editedProduct.getProductName(), foundProduct.getProductName());
        assertEquals(editedProduct.getProductQuantity(), foundProduct.getProductQuantity());
    }

    @Test
    void testEditProductNegative() {
        // Create initial product
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Edit product
        Product editedProduct = new Product();
        editedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editedProduct.setProductName("Sampo Cap Bambang Edit");
        editedProduct.setProductQuantity(-1);
        productRepository.edit(editedProduct);

        // Verify edit
        Product foundProduct = productRepository.findById(product.getProductId());
        assertEquals(editedProduct.getProductName(), foundProduct.getProductName());
        assertEquals(0, foundProduct.getProductQuantity());
    }

    @Test
    void testDeleteProduct() {
        // Create product
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        productRepository.create(product);

        // Delete product
        productRepository.delete(product);

        // Verify deletion
        Iterator<Product> products = productRepository.findAll();
        assertFalse(products.hasNext());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEdit_NonExistentProduct_ShouldReturnNull() {
        // Arrange: Add a product that exists
        Product existingProduct = new Product();
        existingProduct.setProductId("existing-id");
        existingProduct.setProductName("Existing Product");
        productRepository.create(existingProduct);

        // Act: Try editing a product that does not exist
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("Test Product");

        Product result = productRepository.edit(nonExistentProduct);

        // Assert: Should return null
        assertNull(result, "Editing a non-existent product should return null");
    }


    @Test
    void testDelete_NonExistentProduct_ShouldReturnNull() {
        // Arrange: Add an unrelated product to ensure the loop runs
        Product existingProduct = new Product();
        existingProduct.setProductId("existing-id");
        existingProduct.setProductName("Existing Product");
        productRepository.create(existingProduct);

        // Act: Try deleting a product that does not exist
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");

        Product result = productRepository.delete(nonExistentProduct);

        // Assert: Should return null
        assertNull(result, "Deleting a non-existent product should return null");
    }

    @Test
    void testFindById_ProductNotInRepository_ShouldReturnNull() {
        // Arrange: Add some products
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Product 1");
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Product 2");
        productRepository.create(product2);

        // Act: Search for a product ID that does NOT exist
        Product result = productRepository.findById("non-existent-id");

        // Assert: Should return null
        assertNull(result, "Searching for a non-existent product in a non-empty repository should return null");
    }
}