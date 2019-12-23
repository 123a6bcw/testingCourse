package ru.hse.testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WebPage {
    static final String URL = "localhost:8080/users";
    private WebDriverWait wait;
    private WebDriver driver;

    public WebPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);

        loadWebElements();
    }

    protected WebElement getElementById(String id) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        return getDriver().findElement(By.id(id));
    }

    protected WebElement getClickableElementById(String id) {
        getWait().until(ExpectedConditions.elementToBeClickable(By.id(id)));
        return getDriver().findElement(By.id(id));
    }

    protected abstract void loadWebElements();

    public WebDriverWait getWait() {
        return wait;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public UsersPage getToUsers() {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Users"))).click();
        return new UsersPage(getDriver());
    }
}
