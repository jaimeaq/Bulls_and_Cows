package bullscows.controller;

import bullscows.model.GameState;
import bullscows.model.Grade;
import bullscows.model.SecretCode;
import bullscows.view.ConsoleView;
import bullscows.view.View;

import java.util.Scanner;

public class Game {
    private final Scanner scanner;
    private SecretCode secretCode;
    private Grade grade;
    private final View view;
    private GameState gameState;
    private boolean isOver;
    private int turn;
    private String error;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.secretCode = new SecretCode();
        this.grade = new Grade();
        this.gameState = GameState.GENERATING_SECRET_CODE;
        this.view = new ConsoleView();
        isOver = false;
        turn = 1;
    }

    public void start() {
        while (!isOver) {
            switch (gameState) {
                case GENERATING_SECRET_CODE:
                    generateSecretCode();
                    gameState = gameState == GameState.ERROR ? GameState.ERROR : GameState.RUNNING;
                    break;
                case RUNNING:
                    running();
                    gameState = gameState == GameState.ERROR ? GameState.ERROR : GameState.ENDING;
                    break;
                case ENDING:
                    isOver = true;
                    break;
                case ERROR:
                    view.displayError("Error: " + error + ".");
                    isOver = true;
                    break;
            }
        }
        scanner.close();
    }

    private void generateSecretCode() {
        while(!secretCode.isValid()) {

            view.displayMessage("Input the length of the secret code:");
            String scLengthString = scanner.nextLine();
            if(!isNumeric(scLengthString)) {
                showError(scLengthString + "isn't a valid number");
                return;
            }
            int scLength = Integer.parseInt(scLengthString);

            if(scLength <= 0) {
                showError("Length must be greater than 0");
                return;
            }

            view.displayMessage("Input the number of possible symbols in the code:");
            String scNumberOfSymbolsString = scanner.nextLine();
            if(!isNumeric(scNumberOfSymbolsString)) {
                showError(scNumberOfSymbolsString + "isn't a valid number");
                return;
            }
            int scNumberOfSymbols = Integer.parseInt(scNumberOfSymbolsString);

            if(scNumberOfSymbols > secretCode.getCharacters().length()) {
                showError("maximum number of possible symbols in the code is " + secretCode.getCharacters().length());
                return;
            }

            if(scLength > scNumberOfSymbols) {
                showError("it's not possible to generate a code with a length of " + scLength + " with " +
                        scNumberOfSymbols + " unique symbols");
                return;
            }

            secretCode = secretCode.generate(scLength, scNumberOfSymbols);
        }
        view.displayMessage("The secret is prepared: " + secretCode.starred() + " " + secretCode.charRangeToString());
    }

    private void running() {
        String guess;
        view.displayMessage("Okay, let's start a game!");
        while(!grade.isPerfect) {
            view.displayMessage("Turn " + turn + ":");
            guess = scanner.nextLine();
            grade = grade.evalGuess(secretCode, guess);
            view.displayMessage(grade.toString());
            turn++;
        }
        view.displayMessage("Congratulations! You guessed the secret code.");
    }

    private boolean isNumeric(String scLengthString) {
        try {
            Integer.parseInt(scLengthString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showError(String description) {
        gameState = GameState.ERROR;
        error = description;
    }
}
