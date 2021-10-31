package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.TestUtils;

import static org.testng.Assert.assertEquals;

public class SignUpResultsPage {

    private static Logger log = LogManager.getLogger(SignUpResultsPage.class);

    private TestUtils testUtils;

    private By showCarsButtonLocator = By.cssSelector("a[data-test='signUpConfirmation.showVehiclesBtn']");
    private By successTitleLocator = By.cssSelector("h2[class='text-center']");

    private String successMessage = "You are ready to start driving";


    public SignUpResultsPage(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    public void assertSuccess() {

        log.info("Asserting that signing up was successful.");

        WebElement headerElement = testUtils.waitForAndGetPresentElement(successTitleLocator);
        assertEquals(headerElement.getText(), successMessage);

        testUtils.waitForAndGetClickableElement(showCarsButtonLocator);
    }

}
