package ru.hse.testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserTest {
    private static final int MAX_LENGTH = 50;

    private WebDriver driver;
    private UsersPage usersPage;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
    }

    @BeforeEach
    void setUp() {
        driver = Main.start();
        usersPage = new LoginPage(driver).login();

        for (String userName : usersPage.getUsersNames()) {
            if (!userName.equals("guest") && !userName.equals("root")) {
                usersPage.deleteUser(userName);
            }
        }

        usersPage.refreshPage();
    }

    @AfterEach
    void tearDown() {
        driver.close();
    }

    @Test
    void correctUserNameAndLoginCreatesUser() {
        testCorrectUser("user");
    }

    @Test
    void allowedSpecialSymbolsInLoginAreAllowed() {
        testCorrectUser("_user_");
        testCorrectUser("123467890!@#$%^&*(){}\"';.,");
    }

    @Test
    void sameUserLoginNameShouldGiveAnError() {
        testCorrectUser("user");
    }

    @Test
    void usersWithUndercaseAndUpperCaseAreTheSameUsers() {
        testCorrectUser("User");
        testIncorrectUser("user", "Value should be unique: login", "User and user login should be the same");
    }

    @Test
    void maxLoginName() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MAX_LENGTH; i++) {
            builder.append('a');
        }

        testCorrectUser(builder.toString());
    }

    @Test
    void tooLongNameShouldTruncToMaxLengthSymbols() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MAX_LENGTH; i++) {
            builder.append('b');
        }

        String expectedUserName = builder.toString();

        for (int i = 0; i < 10; i++) {
            builder.append('b' + i);
        }

        usersPage = createUser(builder.toString()).getToUsers();
        usersPage.refreshPage();
        assertNotNull(usersPage.getUser(expectedUserName));
        assertEquals(3, usersPage.getUsers().size());
    }

    @Test
    void emptyLoginGivesError() {
        testIncorrectUser("", null);
    }

    @Test
    void spacesInLoginGivesError() {
        String expectedMessage = "Restricted character ' ' in the name";

        testIncorrectUser(" user", expectedMessage);
        testIncorrectUser("user ", expectedMessage);
    }

    @Test
    void lessThanGreaterThanAndSlashInLoginShouldGiveAnError() {
        String expectedMessage = "login shouldn't contain characters \"<\", \"/\", \">\": login";

        testIncorrectUser("user>", expectedMessage);
        testIncorrectUser(">user", expectedMessage);
        testIncorrectUser("<user", expectedMessage);
        testIncorrectUser("user<", expectedMessage);
        testIncorrectUser("user/", expectedMessage);
        testIncorrectUser("/user", expectedMessage);
    }

    @Test
    public void loginWithOnlyDotShouldGiveAnError() {
        testIncorrectUser(".", "Can't use \"..\", \".\" for login: login");
        testIncorrectUser("..", "Can't use \"..\", \".\" for login: login");
    }

    private void testCorrectUser(String login) {
        usersPage = createUser(login).getToUsers();
        usersPage.refreshPage();
        assertNotNull(usersPage.getUser(login));
    }

    private NewUserPage createUser(String login) {
        NewUserPage newUserPage = usersPage.createUser();
        newUserPage.setTextFields(login, "password");
        newUserPage.createUser();
        return newUserPage;
    }

    private void testIncorrectUser(String login) {
        testIncorrectUser(login, null, "");
    }

    private void testIncorrectUser(String login, String expectedMessage) {
        testIncorrectUser(login, expectedMessage, "");
    }

    private void testIncorrectUser(String login, String expectedMessage, String message) {
        NewUserPage newUserPage = createUser(login);
        if (expectedMessage != null) {
            assertEquals(expectedMessage, usersPage.getErrorMessage());
            newUserPage.cancel();
        }

        assertNull(usersPage.getUser(login));
    }
}