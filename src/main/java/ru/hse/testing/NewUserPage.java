package ru.hse.testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewUserPage extends WebPage {
    private WebElement loginTextField;
    private WebElement passwordTextField;
    private WebElement confirmPasswordTextField;
    private WebElement okButton;
    private WebElement cancelButton;


    public NewUserPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void loadWebElements() {
        loginTextField = getElementById("id_l.U.cr.login");
        passwordTextField = getElementById("id_l.U.cr.password");
        confirmPasswordTextField = getElementById("id_l.U.cr.confirmPassword");
        okButton = getElementById("id_l.U.cr.createUserOk");
        cancelButton = getElementById("id_l.U.cr.createUserCancel");
    }

    public void createUser() {
        okButton.click();
        getWait().until(ExpectedConditions.or(
                ExpectedConditions.urlContains("editUser"),
                ExpectedConditions.visibilityOfElementLocated(By.className("error-bulb2")),
                ExpectedConditions.visibilityOfElementLocated(By.className("errorSeverity"))
        ));
    }

    public void cancel() {
        cancelButton.click();
    }

    public void setTextFields(String login, String password) {
        loginTextField.sendKeys(login);
        passwordTextField.sendKeys(password);
        confirmPasswordTextField.sendKeys(password);
    }
}
