package javastudybuddies.discordbots.stackoverflowbot;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class StackoverflowBot {
    public static void main(String[] args) throws Exception {
            SshotofElement.screenShotElement();
    }


}



 class SshotofElement {

    public static void screenShotElement() throws InterruptedException,IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\parsecer\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("marionette", true);
        WebDriver driver = new ChromeDriver(capabilities);

        driver.get("http://learn-selenium-easy.blogspot.com/");
        driver.manage().window().maximize();

        // Xpath of element to take screen shot
        WebElement element=driver.findElement(By.xpath("//*[@id='PopularPosts1']"));
        System.out.println(element.getSize());
        System.out.println(element.getLocation());
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        // Take full screen screenshot
        BufferedImage  fullImg = ImageIO.read(screenshot);
        Point point = element.getLocation();
        int elementWidth = element.getSize().getWidth();
        int elementHeight = element.getSize().getHeight();
        BufferedImage elementScreenshot= fullImg.getSubimage(point.getX(), point.getY(), elementWidth,elementHeight);

        // crop the image to required
        ImageIO.write(elementScreenshot, "png", screenshot);
        FileUtils.copyFile(screenshot, new File("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\JavaStudyBuddies\\mostread_screenshot.png"));//path to save screen shot

        driver.close();
    }
}