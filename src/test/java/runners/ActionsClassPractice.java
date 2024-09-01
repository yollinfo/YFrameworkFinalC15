package runners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class ActionsClassPractice {

    /**
     * What is the Actions class?
     * What are available actions methods(); doubleClick, dragAndDrop, moveToElement, contextClick, perform
     * Mouse Actions:
     * click()
     *          Actions actions = new Actions(driver);
     *         // first hover over, go ahead click
     *         actions.moveToElement(userOnePic).click().perform();
     * clickAndHold(), - This method combines moving the mouse to the center of an element with pressing the left mouse button.
     *                   This is useful for focusing a specific element:
     * contextClick(), - is designed to right click to the specific element
     * doubleClick(), - is designed to double click to the specific element
     * dragAndDrop(), - is designed to drag specific elem, to another elem
     * moveToElement(), - is designed to hover(mouse) over the specific element
     * release(),    - is designed to release the focus from element
     * build()/perform() - perform() has to be called after complete the logical build.
     *                     perform() will internally call build() which is chaining all available actions into 1 action
     *
     *                     we can do it ourselves by calling build().
     *
     *                     build() will return Action object, we can assign to variable and call perform later
     *                   WebElement userName = driver.findElement(By.id("txtUsername"));
     *                   Actions actions = new Actions(driver);
     *
     *                  Action chainedAction = actions
     *                          .moveToElement(userName)   // hoverOver webElement
     *                          .click()                   // click the username textbox
     *                          .keyDown(Keys.SHIFT)       // press shift key down
     *                          .sendKeys("yollstudent")   // type yollstudent
     *                          .keyUp(Keys.SHIFT)        // release shift key up
     *                          .build();                // build action
     *
     *
     *                           chainedAction.perform(); // perform action
     *
     *
     *
     * Keyboard Actions:
     * sendKeys(),  - is designed to type some text on webElement
     * keyUp(Keys.SHIFT), - is designed to release already pressed KEY. Keys.Shift
     * keyDown(Keys.SHIFT)  - is designed to press to the KEY. Keys.Shift
     */
    @Test
    public void hoverOver() throws InterruptedException {
        // hover over the element
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://the-internet.herokuapp.com/hovers");
        Thread.sleep(5000);
        WebElement userOnePic = driver.findElement(By.xpath("//*[@class = 'example']/div[1]/img"));
        Actions actions = new Actions(driver);
        // hover over
        actions.moveToElement(userOnePic).perform();
        Thread.sleep(2000);
        boolean isTextDisplayed = driver.findElement(By.xpath("//*[@class = 'example']/div[1]//h5")).isDisplayed();

        System.out.println(isTextDisplayed);
        driver.quit();

    }

    @Test
    public void rightClick() throws InterruptedException {
        // right click to the element
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://swisnl.github.io/jQuery-contextMenu/demo.html");
        Thread.sleep(5000);

        WebElement element = driver.findElement(By.xpath("//span[text() = 'right click me']"));
        Actions actions = new Actions(driver);
        actions.contextClick(element).perform();

        Thread.sleep(5000);
        driver.quit();
    }

    @Test
    public void dragDrop() throws InterruptedException {
        // drag and drop the element
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://www.dhtmlgoodies.com/scripts/drag-drop-custom/demo-drag-drop-3.html");
        Thread.sleep(5000);

        // Locate source webElement - dragged one
        WebElement washington = driver.findElement(By.id("box3"));
        // Locate target webElement
        WebElement usa = driver.findElement(By.id("box103"));
        // create an object from Actions Class
        Actions actions = new Actions(driver);
        // call dragDrop()
        actions.dragAndDrop(washington, usa).perform();
        Thread.sleep(5000);
        String expected = "rgba(0, 255, 0, 1)";
        String backgroundColor = washington.getCssValue("background-color");

        System.out.println(backgroundColor);

        driver.quit();
    }

    @Test
    public void doubleClick() throws InterruptedException {
        // double click to element
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://api.jquery.com/dblclick/");
        Thread.sleep(5000);
        // make sure elementDbl is visible in UI port before interacting
        // scroll to elementDbl
        WebElement demoLabel = driver.findElement(By.cssSelector("#example-1 h4"));
        Thread.sleep(2000);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", demoLabel);
        WebElement iframe = driver.findElement(By.cssSelector(".demo.code-demo iframe"));
        driver.switchTo().frame(iframe);
        WebElement elementDbl = driver.findElement(By.tagName("div"));
        // get background color
        String colorBeforeDoubleClick = elementDbl.getCssValue("background");
        // double click
        Thread.sleep(4000);
        Actions actions = new Actions(driver);
        actions.doubleClick(elementDbl).perform();
        Thread.sleep(4000);
        // get background color again
        String colorAfterDoubleClick = elementDbl.getCssValue("background");
        // verify it changed
        System.out.println(colorAfterDoubleClick);
        System.out.println(colorBeforeDoubleClick);

        driver.quit();
    }

    @Test
    public void chainedActions() throws InterruptedException {
        // chained actions
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://dev-hrm.yoll.io/index.php/auth/login");
        // locate webElement
        WebElement userName = driver.findElement(By.id("txtUsername"));
        Actions actions = new Actions(driver);

        Action chainedAction = actions
                .moveToElement(userName)   // hoverOver webElement
                .click()                   // click the username textbox
                .keyDown(Keys.SHIFT)       // press shift key down
                .sendKeys("yollstudent")   // type yollstudent
                .keyUp(Keys.SHIFT)        // release shift key up
                .build();                // build the action


        chainedAction.perform();   // perform the action

        Thread.sleep(2000);
        driver.quit();

    }

    @Test
    public void tooltip(){
        // interacting with tooltip
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://jqueryui.com/tooltip/");

        // tooltip should be as "We ask for your age only for statistical purposes."

        WebElement iframe = driver.findElement(By.className("demo-frame"));
        driver.switchTo().frame(iframe);
        WebElement input = driver.findElement(By.id("age"));
        // we can get the value of tooltip by retrieving the value of title attribute
        // having the title attribute in webElement common, but are not the only way for developers.
        String valueOfTitle = input.getAttribute("title");
        System.out.println(valueOfTitle);
        driver.quit();
    }

}
