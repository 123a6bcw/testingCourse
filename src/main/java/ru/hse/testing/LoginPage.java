package ru.hse.testing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends WebPage {
    private WebElement loginTextField;
    private WebElement passwordTextField;
    private WebElement okButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void loadWebElements() {
        loginTextField = getElementById("id_l.L.login");
        passwordTextField = getElementById("id_l.L.password");
        okButton = getElementById("id_l.L.loginButton");
    }

    public UsersPage login() {
        loginTextField.sendKeys("root");
        passwordTextField.sendKeys("root");
        okButton.click();
        return new UsersPage(getDriver());
    }
}