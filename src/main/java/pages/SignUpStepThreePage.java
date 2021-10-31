package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import utils.TestUtils;

import java.net.URL;

import static org.testng.Assert.assertTrue;

public class SignUpStepThreePage {

    private static Logger log = LogManager.getLogger(SignUpStepThreePage.class);

    private TestUtils testUtils;

    private By createAccountButtonStepThreeLocator = By.cssSelector("button[data-test='signUpStepThree.doneBtn']");
    private By drivingLicenseLocator = By.cssSelector("div[for='file-driving licence (front)']");
    private By uploadDrivingLicenceInputLocator = By.id("file-driving licence (front)");


    public SignUpStepThreePage(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    public void assertMinimumPageElementsArePresent() {

        log.info("Asserting that minimum page elements are present.");

        assertTrue(testUtils.waitForAndGetPresentElement(drivingLicenseLocator).isDisplayed());

        testUtils.waitForAndGetClickableElement(createAccountButtonStepThreeLocator);
    }

    public void clickDoneButton() {
        log.info("Clicking done button.");
        testUtils.waitForAndGetClickableElement(createAccountButtonStepThreeLocator).click();
    }

    public void uploadDrivingLicence(String filePath) {

        log.info("Uploading driving licence.");

        URL fileURL = SignUpStepThreePage.class.getClassLoader().getResource(filePath);
        if(null == fileURL) {
            String errorMessage = "Error uploading driving license: no file found for file path [" + filePath + "].";
            log.fatal(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        testUtils.waitForAndGetPresentElement(uploadDrivingLicenceInputLocator)
                .sendKeys(fileURL.getPath());
    }

}
