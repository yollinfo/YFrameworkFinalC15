package runners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class SeleniumGridPractice {
    public void chromeHeadedMode() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://dev-hrm.yoll.io/");
        Thread.sleep(5000);
        driver.quit();
    }

    public void chromeHeadlessMode() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
        driver.get("http://dev-hrm.yoll.io/");
        Thread.sleep(5000);
        driver.quit();
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--ignore-ssl-errors=yes");
            options.addArguments("--ignore-certificate-errors");
            WebDriver driver = new RemoteWebDriver(new URL("http://54.80.58.108:4444/wd/hub"), options);
            driver.get("http://dev-hrm.yoll.io/");
            Thread.sleep(5000);
            driver.quit();
        } catch (MalformedURLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    }
