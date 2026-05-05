package com.example.projet2.sceneControllers;

import com.example.projet2.*;

import com.example.projet2.repository.TransactionRepository;
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

    Map<String, String> currencies;

    // Window dimensions in pixels
    private static final int SCENE_WIDTH = 450;
    private static final int SCENE_HEIGHT = 100;

    private static final double HBOX_SPACING = 20;
    private static final double VBOX_SPACING = 15;
    private static final double BUTTON_SPACING = 10;

    public Scene buildScene() {

        currencies = new HashMap<>(); //getCurrencies();
        currencies.put("USD", "United States Dollar");
        currencies.put("EUR", "Euro");
        currencies.put("Coolness Coins", "Hi");

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

        convert.setOnAction(_ -> { convert(comboBox.getValue()); });

        VBox root = new VBox(hBox, buttonBox);
        root.setSpacing(VBOX_SPACING);
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        return scene;
    }

    private void handleReturn() {
        SceneManager.getInstance().navigateTo(SceneType.DASHBOARD);
    }

    private void convert(String newCurrencyFull) {
        User currentUser = TransactionModel.getInstance().getCurrentUser();
        String oldCurrency = currentUser.getCurrency();
        String newCurrencyShort = extractKeyFromValue(newCurrencyFull);
        if (newCurrencyShort == null) {
            return;
        }

        double conversionRate = 2;//getConversion(oldCurrency, newCurrencyShort);
        List<Transaction> transactions = TransactionModel.getInstance().getTransactions();
        for (Transaction transaction : transactions) {
            transaction.setAmount(transaction.getAmount() * conversionRate);
            TransactionRepository.updateTransaction(transaction);
            TransactionModel.getInstance().updateTransaction(transaction, transaction);
        }

        currentUser.setCurrency(newCurrencyShort);
        UserRepository.updateCurrency(currentUser.getUsername(), newCurrencyShort);
        SceneManager.getInstance().uncache(SceneType.TRANSACTION_DETAIL);
    }

    private String extractKeyFromValue(String value) {
        if (currencies.containsValue(value)) {
            for (String key : currencies.keySet()) {
                if (currencies.get(key).equals(value)) {
                    return key;
                }
            }
        }
        return null;
    }

    private double getConversion(String oldCurrency, String newCurrency) {
        if (oldCurrency.equals(newCurrency)) {
            return 1;
        }

        try {
            String conversion = getRequest("https://api.exchangerate.host/convert?" +
                                            "access_key=" + APIKey +
                                            "&from=" + oldCurrency +
                                            "&to=" + newCurrency +
                                            "&amount=" + 1); // Get conversion rate so calculations can be done locally.

            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> map = mapper.readValue(conversion, new TypeReference<HashMap<String, Object>>(){});
            return mapper.convertValue(map.get("result"), new TypeReference<Double>(){});

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
