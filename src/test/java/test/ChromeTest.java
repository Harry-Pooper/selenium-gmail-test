package test;

import org.junit.BeforeClass;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Chrome engine test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChromeTest {

    private static Tester tester;

    private boolean failed = false;

    @BeforeAll
    public static void setUpChromeDriver() {
        tester = new Tester();
        tester.init();
    }

    @Test
    public void test() {
        try {
            try {
                boolean openPage = tester.openPage();
                if (!openPage) {
                    fail("openPage failed");
                }
                System.out.println("openPage success");
            } catch (Exception ex) {
                tester.takeScreenshot();
                fail("openPage failed unexpectedly\n" + ex.getMessage());
            }
            try {
                boolean login = tester.login();
                if (!login) {
                    fail("Login failed");
                }
                System.out.println("Login success");
            } catch (Exception ex) {
                tester.takeScreenshot();
                fail("Login failed unexpectedly\n" + ex.getMessage());
            }
            try {
                boolean sendMail = tester.sendMailCheck();
                if (!sendMail) {
                    fail("Send mail failed");
                }
                System.out.println("Send mail success");
            } catch (Exception ex) {
                tester.takeScreenshot();
                fail("Send mail failed unexpectedly\n" + ex.getMessage());
            }
            try {
                boolean openMail = tester.openMail();
                if (!openMail) {
                    fail("Open mail failed");
                }
                System.out.println("Open mail success");
            } catch (Exception ex) {
                tester.takeScreenshot();
                fail("Open mail failed unexpectedly\n" + ex.getMessage());
            }
        } finally {
            tester.getDriver().quit();
        }
    }
}
