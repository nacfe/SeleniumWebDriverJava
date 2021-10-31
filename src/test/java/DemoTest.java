import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.PrivateHireCarDetailsPage;
import pages.PrivateHireSearchResultsPage;
import pages.PrivateHireSearchSettingsPage;
import pages.SignUpResultsPage;
import pages.SignUpStepOnePage;
import pages.SignUpStepThreePage;
import pages.SignUpStepTwoPage;
import pages.StartPage;

import java.io.FileNotFoundException;

public class DemoTest extends BaseTest {

    private static Logger log = LogManager.getLogger(DemoTest.class);

    private PrivateHireCarDetailsPage privateHireCarDetailsPage;
    private PrivateHireSearchResultsPage privateHireSearchResultsPage;
    private PrivateHireSearchSettingsPage privateHireSearchSettingsPage;
    private SignUpStepOnePage signUpStepOnePage;
    private SignUpStepTwoPage signUpStepTwoPage;
    private SignUpStepThreePage signUpStepThreePage;
    private SignUpResultsPage signUpResultsPage;
    private StartPage startPage;

    private TestData testData;

    private class TestData extends BaseTestData {

        protected String firstName;
        protected String lastName;
        protected int birthdateDay;
        protected int birthdateMonth;
        protected int birthdateYear;
        protected String phoneNumber;
        protected String email;
        protected String password;
        protected String postCode;
        protected String licenseFilePath;

        protected String carManufacturer;
        protected String carModel;
        protected String carNumber;
    }

    @BeforeClass
    public void setUpTest() throws FileNotFoundException {

        log.info("Setting up test.");

        privateHireCarDetailsPage = new PrivateHireCarDetailsPage(testUtils);
        privateHireSearchResultsPage = new PrivateHireSearchResultsPage(testUtils);
        privateHireSearchSettingsPage = new PrivateHireSearchSettingsPage(testUtils);
        signUpStepOnePage = new SignUpStepOnePage(testUtils);
        signUpStepTwoPage = new SignUpStepTwoPage(testUtils);
        signUpStepThreePage = new SignUpStepThreePage(testUtils);
        signUpResultsPage = new SignUpResultsPage(testUtils);
        startPage = new StartPage(testUtils);

        testData = (TestData) populateTestData(TestData.class, "input/demoTest.json");
    }

    @BeforeMethod
    public void goToStartPage() {

        log.info("Starting test page.");

        dismissCookiesOverlay(domain);

        driver.get("https://" + domain + "/");
    }

    private void dismissCookiesOverlay(String domain) {

        /*
         * There are at least two different ways to dismiss cookies, but both with a disadvantage:
         *
         * 1) Search for add press the button that allows cookies.
         *    Disadvantage: the test runs in a maximized window but if the window gets shortened, another different
         *    button has to be clicked instead.
         *
         * 2) Add a cookie that states that the user already allowed cookies.
         *    Disadvantage: selenium only adds the cookie after a domain's page has been called. But the cookie
         *    must be defined before the page starts for the overlay to not appear. Then a hack must be done and the
         *    page must be started twice.
         */

        // This will apply method number 2

        log.info("Dismissing cookies overlay.");

        // TODO: Change this page for a faster loading page, like a 404 result page
        driver.get("https://" + domain + "/");

        addCookies(domain, testData.cookies);
    }

    @Test
    public void run() {

    	try {
			log.info("Starting test.");

			startPage.assertMinimumPageElementsArePresent();
			startPage.clickPCOCarsLink();

			privateHireSearchSettingsPage.assertMinimumPageElementsArePresent();
			privateHireSearchSettingsPage.clickFindYourPCOCarLink();

			privateHireSearchResultsPage.assertMinimumPageElementsArePresent();
			privateHireSearchResultsPage.selectCar(testData.carManufacturer, testData.carModel, testData.carNumber);

			privateHireCarDetailsPage.assertMinimumPageElementsArePresent();
			privateHireCarDetailsPage.clickOnSignUpButton();

			signUpStepOnePage.assertMinimumPageElementsArePresent();
			signUpStepOnePage.fillBasicDetailsRequiredFields(testData.firstName,
					testData.lastName,
					testData.birthdateDay,
					testData.birthdateMonth,
					testData.birthdateYear,
					testData.phoneNumber,
					testData.email,
					testData.password);
			signUpStepOnePage.acceptTerms();
			signUpStepOnePage.clickCreateAccountButton();

			signUpStepTwoPage.assertMinimumPageElementsArePresent();
			signUpStepTwoPage.fillInsuranceInformation(testData.postCode);
			signUpStepTwoPage.clickNextButton();

			signUpStepThreePage.assertMinimumPageElementsArePresent();
			signUpStepThreePage.uploadDrivingLicence(testData.licenseFilePath);
			signUpStepThreePage.clickDoneButton();

			signUpResultsPage.assertSuccess();

			log.info("Test finished.");

    	} catch(Exception ex) {
        	log.fatal("Test has failed: ", ex);
        	throw ex;
    	}
    }

    @AfterMethod
    public void recordFailure(ITestResult result){

        if(ITestResult.FAILURE == result.getStatus()) {
        	takeScreenshot();
        }
    }

}
