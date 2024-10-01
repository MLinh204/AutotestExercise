package Test;

import Config.ConfigProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import Object.MainObject;

import java.time.Duration;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class Test {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainObject mainObject;

    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        this.driver = new ChromeDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.mainObject = new MainObject(driver);
        driver.manage().window().maximize();
    }
    @AfterClass
    public void close(){
        if (driver!=null){
            driver.quit();
        }
    }
    //Test case 1
    @org.testng.annotations.Test
    public void TC1(){
        //Step 1: Navigate to website
        driver.get(ConfigProperties.BASE_URL);
        //Step 2: Verify home page is visible
        WebElement navHomeText = mainObject.findNavBtnByText(" Home");
        assertTrue(navHomeText.isDisplayed());
        //Step 3: Click Signup/Login button
        WebElement signupLoginBtn = mainObject.findNavBtnByText(" Signup / Login");
        signupLoginBtn.click();
        //Step 4: Verify 'New User Signup!' is visible
        WebElement signupForm = mainObject.getSignupForm();
        assertEquals("New User Signup!", signupForm.findElement(By.xpath(".//h2")).getText());
        //Step 5: Enter name and email address
        mainObject.signupNameInput().sendKeys("Test");
        mainObject.signupEmailInput().sendKeys("Test@a.com");
        //Step 6: Click signup button
        mainObject.signupBtn().click();
        //Step 7: Verify that 'ENTER ACCOUNT INFORMATION' is visible
        assertEquals("ENTER ACCOUNT INFORMATION", mainObject.accountInformationTitle().getText());
        //Step 8: Fill details: Title, Name, Email, Password, Date of birth
        mainObject.selectTitleByName("Mr. ").click();
        WebElement nameField = driver.findElement(By.xpath("//input[@id = 'name']"));
        nameField.clear();
        nameField.sendKeys("Test");
        WebElement passwordField = driver.findElement(By.xpath("//input[@id = 'password']"));
        passwordField.sendKeys("Test123");
        Select selectDay = new Select(mainObject.dateOptionsById("days"));
        Select selectMonth = new Select(mainObject.dateOptionsById("months"));
        Select selectYear = new Select(mainObject.dateOptionsById("years"));
        selectDay.selectByIndex(1);
        selectMonth.selectByIndex(2);
        selectYear.selectByIndex(3);
        //Step 9: Select checkbox 'Sign up for our newsletter!'
        mainObject.checkboxById("newsletter").click();
    }

}
