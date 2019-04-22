package webapp.user;

import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import webapp.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import java.util.Random;

/*
User creates an account with randomised NUMBER
User logs in
User submit ticket
User views ticket
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class usecase1 {
    private static WebDriver driver;
    private static String TESTUSER;
    private static int NUMBER;

    @BeforeClass
    public static void DriverSetUp() throws IOException {

        Random rand = new Random();
        TESTUSER = "USER" + String.format("%010d", NUMBER = rand.nextInt(100000000));
        System.out.println(TESTUSER);

        File file = new File("D:\\Root\\Documents\\IJ\\accenture_testsuite\\src\\webapp\\testusers.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.append(TESTUSER);
        writer.newLine();
        writer.close();

        System.setProperty("webdriver.chrome.driver","C:/Programs/Java/ChromeDriver-73.0.3683.68/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/");

    }

    @Test
    public void Test1Register() throws InterruptedException {
        driver.findElement(By.linkText("Sign Up")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.tagName("form"))));

        assertEquals(driver.getCurrentUrl() , "http://localhost:8080/signup");

        WebElement fullnameInput = driver.findElement(By.name("full_name"));
        fullnameInput.sendKeys(TESTUSER);
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys(TESTUSER);
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(TESTUSER + "user");

        //email
        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.sendKeys(TESTUSER + "@mymail.sutd.edu.sg");
        //phone number
        WebElement phoneInput = driver.findElement(By.name("phone"));
        phoneInput.sendKeys(String.valueOf(NUMBER));
        //company
        WebElement companyInput = driver.findElement(By.name("company"));
        companyInput.sendKeys(TESTUSER);

        WebElement submitButton = driver.findElement(By.className("btn-form"));
        submitButton.click();
        Thread.sleep(1500);
    }

    @Test
    public void Test2Login() throws InterruptedException {
        driver.findElement(By.linkText("LOG IN")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.tagName("form"))));

        System.out.println("User attempts to log in.");

        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys(TESTUSER);
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(TESTUSER + "user");
        WebElement submitButton = driver.findElement(By.className("btn-form"));
        submitButton.click();

        Thread.sleep(1500);

        if (driver.getCurrentUrl() == "http://localhost:8080/homepage"){
            System.out.println("Log in success!");
        }

        assertEquals(driver.getCurrentUrl(), "http://localhost:8080/homepage");

    }

    @Test
    public void Test3SubmitTicket() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(By.linkText("SUBMIT A TICKET")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.tagName("form"))));

        //subject
        WebElement subjectInput = driver.findElement(By.name("subject"));
        subjectInput.sendKeys(TESTUSER);

        //category
        Select categoryInput = new Select(driver.findElement(By.name("category")));
        categoryInput.selectByVisibleText("APIs");

        //image
        //WebElement imageInput = driver.findElement(By.name("image"));
        //imageInput.sendKeys("D:\\Root\\Documents\\IJ\\accenture_testsuite\\src\\webapp\\testImage.png");

        //message
        WebElement messageInput = driver.findElement(By.name("message"));
        messageInput.sendKeys(TESTUSER + "is testing!");

        //submit
        WebElement submitButton = driver.findElement(By.className("btn-form"));
        submitButton.click();

        Thread.sleep(2000);

        String alertText = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        assertEquals("Ticket submitted!", alertText);
        Thread.sleep(2000);

    }

    @Test
    public void Test4CheckTicket(){

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.linkText("MY TICKETS"))));

        driver.findElement(By.linkText("MY TICKETS")).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("container"))));

        List<WebElement> tickets = driver.findElements(By.xpath("//h3"));
        List<String> ticketss = new ArrayList<>();

        for (WebElement tix: tickets){
            System.out.println(tix.getText());
            ticketss.add(tix.getText());
        }

        assert(ticketss.contains(TESTUSER));
    }

//    @AfterClass
//    public static void BrowserClose() {
//        driver.close();
//    }
}
