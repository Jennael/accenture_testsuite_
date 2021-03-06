package webapp.user;

import org.junit.experimental.theories.Theories;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.*;
import webapp.*;

import java.util.List;
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

    @Test
    public void Query() throws InterruptedException {
        driver.get("http://localhost:8080/");
        //Maximise window
        driver.manage().window().maximize();

        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        WebElement queryInput = driver.findElement(By.id("query"));
        queryInput.sendKeys("I have a question.\n");

        List<WebElement> chatResult = driver.findElements(By.xpath("//td[@id='result']/div"));

        for(WebElement childDiv : chatResult){
            Thread.sleep(500);
            System.out.println(childDiv.getText());
        }
        assert(chatResult.size() == 2);
    }

    @AfterClass
    public static void BrowserClose() {
        driver.close();
    }
}


