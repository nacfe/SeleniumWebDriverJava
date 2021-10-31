package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import utils.TestUtils;

public class StartPage {

    private static Logger log = LogManager.getLogger(StartPage.class);

    private TestUtils testUtils;

    private By joinNowButtonLocator = By.cssSelector("button[data-testid='toggle-sign-up-button']"); // caution: there's more than one
    private By logInButtonLocator = By.cssSelector("a[data-test='navbar.signInBtn']");

    private String carsSearchLinkText = "/cars/search";
    private String pcoCarsLinkText = "/private-hire";


    public StartPage(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    public void assertMinimumPageElementsArePresent() {

        log.info("Asserting that minimum page elements are present.");

        // No need for explicit asserts here. The "get" methods already check for presence / click-ability.
        testUtils.waitForAndGetHrefElementThatEndsWithText(carsSearchLinkText);
        testUtils.waitForAndGetHrefElementThatEndsWithText(pcoCarsLinkText);
        testUtils.waitForAndGetClickableElement(joinNowButtonLocator);
        testUtils.waitForAndGetClickableElement(logInButtonLocator);
    }

    public void clickPCOCarsLink() {
        log.info("Clicking 'PCO cars' link.");
        testUtils.waitForAndGetHrefElementThatEndsWithText(pcoCarsLinkText).click();
    }
}
