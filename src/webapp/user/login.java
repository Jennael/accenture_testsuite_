package webapp.user;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webapp.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class login {
    private static WebDriver driver;

    @BeforeClass
    public static void DriverSetUp(){
        System.setProperty("webdriver.chrome.driver","C:/Programs/Java/ChromeDriver-73.0.3683.68/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
    }

    @Test
    public void TestLink(){
        driver.get("http://localhost:8080/login");
        System.out.println("TestLink\n");

        //Maximise window
        driver.manage().window().maximize();
        //Minimise window to very small
        Dimension d = new Dimension (300,300);
        driver.manage().window().setSize(d);

        //Assert page loads completely / link works
        assertEquals(driver.getTitle(), "ACNAPI Help");
    }

    @Test
    public void TestAllLinks() throws InterruptedException {
        driver.get("http://localhost:8080/login");
        System.out.println("TestAllLinks\n");

        //Maximise window
        driver.manage().window().maximize();
        FindElements.clickAllLinks(driver);
    }

    @Test
    public void Login() throws InterruptedException {
        //Maximise window
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/login");

        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("d");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("d");
        WebElement submitButton = driver.findElement(By.className("btn-form"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("hero-text-box"))));

        assertEquals(driver.getCurrentUrl(), "http://localhost:8080/homepage");
    }

    @AfterClass
    public static void BrowserClose() {
        driver.close();
    }
}


