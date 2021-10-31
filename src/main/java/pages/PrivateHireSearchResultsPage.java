package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.TestUtils;

import static org.testng.Assert.assertNotEquals;

public class PrivateHireSearchResultsPage {

    private static Logger log = LogManager.getLogger(PrivateHireSearchResultsPage.class);

    private TestUtils testUtils;

    private By searchResultsTitleLocator = By.cssSelector("h2");

    private String noResultsText = "No Cars Available";


    public PrivateHireSearchResultsPage(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    public void assertMinimumPageElementsArePresent() {

        log.info("Asserting that minimum page elements are present.");

        // NOTE: there are other ways to check for the number of results, like counting the divs that contain the cars.
        WebElement titleElement = testUtils.waitForAndGetPresentElement(searchResultsTitleLocator);
        assertNotEquals(titleElement.getText(), noResultsText);

        // TODO: check for more relevant page elements, like the filters
    }

    public void selectCar(String manufacturer, String model, String carNumber) {

        String carHref = manufacturer + "/" + model + "/" + carNumber;
        log.info("Selecting car: " + carHref + ".");
        testUtils.waitForAndGetHrefElementThatEndsWithText(carHref).click();
    }

}
