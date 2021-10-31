package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestUtils {

    private static Logger log = LogManager.getLogger(TestUtils.class);

    private long shortWaitForElement;
    private long longWaitForElement;
    private WebDriver driver;

    private enum HrefSearchingMode { CONTAINS, ENDS_WITH, MATCHES }

    public TestUtils(WebDriver driver, long shortWaitForElement, long longWaitForElement) {
        this.driver = driver;
        this.shortWaitForElement = shortWaitForElement;
        this.longWaitForElement = longWaitForElement;
    }


    public void assertElementHasExpectedText(By locator, String expectedText) {
        log.debug("Asserting that element has text " + expectedText + ".");
        WebElement element = waitForAndGetPresentElement(locator);
        assertTrue(element.isDisplayed());
        assertEquals(element.getText(), expectedText);
    }

    public int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public String getCurrentDateAsFormattedText() {

        DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date now = Calendar.getInstance().getTime();
        return df.format(now);
    }

    public WebElement waitForAndGetHrefElementThatContainsText(String textToSearch) {
        log.debug("Waiting for href element containing text: " + textToSearch + ".");
        return waitForAndGetHrefElement(textToSearch, HrefSearchingMode.CONTAINS);
    }

    public WebElement waitForAndGetHrefElementThatEndsWithText(String textToSearch) {
        log.debug("Waiting for href element ending with text: " + textToSearch + ".");
        return waitForAndGetHrefElement(textToSearch, HrefSearchingMode.ENDS_WITH);
    }

    public WebElement waitForAndGetHrefElementThatMatchesText(String patternToSearch) {
        log.debug("Waiting for href element that matches pattern: " + patternToSearch + ".");
        return waitForAndGetHrefElement(patternToSearch, HrefSearchingMode.MATCHES);
    }

    public WebElement waitForAndGetClickableElement(By locator) {
        log.debug("Waiting for element to be clickable.");
        return waitForAndGetElement(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForAndGetPresentElement(By locator) {
        log.debug("Waiting for element to be present.");
        return waitForAndGetElement(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForAndGetElementWithText(By locator, String textToSearch) {
        log.debug("Waiting for and getting element with text " + textToSearch + ".");
        WebDriverWait wait = new WebDriverWait(driver, shortWaitForElement);
        WebElement element = wait.until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {

                for (WebElement element : driver.findElements(locator)) {

                    if (element.isDisplayed() && element.getText().equals(textToSearch)) {
                        return element;
                    }
                }
                log.debug("No element found.");
                return null;
            }
        });

        scrollToElement(element);
        return element;
    }

    private WebElement waitForAndGetElement(Function<? super WebDriver, WebElement> expectedCondition) {
        WebDriverWait wait = new WebDriverWait(driver, shortWaitForElement);
        WebElement element = wait.until(expectedCondition);
        scrollToElement(element);
        return element;
    }

    private void scrollToElement(WebElement element) {
        log.debug("Scrolling to element.");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",element);
    }

    private WebElement waitForAndGetHrefElement(String href, HrefSearchingMode searchingMode) {

        // TODO: improvement: adapt this to accept a timeout, instead of only using the long timeout
        WebDriverWait wait = new WebDriverWait(driver, longWaitForElement);

        WebElement element = wait.until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {

                List<WebElement> hyperlinks = driver.findElements(By.tagName("a"));

                log.debug("Looping to find href element.");
                for(WebElement hyperlink : hyperlinks) {

                    if(hyperlink.isDisplayed()) {

                        String retrievedHref = hyperlink.getAttribute("href");
                        if( null != retrievedHref
                            && ((searchingMode.equals(HrefSearchingMode.CONTAINS) && retrievedHref.contains(href))
                                    || (searchingMode.equals(HrefSearchingMode.ENDS_WITH) && retrievedHref.endsWith(href))
                                    || (searchingMode.equals(HrefSearchingMode.MATCHES) && retrievedHref.matches(href)))) {

                            return hyperlink;
                        }
                    }
                }
                log.debug("No element found.");
                return null;
            }
        });

        scrollToElement(element);
        return element;
    }
}