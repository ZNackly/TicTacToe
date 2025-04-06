package com.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.jpg")));
    private static final int SIZE = 3;
    private Button[][] buttons = new Button[SIZE][SIZE];
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private Button restartButton;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane grid = createGrid();
        restartButton = createRestartButton();

        root.setCenter(grid);
        root.setBottom(restartButton);
        BorderPane.setAlignment(restartButton, Pos.CENTER);
        BorderPane.setMargin(restartButton, new Insets(10));

        primaryStage.setTitle("Крестики-нолики");
        primaryStage.setScene(new Scene(root, 300, 350));
        primaryStage.show();
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button btn = new Button();
                btn.setMinSize(80, 80);
                btn.setStyle("-fx-font-size: 24;");
                buttons[i][j] = btn;
                grid.add(btn, j, i);
                final int row = i;
                final int col = j;
                btn.setOnAction(e -> handleButtonClick(row, col));
            }
        }
        return grid;
    }

    private Button createRestartButton() {
        Button btn = new Button("Новая игра");
        btn.setStyle("-fx-font-size: 16; -fx-padding: 10 20;");
        btn.setOnAction(e -> resetGame());
        return btn;
    }

    private void resetGame() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = 'X';
        gameOver = false;
    }

    private void handleButtonClick(int row, int col) {
        Button btn = buttons[row][col];
        if (gameOver || !btn.getText().isEmpty()) return;

        btn.setText(String.valueOf(currentPlayer));
        if (checkWin(row, col)) {
            showAlert("Игрок " + currentPlayer + " победил!");
            gameOver = true;
        } else if (isBoardFull()) {
            showAlert("Ничья!");
            gameOver = true;
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private boolean checkWin(int row, int col) {
        // Проверка строки
        if (buttons[row][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[row][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[row][2].getText().equals(String.valueOf(currentPlayer))) return true;

        // Проверка столбца
        if (buttons[0][col].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][col].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][col].getText().equals(String.valueOf(currentPlayer))) return true;

        // Проверка диагоналей
        if (row == col &&
                buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][2].getText().equals(String.valueOf(currentPlayer))) return true;

        if (row + col == 2 &&
                buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][0].getText().equals(String.valueOf(currentPlayer))) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (Button[] row : buttons) {
            for (Button btn : row) {
                if (btn.getText().isEmpty()) return false;
            }
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Игра окончена");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
