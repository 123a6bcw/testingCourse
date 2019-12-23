package ru.hse.testing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static WebDriver start() {
        WebDriver driver = new ChromeDriver();
        driver.get("localhost:8080/users");
        return driver;
    }
}