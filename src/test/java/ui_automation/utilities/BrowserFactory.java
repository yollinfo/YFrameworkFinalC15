package ui_automation.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class BrowserFactory {
    /**
     * the createInstance method will create different browser instances
     * based on the need.
     * If we execute through the command line like below, by specifying the type of browser
     * `mvn clean test -Dtest=UITestRunner -Dbrowser=chromeRemote`
     * if we don't specify the type of browser from command line, or execute though IDE,
     * JVM won't have browser property
     *
     * mvn clean test -Dtest=UITestRunner
     *
     * @return WebDriver instance
     */
        public static WebDriver createInstance() {

            WebDriver driver = null;

            try {
                    if(System.getProperty("browser")==null){
                        // because of the selenium version 3, we need to specify where is the exec file
                        // Boni Garcia Library is profiding below line to find out during runtime
                        WebDriverManager.chromedriver().setup();
                        // Create a key/value of ther preferences
                        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                        String path = System.getProperty("user.dir")+
                                File.separator +
                                "src" +
                                File.separator +
                                "test" +
                                File.separator +
                                "resources" +
                                File.separator +
                                "testData" +
                                File.separator +
                                "Downloads";
                        chromePrefs.put("download.default_directory", path);
                        // create the Chrome Options
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("--ignore-ssl-errors=yes");
                        options.addArguments("--ignore-certificate-errors");
                        options.setExperimentalOption("prefs", chromePrefs);
                        driver = new ChromeDriver(options);
                    }
                    else {
                        switch (System.getProperty("browser")) {
                            case "chrome-headless":
                                WebDriverManager.chromedriver().setup();
                                driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
                                break;
                            case "chromeRemote":
                                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                                chromePrefs.put("download.default_directory", System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\Downloads");
                                ChromeOptions chrOptions = new ChromeOptions();
                                chrOptions.addArguments("--ignore-ssl-errors=yes");
                                chrOptions.addArguments("--ignore-certificate-errors");
                                chrOptions.setExperimentalOption("prefs", chromePrefs);
                                try {
                                    driver = new RemoteWebDriver(new URL("http://54.224.48.204:4444/wd/hub"), chrOptions);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "firefox":
                                WebDriverManager.firefoxdriver().setup();
                                driver = new FirefoxDriver();
                                break;
                            case "firefox-headless":
                                WebDriverManager.firefoxdriver().setup();
                                driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true));
                                break;
                            case "firefoxRemote":
                                FirefoxOptions firOptions = new FirefoxOptions();
                                try {
                                    driver = new RemoteWebDriver(new URL("http://54.224.48.204:4444/wd/hub"), firOptions);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "ie":
                                if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                                    throw new WebDriverException("Your operating system does not support the requested browser");
                                }
                                WebDriverManager.iedriver().setup();
                                driver = new InternetExplorerDriver();
                                break;

                            case "edge":
                                if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                                    throw new WebDriverException("Your operating system does not support the requested browser");
                                }
                                WebDriverManager.edgedriver().setup();
                                driver = new EdgeDriver();
                                break;

                            case "safari":
                                if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                                    throw new WebDriverException("Your operating system does not support the requested browser");
                                }
                                WebDriverManager.getInstance(SafariDriver.class).setup();
                                driver = new SafariDriver();
                                break;
                            default:
                                WebDriverManager.chromedriver().setup();
                                driver = new ChromeDriver();
                                break;
                        }
                    }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return driver;
        }
    }