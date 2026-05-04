package com.example.projet2.sceneControllers;

import com.example.projet2.SceneManager;
import com.example.projet2.SceneType;

import com.example.projet2.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ConverterController {

    private static final String APIKey = "d6511f794deda2c3503d483b0672bb8c";

    // Window dimensions in pixels
    private static final int SCENE_WIDTH = 300;
    private static final int SCENE_HEIGHT = 100;

    private static final double HBOX_SPACING = 5;
    private static final double VBOX_SPACING = 15;
    private static final double BUTTON_SPACING = 10;

    public Scene buildScene() {

        Map<String, String> currencies = new HashMap<>(); //getCurrencies();
        currencies.put("USD", "United States Dollar");
        currencies.put("EUR", "Euro");
        currencies.put("MXN", "Mexican Peso");

        List<String> currencyValues = new ArrayList<>(currencies.values().stream().toList());
        currencyValues.sort(String.CASE_INSENSITIVE_ORDER);

        Label label = new Label("Select currency: ");
        ComboBox<String> comboBox = new ComboBox<>();

        for (String currency : currencyValues) {
            comboBox.getItems().add(currency);
        }
        HBox hBox = new HBox(label, comboBox);
        hBox.setSpacing(HBOX_SPACING);

        Button rtrn = new Button("Return to Dashboard");
        Button convert = new Button("Convert");
        HBox buttonBox = new HBox(rtrn, convert);
        buttonBox.setSpacing(BUTTON_SPACING);

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

    private HashMap<String, String> getCurrencies() {
        try {
            String json = getRequest("https://api.exchangerate.host/list?access_key=" + APIKey);

            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> jsonMap = mapper.readValue(json, new TypeReference<HashMap<String, Object>>(){});

            return mapper.convertValue(jsonMap.get("currencies"), new TypeReference<HashMap<String, String>>(){});

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
