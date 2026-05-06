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
class LoginTest extends ApplicationTest {

    private static Stage stage;
    private static SceneManager sceneManager;

    @BeforeAll
    static void bootToolkit() throws Exception{
        stage = FxToolkit.registerPrimaryStage();
        SceneManager.init(stage);
        sceneManager = SceneManager.getInstance();
    }

    @BeforeEach
    void setup() throws Exception {
        runOnFxThreadAndWait( () -> {
            System.setProperty("app.db.url", "jdbc:sqlite::memory");
            // Username and Password are impossible to create naturally
            // Won't interfere with any real users
            UserRepository.insertUser(new User(-1, "x", "password"));
            sceneManager.uncacheAll();
            sceneManager.navigateTo(SceneType.LOGIN);
            stage.show();
        });
    }

    @AfterEach
    void deleteUser(){
        UserRepository.deleteUserByUsername("x");
        sceneManager.uncacheAll();
    }

    @Test
    @DisplayName("Test Login")
    void loginTest() {
        Scene loginScene = stage.getScene();

        // Test Empty Username and Password
        clickOn("#loginButton");
        assertEquals(loginScene, stage.getScene());

        // Test Incorrect Username and Password
        clickOn("#usernameField");
        write("No");
        clickOn("#passwordField");
        write("awful password 67 Skibidi Toilet Ohio Rizz Hawk Tuah TUNG TUNG TUNG");
        clickOn("#loginButton");
        assertEquals(loginScene, stage.getScene());

        // Test correct Username and Password
        clickOn("#usernameField");
        write("x");
        clickOn("#passwordField");
        write("password");
        clickOn("#loginButton");
        assertNotEquals(loginScene, stage.getScene());
    }


    @Test
    @DisplayName("Test sign up button works")
    void signUpTest() {
        // Test sign up button navigates away from login scene
        Scene loginScene = stage.getScene();
        clickOn("#signupButton");
        assertNotEquals(loginScene, stage.getScene());

        // Test return to same login scene
        clickOn("#returnButton");
        assertEquals(loginScene, stage.getScene());
    }

    private void runOnFxThreadAndWait(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timed out waiting for FX thread");
        }
    }
}
