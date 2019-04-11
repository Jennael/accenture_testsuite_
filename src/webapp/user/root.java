package webapp.user;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.*;
import webapp.*;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class root{
    private static WebDriver driver;

    @BeforeClass
    public static void DriverSetUp(){
        System.setProperty("webdriver.chrome.driver","C:/Programs/Java/ChromeDriver-73.0.3683.68/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
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
        assertEquals(driver.getTitle(), "ACNAPI Help");
    }

    @Test
    public void TestAllLinks() throws InterruptedException {
        System.out.println("TestAllLinks\n");

        //Maximise window
        driver.manage().window().maximize();
        FindElements.clickAllLinks(driver);
    }

    @AfterClass
    public static void BrowserClose() {
        driver.close();
    }
}


