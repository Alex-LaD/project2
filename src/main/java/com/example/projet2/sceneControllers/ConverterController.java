package com.example.projet2.sceneControllers;

import com.example.projet2.SceneManager;
import com.example.projet2.SceneType;
import com.example.projet2.repository.TransactionRepository;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ConverterController {

    private static final String APIKey = "d6511f794deda2c3503d483b0672bb8c";

    // Window dimensions in pixels
    private static final int SCENE_WIDTH = 200;
    private static final int SCENE_HEIGHT = 100;

    private static final double HBOX_SPACING = 5;
    private static final double VBOX_SPACING = 15;

    public Scene buildScene() {
        Label label = new Label("Select currency: ");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("https://api.exchangerate.host/list?access_key=" + APIKey);
        HBox hBox = new HBox(label, comboBox);
        hBox.setSpacing(HBOX_SPACING);

        Button rtrn = new Button("Return to Dashboard");
        Button convert = new Button("Convert");
        HBox buttonBox = new HBox(rtrn, convert);

        rtrn.setOnAction(_ -> { handleReturn(); });

        convert.setOnAction(_ -> { convert(); });

        VBox root = new VBox(hBox, buttonBox);
        root.setSpacing(VBOX_SPACING);
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        return scene;
    }

    private void handleReturn() {
        SceneManager.getInstance().navigateTo(SceneType.DASHBOARD);
    }

    private void convert() {
        return;
    }
}
