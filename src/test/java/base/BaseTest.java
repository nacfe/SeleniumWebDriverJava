package base;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utils.TestUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardCopyOption.*;

public abstract class BaseTest {

	private static Logger log = LogManager.getLogger(BaseTest.class);

	private static long pageLoadTimeout;
	private static long scriptTimeout;
	private static long shortWaitForElement;
	private static long longWaitForElement;
	protected static String domain;

	protected WebDriver driver;
	protected TestUtils testUtils;

	protected abstract class BaseTestData {

		public List<TestCookie> cookies;
	}

	protected class TestCookie {

		protected String name;
		protected String value;
		protected String path;
		protected int validForDays;
	}

	@BeforeTest
	public void setUp() {

		log.info("********** Set up started.");

		log.info("Setting up test variables.");
		setupTestVariables();

		log.info("Setting up web driver.");
		//TODO: put this dynamic to accept more browsers
		System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
		driver = new ChromeDriver();

		driver.manage().timeouts()
				.pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS)
				.setScriptTimeout(scriptTimeout, TimeUnit.SECONDS);

		driver.manage().window().maximize();

		testUtils = new TestUtils(driver, shortWaitForElement, longWaitForElement);

		log.info("Set up finished.");
	}

	@AfterTest
	public void tearDown() {

		log.info("Tear down started.");
		driver.quit();
		log.info("********** Tear down finished.");
	}

	protected void addCookies(String domain, List<TestCookie> cookies) {

		for(TestCookie cookie : cookies) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, cookie.validForDays);
			Date dayInFuture = cal.getTime();

			log.info("Adding cookie with name " + cookie.name + ".");
			driver.manage().addCookie(new Cookie(
					cookie.name,
					cookie.value,
					"." + domain,
					cookie.path,
					dayInFuture));
		}
	}

	protected <T>Object populateTestData(Class klass, String inputFilePath) throws FileNotFoundException {

		log.info("Populating test data.");

		URL fileURL = BaseTest.class.getClassLoader().getResource(inputFilePath);
		if(null == fileURL) {
			String errorMessage = "Error uploading test input file: no file found for file path [" + inputFilePath + "].";
			log.fatal(errorMessage);
			throw new RuntimeException(errorMessage);
		}

		Gson gson = new Gson();
		JsonReader reader =  new JsonReader(new FileReader(fileURL.getPath()));
		return gson.fromJson(reader, klass);
	}

	private void setupTestVariables() {

		pageLoadTimeout = (null != System.getenv("AUTO_TEST_PAGELOAD_TIMEOUT")) ? Long.valueOf(System.getenv("AUTO_TEST_PAGELOAD_TIMEOUT")) : 15;
		log.info("Page load timeout set to " + pageLoadTimeout + " seconds.");

		scriptTimeout = (null != System.getenv("AUTO_TEST_SCRIPT_TIMEOUT")) ? Long.valueOf(System.getenv("AUTO_TEST_SCRIPT_TIMEOUT")) : 10;
		log.info("Script timeout set to " + scriptTimeout + " seconds.");

		shortWaitForElement = (null != System.getenv("AUTO_TEST_SHORT_WAIT")) ? Long.valueOf(System.getenv("AUTO_TEST_SHORT_WAIT")) : 5;
		log.info("Short wait for element set to " + shortWaitForElement + " seconds.");

		longWaitForElement = (null != System.getenv("AUTO_TEST_LONG_WAIT")) ? Long.valueOf(System.getenv("AUTO_TEST_LONG_WAIT")) : 10;
		log.info("Long wait for element set to " + longWaitForElement + " seconds.");

		domain = System.getenv("AUTO_TEST_DOMAIN");
		log.info("Domain set to [" + domain + "].");
	}

	protected void takeScreenshot() {

		log.info("Taking screenshot.");
		TakesScreenshot camera = (TakesScreenshot)driver;
		File screenshot = camera.getScreenshotAs(OutputType.FILE);
		String dateText = testUtils.getCurrentDateAsFormattedText();

		try {
			Files.move(screenshot.toPath(),
					new File("resources/logs/autoTestScreenshot_" + dateText + ".png").toPath(),
					REPLACE_EXISTING);
		} catch (IOException e) {
			log.error("Error while moving screenshot file.");
		}
	}
}
