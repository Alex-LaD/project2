package com.example.projet2.tests;

import com.example.projet2.SceneManager;
import com.example.projet2.SceneType;
import com.example.projet2.User;
import com.example.projet2.repository.UserRepository;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;
import org.testfx.api.FxToolkit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@DisplayName("Login Tests")
class SignupTest extends ApplicationTest {

    private static Stage stage;
    private static SceneManager sceneManager;

    @BeforeAll
    static void bootToolkit() throws Exception {
        stage = FxToolkit.registerPrimaryStage();
        SceneManager.init(stage);
        sceneManager = SceneManager.getInstance();
    }

    @BeforeEach
    void setup() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                System.setProperty("app.db.url", "jdbc:sqlite::memory");
                sceneManager.uncacheAll();
                sceneManager.navigateTo(SceneType.SIGNUP);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(15, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timed out waiting for FX thread");
        }
    }

    @Test
    @DisplayName("Test Username Already Exists")
    void usernameAlreadyExists() {
        int usernameNumber = 0;
        String usernameBase = "already took your username";
        try {
            while (UserRepository.getUserByUsername(usernameBase + usernameNumber) != null) {
                usernameNumber++;
            }

            String username = usernameBase + usernameNumber;
            String password = "password1!";

            UserRepository.insertUser(new User(-1, username, password));

            enterFields(username, password, password);

            Scene currentScene = stage.getScene();
            clickOn("#signupButton");
            UserRepository.deleteUserByUsername(username);
            assertEquals(currentScene, stage.getScene());

        } catch (Exception e) {
            UserRepository.deleteUserByUsername(usernameBase + usernameNumber);
            e.printStackTrace();
            // Test failed if it ended up in the catch block
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Test Username Too Short")
    void usernameTooShort() {
        try {
            enterFields("67", "baller1!", "baller1!");

            Scene currentScene = stage.getScene();
            clickOn("#signupButton");
            UserRepository.deleteUserByUsername("12");
            assertEquals(currentScene, stage.getScene());

        } catch (Exception e) {
            UserRepository.deleteUserByUsername("12");
            e.printStackTrace();
            // Test failed if it ended up in the catch block
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Test Password Too Short")
    void passwordTooShort() {
        int usernameNumber = 0;
        String usernameBase = "too short";
        try {
            while (UserRepository.getUserByUsername(usernameBase + usernameNumber) != null) {
                usernameNumber++;
            }

            String username = usernameBase + usernameNumber;
            String password = "Sword1!";

            enterFields(username, password, password);

            Scene currentScene = stage.getScene();
            clickOn("#signupButton");
            UserRepository.deleteUserByUsername(username);
            assertEquals(currentScene, stage.getScene());

        } catch (Exception e) {
            UserRepository.deleteUserByUsername(usernameBase + usernameNumber);
            e.printStackTrace();
            // Test failed if it ended up in the catch block
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Test Password Doesn't Contain Digit")
    void passwordDoesNotContainDigit() {
        int usernameNumber = 0;
        String usernameBase = "numero no";
        try {
            while (UserRepository.getUserByUsername(usernameBase + usernameNumber) != null) {
                usernameNumber++;
            }

            String username = usernameBase + usernameNumber;
            String password = "password!";

            enterFields(username, password, password);

            Scene currentScene = stage.getScene();
            clickOn("#signupButton");
            UserRepository.deleteUserByUsername(username);
            assertEquals(currentScene, stage.getScene());

        } catch (Exception e) {
            UserRepository.deleteUserByUsername(usernameBase + usernameNumber);
            e.printStackTrace();
            // Test failed if it ended up in the catch block
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Test Password Doesn't Contain Special Character")
    void passwordDoesNotContainSpecial() {
        int usernameNumber = 0;
        String usernameBase = "not special";
        try {
            while (UserRepository.getUserByUsername(usernameBase + usernameNumber) != null) {
                usernameNumber++;
            }

            String username = usernameBase + usernameNumber;
            String password = "password1";

            enterFields(username, password, password);

            Scene currentScene = stage.getScene();
            clickOn("#signupButton");
            UserRepository.deleteUserByUsername(username);
            assertEquals(currentScene, stage.getScene());

        } catch (Exception e) {
            UserRepository.deleteUserByUsername(usernameBase + usernameNumber);
            e.printStackTrace();
            // Test failed if it ended up in the catch block
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Test Password And Confirm Not Equal")
    void PassWordAndConfirmNotEqual() {
        int usernameNumber = 0;
        String usernameBase = "mismatch";
        try {
            while (UserRepository.getUserByUsername(usernameBase + usernameNumber) != null) {
                usernameNumber++;
            }

            String username = usernameBase + usernameNumber;
            String password = "password1!";

            enterFields(username, password, "OOPS! WRONG PASSWORD LOL");

            Scene currentScene = stage.getScene();
            clickOn("#signupButton");
            UserRepository.deleteUserByUsername(username);
            assertEquals(currentScene, stage.getScene());

        } catch (Exception e) {
            UserRepository.deleteUserByUsername(usernameBase + usernameNumber);
            e.printStackTrace();
            // Test failed if it ended up in the catch block
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Test Working Username And Password")
    void testWorking() {
        int usernameNumber = 0;
        String usernameBase = "good";
        try {
            while (UserRepository.getUserByUsername(usernameBase + usernameNumber) != null) {
                usernameNumber++;
            }

            String username = usernameBase + usernameNumber;
            String password = "password1!";

            enterFields(username, password, password);

            Scene currentScene = stage.getScene();
            clickOn("#signupButton");
            UserRepository.deleteUserByUsername(username);
            assertNotEquals(currentScene, stage.getScene());

            clickOn("#signupButton");
            assertNotEquals(currentScene, stage.getScene());

        } catch (Exception e) {
            UserRepository.deleteUserByUsername(usernameBase + usernameNumber);
            e.printStackTrace();
            // Test failed if it ended up in the catch block
            assertTrue(false);
        }
    }

    @Test
    @DisplayName("Check Return To Login Button")
    void returnTest() {

        Scene currentScene = stage.getScene();
        clickOn("#returnButton");
        assertNotEquals(currentScene, stage.getScene());

        clickOn("#signupButton");
        assertEquals(currentScene, stage.getScene());
    }

    void enterFields(String username, String password, String confirm) {
        clickOn("#usernameField");
        write(username);
        clickOn("#passwordField");
        write(password);
        clickOn("#confirmField");
        write(confirm);
    }
}
