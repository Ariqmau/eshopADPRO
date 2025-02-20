package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String createUrl;
    private String listUrl;

    @BeforeEach
    void setupTest() {
        createUrl = String.format("%s:%d/product/create", testBaseUrl, serverPort);
        listUrl = String.format("%s:%d/product/list", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_isCorrect(ChromeDriver driver) {
        // Open Create Product page
        driver.get(createUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Find input fields
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("nameInput")));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));

        // Clear and fill form
        nameInput.clear();
        quantityInput.clear();
        nameInput.sendKeys("Sampo Cap Bambang");
        quantityInput.sendKeys("100");

        // Click submit button
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Open Product List Page
        driver.get(listUrl);

        // Verify the new product appears in the list
        WebElement productName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productName")));
        WebElement productQuantity = driver.findElement(By.id("productQuantity"));

        assertEquals("Sampo Cap Bambang", productName.getText());
        assertEquals("100", productQuantity.getText());
    }
}
