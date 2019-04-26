package webapp.user;





/*
Anonymous User submit ticket without logging in or signing up
 */

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class usecase2 {
    private static WebDriver driver;
    private static String TESTUSER;
    private static int NUMBER;


    @BeforeClass
    public static void DriverSetUp() throws IOException {

        Random rand = new Random();
        TESTUSER = "ANON" + String.format("%010d", NUMBER = rand.nextInt(100000000));
        System.out.println(TESTUSER);

        File file = new File("D:\\Root\\Documents\\IJ\\accenture_testsuite\\src\\webapp\\testanon.txt");
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
    public void TestSubmitTicket() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(By.linkText("SUBMIT A TICKET")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.tagName("form"))));

        assertEquals(driver.getCurrentUrl(), "http://localhost:8080/submitaticket");

        //full name
        WebElement fullnameInput = driver.findElement(By.name("full_name"));
        fullnameInput.sendKeys(TESTUSER);

        //company
        WebElement companyInput = driver.findElement(By.name("company"));
        companyInput.sendKeys(TESTUSER);

        //email
        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.sendKeys(TESTUSER + "@mymail.sutd.edu.sg");

        //phone number
        WebElement phoneInput = driver.findElement(By.name("phone"));
        phoneInput.sendKeys(String.valueOf(NUMBER));

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
        messageInput.sendKeys(TESTUSER + " is testing!");

        //submit
        WebElement submitButton = driver.findElement(By.className("btn-form"));
        submitButton.click();

        Thread.sleep(2000);


    }

//    @AfterClass
//    public static void BrowserClose() {
//        driver.close();
//    }

}
