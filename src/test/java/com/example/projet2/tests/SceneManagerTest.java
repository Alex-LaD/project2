package com.example.projet2.tests;

import com.example.projet2.SceneManager;
import com.example.projet2.SceneType;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.junit.jupiter.api.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Scene Manager Tests")
class SceneManagerTest {
    static SceneManager sceneManager;
    static Stage stage;

    @BeforeAll
    static void init() throws InterruptedException {
        CountDownLatch initLatch = new CountDownLatch(2);

        try {
            Platform.startup(initLatch::countDown);
        } catch (IllegalStateException ignored) {
            // JavaFX already started
        }

        Platform.runLater(() -> {
            stage = new Stage();
            SceneManager.init(stage);
            sceneManager = SceneManager.getInstance();
            initLatch.countDown();
        });

        if (!initLatch.await(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("Stage creation timed out");
        }
    }

    @BeforeEach
    void reset() {
        CountDownLatch resetLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stage.setScene(new Scene(new VBox()));
            sceneManager.uncacheAll();
            resetLatch.countDown();
        });
    }

    @Test
    @DisplayName("Can navigate to different scenes")
    void navigateToTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> error = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                sceneManager.navigateTo(SceneType.LOGIN);
                assertNotNull(stage.getScene());
                Scene oldScene = stage.getScene();
                sceneManager.navigateTo(SceneType.SIGNUP);
                assertNotEquals(oldScene, stage.getScene());
                assertNotNull(stage.getScene());
            } catch (Throwable t) {
                error.set(t);
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("navigateToTest timed out");
        }
        if (error.get() != null) {
            throw new RuntimeException(error.get());
        }
    }

    @Test
    @DisplayName("Can navigate to different scenes using the cache")
    void cacheAndUncache() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> error = new AtomicReference<>();

        Platform.runLater(() -> {
            try {

                assertNotNull(stage.getScene());

                sceneManager.navigateTo(SceneType.SIGNUP);
                Scene signupScene = stage.getScene();

                sceneManager.navigateTo(SceneType.LOGIN);
                Scene loginScene = stage.getScene();
                assertNotEquals(signupScene, loginScene);
                sceneManager.navigateTo(SceneType.SIGNUP);
                assertEquals(signupScene, stage.getScene());

                sceneManager.uncache(SceneType.LOGIN);
                sceneManager.navigateTo(SceneType.LOGIN);
                assertNotEquals(loginScene, stage.getScene());

            } catch (Throwable t) {
                error.set(t);
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("navigateUsingCache timed out");
        }
        if (error.get() != null) {
            throw new RuntimeException(error.get());
        }
    }
}
