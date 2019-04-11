package webapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FindElements {

    public static void clickAllLinks(WebDriver driver) throws InterruptedException {

        System.out.println("Current Page: " + driver.getCurrentUrl());
        List<WebElement> linksMain = driver.findElements(By.tagName("a"));

        for (int i = 0; i < linksMain.size(); i++) {
            System.out.println("Current Page: " + driver.getCurrentUrl());
            linksMain = driver.findElements(By.tagName("a"));

            System.out.println("Link " + i + " : " + linksMain.get(i).getText());
            System.out.println("Link " + i + " : " + linksMain.get(i).getAttribute("href"));

            if(linksMain.get(i).getAttribute("href") != null && linksMain.get(i).getAttribute("href").contains("http")){
                linksMain.get(i).click();
                printAllLinks(driver);
                driver.navigate().back();
            }
        }
    }

    public static void printAllLinks(WebDriver driver) throws InterruptedException {

        System.out.println("Current Page: " + driver.getCurrentUrl());
        List<WebElement> linksNext = driver.findElements(By.tagName("a"));

        for (int i = 0; i < linksNext.size(); i++) {
            linksNext = driver.findElements(By.tagName("a"));

            System.out.println("Link " + i + " : " + linksNext.get(i).getText());
            System.out.println("Link " + i + " : " + linksNext.get(i).getAttribute("href"));

        }
    }

}
