package ru.hse.testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class UsersPage extends WebPage {
    private WebElement createUserButton;

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void loadWebElements() {
        createUserButton = getClickableElementById("id_l.U.createNewUser");
    }

    public List<WebElement> getUsers() {
        getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//a[starts-with(@id, 'id_l.U.usersList.UserLogin.editUser')]")));
        return getDriver().findElements(By.xpath(".//a[starts-with(@id, 'id_l.U.usersList.UserLogin.editUser')]"));
    }

    public List<String> getUsersNames() {
        return getUsers().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public WebElement getUser(String name) {
        for (WebElement user : getUsers()) {
            if (user.getText().equals(name)) {
                return user;
            }
        }

        return null;
    }

    public void refreshPage() {
        getDriver().get(URL);
        loadWebElements();
    }

    public String getErrorMessage() {
        return getDriver().findElement(By.className("errorSeverity")).getText();
    }

    public NewUserPage createUser() {
        loadWebElements();
        createUserButton.click();
        return new NewUserPage(getDriver());
    }

    public void deleteUser(String name) {
        WebElement user = getUser(name);
        user.findElement(By.xpath(".//../../td[6]/a[1]")).click();
        getWait().until(ExpectedConditions.alertIsPresent());
        getDriver().switchTo().alert().accept();
        refreshPage();
    }
}
