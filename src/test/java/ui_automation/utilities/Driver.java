package ui_automation.utilities;

import org.openqa.selenium.WebDriver;

public class Driver {
    // make constructor private
    private Driver(){

    }
    // Create a Driver instance which is private
    private static Driver instance=new Driver();

    // create a getter method to return that instance
    public static Driver getInstance(){
        return instance;
    }

    /**
     * ThreadLocal is the Java class which will be storing data and access to that
     * based on the Thread itself
     */
    private ThreadLocal<WebDriver> driverThreadLocal=new ThreadLocal<WebDriver>();// thread local driver object for webdriver


    public WebDriver getDriver(){
        return driverThreadLocal.get();
    }

    public  void setDriver(WebDriver driverParameter){

        driverThreadLocal.set(driverParameter);
    }
    public void removeDriver(){
        driverThreadLocal.get().quit();
        driverThreadLocal.remove();
    }
}