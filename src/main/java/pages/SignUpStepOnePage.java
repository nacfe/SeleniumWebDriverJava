package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import utils.TestUtils;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SignUpStepOnePage {

	private static Logger log = LogManager.getLogger(SignUpStepOnePage.class);

    private TestUtils testUtils;

    private By birthdateDayFieldLocator = By.cssSelector("input[class*='birthdate-day']");
    private By birthdateMonthDropdownLocator = By.id("dropdown-birthdateMonth");
    private By birthdateYearFieldLocator = By.cssSelector("input[class*='birthdate-year']");
    private By checkPCOImageLocator = By.cssSelector("img[alt='check PCO Hire']");
    private By createAccountButtonStepOneLocator = By.cssSelector("button[data-test='signUp.createBtn']");
    private By emailLocator = By.id("partner_email");
    private By firstNameFieldLocator = By.id("partner_firstName");
    private By lastNameFieldLocator = By.id("partner_lastName");
    private By passwordLocator = By.id("partner_password");
    private By phoneLocator = By.id("partner_phone");
    private By privacyTermsLocator = By.cssSelector("div[class='checkbox-content']");
    private By signUpPCOButtonLocator = By.cssSelector("div[data-test='signUp.pcoBtn']");
    private By termsCheckboxLocator = By.cssSelector("label[for='partner_terms']");

    private String privacyPolicyFilePattern = ".*privacy_policy.*.pdf";
    private String privacyPolicyText = "I have read, understood and consented to the terms of use and privacy policy.";
    private String termsOfUseFilePattern = ".*terms_of_use.*.pdf";


    public SignUpStepOnePage(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    public void acceptTerms() {
        testUtils.waitForAndGetClickableElement(termsCheckboxLocator).click();
    }

    public void assertMinimumPageElementsArePresent() {

        log.info("Asserting that minimum page elements are present.");

        // assert PCO radio button is selected.
        // TODO: find a simpler way to do this. The "isSelected()" is not working for the input field...
        assertTrue(testUtils.waitForAndGetPresentElement(signUpPCOButtonLocator)
                .findElement(checkPCOImageLocator)
                .isDisplayed());

        assertFalse(testUtils.waitForAndGetClickableElement(termsCheckboxLocator).isSelected());

        testUtils.assertElementHasExpectedText(privacyTermsLocator, privacyPolicyText);

        // links to privacy policies must be present
        testUtils.waitForAndGetHrefElementThatMatchesText(privacyPolicyFilePattern);
        testUtils.waitForAndGetHrefElementThatMatchesText(termsOfUseFilePattern);
    }

    public void fillBasicDetailsRequiredFields(String firstName,
                                               String lastName,
                                               int birthdateDay,
                                               int birthdateMonth,
                                               int birthdateYear,
                                               String phoneNumber,
                                               String email,
                                               String password) {

    	log.info("Filling account's basic details required fields.");

        testUtils.waitForAndGetClickableElement(firstNameFieldLocator).sendKeys(firstName);

        testUtils.waitForAndGetClickableElement(lastNameFieldLocator).sendKeys(lastName);

        testUtils.waitForAndGetClickableElement(birthdateDayFieldLocator).sendKeys(String.valueOf(birthdateDay));

        testUtils.waitForAndGetClickableElement(birthdateMonthDropdownLocator).click(); // opens dropdown
        testUtils.waitForAndGetClickableElement(By.id("option-" + birthdateMonth)).click(); // clicks dropdown's desired value

        testUtils.waitForAndGetClickableElement(birthdateYearFieldLocator).sendKeys(String.valueOf(birthdateYear));

        testUtils.waitForAndGetClickableElement(phoneLocator).sendKeys(phoneNumber);

        String randomEmail = email.replace("@", testUtils.generateRandomNumber(100000, 999999) + "@");
        log.info("Random email [" + randomEmail + "] will be used.");
        testUtils.waitForAndGetClickableElement(emailLocator).sendKeys(randomEmail);

        testUtils.waitForAndGetClickableElement(passwordLocator).sendKeys(password);
    }

    public void clickCreateAccountButton() {
    	log.info("Clicking create account button.");
        testUtils.waitForAndGetClickableElement(createAccountButtonStepOneLocator).click();
    }

}
