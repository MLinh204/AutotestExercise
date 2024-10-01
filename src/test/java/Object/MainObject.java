package Object;

import Function.MainFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainObject {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainFunction function;

    public MainObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.function = new MainFunction(driver);
    }

    // Home page methods
    public boolean isHomePageVisible() {
        return findNavBtnByText(" Home").isDisplayed();
    }

    public void clickSignupLoginButton() {
        findNavBtnByText(" Signup / Login").click();
    }

    public WebElement findNavBtnByText(String text) {
        String xpath = String.format("//ul[@class='nav navbar-nav']//a[text()='%s']", text);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public String getLoggedInUserName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li//a[i[@class='fa fa-user']]"))).getText();
    }

    public void deleteAccount() {
        driver.findElement(By.xpath("//li//a[i[@class='fa fa-trash-o']]")).click();
    }

    // Signup/Login methods
    public boolean isNewUserSignupVisible() {
        return getSignupForm().findElement(By.xpath(".//h2")).getText().equals("New User Signup!");
    }

    public void performSignup(String name, String email) {
        signupNameInput().sendKeys(name);
        signupEmailInput().sendKeys(email);
        signupBtn().click();
    }

    public WebElement getSignupForm() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='signup-form']")));
    }

    private WebElement signupNameInput() {
        WebElement signupForm = getSignupForm();
        return signupForm.findElement(By.xpath(".//input[@name='name']"));
    }

    private WebElement signupEmailInput() {
        WebElement signupForm = getSignupForm();
        return signupForm.findElement(By.xpath(".//input[@name='email']"));
    }

    private WebElement signupBtn() {
        WebElement signupForm = getSignupForm();
        return signupForm.findElement(By.xpath(".//button"));
    }

    public String getAccountInformationTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Enter Account Information']"))).getText();
    }

    public void fillAccountDetails(String name, String password, String title, String firstName, String lastName,
                                   String company, String address1, String address2, String country,
                                   String state, String city, String zipcode, String mobileNumber) {
        selectTitleByName(title).click();
        findAddressFieldById("name").clear();
        findAddressFieldById("name").sendKeys(name);
        findAddressFieldById("password").sendKeys(password);

        selectDateOfBirth("1", "2", "3");  // Example values, adjust as needed

        checkboxById("newsletter").click();
        checkboxById("optin").click();

        findAddressFieldById("first_name").sendKeys(firstName);
        findAddressFieldById("last_name").sendKeys(lastName);
        findAddressFieldById("company").sendKeys(company);
        findAddressFieldById("address1").sendKeys(address1);
        findAddressFieldById("address2").sendKeys(address2);

        Select selectCountry = new Select(countryOption());
        selectCountry.selectByValue(country);

        findAddressFieldById("state").sendKeys(state);
        findAddressFieldById("city").sendKeys(city);
        findAddressFieldById("zipcode").sendKeys(zipcode);
        findAddressFieldById("mobile_number").sendKeys(mobileNumber);

        createAccountBtn().click();
    }

    private void selectDateOfBirth(String day, String month, String year) {
        new Select(dateOptionsById("days")).selectByValue(day);
        new Select(dateOptionsById("months")).selectByValue(month);
        new Select(dateOptionsById("years")).selectByValue(year);
    }

    public boolean isAccountCreatedVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Created!']"))).isDisplayed();
    }

    public void continueAfterAccountCreation() {
        driver.findElement(By.xpath("//a[text()='Continue']")).click();
    }

    public boolean isAccountDeletedVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Account Deleted!']"))).isDisplayed();
    }

    private WebElement selectTitleByName(String index) {
        String xpath = String.format("//input[@id='id_gender%s']", index);
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement dateOptionsById(String id) {
        String xpath = String.format("//select[@id='%s']", id);
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement checkboxById(String id) {
        String xpath = String.format("//input[@id='%s']", id);
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement findAddressFieldById(String id) {
        String xpath = String.format("//input[@id='%s']", id);
        WebElement field = driver.findElement(By.xpath(xpath));
        function.scrollIntoView(field);
        return field;
    }

    private WebElement countryOption() {
        WebElement dropdown = driver.findElement(By.xpath("//select[@id='country']"));
        function.scrollIntoView(dropdown);
        return dropdown;
    }

    private WebElement createAccountBtn() {
        WebElement button = driver.findElement(By.xpath("//button[text()='Create Account']"));
        function.scrollIntoView(button);
        return button;
    }
}