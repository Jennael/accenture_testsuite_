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
        driver.get("http://localhost:8080/submitaticket");
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
        driver.get("http://localhost:8080/submitaticket");
        System.out.println("TestAllLinks\n");

        //Maximise window
        driver.manage().window().maximize();
        FindElements.clickAllLinks(driver);
    }

    @Test
    public void Submit() throws InterruptedException {
        driver.get("http://localhost:8080/submitaticket");
        System.out.println("TestTicketSubmit\n");

        //full name
        WebElement nameInput = driver.findElement(By.name("full_name"));
        nameInput.sendKeys("TestSubject");
        //company
        WebElement companyInput = driver.findElement(By.name("company"));
        companyInput.sendKeys("TestSubject");
        //email
        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.sendKeys("meimei_tay@mymail.sutd.edu.sg");
        //phone number
        WebElement phoneInput = driver.findElement(By.name("phone"));
        phoneInput.sendKeys("00000000");
        //subject
        WebElement subjectInput = driver.findElement(By.name("subject"));
        subjectInput.sendKeys("TestMessage");
        //category
        Select categoryInput = new Select(driver.findElement(By.name("category")));
        categoryInput.selectByVisibleText("APIs");
        //image
        //WebElement imageInput = driver.findElement(By.name("image"));
        //imageInput.sendKeys("D:\\Root\\Documents\\IJ\\accenture_testsuite\\src\\webapp\\testImage.png");
        //message
        WebElement messageInput = driver.findElement(By.name("message"));
        messageInput.sendKeys("TestMessage");
        //submit
        WebElement submitButton = driver.findElement(By.className("btn-form"));
        submitButton.click();

        Thread.sleep(2000);

        String alertText = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        assertEquals(alertText, "Ticket submitted!");
    }

    @AfterClass
    public static void BrowserClose() {
        driver.close();
    }

}
