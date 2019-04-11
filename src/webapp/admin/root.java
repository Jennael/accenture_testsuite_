package webapp.admin;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webapp.FindElements;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class root{
    private static WebDriver driver;

    @BeforeClass
    public static void DriverSetUp(){
        System.setProperty("webdriver.chrome.driver","C:/Programs/Java/ChromeDriver-73.0.3683.68/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:3000/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void TestLink(){
        System.out.println("TestLink\n");

        //Maximise window
        driver.manage().window().maximize();

        //Minimise window to very small
        Dimension d = new Dimension (300,300);
        driver.manage().window().setSize(d);

        //Assert page loads completely / link works
        assertEquals(driver.getTitle(), "ACNAPI - Administration");
    }

    @Test
    public void TestAllLinks() throws InterruptedException {
        System.out.println("TestAllLinks\n");

        //Maximise window
        driver.manage().window().maximize();
        FindElements.clickAllLinks(driver);
    }

    @Test
    public void TestLogin() throws InterruptedException {
        System.out.println("TestLogin\n");

        //Maximise window
        driver.manage().window().maximize();

        driver.findElement(By.id("adminUsername")).sendKeys("admin-thomas");
        driver.findElement(By.id("adminPassword")).sendKeys("thomas123");
        driver.findElement(By.id("adminVerf")).sendKeys("xxxVerfxxx123");
        driver.findElement(By.id("login-button")).click();
//        Thread.sleep(3000); //wait 3 seconds for page to load

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("root-home"))));

        assertEquals("http://localhost:3000/home", driver.getCurrentUrl());
    }

    @AfterClass
    public static void BrowserClose() {
        driver.quit();
    }
}
