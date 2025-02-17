package io.loop.step_definitions;

import io.cucumber.java.*;
import io.loop.utilities.BrowserUtils;
import io.loop.utilities.Driver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static io.loop.utilities.BrowserUtils.myScenario;

public class Hook {
    @Before
    public void SetUp(Scenario scenario) {
        Driver.getDriver();
        myScenario = scenario;


    }
    @After
    public void TearDown(Scenario scenario) {
        // ONLY TAKES SCREENSHOT WHEN SCENARIO IS FAILED
        if(scenario.isFailed()){
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            myScenario.attach(screenshot, "image/png", myScenario.getName());
        }
        Driver.closeDriver();
    }
    @AfterStep
    public void takeScreenshot(Scenario scenario) {
        final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
        myScenario.attach(screenshot, "image/png", myScenario.getName());

    }
}
