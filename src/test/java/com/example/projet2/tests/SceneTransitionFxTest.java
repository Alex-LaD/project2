package com.example.projet2.tests;

import com.example.projet2.SceneManager;
import com.example.projet2.SceneType;
import com.example.projet2.Transaction;
import com.example.projet2.TransactionModel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;


import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FX scene transition tests")
class SceneTransitionFxTest extends ApplicationTest {

    private static Stage stage;
    private static SceneManager sceneManager;

    @BeforeAll
    static void bootToolkit() throws Exception {
        stage = FxToolkit.registerPrimaryStage();
        SceneManager.init(stage);
        sceneManager = SceneManager.getInstance();
    }

    @BeforeEach
    void resetState() throws Exception {
        runOnFxThreadAndWait(() -> {
            TransactionModel model = TransactionModel.getInstance();
            model.getTransactions().clear();
            model.setSelectedTransaction(null);
            model.setCurrentUser(null);
            sceneManager.uncacheAll();
            stage.show();
        });
    }

    @Test
    @DisplayName("Login -> Signup -> Login")
    void loginSignupLoginTransition() throws Exception {
        navigateTo(SceneType.LOGIN);
        Scene loginScene = stage.getScene();
        assertNotNull(lookup("#loginButton").queryButton());

        clickOn("#signupButton");
        WaitForAsyncUtils.waitForFxEvents();
        assertNotSame(loginScene, stage.getScene());
        assertNotNull(lookup("#returnButton").queryButton());

        clickOn("#returnButton");
        WaitForAsyncUtils.waitForFxEvents();
        assertSame(loginScene, stage.getScene(), "Le retour utilise la scene LOGIN deja en cache.");
        assertNotNull(lookup("#loginButton").queryButton());
    }

    @Test
    @DisplayName("Dashboard -> Add Transaction -> Dashboard")
    void dashboardTransactionDashboardTransition() throws Exception {
        navigateTo(SceneType.DASHBOARD);
        Scene dashboardScene = stage.getScene();
        assertTrue(lookup("Personal Budget Dashboard").tryQuery().isPresent());

        Node addTransactionButton = lookup("Add Transaction")
                .nth(1)
                .query();

        clickOn(addTransactionButton);
        WaitForAsyncUtils.waitForFxEvents();
        assertNotSame(dashboardScene, stage.getScene());
        assertTrue(lookup("Add Transaction").tryQuery().isPresent());

        clickOn("Back to Dashboard");
        WaitForAsyncUtils.waitForFxEvents();
        assertSame(dashboardScene, stage.getScene(), "Le retour utilise la scene DASHBOARD deja en cache.");
        assertTrue(lookup("Personal Budget Dashboard").tryQuery().isPresent());
    }

    @Test
    @DisplayName("Dashboard -> Transaction details -> Dashboard")
    void dashboardDetailDashboardTransition() throws Exception {
        addSampleTransactionToModel();
        navigateTo(SceneType.DASHBOARD);
        Scene dashboardScene = stage.getScene();

        clickOn("View Transactions");
        WaitForAsyncUtils.waitForFxEvents();
        assertNotSame(dashboardScene, stage.getScene());
        assertTrue(lookup("My Transactions").tryQuery().isPresent());

        clickOn("Back");
        WaitForAsyncUtils.waitForFxEvents();
        assertSame(dashboardScene, stage.getScene(), "Le bouton Back doit revenir au dashboard.");
        assertTrue(lookup("Personal Budget Dashboard").tryQuery().isPresent());
    }

    @Test
    @DisplayName("Transaction details -> Edit transaction -> Transaction details")
    void detailEditDetailTransition() throws Exception {
        addSampleTransactionToModel();
        navigateTo(SceneType.TRANSACTION_DETAIL);
        Scene detailScene = stage.getScene();
        assertTrue(lookup("My Transactions").tryQuery().isPresent());

        clickOn("Lunch");
        clickOn("Edit Selected");
        WaitForAsyncUtils.waitForFxEvents();
        assertNotSame(detailScene, stage.getScene());
        assertTrue(lookup("Edit Transaction").tryQuery().isPresent());

        clickOn("Cancel");
        WaitForAsyncUtils.waitForFxEvents();
        assertSame(detailScene, stage.getScene(), "Cancel doit revenir a la scene de detail.");
        assertTrue(lookup("My Transactions").tryQuery().isPresent());
    }

    @Test
    @DisplayName("SceneFactory peut construire toutes les scenes")
    void everySceneTypeCanBeDisplayed() throws Exception {
        addSampleTransactionToModel();

        for (SceneType sceneType : SceneType.values()) {
            if (sceneType == SceneType.EDIT_TRANSACTION) {
                TransactionModel.getInstance().setSelectedTransaction(
                        TransactionModel.getInstance().getTransactions().get(0)
                );
            }
            navigateTo(sceneType);
            assertNotNull(stage.getScene(), sceneType + " doit produire une scene JavaFX non nulle.");
            assertNotNull(stage.getScene().getRoot(), sceneType + " doit avoir un root node.");
        }
    }

    private void addSampleTransactionToModel() {
        TransactionModel.getInstance().addTransaction(
                new Transaction(1, 1, -12.50, "Food", "Lunch", LocalDate.now())
        );
    }

    private void navigateTo(SceneType sceneType) throws Exception {
        runOnFxThreadAndWait(() -> sceneManager.navigateTo(sceneType));
        WaitForAsyncUtils.waitForFxEvents();
    }

    private static void runOnFxThreadAndWait(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        if (!latch.await(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timed out waiting for FX thread");
        }
    }
}
