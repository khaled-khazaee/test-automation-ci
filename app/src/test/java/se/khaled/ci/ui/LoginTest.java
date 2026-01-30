package se.khaled.ci.ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

  private WebDriver driver;

  @BeforeEach
  void setup() {
    ChromeOptions options = new ChromeOptions();

    // ??? CI ????? headless ????
    if ("true".equalsIgnoreCase(System.getenv("CI"))) {
      options.addArguments("--headless=new");
      options.addArguments("--no-sandbox");
      options.addArguments("--disable-dev-shm-usage");
    }

    driver = new ChromeDriver(options); // Selenium Manager ???? ?????? ?? ???? ??????
  }

  @Test
  void loginWithValidCredentials_shouldGoToInventoryPage() {
    driver.get("https://www.saucedemo.com/");

    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.id("login-button")).click();

    assertTrue(driver.getCurrentUrl().contains("inventory"),
        "Expected URL to contain 'inventory' after successful login");
  }

  @AfterEach
  void tearDown() {
    if (driver != null) driver.quit();
  }
}
