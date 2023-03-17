package testrunner;

import com.github.javafaker.Faker;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

public class PIMTestRunner extends Setup {
    DashboardPage dashboardPage;
    LoginPage loginPage;
    PIMPage pimPage;
    @BeforeTest
    public void doLogin() throws IOException, ParseException {
        loginPage=new LoginPage(driver);
        JSONObject userObject= Utils.loadJSONFile("./src/test/resources/User.json");
        String username= (String) userObject.get("username");
        String password= (String) userObject.get("password");
        loginPage.doLogin(username, password);
    }
    @Test(priority = 2, description = "user can view existing employ list")
    public void searchEmployeeInfo() throws InterruptedException {
        dashboardPage=new DashboardPage(driver);
        Thread.sleep(3000);
        dashboardPage.menus.get(1).click(); //Click on PIM menu
        Thread.sleep(3000);
        String isUserFound= driver.findElements(By.className("oxd-text--span")).get(11).getText();
        Assert.assertTrue(isUserFound.contains("Records Found"));

    }
    @Test(priority = 3, description = "user can search employee by employee status")
    public void selectEmploymentStatus() throws InterruptedException {
        dashboardPage=new DashboardPage(driver);
        dashboardPage.dropdown.get(0).click();
        dashboardPage.dropdown.get(0).sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(1000);
        dashboardPage.dropdown.get(0).sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(1000);
        dashboardPage.dropdown.get(0).sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("[type=submit]")).click();
        Utils.doScroll(driver, 200);

    }
    @Test(priority = 4, description = "Create new employee")
    public void addEmployee() throws InterruptedException, IOException, ParseException {
        pimPage=new PIMPage(driver);
        Faker faker=new Faker();
        String firstName=faker.name().firstName();
        String lastName=faker.name().lastName();
        String username="Test"+Utils.generateRandomNumber(1000,9999);
        String password="P@ssword123";
        pimPage.createEmployee(firstName,lastName,username,password);

        Thread.sleep(5000);
        String header_actual=driver.findElement(By.className("orangehrm-main-title")).getText();
        String header_expected="Personal Details";
        Assert.assertTrue(header_actual.contains(header_expected));

        Utils.addJsonArray(firstName,lastName, username,password);

    }
}
