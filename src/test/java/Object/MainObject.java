package Object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainObject {
    private WebDriver driver;
    private WebDriverWait wait;

    public MainObject(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    //Home element
    public WebElement findNavBtnByText(String text){
        String xpath = String.format("//ul [@class = 'nav navbar-nav']//a[text()='%s']", text);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    //Signup/Login element
    public WebElement getSignupForm(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='signup-form']")));
    }
    public WebElement signupNameInput(){
        WebElement signupForm = getSignupForm();
        return signupForm.findElement(By.xpath(".//input[@name='name']"));
    }
    public WebElement signupEmailInput(){
        WebElement signupForm = getSignupForm();
        return signupForm.findElement(By.xpath(".//input[@name='email']"));
    }
    public WebElement signupBtn(){
        WebElement signupForm = getSignupForm();
        return signupForm.findElement(By.xpath(".//button"));
    }
    public WebElement accountInformationTitle(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[text()='Enter Account Information']")));
    }
    public WebElement addressInformationTitle(){
        return driver.findElement(By.xpath("//b[text()='Enter Account Information']"));
    }
    public WebElement selectTitleByName(String name){
        String xpath = String.format("//div[@class='clearfix']//label[text()='%s']//input", name);
        return driver.findElement(By.xpath(xpath));
    }
    public WebElement dateOptionsById(String id){
        String xpath = String.format("//select[@id = '%s']", id);
        return driver.findElement(By.xpath(xpath));
    }
    public WebElement checkboxById(String id){
        String xpath = String.format("//input[@id = '%s']", id);
        return driver.findElement(By.xpath(xpath));
    }
}
