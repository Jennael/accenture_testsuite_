package webapp.user;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import webapp.*;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class submitTicket {
    private static WebDriver driver;

    @BeforeClass
    public static void DriverSetUp(){
        System.setProperty("webdriver.chrome.driver","C:/Programs/Java/ChromeDriver-73.0.3683.68/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
    }

    @Test
    public void TestLink(){
        driver.get("http://localhost:8080/submitaticketuser");
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
        driver.get("http://localhost:8080/submitaticketuser");
        System.out.println("TestAllLinks\n");

        //Maximise window
        driver.manage().window().maximize();
        FindElements.clickAllLinks(driver);
    }

    @Test
    public void Submit() throws InterruptedException {
        driver.get("http://localhost:8080/submitaticketuser");
        System.out.println("TestTicketSubmit\n");

        //subject
        WebElement subjectInput = driver.findElement(By.name("subject"));
        subjectInput.sendKeys("TestSubject");
        //category
        Select categoryInput = new Select(driver.findElement(By.name("category")));
        categoryInput.selectByVisibleText("APIs");
        //message
        WebElement messageInput = driver.findElement(By.name("message"));
        messageInput.sendKeys("TestMessage");
        //image
        WebElement imageInput = driver.findElement(By.name("image"));
        imageInput.sendKeys("D:\\Root\\Documents\\IJ\\accenture_testsuite\\src\\webapp\\testImage.png");
        //submit
        WebElement submitButton = driver.findElement(By.className("btn-form"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("hero-text-box"))));
    }

    @AfterClass
    public static void BrowserClose() {
        driver.close();
    }

}
