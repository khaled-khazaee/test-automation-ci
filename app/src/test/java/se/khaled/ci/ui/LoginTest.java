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

    if ("true".equalsIgnoreCase(System.getenv("CI"))) {
      options.addArguments("--headless=new");
      options.addArguments("--no-sandbox");
      options.addArguments("--disable-dev-shm-usage");
    }

    driver = new ChromeDriver(options);
  }

  private void openLoginPage() {
    driver.get("https://www.saucedemo.com/");
  }

  private void login(String username, String password) {
    driver.findElement(By.id("user-name")).clear();
    driver.findElement(By.id("user-name")).sendKeys(username);

    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys(password);

    driver.findElement(By.id("login-button")).click();
  }

  private String getErrorMessage() {
    return driver.findElement(By.cssSelector("[data-test='error']")).getText();
  }

  @Test
  void loginWithValidCredentials_shouldGoToInventoryPage() {
    openLoginPage();
    login("standard_user", "secret_sauce");
    assertTrue(driver.getCurrentUrl().contains("inventory"),
        "Expected URL to contain 'inventory' after successful login");
  }

  @Test
  void loginWithWrongUsername_shouldShowErrorMessage() {
    openLoginPage();
    login("wrong_user", "secret_sauce");

    String msg = getErrorMessage();
    assertTrue(msg.toLowerCase().contains("username and password do not match"),
        "Expected an error message for wrong username");
  }

  @Test
  void loginWithWrongPassword_shouldShowErrorMessage() {
    openLoginPage();
    login("standard_user", "wrong_password");

    String msg = getErrorMessage();
    assertTrue(msg.toLowerCase().contains("username and password do not match"),
        "Expected an error message for wrong password");
  }

  @AfterEach
  void tearDown() {
    if (driver != null) driver.quit();
  }
}
