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
    @Order(1)
    @DisplayName("Open gmail")
    public void test1() {
        boolean openPage = tester.openPage();
        if (!openPage) {
            failed = true;
            fail("Open page failed");
        }
    }

    @Test
    @Order(2)
    @DisplayName("login")
    public void test2() {
        if (failed) {
            fail("Previous step failed");
        }
        boolean login = tester.login();
        if (!login) {
            failed = true;
            fail("Login failed");
        }
    }

    @Test
    @Order(3)
    @DisplayName("sendMail")
    public void test3() {
        if (failed) {
            fail("Previous step failed");
        }
        boolean sendMail = tester.sendMailCheck();
        if (!sendMail) {
            failed = true;
            fail("Send mail failed");
        }
    }

    @Test
    @Order(4)
    @DisplayName("openMail")
    public void test4() {
        if (failed) {
            fail("Previous step failed");
        }
        boolean openMail = tester.openMail();
        if (!openMail) {
            failed = true;
            fail("Open mail failed");
        }
    }

    @Test
    @Order(5)
    @DisplayName("respond")
    public void test5() {
        if (failed) {
            fail("Previous step failed");
        }
        boolean respond = tester.respond();
        if (!respond) {
            failed = true;
            fail("Respond failed");
        }
    }

    @Test
    @Order(6)
    @DisplayName("deleteMessage")
    public void test6() {
        if (failed) {
            fail("Previous step failed");
        }
        boolean deleteMessage = tester.deleteMessage();
        if (!deleteMessage) {
            failed = true;
            fail("Delete message failed");
        }
    }

    @Test
    @Order(7)
    @DisplayName("logout")
    public void test7() {
        if (failed) {
            fail("Previous step failed");
        }
        boolean logout = tester.logout();
        if (!logout) {
            fail("Logout failed");
        }
    }
}
