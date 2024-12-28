package TestTisoAssignment.TestTisoAssignment;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class BillingAndProductCRUD {

    WebDriver driver;

	@BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testLoginAndProductCRUD() {
        driver.get("https://tijarah-qa.vercel.app");

        // Log in to the application
        driver.findElement(By.id("phone")).sendKeys("570057001");
        driver.findElement(By.name("password")).sendKeys("00004444");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Automate Billing Module (Web)
        automateBilling(driver); 

        // Automate Product CRUD Module (Web)
        automateProductCRUD(driver);
    }
    
    private static void automateBilling(WebDriver driver) {
        // Navigate to the Billing screen
        driver.findElement(By.xpath("//span[normalize-space(text())='Billing']")).click();
       
       
        WebElement Devicedropdown = driver.findElement(By.id("device"));
        Devicedropdown.click();

        // Step 2: Wait for the drop down options to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//ul[@id='device-listbox']//li[contains(text(), 'Test')]")
        ));
        
        option.click();
        
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
        
        // Add Open Item to the cart
        driver.findElement(By.xpath("//td[contains(.,'Cedarwood Perfume')]")).click();


        // Add Custom Price item to the cart
        driver.findElement(By.xpath("//h6[normalize-space(text())='Velvet Shadows']")).click();
        WebElement price = driver.findElement(By.xpath("//label[text()='Enter price']/following-sibling::div//input"));
        price.click();
        price.sendKeys("200");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Add Multi-Variant item to the cart
        driver.findElement(By.xpath("//table[contains(@class,'MuiTable-root css-1trb3f4')]/tbody[1]/tr[1]/td[2]")).click();
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement variantoption = wait2.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//div[contains(@class,'MuiPaper-root MuiPaper-elevation')])[2]")
            ));
        variantoption.click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();

    }
    
    
    private void automateProductCRUD(WebDriver driver) {
        // Navigate to the Product Catalog
        driver.findElement(By.xpath("(//button[@type='button'])[1]")).click();
        driver.findElement(By.xpath("//span[normalize-space(text())='Products Catalogue']")).click();
        driver.findElement(By.xpath("//span[normalize-space(text())='Products']")).click();

        // Create a new product
        WebDriverWait waitforpageload = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement CreateBtn = waitforpageload.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//button[contains(@class,'MuiButtonBase-root MuiButton-root')])[3]")
        ));
        CreateBtn.click();
        driver.findElement(By.name("productNameEn")).sendKeys("newproduct");
        driver.findElement(By.name("productNameAr")).sendKeys("arnewproduct");

        // Brand DD
        WebElement Branddropdown = driver.findElement(By.id("Brands"));
        Branddropdown.click();
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement brand = wait1.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//ul[@id='Brands-listbox']//li[text()='Abo Ussama']")));
        brand.click();

        // Category DD
        WebElement Categorydropdown = driver.findElement(By.id("reportingCategory"));
        Categorydropdown.click();
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement category = wait2.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//ul[@id='reportingCategory-listbox']//li[text()='Attar']")));
        category.click();

        // Generate SKU
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement skuBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[normalize-space(text())='Generate random SKU']")));
        skuBtn.click();

        try {
            Thread.sleep(5000); // Wait for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click 'Create' button
        driver.findElement(By.xpath("//button[normalize-space(text())='Create']")).click();
        
        try {
            Thread.sleep(5000); // Wait for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Update the product
        driver.findElement(By.xpath("(//td[contains(@class,'MuiTableCell-root MuiTableCell-body')]//button)[1]")).click();
        driver.findElement(By.xpath("//button[normalize-space(text())='Update']")).click();

     // Delete the product
        driver.findElement(By.xpath("(//td[contains(@class,'MuiTableCell-root MuiTableCell-body')]//button)[1]")).click();
        try {
            Thread.sleep(5000); // Wait for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.xpath("//button[normalize-space(text())='Delete']")).click();
        try {
            driver.findElement(By.xpath("(//button[text()='Cancel']/following-sibling::button)[2]")).click();
        } catch (Exception e) {
            System.err.println("Error while clicking on the Delete button: " + e.getMessage());
            e.printStackTrace(); // Logs the full stack trace
        }


    }

	@AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
