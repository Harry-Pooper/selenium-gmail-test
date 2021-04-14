package test;

import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class Tester {


    private static final String SUBJECT = "Test";
    private static final String BODY = "Test body Test body Test body Test body Test body Test body Test body";
    private static final String EMAIL = "tarabukintestoviy@gmail.com";
    private static final String PASSWORD = "password123qwert";

    private static final String WELCOME_TEXT = "Добро пожаловать!";
    private static final String NEW_MAIL_TEXT = "Новое сообщение";
    private static final String MAIL_SENT_TEXT = "Письмо отправлено.";
    private static final String MAIL_DELETED_TEXT = "Цепочка помещена в корзину.";

    private static final String WRONG_EMAIL_ERROR = "Не удалось найти аккаунт Google.";
    private static final String WRONG_PASS_ERROR = "Неверный пароль. Повторите попытку или нажмите на ссылку \"Забыли пароль?\", чтобы сбросить его.";

    private ChromeDriver driver;

    public Tester() {
    }

    public ChromeDriver getDriver() {
        return driver;
    }

    public void init() {
        driver = new ChromeDriver(new ChromeCustomBuilder().build());
    }

    public boolean openPage() {
        driver.get("https://mail.google.com/");
        if (!Strings.isNullOrEmpty(driver.getTitle()) && driver.getTitle().equals("Gmail")) {
            return true;
        } else {
            takeScreenshot();
            return false;
        }
    }

    public boolean login() {
        WebElement element = driver.findElement(By.cssSelector("input[name='identifier']"));
        element.sendKeys(EMAIL);
        WebElement button = driver.findElement(By.cssSelector("button[jsname='LgbsSe']"));
        button.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(driver -> (driver.findElements(By.cssSelector("div[id='initialView'][aria-busy='true']")).size() == 0));
        if (!driver.getPageSource().contains(WELCOME_TEXT)) {
            takeScreenshot();
            return false;
        }
        element = driver.findElement(By.cssSelector("input[name='password']"));
        element.sendKeys(PASSWORD);
        WebElement button1 = driver.findElement(By.cssSelector("button[jsname='LgbsSe']"));
        button1.click();
        wait.until(driver -> (driver.findElements(By.cssSelector("div[id='initialView'][aria-busy='true']")).size() == 0
                || !driver.getTitle().equals("Gmail")));
        if (!Strings.isNullOrEmpty(driver.getTitle()) && !driver.getTitle().equals("Gmail")) {
            return true;
        } else {
            takeScreenshot();
            return false;
        }
    }

    public boolean sendMailCheck() {
        WebElement sendButton = driver.findElement(By.cssSelector("div[role='button'][jscontroller='eIu7Db']"));
        sendButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(driver -> driver.getPageSource().contains(NEW_MAIL_TEXT));
        WebElement toField = driver.findElement(By.cssSelector("textarea[name='to']"));
        WebElement subjectField = driver.findElement(By.cssSelector("input[name='subjectbox']"));
        WebElement bodyField = driver.findElement(By.cssSelector("div[aria-label='Тело письма']"));
        toField.sendKeys(EMAIL);
        subjectField.sendKeys(SUBJECT);
        bodyField.sendKeys(BODY);
        WebElement sendConfirmButton = driver.findElement(By.cssSelector("div[role='button'][class='T-I J-J5-Ji aoO v7 T-I-atl L3']"));
        sendConfirmButton.click();
        WebDriverWait wait1 = new WebDriverWait(driver, 3);
        wait1.until(driver -> driver.getPageSource().contains(MAIL_SENT_TEXT));
        if (driver.getPageSource().contains(MAIL_SENT_TEXT)) {
            return true;
        } else {
            takeScreenshot();
            return false;
        }
    }

    public boolean openMail() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(driver -> driver.getPageSource().contains(SUBJECT));
        WebElement mailSpan = driver.findElement(By.xpath("//*[text()='" + SUBJECT + "']"));
        clickUntilItIsDone(mailSpan);
        WebDriverWait wait1 = new WebDriverWait(driver, 3);
        wait.until(driver -> driver.findElement(By.cssSelector("div[jslog='20283; u014N:cOuCgd,Kr2w4b']")));
        if (driver.getTitle().contains(SUBJECT)) {
            return true;
        } else {
            takeScreenshot();
            return false;
        }
    }

    public void clickUntilItIsDone(WebElement element) {
        try {
            element.click();
        } catch (ElementNotInteractableException ex) {
            clickUntilItIsDone(element.findElement(By.xpath("./..")));
        }
    }

    public boolean respond() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement respondButton = driver.findElement(By.cssSelector("span[class='ams bkH']"));
        respondButton.click();
        wait.until(driver -> driver.findElement(By.cssSelector("div[class='Am aO9 Al editable LW-avf tS-tW'][role='textbox']")));
        try {
            WebElement body = driver.findElement(By.cssSelector("div[class='Am aO9 Al editable LW-avf tS-tW'][role='textbox']"));
            body.sendKeys(BODY);
        } catch (Exception ex) {
            takeScreenshot();
            return false;
        }
        WebElement sendButton = driver.findElement(By.cssSelector("div[class='T-I J-J5-Ji aoO v7 T-I-atl L3']"));
        sendButton.click();
        wait.until(driver -> driver.getPageSource().contains(MAIL_SENT_TEXT));
        if (driver.getPageSource().contains(MAIL_SENT_TEXT)) {
            return true;
        } else {
            takeScreenshot();
            return false;
        }
    }

    public boolean deleteMessage() {
        WebElement deleteButton = driver.findElement(By.cssSelector("div[jslog='20283; u014N:cOuCgd,Kr2w4b']"));
        clickUntilItIsDone(deleteButton);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(driver -> driver.getPageSource().contains(MAIL_DELETED_TEXT));
        if (driver.getPageSource().contains(MAIL_DELETED_TEXT)) {
            return true;
        } else {
            takeScreenshot();
            return false;
        }
    }

    public boolean logout() {
        WebElement avatar = driver.findElement(By.cssSelector("img[class='gb_Da gbii']"));
        clickUntilItIsDone(avatar);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(driver -> driver.findElement(By.id("gb_71")));
        try {
            WebElement logoutButton = driver.findElement(By.id("gb_71"));
            logoutButton.click();
        } catch (NoSuchElementException ex) {
            takeScreenshot();
            return false;
        }
        wait.until(driver -> driver.getTitle().equals("Gmail"));
        if (driver.getTitle().equals("Gmail")) {
            return true;
        } else {
            takeScreenshot();
            return false;
        }
    }

    private void takeScreenshot() {
        TakesScreenshot prntScr = (TakesScreenshot) driver;
        File scrFile = prntScr.getScreenshotAs(OutputType.FILE);
        File curDirFile = new File(".\\screenshot\\error.png");
        try {
            FileUtils.copyFile(scrFile, curDirFile);
        } catch (IOException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }
}
