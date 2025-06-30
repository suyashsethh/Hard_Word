package org.example.wordle;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WordleGameUI extends Application {

    private final GameService gameService = new GameService(new WordRepository());
    private int guessCount = 0;
    private final int maxGuesses = 6;
    private final VBox guessBox = new VBox(10);

    @Override
    public void start(Stage primaryStage) {
        TextField input = new TextField();
        input.setPromptText("Enter 4-letter word");
        input.setPrefColumnCount(4);
        input.setFont(Font.font(18));

        Button submit = new Button("Submit");
        submit.setFont(Font.font(16));

        HBox inputArea = new HBox(10, input, submit);
        inputArea.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, guessBox, inputArea);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20;");

        submit.setOnAction(e -> {
            String guess = input.getText().toLowerCase().trim();

            if (guess.length() != 4 || !gameService.isValidWord(guess)) {
                showAlert("Invalid guess", "Please enter a valid 4-letter word.");
                return;
            }

            String result = gameService.checkGuess(guess);
            addGuessRow(guess, result);
            guessCount++;
            input.clear();

            if ("🟩🟩🟩🟩".equals(result)) {
                showAlert("You Win!", "Correct word: " + guess);
                input.setDisable(true);
                submit.setDisable(true);
            } else if (guessCount >= maxGuesses) {
                showAlert("Game Over", "The word was: " + gameService.getAnswer());
                input.setDisable(true);
                submit.setDisable(true);
            }
        });

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Wordle FX");
        primaryStage.show();
    }

    private void addGuessRow(String guess, String result) {
        GridPane row = new GridPane();
        row.setHgap(10);
        row.setAlignment(Pos.CENTER);

        for (int i = 0; i < 4; i++) {
            Label letter = new Label("" + guess.charAt(i));
            letter.setFont(Font.font(20));
            letter.setPrefSize(40, 40);
            letter.setAlignment(Pos.CENTER);

            String emoji = result.charAt(i) + "";
            if (emoji.equals("🟩")) {
                letter.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-alignment: center; -fx-border-color: black;");
            } else if (emoji.equals("🟨")) {
                letter.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-alignment: center; -fx-border-color: black;");
            } else {
                letter.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-alignment: center; -fx-border-color: black;");
            }
            row.add(letter, i, 0);
        }

        guessBox.getChildren().add(row);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
