package ui_automation.step_definitions;

import org.openqa.selenium.support.PageFactory;
import ui_automation.utilities.Driver;

public class OhrmLoginPage {
    public OhrmLoginPage(){
        PageFactory.initElements(Driver.getInstance().getDriver(), this);
    }
}
