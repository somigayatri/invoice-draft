package io.cucumber.skeleton;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import netscape.javascript.JSObject;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;

import java.util.concurrent.TimeUnit;

public class StepDefinitions {
    WebDriver driver;

    @Before
    public void init() throws FileNotFoundException {

        //ToDo: Update value with path to geckodriver. Eg: "/Applications/geckodriver"
        System.setProperty("webdriver.gecko.driver", UPDATE HERE);

        driver = new FirefoxDriver();

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get("https://www.xero.com/");
        driver.findElement(By.linkText("Log in")).click();

        //ToDo: Update value with email address to Xero Test Account. Eg: "somigayatri@gmail.com"
        driver.findElement(By.name("Username")).sendKeys(UPDATE_HERE);

        //ToDo: Update value with email address to Xero Test Account. Eg: "SecretKey123"
        driver.findElement(By.name("Password")).sendKeys(UPDATE_HERE);

        driver.findElement(By.id("xl-form-submit")).click();
    }

    @After
    public void close() {
        driver.close();
    }

    @Given("User is on Xero Dashboard")
    public void user_is_on_xero_dashboard() {
        String curr = driver.findElement(By.className("xnav-header-button")).getText();
        if(!curr.equals("Demo Company (NZ)")) {
            driver.findElement(By.className("xnav-header-button")).click();
            driver.findElement(By.linkText("Demo Company (NZ)")).click();
        }
    }

    @Given("clicks Business tab on the navbar menu")
    public void clicks_business_tab_on_the_navbar_menu() {
        driver.findElement(By.xpath("/html/body/div[1]/header/div/ol[1]/li[2]/button")).click();
    }

    @Given("clicks Quotes option from the dropdown menu")
    public void clicks_quotes_option_from_the_dropdown_menu() {
        driver.findElement(By.linkText("Quotes")).click();
        if(!driver.getCurrentUrl().contains("/Accounts/Receivable/Quotes/Search")) {
            throw new io.cucumber.java.PendingException();
        }
    }

    @Given("User clicks on New Quote Button")
    public void user_clicks_on_new_quote_button() {
        driver.findElement(By.id("button-1024-btnInnerEl")).click();
    }

    @When("User is navigated to the New Quote Page")
    public void user_is_navigated_to_the_new_quote_page() {
        if(!driver.getCurrentUrl().contains("/Accounts/Receivable/Quotes/New")) {
            throw new io.cucumber.java.PendingException();
        }
    }

    @Then("User creates a new quote with required params")
    public void user_creates_a_new_quote_with_required_params() throws InterruptedException {
        WebElement webElement = driver.findElement(By.id("combo-contact-1034-inputEl"));
        webElement.sendKeys("somigayatri@gmail.com");
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("combo-contact-1034-boundlist-listEl")));
        driver.findElement(By.id("combo-contact-1034-inputEl")).click();

        webElement = driver.findElement(By.id("combo-contact-1034-inputEl"));
        webElement.sendKeys(Keys.TAB, Keys.TAB, Keys.TAB,
                            Keys.TAB, Keys.TAB, Keys.TAB,
                            Keys.TAB, Keys.TAB, Keys.TAB);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement form_element = driver.findElement(By.id("ext-comp-1118"));
        js.executeScript("arguments[0].setAttribute('display', 'block')", form_element);

        WebElement ell = driver.switchTo().activeElement();
        ell.sendKeys( "1");
        Thread.sleep(2000);
        ell.sendKeys(Keys.TAB);

        driver.findElement(By.id("button-1084")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("text-1143-inputEl")).sendKeys("adsfasf@sfas.com");
        Thread.sleep(2000);
        driver.findElement(By.id("button-1154-btnEl")).click();
        Thread.sleep(3000);
    }

    @When("clicks Sent tab to view quotes that are already sent")
    public void clicks_sent_tab_to_view_quotes_that_are_already_sent() {
        driver.findElement(By.id("tab-1017")).click();
        System.out.println("currently " + driver.findElement(By.id("tab-1017-btnInnerEl")).getText());
        if(driver.findElement(By.id("tab-1017-btnInnerEl")).getText().equals("Sent (0)")) {
            throw new RuntimeException("Should have successfully found a quote");
        }
    }

    @Then("accepts all sent quotes")
    public void accepts_all_sent_quotes() throws InterruptedException {
        Thread.sleep(3000);
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("x-column-header-first")));
        driver.findElement(By.className("x-column-header-first")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("buttonanchor-1040-btnEl")).click();
    }

    @Given("clicks Accepted tab to view quotes which are accepted")
    public void clicks_accepted_tab_to_view_quotes_which_are_accepted() {
        driver.findElement(By.id("tab-1019")).click();
    }

    @When("selects the recently accepted quote")
    public void selects_the_recently_accepted_quote() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("x-column-header-first")));
        Thread.sleep(5000);
        driver.findElement(By.xpath("/html/body/div[3]/div[3]/div[2]/div/div/div[3]/div/table/tbody/tr[1]/td[5]\n")).click();
        Thread.sleep(3000);
    }

    @Then("creates invoice from the quote")
    public void creates_invoice_from_the_quote() throws InterruptedException {
        if(!driver.getCurrentUrl().contains("/Accounts/Receivable/Quotes/View")) {
            throw new RuntimeException("Should have navigated to latest accepted quote");
        }
        driver.findElement(By.id("button-1032")).click();
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-1080")));
        driver.findElement(By.id("button-1080")).click();
        Thread.sleep(3000);
        driver.findElement(By.linkText("Save")).click();
        Thread.sleep(3000);
        if(!driver.getCurrentUrl().contains("invoiceStatus=INVOICESTATUS/DRAFT")) {
            throw new RuntimeException("Failed to save draft invoice");
        }

    }


}
