package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import utils.TestUtils;

import static org.testng.Assert.assertTrue;

public class PrivateHireSearchSettingsPage {

    private static Logger log = LogManager.getLogger(PrivateHireSearchSettingsPage.class);

    private TestUtils testUtils;

    private By fuelTypeDropdownLocator = By.id("dropdown-pco-home-fuel-type");
    private By fuelTypeParagraphLocator = By.cssSelector("p[for='pco-home-fuel-type']");
    private By priceRangeParagraphLocator = By.cssSelector("p[for='search-filter-price-range']");
    private By priceSliderLocator = By.cssSelector("div[class='rc-slider']");
    private By yearDropdownLocator = By.id("dropdown-pco-home-year");
    private By yearParagraphLocator = By.cssSelector("p[for='pco-home-year']");

    private String findYourPCOCarLink = "/private-hire/search?";
    private String fuelTypeText = "Fuel Type";
    private String priceRangeText = "Weekly Price";
    private String yearText = "Year";


    public PrivateHireSearchSettingsPage(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    public void assertMinimumPageElementsArePresent() {

        log.info("Asserting that minimum page elements are present.");

        testUtils.assertElementHasExpectedText(fuelTypeParagraphLocator, fuelTypeText);
        assertTrue(testUtils.waitForAndGetPresentElement(fuelTypeDropdownLocator)
                            .isDisplayed());

        testUtils.assertElementHasExpectedText(yearParagraphLocator, yearText);
        assertTrue(testUtils.waitForAndGetPresentElement(yearDropdownLocator)
                            .isDisplayed());

        testUtils.assertElementHasExpectedText(priceRangeParagraphLocator, priceRangeText);
        assertTrue(testUtils.waitForAndGetPresentElement(priceSliderLocator)
                .isDisplayed());

        testUtils.waitForAndGetHrefElementThatContainsText(findYourPCOCarLink);
    }

    public void clickFindYourPCOCarLink() {
        log.info("Clicking 'find your PCO car' link.");
        testUtils.waitForAndGetHrefElementThatContainsText(findYourPCOCarLink).click();
    }

}
