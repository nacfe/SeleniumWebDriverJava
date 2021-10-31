package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import utils.TestUtils;

public class PrivateHireCarDetailsPage {

    private static Logger log = LogManager.getLogger(PrivateHireCarDetailsPage.class);

    private TestUtils testUtils;

    private By carDetailsLocator = By.cssSelector("div[alt='car details']");
    private By h4Locator = By.cssSelector("h4");

    private String createAccountText = "To book this car, you’ll need to create an account with us.";
    private String loginLink = "/login";
    private String signUpLink = "/signup";


    public PrivateHireCarDetailsPage(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    public void assertMinimumPageElementsArePresent() {

        log.info("Asserting that minimum page elements are present.");

        testUtils.waitForAndGetPresentElement(carDetailsLocator);

        testUtils.waitForAndGetElementWithText(h4Locator, createAccountText);

        testUtils.waitForAndGetHrefElementThatEndsWithText(loginLink);

        testUtils.waitForAndGetHrefElementThatEndsWithText(signUpLink);

        // TODO: check for more relevant page elements, like the prices
    }

    public void clickOnSignUpButton() {
        log.info("Clicking on sign up button.");
        testUtils.waitForAndGetHrefElementThatEndsWithText(signUpLink).click();
    }

}
