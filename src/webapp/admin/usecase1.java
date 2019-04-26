package webapp.admin;


import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

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
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("home"))));

        assertEquals("http://localhost:3000/home", driver.getCurrentUrl());
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

            //click button
            List<WebElement> attendButton = driver.findElements(By.xpath(".//button"));
            attendButton.get(i).click();

            tickets = driver.findElements(By.xpath("//div[@id='root-home']/*"));
            Thread.sleep(2000);

            //click button
            List<WebElement> resolveButton = driver.findElements(By.xpath(".//button"));
            resolveButton.get(i).click();

            driver.navigate().back();
            Thread.sleep(2000);
        }
    }

}





