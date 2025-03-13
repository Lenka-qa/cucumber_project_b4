package io.loop.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.loop.pages.GoogleSearchPage;
import io.loop.pages.POM;
import io.loop.utilities.BrowserUtils;
import io.loop.utilities.ConfigurationReader;
import io.loop.utilities.Driver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GoogleSearchStepDefs {

    GoogleSearchPage googleSearchPage = new GoogleSearchPage();
    POM pages = new POM();

    @Given("user in on Google search page")
    public void user_in_on_google_search_page() {
        Driver.getDriver().get(ConfigurationReader.getProperties("google.url"));
        BrowserUtils.takeScreenshot();
    }
    @When("user types Loop Academy in the google search box and clicks enter")
    public void user_types_loop_academy_in_the_google_search_box_and_clicks_enter() throws InterruptedException {
        googleSearchPage.searchBox.sendKeys("Loop Academy" + Keys.ENTER);

        WebElement element = Driver.getDriver().findElement(By.xpath("//iframe[@title='reCAPTCHA']"));

        Driver.getDriver().switchTo().frame(element);

        if(googleSearchPage.captcha.isDisplayed()){
            googleSearchPage.captcha.click();
        }

        Driver.getDriver().switchTo().defaultContent();

        Thread.sleep(5000);
    }

    @Then("user should be see Loop Academy - Google search in the google title")
    public void user_should_be_see_loop_academy_google_search_in_the_google_title() {
        String actualTitle = Driver.getDriver().getTitle();
        assertEquals("Expected result does not match the actual", "Loop Academy - Google Search", actualTitle);
    }

    @When("user types {string} in the google search box and clicks enter")
    public void user_types_in_the_google_search_box_and_clicks_enter(String input) throws InterruptedException {
        googleSearchPage.searchBox.sendKeys(input + Keys.ENTER);
        WebElement element = Driver.getDriver().findElement(By.xpath("//iframe[@title='reCAPTCHA']"));
        Driver.getDriver().switchTo().frame(element);
        if(googleSearchPage.captcha.isDisplayed()){
            googleSearchPage.captcha.click();
        }
        Driver.getDriver().switchTo().defaultContent();
    }

    @Then("user should see {string} in the google title")
    public void user_should_see_in_the_google_title(String expectedTitle) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(Integer.valueOf(ConfigurationReader.getProperties("timeouts"))));
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        String actualTitle = Driver.getDriver().getTitle();
        assertEquals("Expected result does not match the actual", expectedTitle, actualTitle);
    }

    @Then("user searches the following items")
    public void user_searches_the_following_items(List<String> items) {
//        for (String item : items) {
//            googleSearchPage.searchBox.clear();
//            googleSearchPage.searchBox.sendKeys(item + Keys.ENTER);
//            googleSearchPage.handleReCaptcha(Driver.getDriver(), googleSearchPage.captcha);
//            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(Integer.valueOf(ConfigurationReader.getProperties("timeouts"))));
//            wait.until(ExpectedConditions.titleIs(item + " - Google Search"));
//            assertEquals("Expected result does not match actual", item + " - Google Search", Driver.getDriver().getTitle());
//        }
        items.forEach(p -> {
            googleSearchPage.searchBox.clear();
            googleSearchPage.searchBox.sendKeys( p + Keys.ENTER);
            googleSearchPage.handleCaptcha(Driver.getDriver(), googleSearchPage.captcha);
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(Integer.valueOf(ConfigurationReader.getProperties("timeouts"))));
            wait.until(ExpectedConditions.titleIs(p + " - Google Search"));
            assertEquals("Expected result does not match actual", p + " - Google Search", Driver.getDriver().getTitle());
        });

    }
    @Then("We love Loop Academy")
    public void we_love_loop_academy() {
        System.out.println("We love Loop Academy");
    }

    @When("user search for {string}")
    public void user_search_for(String country) throws InterruptedException {
        googleSearchPage.searchBox.sendKeys("What is the capital of " + country + Keys.ENTER);
        Thread.sleep(3000);
    }
    @Then("user should see the {string} in the results")
    public void user_should_see_the_in_the_results(String capital) {
        googleSearchPage.handleCaptcha(Driver.getDriver(), googleSearchPage.captcha);
        assertEquals("Expected capital city: " + capital + " does not match with actual one: " + googleSearchPage.capital.getText(), capital, googleSearchPage.capital.getText());
    }


}
