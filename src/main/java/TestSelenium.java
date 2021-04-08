import com.google.common.base.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSelenium {

    private static final String SUBJECT = "Test";
    private static final String BODY = "Test body Test body Test body Test body Test body Test body Test body";
    private static final String EMAIL = "tarabukintestoviy@gmail.com";
    private static final String PASSWORD = "password123qwert";

    private static final String WELCOME_TEXT = "Добро пожаловать!";
    private static final String NEW_MAIL_TEXT = "Новое сообщение";
    private static final String SEND_BUTTON_TEXT = "Отправить \u202A(Ctrl + Enter)\u202C";
    private static final String MAIL_SENT_TEXT = "Письмо отправлено.";
    private static final String MAIL_DELETED_TEXT = "Цепочка помещена в корзину.";

    private static final String WRONG_EMAIL_ERROR = "Не удалось найти аккаунт Google.";
    private static final String WRONG_PASS_ERROR = "Неверный пароль. Повторите попытку или нажмите на ссылку \"Забыли пароль?\", чтобы сбросить его.";

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\apps\\chromedriver.exe");
        Tester tester = new Tester();
        tester.start();
    }

    private static class Tester {

        private void start() {
            ChromeDriver driver = new ChromeDriver();
            System.out.println("=====================OPEN PAGE CHECK======================");
            boolean open = openPage(driver);
            if (!open) {
                driver.close();
                return;
            }

            System.out.println("====================LOGIN CHECK=========================");
            boolean login = login(driver);
            if (!login) {
                driver.close();
                return;
            }

            System.out.println("=====================SEND MAIL CHECK=========================");
            if (!sendMailCheck(driver)) {
                driver.close();
                return;
            }

            System.out.println("======================OPEN MAIL CHECK=======================");
            if (!openMail(driver)) {
                driver.close();
                return;
            }

            System.out.println("=======================RESPOND CHECK=========================");
            if (!respond(driver)) {
                driver.close();
                return;
            }

            System.out.println("=======================DELETE MAIL CHECK====================");
            if (!deleteMessage(driver)) {
                driver.close();
                return;
            }

            System.out.println("=======================LOGOUT CHECK=====================");
            logout(driver);
        }

        private boolean openPage(ChromeDriver driver) {
            driver.get("https://mail.google.com/");
            if (!Strings.isNullOrEmpty(driver.getTitle()) && driver.getTitle().equals("Gmail")) {
                System.out.println("Page open - check");
                return true;
            } else {
                System.out.println("Page open - failed");
                return false;
            }
        }

        private boolean login(ChromeDriver driver) {
            String wrongEmail = "geih135135ihertihi@gmail.com";
            WebElement element = driver.findElement(By.xpath("//input[@name='identifier']"));
            element.sendKeys(wrongEmail);
            WebElement button = driver.findElement(By.cssSelector("button[jsname='LgbsSe']"));
            button.click();
            sleep05Sec();
            if (driver.getPageSource().contains(WRONG_EMAIL_ERROR)) {
                System.out.println("Wrong email error (" + wrongEmail + ") - Check");
            }
            element.clear();
            element.sendKeys(EMAIL);
            button.click();
            sleep05Sec();
            if (driver.getPageSource().contains(WELCOME_TEXT)) {
                System.out.println("Correct email (" + EMAIL + ") - Check");
            }
            element = driver.findElement(By.xpath("//input[@name='password']"));
            String wrongPassword = "ojojojo";
            element.sendKeys(wrongPassword);
            sleep05Sec();
            WebElement button1 = driver.findElement(By.cssSelector("button[jsname='LgbsSe']"));
            button1.click();
            sleep05Sec();
            if (driver.getPageSource().contains(WRONG_PASS_ERROR)) {
                System.out.println("Wrong pass error (" + wrongPassword + ") - Check");
            }
            element.clear();
            element.sendKeys(PASSWORD);
            sleep05Sec();
            button1.click();
            sleep3Sec();
            sleep3Sec();
            if (!Strings.isNullOrEmpty(driver.getTitle()) && !driver.getTitle().equals("Gmail")) {
                System.out.println("Login - check");
                return true;
            } else {
                System.out.println("Login - failed");
                return false;
            }
        }

        private boolean sendMailCheck(ChromeDriver driver) {
            WebElement sendButton = driver.findElement(By.cssSelector("div[jscontroller='eIu7Db']"));
            sendButton.click();
            sleep2Sec();
            if (driver.getPageSource().contains(NEW_MAIL_TEXT)) {
                System.out.println("Send mail window open - check");
            } else {
                System.out.println("Send mail window open - failed");
                return false;
            }
            WebElement toField = driver.findElement(By.cssSelector("textarea[name='to']"));
            WebElement subjectField = driver.findElement(By.cssSelector("input[name='subjectbox']"));
            WebElement bodyField = driver.findElement(By.cssSelector("div[aria-label='Тело письма']"));
            toField.sendKeys(EMAIL);
            subjectField.sendKeys(SUBJECT);
            bodyField.sendKeys(BODY);
            WebElement sendConfirmButton = driver.findElement(By.cssSelector("div[class='T-I J-J5-Ji aoO v7 T-I-atl L3']"));
            sendConfirmButton.click();
            sleep2Sec();
            if (driver.getPageSource().contains(MAIL_SENT_TEXT)) {
                System.out.println("Mail sent - check");
                return true;
            } else {
                System.out.println("Mail sent - failed");
                return false;
            }
        }

        private boolean openMail(ChromeDriver driver) {
            sleep3Sec();
            WebElement mailSpan = driver.findElement(By.xpath("//*[text()='" + SUBJECT + "']"));
            clickUntilItIsDone(mailSpan);
            sleep1Sec();
            if (driver.getTitle().contains(SUBJECT)) {
                System.out.println("Open mail - check");
                return true;
            } else {
                System.out.println("Open mail - failed");
                return false;
            }
        }

        private void clickUntilItIsDone(WebElement element) {
            try {
                element.click();
            } catch (ElementNotInteractableException ex) {
                clickUntilItIsDone(element.findElement(By.xpath("./..")));
            }
        }

        private boolean respond(ChromeDriver driver) {
            WebElement respondButton = driver.findElement(By.cssSelector("span[class='ams bkH']"));
            respondButton.click();
            sleep05Sec();
            try {
                WebElement body = driver.findElement(By.cssSelector("div[aria-label='Тело письма']"));
                body.sendKeys(BODY);
            } catch (Exception ex) {
                System.out.println("Open respond field - failed");
                return false;
            }
            System.out.println("Open respond field - check");
            WebElement sendButton = driver.findElement(By.cssSelector("div[class='T-I J-J5-Ji aoO v7 T-I-atl L3']"));
            sendButton.click();
            sleep2Sec();
            if (driver.getPageSource().contains(MAIL_SENT_TEXT)) {
                System.out.println("Response sent - check");
                return true;
            } else {
                System.out.println("Response sent - failed");
                return false;
            }
        }

        private boolean deleteMessage(ChromeDriver driver) {
            WebElement deleteButton = driver.findElement(By.cssSelector("div[jslog='20283; u014N:cOuCgd,Kr2w4b']"));
            clickUntilItIsDone(deleteButton);
            sleep2Sec();
            if (driver.getPageSource().contains(MAIL_DELETED_TEXT)) {
                System.out.println("Mail delete - check");
                return true;
            } else {
                System.out.println("Mail delete - failed");
                return false;
            }
        }

        private boolean logout(ChromeDriver driver) {
            WebElement avatar = driver.findElement(By.cssSelector("img[class='gb_Da gbii']"));
            clickUntilItIsDone(avatar);
            sleep05Sec();
            try {
                WebElement logoutButton = driver.findElement(By.id("gb_71"));
                System.out.println("Logout menu open - check");
                logoutButton.click();
            } catch (NoSuchElementException ex) {
                System.out.println("Logout menu open - failed");
            }
            sleep3Sec();
            if (driver.getTitle().equals("Gmail")) {
                System.out.println("Logout - check");
                return true;
            } else {
                System.out.println("Logout - failed");
                return false;
            }
        }

        private void sleep05Sec() {
            try {
                Thread.sleep(500L);
            } catch (Exception ex) {
                throw new IllegalStateException(ex.getMessage());
            }
        }

        private void sleep1Sec() {
            try {
                Thread.sleep(1000L);
            } catch (Exception ex) {
                throw new IllegalStateException(ex.getMessage());
            }
        }

        private void sleep2Sec() {
            sleep1Sec();
            sleep1Sec();
        }

        private void sleep3Sec() {
            sleep1Sec();
            sleep2Sec();
        }
    }
}
