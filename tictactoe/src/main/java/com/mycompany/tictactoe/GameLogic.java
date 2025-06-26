package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLogic {
    private JButton[][] buttons;
    private boolean vsComputer;
    private int difficulty;
    private String currentPlayer;
    private GamePanel gamePanel;
    private JLabel turnLabel;
    private Timer computerMoveTimer;
    
    public GameLogic(boolean vsComputer, int difficulty) {
        this(vsComputer, difficulty, true);
    }
    
    public GameLogic(boolean vsComputer, int difficulty, boolean playerFirst) {
        this.vsComputer = vsComputer;
        this.difficulty = difficulty;
        this.currentPlayer = playerFirst ? "X" : "O";
    }
    
    public void setButtons(JButton[][] buttons) {
        this.buttons = buttons;
    }
    
    public JButton[][] getButtons() {
        return buttons;
    }
    
    public void setTurnLabel(JLabel turnLabel) {
        this.turnLabel = turnLabel;
        updateTurnLabel();
    }
    
    private void updateTurnLabel() {
        if (turnLabel != null) {
            turnLabel.setText("Current Turn: " + currentPlayer);
        }
    }
    
    public void makeMove(int row, int col, String player) {
        buttons[row][col].setText(player);
        currentPlayer = player.equals("X") ? "O" : "X";
        updateTurnLabel();
    }
    
    public String getCurrentPlayer() {
        return currentPlayer;
    }
    
    public boolean checkGameOver() {
        for (int row = 0; row < 3; row++) {
            if (!buttons[row][0].getText().isEmpty() &&
                buttons[row][0].getText().equals(buttons[row][1].getText()) &&
                buttons[row][0].getText().equals(buttons[row][2].getText())) {
                endGame(buttons[row][0].getText() + " wins!");
                return true;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (!buttons[0][col].getText().isEmpty() &&
                buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                buttons[0][col].getText().equals(buttons[2][col].getText())) {
                endGame(buttons[0][col].getText() + " wins!");
                return true;
            }
        }

        if (!buttons[0][0].getText().isEmpty() &&
            buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[0][0].getText().equals(buttons[2][2].getText())) {
            endGame(buttons[0][0].getText() + " wins!");
            return true;
        }

        if (!buttons[0][2].getText().isEmpty() &&
            buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[0][2].getText().equals(buttons[2][0].getText())) {
            endGame(buttons[0][2].getText() + " wins!");
            return true;
        }

        if (isBoardFull()) {
            endGame("It's a draw!");
            return true;
        }

        return false;
    }
    
    public boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void scheduleComputerMove() {
        if (computerMoveTimer != null && computerMoveTimer.isRunning()) {
            computerMoveTimer.stop();
        }
        
        computerMoveTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computerMoveTimer.stop();
                if (!isBoardFull() && !checkGameOver()) {
                    int[] computerMove = getComputerMove();
                    if (computerMove != null) {
                        JButton computerBtn = buttons[computerMove[0]][computerMove[1]];
                        computerBtn.setText("O");
                        makeMove(computerMove[0], computerMove[1], "O");
                        checkGameOver();
                        gamePanel.onComputerMoveComplete();
                    }
                }
            }
        });
        computerMoveTimer.setRepeats(false);
        computerMoveTimer.start();
    }
    
    private int[] getComputerMove() {
        if (difficulty == 1) {
            return getRandomMove();
        }
        else if (difficulty == 2) {
            int[] winningMove = findWinningMove("O");
            if (winningMove != null) return winningMove;
            
            int[] blockingMove = findWinningMove("X");
            if (blockingMove != null) return blockingMove;
            
            return getRandomMove();
        }
        else {
            return findBestMove();
        }
    }
    
    private int[] getRandomMove() {
        int row, col;
        do {
            row = (int) (Math.random() * 3);
            col = (int) (Math.random() * 3);
        } while (!buttons[row][col].getText().isEmpty());
        
        return new int[]{row, col};
    }
    
    private int[] findWinningMove(String player) {
        for (int row = 0; row < 3; row++) {
            if (buttons[row][0].getText().equals(player) && buttons[row][1].getText().equals(player) && buttons[row][2].getText().isEmpty()) {
                return new int[]{row, 2};
            }
            if (buttons[row][0].getText().equals(player) && buttons[row][2].getText().equals(player) && buttons[row][1].getText().isEmpty()) {
                return new int[]{row, 1};
            }
            if (buttons[row][1].getText().equals(player) && buttons[row][2].getText().equals(player) && buttons[row][0].getText().isEmpty()) {
                return new int[]{row, 0};
            }
        }
        
        for (int col = 0; col < 3; col++) {
            if (buttons[0][col].getText().equals(player) && buttons[1][col].getText().equals(player) && buttons[2][col].getText().isEmpty()) {
                return new int[]{2, col};
            }
            if (buttons[0][col].getText().equals(player) && buttons[2][col].getText().equals(player) && buttons[1][col].getText().isEmpty()) {
                return new int[]{1, col};
            }
            if (buttons[1][col].getText().equals(player) && buttons[2][col].getText().equals(player) && buttons[0][col].getText().isEmpty()) {
                return new int[]{0, col};
            }
        }
        
        if (buttons[0][0].getText().equals(player) && buttons[1][1].getText().equals(player) && buttons[2][2].getText().isEmpty()) {
            return new int[]{2, 2};
        }
        if (buttons[0][0].getText().equals(player) && buttons[2][2].getText().equals(player) && buttons[1][1].getText().isEmpty()) {
            return new int[]{1, 1};
        }
        if (buttons[1][1].getText().equals(player) && buttons[2][2].getText().equals(player) && buttons[0][0].getText().isEmpty()) {
            return new int[]{0, 0};
        }
        
        if (buttons[0][2].getText().equals(player) && buttons[1][1].getText().equals(player) && buttons[2][0].getText().isEmpty()) {
            return new int[]{2, 0};
        }
        if (buttons[0][2].getText().equals(player) && buttons[2][0].getText().equals(player) && buttons[1][1].getText().isEmpty()) {
            return new int[]{1, 1};
        }
        if (buttons[1][1].getText().equals(player) && buttons[2][0].getText().equals(player) && buttons[0][2].getText().isEmpty()) {
            return new int[]{0, 2};
        }
        
        return null;
    }
    
    private int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    buttons[row][col].setText("O");
                    int score = minimax(0, false);
                    buttons[row][col].setText("");
                    
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }
        
        return bestMove;
    }
    
    private int minimax(int depth, boolean isMaximizing) {
        if (checkWin("O")) return 10 - depth;
        if (checkWin("X")) return depth - 10;
        if (isBoardFull()) return 0;
        
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (buttons[row][col].getText().isEmpty()) {
                        buttons[row][col].setText("O");
                        int score = minimax(depth + 1, false);
                        buttons[row][col].setText("");
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (buttons[row][col].getText().isEmpty()) {
                        buttons[row][col].setText("X");
                        int score = minimax(depth + 1, true);
                        buttons[row][col].setText("");
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }
    
    private boolean checkWin(String player) {
        for (int row = 0; row < 3; row++) {
            if (buttons[row][0].getText().equals(player) &&
                buttons[row][1].getText().equals(player) &&
                buttons[row][2].getText().equals(player)) {
                return true;
            }
        }
        
        for (int col = 0; col < 3; col++) {
            if (buttons[0][col].getText().equals(player) &&
                buttons[1][col].getText().equals(player) &&
                buttons[2][col].getText().equals(player)) {
                return true;
            }
        }
        
        if (buttons[0][0].getText().equals(player) &&
            buttons[1][1].getText().equals(player) &&
            buttons[2][2].getText().equals(player)) {
            return true;
        }
        
        if (buttons[0][2].getText().equals(player) &&
            buttons[1][1].getText().equals(player) &&
            buttons[2][0].getText().equals(player)) {
            return true;
        }
        
        return false;
    }
    
    private void endGame(String result) {
        gamePanel.showGameResult(result);
    }
    
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}