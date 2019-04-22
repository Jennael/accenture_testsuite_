package webapp.user;

import org.junit.BeforeClass;
import org.junit.Test;
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
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class signup_fuzzing {
    private static WebDriver driver;
    private static String TESTUSER;
    private static int NUMBER;
    private static int FUZZ_COUNT;

    @BeforeClass
    public static void DriverSetUp() throws IOException, InterruptedException {

        FUZZ_COUNT = 5;

        Random rand = new Random();
        TESTUSER = "USER" + String.format("%08d", NUMBER = rand.nextInt(100000000));
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

        initRegister();

    }

    public static void initRegister() throws InterruptedException {
        driver.get("http://localhost:8080/signup");

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.tagName("form"))));

        WebElement fullnameInput = driver.findElement(By.name("full_name"));
        fullnameInput.sendKeys(TESTUSER);
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys(TESTUSER);

        //email
        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.sendKeys(TESTUSER + "@mymail.sutd.edu.sg");
        //phone number
        WebElement phoneInput = driver.findElement(By.name("phone"));
        phoneInput.sendKeys(String.valueOf(12312312));
        //company
        WebElement companyInput = driver.findElement(By.name("company"));
        companyInput.sendKeys(TESTUSER);

//        WebElement submitButton = driver.findElement(By.className("btn-form"));
//        submitButton.click();

    }


    /*
    Instead of clicking submit button, test different inputs
    */
    @Test
    public void fuzzPasswordInvalid() throws InterruptedException, IOException {
        Random rand = new Random();

        for (int i = 0; i < FUZZ_COUNT; i++){

            WebElement passwordInput = driver.findElement(By.name("password"));
            String password = webapp.InputFuzzer.generate(rand.nextInt(10)+1, 5, 0);

            passwordInput.sendKeys(password);
            WebElement submitButton = driver.findElement(By.className("btn-form"));
            submitButton.click();
            Thread.sleep(2000);

            if (!passwordValid(password)){
                String alertText = driver.switchTo().alert().getText();
                driver.switchTo().alert().accept();
                assertEquals("Password should have at least one lowercase letter, one uppercase letter, a number, and minimum 8 characters!", alertText);
            } else {
                assertEquals (driver.getCurrentUrl(), "http://localhost:8080/login");

                rand = new Random();
                TESTUSER = "USER" + String.format("%08d", NUMBER = rand.nextInt(100000000));
                System.out.println(TESTUSER);

                File file = new File("D:\\Root\\Documents\\IJ\\accenture_testsuite\\src\\webapp\\testusers.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.append(TESTUSER);
                writer.newLine();
                writer.close();
            }
            initRegister();
        }
    }

//    @AfterClass
//    public static void BrowserClose() {
//        driver.close();
//    }

    public static Boolean passwordValid(String password){
        Boolean digit = false;
        Boolean upper = false;
        Boolean lower = false;

        if (password != null && !password.isEmpty()) {
            for (char c : password.toCharArray()) {
                if (Character.isDigit(c)) {
                    digit = true;
                }
                if (Character.isLowerCase(c)){
                    lower = true;
                }
                if (Character.isUpperCase(c)) {
                    upper = true;
                }
            }
        }

        if (password.length()<8 || !digit || !upper || !lower){
            return false;
        } else {
            return true;
        }
    }
}
