package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Utils;

import java.util.List;

public class PIMPage {
    @FindBy(className = "oxd-button--secondary")
    List <WebElement> button;
    @FindBy(css = "[type=submit]")
    WebElement btnSubmit;
    @FindBy(className = "employee-image")
    WebElement imgProfilePic;
    @FindBy(name="firstName")
    WebElement txtFirstName;
    @FindBy(name = "lastName")
    WebElement txtLastName;
    @FindBy(className = "oxd-switch-input")
    WebElement btnToggle;
    @FindBy(tagName = "input")
    List <WebElement> txtInput;



    public PIMPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
    public void createEmployee(String firstName, String lastName, String username, String password) throws InterruptedException {
        button.get(1).click();
       // Utils.waitForElement(driver, imgProfilePic,50);
        txtFirstName.sendKeys(firstName);
        txtLastName.sendKeys(lastName);
        Thread.sleep(3000);
        btnToggle.click();
        txtInput.get(7).sendKeys(username); //input username
        txtInput.get(10).sendKeys(password); //input password
        txtInput.get(11).sendKeys(password); //confirm password
        btnSubmit.click();
    }
}
