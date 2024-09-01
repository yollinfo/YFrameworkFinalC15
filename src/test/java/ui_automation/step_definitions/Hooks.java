package ui_automation.step_definitions;


import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ui_automation.utilities.BrowserFactory;
import ui_automation.utilities.Driver;

import java.util.concurrent.TimeUnit;

public class Hooks {
    public WebDriver driver=null;

    @Before
    public void setUp(){
        // actual driver whether is chromedriver or edgedriver will come from BrowserFactory
        // let's assume there is no browser property
        // BrowserFactory.createInstance() will return new ChromeDriver
        driver = BrowserFactory.createInstance();
        // driver = new ChromeDriver();
        // Get the instance of Driver class from JVM which is SingleTon
        // call the setDriver() of that instance, and pass parameter
        // the parameter will first find it's way to the SingleTon Class
        // based on ThreadNumber it'll find the way to ThreadLocal
        Driver.getInstance().setDriver(driver);
        // Driver.getInstance().getDriver() will be only option to reach out that driver
        driver = Driver.getInstance().getDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        //
    }

    @After
    public void tearDown(Scenario scenario) {
//        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
//        }
       Driver.getInstance().removeDriver();
    }
}