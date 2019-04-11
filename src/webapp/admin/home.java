package webapp.admin;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webapp.FindElements;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class home{
    private static WebDriver driver;

    @BeforeClass
    public static void DriverSetUp(){
        System.setProperty("webdriver.chrome.driver","C:/Programs/Java/ChromeDriver-73.0.3683.68/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:3000/home");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Before
    public void Before(){
        if (driver.getCurrentUrl() != "http://localhost:3000/home"){
            driver.get("http://localhost:3000/home");
        }
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
        assertEquals(driver.getTitle(), "ACNAPI - Admin Home");
    }

    @Test
    public void TestAllLinks() throws InterruptedException {
        System.out.println("TestAllLinks\n");

        //Maximise window
        driver.manage().window().maximize();
        FindElements.clickAllLinks(driver);
    }

    @Test
    public void TestViewTickets() throws InterruptedException {
        System.out.println("TestViewTickets\n");

        //Maximise window
        driver.manage().window().maximize();

        //ticket list object
        List<WebElement> tickets = driver.findElements(By.xpath("//div[@id='root-home']/*"));

        //read all tickets
        for (int i = 0; i < tickets.size(); i++){

            System.out.println("Ticket " + i + ": ");
            String status = tickets.get(i).findElement(By.xpath(".//span[@class='w3-right w3-margin-right w3-opacity']")).getText();
            System.out.println("Status: " + status);
            String category = tickets.get(i).findElement(By.xpath(".//h3[@class='cat-design']")).getText();
            System.out.println("Category: " + category);
            String title = tickets.get(i).findElement(By.xpath(".//h5[@class='w3-left']")).getText();
            System.out.println("Title: " + title);
            String message = tickets.get(i).findElement(By.xpath(".//p")).getText();
            System.out.println("Message: " + message);
            System.out.println("\n");
        }
    }

    @Test
    public void TestAttendTickets() throws InterruptedException {
        System.out.println("TestAttendTickets\n");

        //Maximise window
        driver.manage().window().maximize();

        //Ticket list object
        List<WebElement> tickets = driver.findElements(By.xpath("//div[@id='root-home']/*"));

        for (int i = 0; i < tickets.size(); i++){
            //update list after refreshing page
            tickets = driver.findElements(By.xpath("//div[@id='root-home']/*"));
            System.out.println("Ticket " + i + ": ");
            tickets.get(i).findElement(By.xpath(".//button")).click();
            Thread.sleep(2000);

//            WebDriverWait wait = new WebDriverWait(driver, 5);
//            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("w3-container w3-content align-middle"))));

            //Ticket details list
            List<WebElement> details = driver.findElements(By.xpath("//div[@class='w3-col m5']/p"));
            
            String userDetails = null;
            
            for(int j = 0; j < details.size(); j++){
                userDetails = userDetails + details.get(j).getText() + "\n";
            }

            System.out.println("Details: \n" + userDetails);
            System.out.println("\n");

            driver.navigate().back();
            Thread.sleep(2000);
        }
    }

    @AfterClass
    public static void BrowserClose() {
        driver.quit();
    }
}
