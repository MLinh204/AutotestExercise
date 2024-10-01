package Test;

import Config.ConfigProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import Object.MainObject;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Logger;

public class Test {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainObject mainObject;
    private SoftAssert softAssert;
    private static final Logger logger = Logger.getLogger(Test.class.getName());

    private static final String TEST_NAME = "Test";
    private static final String TEST_EMAIL = "Test7@a.com";
    private static final String TEST_PASSWORD = "Test123";

    @BeforeClass
    public void setup() {
        logger.info("Setting up the test environment");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.mainObject = new MainObject(driver);
        this.softAssert = new SoftAssert();
        driver.manage().window().maximize();
        logger.info("Test environment set up completed");
    }

    @AfterClass
    public void close() {
        logger.info("Closing the test environment");
        if (driver != null) {
            driver.quit();
        }
        logger.info("Test environment closed");
    }

    @org.testng.annotations.Test
    public void testUserRegistration() {
        try {
            logger.info("Starting user registration test");

            // Step 1: Navigate to website
            logger.info("Step 1: Navigating to website");
            driver.get(ConfigProperties.BASE_URL);

            // Step 2: Verify home page is visible
            logger.info("Step 2: Verifying home page is visible");
            softAssert.assertTrue(mainObject.isHomePageVisible(), "Home page should be visible");

            // Step 3: Click Signup/Login button
            logger.info("Step 3: Clicking Signup/Login button");
            mainObject.clickSignupLoginButton();

            // Step 4: Verify 'New User Signup!' is visible
            logger.info("Step 4: Verifying 'New User Signup!' is visible");
            softAssert.assertTrue(mainObject.isNewUserSignupVisible(), "'New User Signup!' should be visible");

            // Step 5 & 6: Enter name and email address, then click signup
            logger.info("Step 5 & 6: Entering name and email address, then clicking signup");
            mainObject.performSignup(TEST_NAME, TEST_EMAIL);

            // Step 7: Verify that 'ENTER ACCOUNT INFORMATION' is visible
            logger.info("Step 7: Verifying 'ENTER ACCOUNT INFORMATION' is visible");
            softAssert.assertEquals(mainObject.getAccountInformationTitle(), "ENTER ACCOUNT INFORMATION",
                    "'ENTER ACCOUNT INFORMATION' should be visible");

            // Step 8-12: Fill in account details and create account
            logger.info("Step 8-12: Filling in account details and creating account");
            mainObject.fillAccountDetails(TEST_NAME, TEST_PASSWORD, "1", "Johan", "Test", "Test Company",
                    "Youtube, Facebook", "Instagram, Pinterest", "1", "Test state",
                    "Test city", "123123", "5554443333");

            // Step 13: Verify that 'ACCOUNT CREATED!' is visible
            logger.info("Step 13: Verifying 'ACCOUNT CREATED!' is visible");
            softAssert.assertTrue(mainObject.isAccountCreatedVisible(), "'ACCOUNT CREATED!' should be visible");

            logger.info("Continuing after account creation");
            mainObject.continueAfterAccountCreation();

            // Step 14: Verify that 'Logged in as username' is visible
            logger.info("Step 14: Verifying 'Logged in as username' is visible");
            softAssert.assertEquals(mainObject.getLoggedInUserName(), "Logged in as " + TEST_NAME,
                    "User should be logged in with correct name");

            // Step 15: Click 'Delete Account' button
            logger.info("Step 15: Clicking 'Delete Account' button");
            mainObject.deleteAccount();

            // Step 16: Verify that 'ACCOUNT DELETED!' is visible
            logger.info("Step 16: Verifying 'ACCOUNT DELETED!' is visible");
            softAssert.assertTrue(mainObject.isAccountDeletedVisible(), "'ACCOUNT DELETED!' should be visible");

            logger.info("All test steps completed");
            softAssert.assertAll();
        } catch (Exception e) {
            logger.severe("Test failed with exception: " + e.getMessage());
            softAssert.fail("Test failed with exception: " + e.getMessage());
        }
    }
}