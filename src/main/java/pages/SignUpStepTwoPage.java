package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import utils.TestUtils;

import static org.testng.Assert.assertTrue;

public class SignUpStepTwoPage {

    private static Logger log = LogManager.getLogger(SignUpStepTwoPage.class);

    private TestUtils testUtils;

    private By createAccountButtonStepTwoLocator = By.cssSelector("button[data-test='signUpTwo.createBtn']");
    private By findPostCodeButtonLocator = By.cssSelector("button[data-test='partner.findPostcodeBtn']");
    private By insuranceQuestionsLocator = By.cssSelector("label[class='sub-label mb-2']");
    private By noAccident3YearsLocator = By.cssSelector("div[data-test='partner.accident3Years']");
    private By noCriminal5YearsLocator = By.cssSelector("div[data-test='partner.criminal5Years']");
    private By noDivLocator = By.xpath("div[text()='No']");
    private By noRefusedInsuranceLocator = By.cssSelector("div[data-test='partner.refusedInsurance']");
    private By paragraphLocator = By.cssSelector("p");
    private By postCodeLocator = By.id("partner_postcode");

    private String insuranceQuestionOne = "Have you been involved in an accident or made any insurance claims in the last 3 years, whether your fault or someone else's fault?";
    private String insuranceQuestionThree = "Have you ever been refused motor insurance or had any special terms imposed?";
    private String insuranceQuestionTwo = "Have you been convicted (or have pending) of any criminal convictions in the last 5 years?";
    private String pcoKeyFactsFilePattern = ".*pco_key_facts_default..*.pdf";
    private String postCodeText = "Post code";
    private String subcriptionTermsFilePattern = ".*subscription_terms..*.pdf";
    private String subscriptionTermsText = "I have read & understood the Subscription Terms and Key Facts.";


    public SignUpStepTwoPage(TestUtils testUtils) {
        this.testUtils = testUtils;
    }

    public void assertMinimumPageElementsArePresent() {

        log.info("Asserting that minimum page elements are present.");

        testUtils.waitForAndGetClickableElement(findPostCodeButtonLocator);

        assertTrue(testUtils.waitForAndGetElementWithText(paragraphLocator, postCodeText).isDisplayed());

        assertTrue(testUtils.waitForAndGetElementWithText(insuranceQuestionsLocator, insuranceQuestionOne).isDisplayed());
        assertTrue(testUtils.waitForAndGetElementWithText(insuranceQuestionsLocator, insuranceQuestionTwo).isDisplayed());
        assertTrue(testUtils.waitForAndGetElementWithText(insuranceQuestionsLocator, insuranceQuestionThree).isDisplayed());

        assertTrue(testUtils.waitForAndGetElementWithText(paragraphLocator, subscriptionTermsText).isDisplayed());

        // links to terms' files must be present
        testUtils.waitForAndGetHrefElementThatMatchesText(subcriptionTermsFilePattern);
        testUtils.waitForAndGetHrefElementThatMatchesText(pcoKeyFactsFilePattern);

        testUtils.waitForAndGetClickableElement(createAccountButtonStepTwoLocator);
    }

    public void clickNextButton() {
        log.info("Clicking 'next' button.");
        testUtils.waitForAndGetClickableElement(createAccountButtonStepTwoLocator).click();
    }

    public void fillInsuranceInformation(String postCode) {

        log.info("Filling insurance information.");

        testUtils.waitForAndGetClickableElement(postCodeLocator).sendKeys(postCode);

        log.info("Answering 'No' to all insurance's questions.");

        clickOnChildNo(noAccident3YearsLocator);
        clickOnChildNo(noCriminal5YearsLocator);
        clickOnChildNo(noRefusedInsuranceLocator);
    }

    private void clickOnChildNo(By parentLocator) {
        testUtils.waitForAndGetClickableElement(parentLocator)
                .findElement(noDivLocator)
                .click();
    }
}
